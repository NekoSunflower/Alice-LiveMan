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

import site.alice.liveman.service.broadcast.BroadcastServiceManager.BroadcastTask;
import site.alice.liveman.service.external.TextLocation;
import site.alice.liveman.utils.SecurityUtils;

import java.io.Serializable;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class VideoInfo implements Serializable {

    private ChannelInfo                          channelInfo;
    private String                               videoId;
    private AccountInfo                          privateAccount;
    private String                               part;
    private String                               title;
    private String                               description;
    private URI                                  videoInfoUrl;
    private URI                                  mediaProxyUrl;
    private URI                                  mediaUrl;
    private String                               mediaFormat;
    private String                               encodeMethod;
    private byte[]                               encodeKey;
    private byte[]                               encodeIV;
    private CopyOnWriteArraySet<BroadcastTask>   broadcastTasks;
    private CopyOnWriteArraySet<BroadcastConfig> broadcastConfigs;
    private Double                               frameRate;
    private String                               requiredResolution;
    private String                               realResolution;
    private List<TextLocation>                   textLocations;

    public VideoInfo(ChannelInfo channelInfo, String videoId, String title, URI videoInfoUrl, URI mediaUrl, String mediaFormat) {
        this.channelInfo = channelInfo;
        this.videoId = videoId;
        this.title = title;
        this.videoInfoUrl = videoInfoUrl;
        this.mediaUrl = mediaUrl;
        this.mediaFormat = mediaFormat;
        this.broadcastTasks = new CopyOnWriteArraySet<>();
        this.broadcastConfigs = new CopyOnWriteArraySet<>();
    }

    public AccountInfo getPrivateAccount() {
        return privateAccount;
    }

    public void setPrivateAccount(AccountInfo privateAccount) {
        this.privateAccount = privateAccount;
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
        return videoId + (part == null ? "" : "." + part) + (privateAccount == null ? "" : "." + SecurityUtils.md5Encode(privateAccount.getAccountId().getBytes(StandardCharsets.UTF_8)) + "." + requiredResolution);
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

    public Set<BroadcastTask> getBroadcastTasks() {
        return broadcastTasks;
    }

    public boolean addBroadcastTask(BroadcastTask broadcastTask) {
        return this.broadcastTasks.add(broadcastTask);
    }

    public boolean removeBroadcastTask(BroadcastTask broadcastTask) {
        return this.broadcastTasks.remove(broadcastTask);
    }

    public BroadcastTask getBroadcastTask(AccountInfo accountInfo) {
        for (BroadcastTask broadcastTask : broadcastTasks) {
            if (broadcastTask.getBroadcastAccount().equals(accountInfo)) {
                return broadcastTask;
            }
        }
        return null;
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

    public CopyOnWriteArraySet<BroadcastConfig> getBroadcastConfigs() {
        return broadcastConfigs;
    }

    public void setBroadcastConfigs(CopyOnWriteArraySet<BroadcastConfig> broadcastConfigs) {
        this.broadcastConfigs = broadcastConfigs;
    }

    public BroadcastConfig getBroadcastConfig(AccountInfo accountInfo) {
        for (BroadcastConfig broadcastConfig : broadcastConfigs) {
            if (broadcastConfig.getAccountInfo().equals(accountInfo)) {
                return broadcastConfig;
            }
        }
        return null;
    }

    public void addBroadcastConfig(BroadcastConfig broadcastConfig) {
        if (!this.broadcastConfigs.add(broadcastConfig)) {
            this.broadcastConfigs.remove(broadcastConfig);
            this.broadcastConfigs.add(broadcastConfig);
        }
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
