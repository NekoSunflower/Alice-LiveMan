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

public class VideoTaskDOWithBLOBs extends VideoTaskDO {
    /**
     * 加密秘钥
     */
    private byte[] encodeKey;

    /**
     * 加密秘钥向量
     */
    private byte[] encodeIv;

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