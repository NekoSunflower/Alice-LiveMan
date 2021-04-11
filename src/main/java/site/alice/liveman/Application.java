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
package site.alice.liveman;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = {"site.alice.liveman"})
@Configuration
@PropertySource("classpath:application.properties")
@EnableScheduling
public class Application {

    public static void main(String[] args) throws IOException {
        int round = 0;
        int x = 500;
        int y = 500;
        JWindow window = new JWindow();
        window.setVisible(true);
        window.setBounds(20, 20, 1000, 1000);
        Graphics2D graphics = (Graphics2D) window.getGraphics();
        int[] pixel = new int[]{255, 255, 255};
        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        for (; round < 500; round += 2) {
            System.out.println("第" + (round / 2) + "回合");
            WritableRaster raster = bufferedImage.getRaster();
            int _x = round / 2, _y = round / 2;
            for (int i = 0; i < (x + y - 2 * round) * 2; i++) {
                System.out.println(String.format("x=%s, y=%s", _x, _y));
                raster.setPixel(_x, _y, pixel);
                graphics.drawImage(bufferedImage, 0, 0, window.getWidth(), window.getHeight(), null);
                if (i < (x - round)) {
                    _x++;
                    _x = Math.min(_x, x - round / 2 - 1);
                } else if (i < (x - round + (y - round))) {
                    _y++;
                    _y = Math.min(_y, y - round / 2 - 1);
                } else if (i < (2 * (x - round) + (y - round))) {
                    _x--;
                    _x = Math.max(_x, round / 2);
                } else {
                    _y--;
                    _y = Math.max(_y, round / 2);
                }
            }
        }
    }

}
