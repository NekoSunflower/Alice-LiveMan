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

import java.awt.image.BufferedImage;

public class KeyFrame {
    private Integer       fps;
    private Integer       width;
    private Integer       height;
    private BufferedImage frameImage;

    public KeyFrame(Integer fps, BufferedImage frameImage) {
        this.fps = fps;
        this.frameImage = frameImage;
        if (frameImage != null) {
            this.width = frameImage.getWidth();
            this.height = frameImage.getHeight();
        }
    }

    public Integer getFps() {
        return fps;
    }

    public void setFps(Integer fps) {
        this.fps = fps;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public BufferedImage getFrameImage() {
        return frameImage;
    }

    public void setFrameImage(BufferedImage frameImage) {
        this.frameImage = frameImage;
    }
}