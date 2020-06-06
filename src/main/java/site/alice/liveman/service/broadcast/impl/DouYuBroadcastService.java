/*
 * <Alice LiveMan>
 * Copyright (C) <2018>  <NekoSunflower>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package site.alice.liveman.service.broadcast.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alice.liveman.model.AccountInfo;
import site.alice.liveman.service.broadcast.BroadcastService;
import site.alice.liveman.utils.HttpRequestUtil;
import site.alice.liveman.utils.SecurityUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DouYuBroadcastService implements BroadcastService {

    public static final String SESSION_ATTRIBUTE = "douyu-qrcode";
    public static final String URL_GENERATE_CODE = "https://passport.douyu.com/scan/generateCode";
    public static final String URL_CHECK_CODE    = "https://passport.douyu.com/lapi/passport/qrcode/check?time=%s&code=%s";
    public static final String URL_ROOM_INFO     = "https://mp.douyu.com/live/room/getroominfo";
    public static final String URL_MEMBER_INFO   = "https://www.douyu.com/lapi/member/api/getInfo?client_type=0";
    public static final String URL_OPEN_SHOW     = "https://www.douyu.com/room/my/first_show";
    public static final String URL_CLOSE_SHOW    = "https://www.douyu.com/room/my/close_show";
    public static final String URL_GET_RTMP      = "https://www.douyu.com/room/my/get_live_code";

    @Autowired
    private HttpSession session;

    @Override
    public boolean isMatch(String accountSite) {
        return "douyu".equals(accountSite);
    }

    @Override
    public String getBroadcastAddress(AccountInfo accountInfo) throws Exception {
        if (accountInfo.getRtmpUrlRefreshTime() != null && accountInfo.getRtmpUrl() != null) {
            if (System.currentTimeMillis() - accountInfo.getRtmpUrlRefreshTime() < 10000) {
                return accountInfo.getRtmpUrl();
            }
        }
        if (accountInfo.readCookies() == null) {
            accountInfo.setDisable(true);
            throw new RuntimeException("账户Cookies为空！提示：如果是自动转播请检查【我的账号】中【自动保存Cookies】选项是否已开启，如果是手动认领请尝试重新登录或联系管理员。");
        }
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("referer", "https://www.douyu.com/" + accountInfo.getRoomId());
        requestProperties.put("x-requested-with", "XMLHttpRequest");
        Matcher matcher = Pattern.compile("acf_ccn=(.{32})").matcher(accountInfo.readCookies());
        String csrfToken = "";
        if (matcher.find()) {
            csrfToken = matcher.group(1);
        }
        String startLiveJson = HttpRequestUtil.downloadUrl(new URI(URL_OPEN_SHOW), accountInfo.readCookies(), "ctn=" + csrfToken + "&notshowtip=1", requestProperties, StandardCharsets.UTF_8);
        JSONObject startLiveObject = JSON.parseObject(startLiveJson);
        JSONObject rtmpObject;
        if (startLiveObject.getInteger("error") == 0 || startLiveObject.getString("msg").contains("重复")) {
            Thread.sleep(1000);
            String getRtmpJson = HttpRequestUtil.downloadUrl(new URI(URL_GET_RTMP), accountInfo.readCookies(), "ctn=" + csrfToken + "&room_id=" + accountInfo.getRoomId(), requestProperties, StandardCharsets.UTF_8);
            rtmpObject = JSON.parseObject(getRtmpJson).getJSONObject("rtmp_send");
        } else {
            accountInfo.setDisable(true);
            if (startLiveObject.getInteger("error") == -99) {
                throw new RuntimeException("开启斗鱼直播间失败，账号登录凭据已失效。");
            } else {
                throw new RuntimeException("开启斗鱼直播间失败" + startLiveObject);
            }
        }
        String addr = rtmpObject.getString("rtmp_url");
        String code = rtmpObject.getString("rtmp_val");
        String rtmpUrl;
        if (!addr.endsWith("/") && !code.startsWith("/")) {
            rtmpUrl = addr + "/" + code;
        } else {
            rtmpUrl = addr + code;
        }
        accountInfo.setRtmpUrlRefreshTime(System.currentTimeMillis());
        accountInfo.setRtmpUrl(rtmpUrl);
        return rtmpUrl;
    }

    @Override
    public void setBroadcastSetting(AccountInfo accountInfo, String title, Integer areaId) {
        log.warn("setBroadcastSetting():暂不支持斗鱼TV的直播间设定");
    }

    @Override
    public String getBroadcastRoomId(AccountInfo accountInfo) throws Exception {
        if (StringUtils.isEmpty(accountInfo.getRoomId())) {
            String liveInfoJson = HttpRequestUtil.downloadUrl(new URI(URL_ROOM_INFO), accountInfo.readCookies(), Collections.emptyMap(), StandardCharsets.UTF_8);
            if (liveInfoJson.contains("申请主播")) {
                throw new RuntimeException("此账号未开通斗鱼直播间");
            }
            JSONObject liveInfoObject = JSON.parseObject(liveInfoJson);
            if (liveInfoObject.get("data") instanceof JSONObject) {
                JSONObject data = liveInfoObject.getJSONObject("data");
                String roomId = data.getString("roomID");
                accountInfo.setRoomId(roomId);
                String memberInfoJson = HttpRequestUtil.downloadUrl(new URI(URL_MEMBER_INFO), accountInfo.readCookies(), Collections.emptyMap(), StandardCharsets.UTF_8);
                JSONObject memberInfoObj = JSON.parseObject(memberInfoJson);
                if (memberInfoObj.getInteger("error") == 0) {
                    JSONObject msg = memberInfoObj.getJSONObject("msg");
                    accountInfo.setNickname(msg.getJSONObject("info").getString("nn"));
                    accountInfo.setUid(msg.getString("uid"));
                    accountInfo.setAccountId(accountInfo.getNickname());
                }
            } else {
                throw new RuntimeException("获取斗鱼直播间信息失败" + liveInfoObject);
            }
        }
        accountInfo.setRoomUrl("https://www.douyu.com/" + accountInfo.getRoomId());
        return accountInfo.getRoomId();
    }

    @Override
    public void stopBroadcast(AccountInfo accountInfo, boolean stopOnPadding) {

    }

    @Override
    public String getBroadcastCookies(String username, String password, String captcha) throws Exception {
        Object douyuQRCode = session.getAttribute(SESSION_ATTRIBUTE);
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("referer", "https://passport.douyu.com/index/login?passport_reg_callback=PASSPORT_REG_SUCCESS_CALLBACK&passport_login_callback=PASSPORT_LOGIN_SUCCESS_CALLBACK&passport_close_callback=PASSPORT_CLOSE_CALLBACK&passport_dp_callback=PASSPORT_DP_CALLBACK&type=login&client_id=1&state=https%3A%2F%2Fwww.douyu.com%2F&source=click_topnavi_login");
        requestProperties.put("x-requested-with", "XMLHttpRequest");
        HttpResponse httpResponse = HttpRequestUtil.getHttpResponse(URI.create(String.format(URL_CHECK_CODE, System.currentTimeMillis(), douyuQRCode)), null, requestProperties);
        List<Header> headerList = new ArrayList<>(Arrays.asList(httpResponse.getHeaders("set-cookie")));
        String checkResultJSON = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
        JSONObject checkResult = JSON.parseObject(checkResultJSON);
        if (checkResult.getInteger("error") == 0) {
            URI redirectUrl = URI.create("https:" + checkResult.getJSONObject("data").getString("url"));
            httpResponse = HttpRequestUtil.getHttpResponse(redirectUrl, null, requestProperties);
            String loginResultJSON = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            JSONObject loginResult = JSON.parseObject(loginResultJSON.substring(1, loginResultJSON.length() - 1));
            if (loginResult.getInteger("error") == 0) {
                headerList.addAll(Arrays.asList(httpResponse.getHeaders("set-cookie")));
                String cookies = headerList.stream().map(header -> header.getValue().split(";")[0]).collect(Collectors.joining(";"));
                httpResponse = HttpRequestUtil.getHttpResponse(URI.create("https://passport.douyu.com/member/login?client_id=97"), cookies, requestProperties);
                String apolloLoginJSON = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                JSONObject apolloLogin = JSON.parseObject(apolloLoginJSON.substring(1, apolloLoginJSON.length() - 1));
                if (apolloLogin.getInteger("error") == 0) {
                    headerList.addAll(Arrays.asList(httpResponse.getHeaders("set-cookie")));
                    String randomCtn = SecurityUtils.md5Encode(String.valueOf(System.nanoTime()).getBytes());
                    headerList.add(new BasicHeader("set-cookie", "apollo_ctn=" + randomCtn + ";"));
                    headerList.add(new BasicHeader("set-cookie", "acf_ccn=" + randomCtn + ";"));
                    return headerList.stream().map(header -> header.getValue().split(";")[0]).collect(Collectors.joining(";"));
                } else {
                    throw new Exception(loginResult.getString("data"));
                }
            } else {
                throw new Exception(loginResult.getString("data"));
            }
        }
        throw new Exception(checkResult.getString("data"));
    }

    @Override
    public InputStream getBroadcastCaptcha() throws IOException {
        Map<String, String> requestProperties = new HashMap<>();
        requestProperties.put("referer", "https://passport.douyu.com/index/login?passport_reg_callback=PASSPORT_REG_SUCCESS_CALLBACK&passport_login_callback=PASSPORT_LOGIN_SUCCESS_CALLBACK&passport_close_callback=PASSPORT_CLOSE_CALLBACK&passport_dp_callback=PASSPORT_DP_CALLBACK&type=login&client_id=1&state=https%3A%2F%2Fwww.douyu.com%2F&source=click_topnavi_login");
        requestProperties.put("x-requested-with", "XMLHttpRequest");
        String generateCodeJSON = HttpRequestUtil.downloadUrl(URI.create(URL_GENERATE_CODE), null, "client_id=1", requestProperties, StandardCharsets.UTF_8);
        JSONObject generateCode = JSON.parseObject(generateCodeJSON);
        if (generateCode.getInteger("error") == 0) {
            String url = generateCode.getJSONObject("data").getString("url");
            String code = generateCode.getJSONObject("data").getString("code");
            session.setAttribute(SESSION_ATTRIBUTE, code);
            try {
                QRCode qrCode = Encoder.encode(url, ErrorCorrectionLevel.M);
                BufferedImage qrCodeImage = new BufferedImage(qrCode.getMatrix().getWidth(), qrCode.getMatrix().getHeight(), BufferedImage.TYPE_BYTE_BINARY);
                for (int x = 0; x < qrCodeImage.getWidth(); x++) {
                    for (int y = 0; y < qrCodeImage.getHeight(); y++) {
                        if (qrCode.getMatrix().get(x, y) == 0) {
                            qrCodeImage.setRGB(x, y, Color.WHITE.getRGB());
                        }
                    }
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(qrCodeImage, "bmp", bos);
                return new ByteArrayInputStream(bos.toByteArray());
            } catch (WriterException e) {
                throw new IOException(e);
            }
        } else {
            log.error("获取qrcode失败:" + generateCodeJSON);
        }
        return null;
    }
}
