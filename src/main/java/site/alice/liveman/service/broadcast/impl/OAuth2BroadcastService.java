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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alice.liveman.model.AccountInfo;
import site.alice.liveman.service.broadcast.BroadcastService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Service
public class OAuth2BroadcastService implements BroadcastService {

    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean isMatch(String accountSite) {
        return accountSite.contains("oauth");
    }

    @Override
    public String getBroadcastAddress(AccountInfo accountInfo) throws Exception {
        String addr = accountInfo.getRtmpHost();
        String code = accountInfo.getRtmpPassword();
        if (StringUtils.isEmpty(addr)) {
            throw new RuntimeException("串流地址不完整，请先前往【我的账号】设置串流信息！");
        }
        if (code != null && !addr.endsWith("/") && !code.startsWith("/")) {
            return addr + "/" + code;
        } else {
            return addr + code;
        }
    }

    @Override
    public void setBroadcastSetting(AccountInfo accountInfo, String title, Integer areaId) {

    }

    @Override
    public String getBroadcastRoomId(AccountInfo accountInfo) throws Exception {
        return accountInfo.getRoomId();
    }

    @Override
    public void stopBroadcast(AccountInfo accountInfo, boolean stopOnPadding) {

    }

    @Override
    public String getBroadcastCookies(String username, String password, String captcha) throws Exception {
        return null;
    }

    @Override
    public InputStream getBroadcastCaptcha() throws IOException {
        return null;
    }
}
