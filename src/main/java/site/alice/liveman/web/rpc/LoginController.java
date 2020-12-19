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

package site.alice.liveman.web.rpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.alice.liveman.bo.ExternalAppSecretBO;
import site.alice.liveman.config.SettingConfig;
import site.alice.liveman.dataobject.ExternalAppSecretDO;
import site.alice.liveman.jenum.ExternalServiceType;
import site.alice.liveman.model.AccountInfo;
import site.alice.liveman.model.LiveManSetting;
import site.alice.liveman.service.broadcast.BroadcastService;
import site.alice.liveman.service.broadcast.BroadcastServiceManager;
import site.alice.liveman.service.broadcast.impl.BilibiliBroadcastService.CaptchaMismatchException;
import site.alice.liveman.utils.HttpRequestUtil;
import site.alice.liveman.web.dataobject.ActionResult;
import site.alice.liveman.web.dataobject.vo.AccountInfoVO;
import site.alice.liveman.web.dataobject.vo.LoginInfoVO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Controller
@RequestMapping("/api/login")
public class LoginController {

    private static final String                  adminRoomId = System.getProperty("admin.room.id");
    @Autowired
    private              HttpSession             session;
    @Autowired
    private              HttpServletResponse     response;
    @Autowired
    private              BroadcastServiceManager broadcastServiceManager;
    @Autowired
    private              LiveManSetting          liveManSetting;
    @Autowired
    private              SettingConfig           settingConfig;
    @Autowired
    private              ExternalAppSecretBO     externalAppSecretBO;

    @ResponseBody
    @RequestMapping("/login.json")
    public ActionResult<Object> loginWithBili(String loginMode, @RequestBody LoginInfoVO loginInfoVO) {
        try {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccountSite(loginInfoVO.getAccountSite());
            accountInfo.setCookies(loginInfoVO.getCookies());
            BroadcastService broadcastService = broadcastServiceManager.getBroadcastService(accountInfo.getAccountSite());
            if (loginMode.equals("userpwd")) {
                String cookies = broadcastService.getBroadcastCookies(loginInfoVO.getUsername(), loginInfoVO.getPassword(), loginInfoVO.getCaptcha());
                accountInfo.setCookies(cookies);
            }
            broadcastService.getBroadcastRoomId(accountInfo);
            AccountInfoVO accountInfoVO = new AccountInfoVO();
            AccountInfo byAccountId;
            if ((byAccountId = liveManSetting.findByAccountId(accountInfo.getAccountId(), accountInfo.getAccountSite())) != null) {
                // 更新新的Cookies
                byAccountId.setCookies(accountInfo.readCookies());
                byAccountId.setAccountId(accountInfo.getAccountId());
                byAccountId.setDisable(false);
                accountInfo = byAccountId;
            } else {
                byAccountId = accountInfo;
                liveManSetting.getAccounts().add(byAccountId);
            }
            accountInfoVO.setSaved(true);
            settingConfig.saveSetting(liveManSetting);
            log.info("adminRoomId = '" + adminRoomId + "'");
            accountInfo.setAdmin(accountInfo.getRoomId().equals(adminRoomId));
            session.setAttribute("account", accountInfo);
            BeanUtils.copyProperties(accountInfo, accountInfoVO);
            accountInfoVO.setBillTimeMap(new HashMap<>(accountInfo.getBillTimeMap()));
            accountInfoVO.setServerPoints(liveManSetting.getServerPoints());
            // 查找该账号下的所有共享号
            CopyOnWriteArraySet<AccountInfo> accounts = liveManSetting.getAccounts();
            List<AccountInfoVO> shardAccounts = new ArrayList<>();
            for (AccountInfo shardAccountInfo : accounts) {
                if (accountInfo.getAccountId().equals(shardAccountInfo.getParentAccountId())) {
                    AccountInfoVO accountVO = new AccountInfoVO();
                    accountVO.setAccountId(shardAccountInfo.getAccountId());
                    accountVO.setNickname(shardAccountInfo.getNickname());
                    accountVO.setAccountSite(shardAccountInfo.getAccountSite());
                    shardAccounts.add(accountVO);
                }
            }
            accountInfoVO.setShareCode(accountInfo.getShareCode());
            accountInfoVO.setShardAccounts(shardAccounts);
            AccountInfo parentAccountInfo = accountInfo.getParentAccountInfo();
            if (parentAccountInfo != null) {
                accountInfoVO.setParentAccountName(parentAccountInfo.getNickname());
            }
            return ActionResult.getSuccessResult(accountInfoVO);
        } catch (Exception e) {
            log.error("登录失败", e);
            ActionResult<Object> errorResult = ActionResult.getErrorResult("登录失败[ErrMsg:" + e.getMessage() + "]");
            if (e instanceof CaptchaMismatchException) {
                errorResult.setCode(-101);
                errorResult.setData(((CaptchaMismatchException) e).getGeetestUrl());
            }
            return errorResult;
        }
    }

    @GetMapping("/oauth2/{oauth2Type}/auth")
    public String oauth2auth(@PathVariable String oauth2Type) {
        ExternalServiceType serviceType = ExternalServiceType.valueOf(oauth2Type.toUpperCase());
        ExternalAppSecretDO appSecret = externalAppSecretBO.getAppSecret(serviceType);
        String url = "";
        if (appSecret != null) {
            switch (serviceType) {
                case GOOGLE_OAUTH2:
                    url = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile openid&state=" + UUID.randomUUID() + "&redirect_uri=" + liveManSetting.getBaseUrl() + "/api/login/oauth2/" + oauth2Type + "/callback&response_type=code&client_id=" + appSecret.getAppId();
                    break;
                case QQ_OAUTH2:
                    url = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=" + appSecret.getAppId() + "&redirect_uri=" + liveManSetting.getBaseUrl() + "/api/login/oauth2/" + oauth2Type + "/callback";
                    break;
            }
            return "redirect:" + url;
        } else {
            log.error("找不到OAUTH2客户端[" + oauth2Type + "]的配置信息");
            return null;
        }
    }

