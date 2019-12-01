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
package site.alice.liveman.model;

import site.alice.liveman.service.broadcast.BroadcastTask;
import site.alice.liveman.service.external.TextLocation;
import site.alice.liveman.utils.SecurityUtils;

import java.io.Serializable;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class VideoInfo implements Serializable {

    private ChannelInfo                    channelInfo;
    private String                         videoId;
    private AccountInfo                    accountInfo;
    private String                         part;
    private String                         title;
    private String                         description;
    private URI                            videoInfoUrl;
    private URI                            mediaProxyUrl;
    private URI                            mediaUrl;
    private String                         mediaFormat;
    private String                         encodeMethod;
    private byte[]                         encodeKey;
    private byte[]                         encodeIV;
    private AtomicReference<BroadcastTask> broadcastTask;
    private BroadcastConfig                broadcastConfig;
    private Double                         frameRate;
    private String                         requiredResolution;
    private String                         realResolution;
    private List<TextLocation>             textLocations;
    private boolean                        isLowVideoInfo;

    public VideoInfo(ChannelInfo channelInfo, AccountInfo accountInfo, String videoId, String title, URI videoInfoUrl, URI mediaUrl, String mediaFormat) {
        this.channelInfo = channelInfo;
        this.videoId = videoId;
        this.title = title;
        this.videoInfoUrl = videoInfoUrl;
        this.mediaUrl = mediaUrl;
        this.mediaFormat = mediaFormat;
        this.accountInfo = accountInfo;
        this.broadcastTask = new AtomicReference<>();
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public URI getVideoInfoUrl() {
        return videoInfoUrl;
    }

    public void setVideoInfoUrl(URI videoInfoUrl) {
        this.videoInfoUrl = videoInfoUrl;
    }

    public String getRequiredResolution() {
        return requiredResolution;
    }

    public void setRequiredResolution(String requiredResolution) {
        this.requiredResolution = requiredResolution;
    }

    public String getVideoUnionId() {
        return videoId + (part == null ? "" : "." + part) + (accountInfo == null ? "" : "." + SecurityUtils.md5Encode(accountInfo.getAccountId().getBytes(StandardCharsets.UTF_8)) + "." + requiredResolution);
    }

    public ChannelInfo getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(ChannelInfo channelInfo) {
        this.channelInfo = channelInfo;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URI getMediaProxyUrl() {
        return mediaProxyUrl;
    }

    public void setMediaProxyUrl(URI mediaProxyUrl) {
        this.mediaProxyUrl = mediaProxyUrl;
    }

    public URI getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(URI mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaFormat() {
        return mediaFormat;
    }

    public void setMediaFormat(String mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    public String getEncodeMethod() {
        return encodeMethod;
    }

    public void setEncodeMethod(String encodeMethod) {
        this.encodeMethod = encodeMethod;
    }

    public byte[] getEncodeKey() {
        return encodeKey;
    }

    public void setEncodeKey(byte[] encodeKey) {
        this.encodeKey = encodeKey;
    }

    public byte[] getEncodeIV() {
        return encodeIV;
    }

    public void setEncodeIV(byte[] encodeIV) {
        this.encodeIV = encodeIV;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }


    public BroadcastTask getBroadcastTask() {
        return broadcastTask.get();
    }

    public boolean setBroadcastTask(BroadcastTask broadcastTask) {
        return this.broadcastTask.compareAndSet(null, broadcastTask);
    }

    public boolean removeBroadcastTask(BroadcastTask broadcastTask) {
        return this.broadcastTask.compareAndSet(broadcastTask, null);
    }

    public BroadcastConfig getBroadcastConfig() {
        return broadcastConfig;
    }

    public void setBroadcastConfig(BroadcastConfig broadcastConfig) {
        this.broadcastConfig = broadcastConfig;
    }

    public Double getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Double frameRate) {
        this.frameRate = frameRate;
    }

    public String getRealResolution() {
        return realResolution;
    }

    public void setRealResolution(String realResolution) {
        this.realResolution = realResolution;
    }

    public List<TextLocation> getTextLocations() {
        return textLocations;
    }

    public void setTextLocations(List<TextLocation> textLocations) {
        this.textLocations = textLocations;
    }

    public boolean isLowVideoInfo() {
        return isLowVideoInfo;
    }

    public void setLowVideoInfo(boolean lowVideoInfo) {
        isLowVideoInfo = lowVideoInfo;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "videoId='" + videoId + '\'' +
                ", part='" + part + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", mediaUrl=" + mediaUrl +
                ", mediaFormat='" + mediaFormat + '\'' +
                ", encodeMethod='" + encodeMethod + '\'' +
                ", encodeKey=" + Arrays.toString(encodeKey) +
                ", encodeIV=" + Arrays.toString(encodeIV) +
                '}';
    }
}
