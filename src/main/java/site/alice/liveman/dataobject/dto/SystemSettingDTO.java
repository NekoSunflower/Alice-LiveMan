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

package site.alice.liveman.dataobject.dto;

import site.alice.liveman.dataobject.BaseDO;

public class SystemSettingDTO extends BaseDO {
    /**
     * 需要屏蔽的油管频道
     */
    private String bannedYoutubeChannel;

    /**
     * 需要屏蔽的关键字
     */
    private String bannedKeywords;

    /**
     * OneDriveClientId
     */
    private String oneDriveClientId;

    /**
     * OneDriveClientSecret
     */
    private String oneDriveClientSecret;

    /**
     * OneDriveToken
     */
    private String oneDriveToken;

    /**
     * 是否开启预编码
     */
    private Boolean preReEncode;

    /**
     * 默认分辨率
     */
    private String defaultResolution;

    /**
     * 网站根地址
     */
    private String baseUrl;

    public String getBannedYoutubeChannel() {
        return bannedYoutubeChannel;
    }

    public void setBannedYoutubeChannel(String bannedYoutubeChannel) {
        this.bannedYoutubeChannel = bannedYoutubeChannel == null ? null : bannedYoutubeChannel.trim();
    }

    public String getBannedKeywords() {
        return bannedKeywords;
    }

    public void setBannedKeywords(String bannedKeywords) {
        this.bannedKeywords = bannedKeywords == null ? null : bannedKeywords.trim();
    }

    public String getOneDriveClientId() {
        return oneDriveClientId;
    }

    public void setOneDriveClientId(String oneDriveClientId) {
        this.oneDriveClientId = oneDriveClientId == null ? null : oneDriveClientId.trim();
    }

    public String getOneDriveClientSecret() {
        return oneDriveClientSecret;
    }

    public void setOneDriveClientSecret(String oneDriveClientSecret) {
        this.oneDriveClientSecret = oneDriveClientSecret == null ? null : oneDriveClientSecret.trim();
    }

    public String getOneDriveToken() {
        return oneDriveToken;
    }

    public void setOneDriveToken(String oneDriveToken) {
        this.oneDriveToken = oneDriveToken == null ? null : oneDriveToken.trim();
    }

    public Boolean getPreReEncode() {
        return preReEncode;
    }

    public void setPreReEncode(Boolean preReEncode) {
        this.preReEncode = preReEncode;
    }

    public String getDefaultResolution() {
        return defaultResolution;
    }

    public void setDefaultResolution(String defaultResolution) {
        this.defaultResolution = defaultResolution == null ? null : defaultResolution.trim();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl == null ? null : baseUrl.trim();
    }
}