    @GetMapping("/oauth2/{oauth2Type}/callback")
    public String oauth2callback(@PathVariable String oauth2Type, String code) {
        ExternalServiceType serviceType = ExternalServiceType.valueOf(oauth2Type.toUpperCase());
        ExternalAppSecretDO appSecret = externalAppSecretBO.getAppSecret(serviceType);
        try {
            String accountId = null;
            String nickname = null;
            if (appSecret != null) {
                switch (serviceType) {
                    case GOOGLE_OAUTH2: {
                        String postData = "code=" + code + "&client_id=" + appSecret.getAppId() + "&client_secret=" + appSecret.getAppKey()
                                + "&redirect_uri=" + liveManSetting.getBaseUrl() + "/api/login/oauth2/" + oauth2Type + "/callback"
                                + "&grant_type=authorization_code";
                        String tokenJson = HttpRequestUtil.downloadUrl(URI.create("https://oauth2.googleapis.com/token"), null, postData, StandardCharsets.UTF_8);
                        String accessToken = JSON.parseObject(tokenJson).getString("access_token");
                        if (StringUtils.isNotEmpty(accessToken)) {
                            String peopleJson = HttpRequestUtil.downloadUrl(URI.create("https://people.googleapis.com/v1/people/me?personFields=emailAddresses,names&access_token=" + accessToken), StandardCharsets.UTF_8);
                            JSONObject peopleObj = JSON.parseObject(peopleJson);
                            JSONArray emailAddresses = peopleObj.getJSONArray("emailAddresses");
                            if (emailAddresses == null || emailAddresses.isEmpty()) {
                                throw new RuntimeException("无效的emailAddresses[" + peopleJson + "]");
                            }
                            accountId = emailAddresses.getJSONObject(0).getString("value");
                            JSONArray names = peopleObj.getJSONArray("names");
                            if (names == null || names.isEmpty()) {
                                nickname = accountId;
                            } else {
                                nickname = names.getJSONObject(0).getString("displayName");
                            }
                        } else {
                            throw new RuntimeException("无效的accessToken[" + tokenJson + "]");
                        }
                        break;
                    }
                    case QQ_OAUTH2: {
                        String queryData = "code=" + code + "&client_id=" + appSecret.getAppId() + "&client_secret=" + appSecret.getAppKey()
                                + "&redirect_uri=" + liveManSetting.getBaseUrl() + "/api/login/oauth2/" + oauth2Type + "/callback"
                                + "&grant_type=authorization_code";
                        String tokenQueryString = HttpRequestUtil.downloadUrl(URI.create("https://graph.qq.com/oauth2.0/token?" + queryData), StandardCharsets.UTF_8);
                        if (StringUtils.isNotEmpty(tokenQueryString)) {
                            String peopleJson = HttpRequestUtil.downloadUrl(URI.create("https://graph.qq.com/oauth2.0/me?" + tokenQueryString), StandardCharsets.UTF_8);
                            peopleJson = peopleJson.replace("callback(", "").replace(");", "");
                            JSONObject peopleObj = JSON.parseObject(peopleJson);
                            accountId = peopleObj.getString("openid");
                            if (accountId == null || accountId.isEmpty()) {
                                throw new RuntimeException("无效的openid[" + peopleJson + "]");
                            }
                            String userJson = HttpRequestUtil.downloadUrl(URI.create("https://graph.qq.com/user/get_user_info?" + tokenQueryString + "&oauth_consumer_key=" + appSecret.getAppId() + "&openid=" + accountId), StandardCharsets.UTF_8);
                            JSONObject userObj = JSON.parseObject(userJson);
                            nickname = userObj.getString("nickname");
                            if (nickname == null || nickname.isEmpty()) {
                                nickname = accountId;
                            }
                        } else {
                            throw new RuntimeException("无效的accessToken[" + tokenQueryString + "]");
                        }
                        break;
                    }
                }
                AccountInfo accountInfo = liveManSetting.findByAccountId(accountId, oauth2Type);
                if (accountInfo != null) {
                    accountInfo.setDisable(false);
                    session.setAttribute("account", accountInfo);
                } else {
                    accountInfo = new AccountInfo();
                    accountInfo.setAccountId(accountId);
                    accountInfo.setNickname(nickname);
                    accountInfo.setAccountSite(oauth2Type);
                    accountInfo.setRoomId(accountInfo.getAccountId());
                    liveManSetting.getAccounts().add(accountInfo);
                    session.setAttribute("account", accountInfo);
                }
                accountInfo.setAdmin(accountInfo.getRoomId().equals(adminRoomId));
                settingConfig.saveSetting(liveManSetting);
                return "redirect:" + liveManSetting.getBaseUrl() + "/main/broadcast";
            } else {
                log.error("找不到OAUTH2客户端[" + oauth2Type + "]的配置信息");
            }
        } catch (Exception e) {
            log.error("登录失败", e);
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/getCaptcha")
    public void getCaptcha(String accountSite) throws IOException {
        BroadcastService broadcastService = broadcastServiceManager.getBroadcastService(accountSite);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(broadcastService.getBroadcastCaptcha(), outputStream);
        }
    }

    @ResponseBody
    @RequestMapping("/logout.json")
    public ActionResult logout() {
        session.invalidate();
        return ActionResult.getSuccessResult(null);
    }
}
