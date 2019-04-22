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

package site.alice.liveman.dataobject;

import java.util.Date;

public class VideoTaskDO extends BaseDO {
    /**
     * 频道ID
     */
    private Long channelId;

    /**
     * 节目唯一识别编号
     */
    private String videoUuid;

    /**
     * 外部平台节目编号
     */
    private String externalVideoId;

    /**
     * 外部平台节目分段编号
     */
    private String externalVideoPart;

    /**
     * 节目标题
     */
    private String videoTitle;

    /**
     * 节目信息来源URL
     */
    private String videoInfoUrl;

    /**
     * 节目视频源URL
     */
    private String mediaUrl;

    /**
     * 节目视频源代理URL
     */
    private String mediaProxyUrl;

    /**
     * 节目视频源格式
     */
    private String mediaFormat;

    /**
     * 节目广播分区
     */
    private String area;

    /**
     * 是否需要强制单声道
     */
    private Boolean audioBanned;

    /**
     * 是否需要录像
     */
    private Boolean needRecord;

    /**
     * 节目视频源加密方式
     */
    private String encodeMethod;

    /**
     * 是否需要竖屏转播
     */
    private Boolean vertical;

    /**
     * 节目视频源帧率
     */
    private Double frameRate;

    /**
     * 节目视频源分辨率
     */
    private String resolution;

    /**
     * 节目状态
     */
    private String status;

    /**
     * 加密秘钥
     */
    private byte[] encodeKey;

    /**
     * 加密秘钥向量
     */
    private byte[] encodeIv;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getVideoUuid() {
        return videoUuid;
    }

    public void setVideoUuid(String videoUuid) {
        this.videoUuid = videoUuid == null ? null : videoUuid.trim();
    }

    public String getExternalVideoId() {
        return externalVideoId;
    }

    public void setExternalVideoId(String externalVideoId) {
        this.externalVideoId = externalVideoId == null ? null : externalVideoId.trim();
    }

    public String getExternalVideoPart() {
        return externalVideoPart;
    }

    public void setExternalVideoPart(String externalVideoPart) {
        this.externalVideoPart = externalVideoPart == null ? null : externalVideoPart.trim();
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle == null ? null : videoTitle.trim();
    }

    public String getVideoInfoUrl() {
        return videoInfoUrl;
    }

    public void setVideoInfoUrl(String videoInfoUrl) {
        this.videoInfoUrl = videoInfoUrl == null ? null : videoInfoUrl.trim();
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl == null ? null : mediaUrl.trim();
    }

    public String getMediaProxyUrl() {
        return mediaProxyUrl;
    }

    public void setMediaProxyUrl(String mediaProxyUrl) {
        this.mediaProxyUrl = mediaProxyUrl == null ? null : mediaProxyUrl.trim();
    }

    public String getMediaFormat() {
        return mediaFormat;
    }

    public void setMediaFormat(String mediaFormat) {
        this.mediaFormat = mediaFormat == null ? null : mediaFormat.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Boolean getAudioBanned() {
        return audioBanned;
    }

    public void setAudioBanned(Boolean audioBanned) {
        this.audioBanned = audioBanned;
    }

    public Boolean getNeedRecord() {
        return needRecord;
    }

    public void setNeedRecord(Boolean needRecord) {
        this.needRecord = needRecord;
    }

    public String getEncodeMethod() {
        return encodeMethod;
    }

    public void setEncodeMethod(String encodeMethod) {
        this.encodeMethod = encodeMethod == null ? null : encodeMethod.trim();
    }

    public Boolean getVertical() {
        return vertical;
    }

    public void setVertical(Boolean vertical) {
        this.vertical = vertical;
    }

    public Double getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Double frameRate) {
        this.frameRate = frameRate;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution == null ? null : resolution.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public byte[] getEncodeKey() {
        return encodeKey;
    }

    public void setEncodeKey(byte[] encodeKey) {
        this.encodeKey = encodeKey;
    }

    public byte[] getEncodeIv() {
        return encodeIv;
    }

    public void setEncodeIv(byte[] encodeIv) {
        this.encodeIv = encodeIv;
    }
}