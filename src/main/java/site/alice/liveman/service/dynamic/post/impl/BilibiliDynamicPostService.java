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

package site.alice.liveman.service.dynamic.post.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alice.liveman.model.AccountInfo;
import site.alice.liveman.service.broadcast.BroadcastServiceManager;
import site.alice.liveman.service.dynamic.post.DynamicPostService;
import site.alice.liveman.utils.HttpRequestUtil;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class BilibiliDynamicPostService implements DynamicPostService {

    private static final String DYNAMIC_POST_API   = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/create";
    private static final String DYNAMIC_POST_PARAM = "dynamic_id=0&type=4&rid=0&content=%s&at_uids=&ctrl=[]&csrf_token=";

    @Autowired
    private BroadcastServiceManager broadcastServiceManager;

    @Override
    public void postDynamic(AccountInfo postAccount, String content) {
        Matcher matcher = Pattern.compile("bili_jct=(.{32})").matcher(postAccount.readCookies());
        String csrfToken = "";
        if (matcher.find()) {
            csrfToken = matcher.group(1);
        }
        String postData = null;
        try {
            broadcastServiceManager.getBroadcastService(postAccount.getAccountSite()).getBroadcastRoomId(postAccount);
            postData = String.format(DYNAMIC_POST_PARAM, content) + csrfToken;
            String res = HttpRequestUtil.downloadUrl(new URI(DYNAMIC_POST_API), postAccount.readCookies(), postData, StandardCharsets.UTF_8);
            JSONObject jsonObject = JSONObject.parseObject(res);
            if (jsonObject.getInteger("code") != 0) {
                log.error("发送B站动态失败[postData=" + postData + "]" + res);
            }
        } catch (Exception ex) {
            log.error("发送B站动态失败[postData=" + postData + "]", ex);
        }
    }

    @Override
    public boolean match(AccountInfo postAccount) {
        return "bilibili".equals(postAccount.getAccountSite());
    }
}
