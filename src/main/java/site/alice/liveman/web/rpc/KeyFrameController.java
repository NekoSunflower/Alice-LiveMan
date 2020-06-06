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

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.alice.liveman.mediaproxy.MediaProxyManager;
import site.alice.liveman.model.AccountInfo;
import site.alice.liveman.model.KeyFrame;
import site.alice.liveman.model.VideoInfo;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@Slf4j
@RestController
@RequestMapping("/api/keyframe")
public class KeyFrameController {

    private static final Logger              logger = LoggerFactory.getLogger(MediaProxyController.class);
    @Autowired
    private              HttpServletResponse response;
    @Autowired
    private              HttpSession         session;

    @RequestMapping("/{videoId}")
    public void keyframe(@PathVariable String videoId) {
        try {
            AccountInfo account = (AccountInfo) session.getAttribute("account");
            VideoInfo videoInfo = MediaProxyManager.findVideoInfoById(videoId, account);
            if (videoInfo != null) {
                KeyFrame keyFrame = videoInfo.getKeyFrame();
                if (keyFrame != null) {
                    BufferedImage scaledKeyFrame = new BufferedImage((int) (keyFrame.getWidth() * (720.0 / keyFrame.getHeight())), 720, BufferedImage.TYPE_INT_RGB);
                    scaledKeyFrame.createGraphics().drawImage(keyFrame.getFrameImage(), 0, 0, scaledKeyFrame.getWidth(), scaledKeyFrame.getHeight(), null);
                    ImageIO.write(scaledKeyFrame, "jpg", response.getOutputStream());
                }
            }
        } catch (Throwable e) {
            logger.error("getKeyFrame Failed videoId=" + videoId, e);
        }
    }
}
