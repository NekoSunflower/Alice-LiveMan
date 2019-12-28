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

package site.alice.liveman.jenum;

public enum ExternalServiceType {
    BAIDU_API("baidu"),
    VULTR_API("vultr"),
    GOOGLE_OAUTH2("google_oauth2"),
    QQ_OAUTH2("qq_oauth2");

    ExternalServiceType(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }
}
