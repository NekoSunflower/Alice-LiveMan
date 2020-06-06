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

import com.fasterxml.jackson.annotation.JsonIgnore;
import site.alice.liveman.customlayout.CustomLayout;
import site.alice.liveman.jenum.VideoBannedTypeEnum;
import site.alice.liveman.jenum.VideoResolutionEnum;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class BroadcastConfig implements Serializable {
    private VideoBannedTypeEnum                videoBannedType = VideoBannedTypeEnum.NONE;
    private boolean                            autoBlur;
    private boolean                            autoImageSegment;
    private int                                blurSize;
    private CopyOnWriteArrayList<CustomLayout> layouts;
    private VideoResolutionEnum                broadcastResolution;
    private int[]                              area;
    private boolean                            vertical;
    private boolean                            isAudioBanned;
    private boolean                            needRecord;
    private boolean                            autoBroadcast;
    private String                             cookies;
    @JsonIgnore
    private byte[]                             cachedDrawBytes;
    @JsonIgnore
    private byte[]                             cachedBlurBytes;

    public BroadcastConfig() {
        layouts = new CopyOnWriteArrayList<>();
    }

    public VideoBannedTypeEnum getVideoBannedType() {
        return videoBannedType;
    }

    public void setVideoBannedType(VideoBannedTypeEnum videoBannedType) {
        this.videoBannedType = videoBannedType;
    }

    public int getBlurSize() {
        return blurSize;
    }

    public void setBlurSize(int blurSize) {
        this.blurSize = blurSize;
    }

    public CopyOnWriteArrayList<CustomLayout> getLayouts() {
        return layouts;
    }

    public void setLayouts(CopyOnWriteArrayList<CustomLayout> layouts) {
        this.layouts = layouts;
    }

    public byte[] getCachedDrawBytes() {
        return cachedDrawBytes;
    }

    public void setCachedDrawBytes(byte[] cachedDrawBytes) {
        this.cachedDrawBytes = cachedDrawBytes;
    }

    public byte[] getCachedBlurBytes() {
        return cachedBlurBytes;
    }

    public void setCachedBlurBytes(byte[] cachedBlurBytes) {
        this.cachedBlurBytes = cachedBlurBytes;
    }

    public boolean isAutoBlur() {
        return autoBlur;
    }

    public void setAutoBlur(boolean autoBlur) {
        this.autoBlur = autoBlur;
    }

    public boolean isAutoImageSegment() {
        return autoImageSegment;
    }

    public void setAutoImageSegment(boolean autoImageSegment) {
        this.autoImageSegment = autoImageSegment;
    }

    public VideoResolutionEnum getBroadcastResolution() {
        return broadcastResolution;
    }

    public int[] getArea() {
        return area;
    }

    public void setArea(int[] area) {
        this.area = area;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public void setBroadcastResolution(VideoResolutionEnum broadcastResolution) {
        this.broadcastResolution = broadcastResolution;
    }

    public boolean isAutoBroadcast() {
        return autoBroadcast;
    }

    public void setAutoBroadcast(boolean autoBroadcast) {
        this.autoBroadcast = autoBroadcast;
    }

    public boolean isAudioBanned() {
        return isAudioBanned;
    }

    public void setAudioBanned(boolean audioBanned) {
        isAudioBanned = audioBanned;
    }

    public boolean isNeedRecord() {
        return needRecord;
    }

    public void setNeedRecord(boolean needRecord) {
        this.needRecord = needRecord;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    @Override
    public String toString() {
        return "BroadcastConfig{" +
                "videoBannedType=" + videoBannedType +
                ", autoBlur=" + autoBlur +
                ", autoImageSegment=" + autoImageSegment +
                ", isAudioBanned=" + isAudioBanned +
                ", needRecord=" + needRecord +
                ", autoBroadcast=" + autoBroadcast +
                '}';
    }
}
