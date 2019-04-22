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

package site.alice.liveman.service.external.consumer;

import site.alice.liveman.service.external.TextLocation;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.BiConsumer;

public interface TextLocationConsumer extends BiConsumer<List<TextLocation>, BufferedImage> {

    @Override
    void accept(List<TextLocation> textLocations, BufferedImage bufferedImage);
}
