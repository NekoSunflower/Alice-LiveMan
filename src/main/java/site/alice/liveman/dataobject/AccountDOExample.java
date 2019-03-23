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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AccountDOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIsNull() {
            addCriterion("gmt_created is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIsNotNull() {
            addCriterion("gmt_created is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedEqualTo(Date value) {
            addCriterion("gmt_created =", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotEqualTo(Date value) {
            addCriterion("gmt_created <>", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThan(Date value) {
            addCriterion("gmt_created >", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_created >=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThan(Date value) {
            addCriterion("gmt_created <", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_created <=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIn(List<Date> values) {
            addCriterion("gmt_created in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotIn(List<Date> values) {
            addCriterion("gmt_created not in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedBetween(Date value1, Date value2) {
            addCriterion("gmt_created between", value1, value2, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_created not between", value1, value2, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andAccountSiteIsNull() {
            addCriterion("account_site is null");
            return (Criteria) this;
        }

        public Criteria andAccountSiteIsNotNull() {
            addCriterion("account_site is not null");
            return (Criteria) this;
        }

        public Criteria andAccountSiteEqualTo(String value) {
            addCriterion("account_site =", value, "accountSite");
            return (Criteria) this;
        }

        public Criteria andAccountSiteNotEqualTo(String value) {
            addCriterion("account_site <>", value, "accountSite");
            return (Criteria) this;
        }

        public Criteria andAccountSiteGreaterThan(String value) {
            addCriterion("account_site >", value, "accountSite");
            return (Criteria) this;
        }

        public Criteria andAccountSiteGreaterThanOrEqualTo(String value) {
            addCriterion("account_site >=", value, "accountSite");
            return (Criteria) this;
        }

        public Criteria andAccountSiteLessThan(String value) {
            addCriterion("account_site <", value, "accountSite");
            return (Criteria) this;
        }

        public Criteria andAccountSiteLessThanOrEqualTo(String value) {
            addCriterion("account_site <=", value, "accountSite");
            return (Criteria) this;
        }

        public Criteria andAccountSiteLike(String value) {
            addCriterion("account_site like", value, "accountSite");
            return (Criteria) this;
        }

        public Criteria andAccountSiteNotLike(String value) {
            addCriterion("account_site not like", value, "accountSite");
            return (Criteria) this;
        }

        public Criteria andAccountSiteIn(List<String> values) {
            addCriterion("account_site in", values, "accountSite");
            return (Criteria) this;
        }

        public Criteria andAccountSiteNotIn(List<String> values) {
            addCriterion("account_site not in", values, "accountSite");
            return (Criteria) this;
        }

        public Criteria andAccountSiteBetween(String value1, String value2) {
            addCriterion("account_site between", value1, value2, "accountSite");
            return (Criteria) this;
        }

        public Criteria andAccountSiteNotBetween(String value1, String value2) {
            addCriterion("account_site not between", value1, value2, "accountSite");
            return (Criteria) this;
        }

        public Criteria andCookiesIsNull() {
            addCriterion("cookies is null");
            return (Criteria) this;
        }

        public Criteria andCookiesIsNotNull() {
            addCriterion("cookies is not null");
            return (Criteria) this;
        }

        public Criteria andCookiesEqualTo(String value) {
            addCriterion("cookies =", value, "cookies");
            return (Criteria) this;
        }

        public Criteria andCookiesNotEqualTo(String value) {
            addCriterion("cookies <>", value, "cookies");
            return (Criteria) this;
        }

        public Criteria andCookiesGreaterThan(String value) {
            addCriterion("cookies >", value, "cookies");
            return (Criteria) this;
        }

        public Criteria andCookiesGreaterThanOrEqualTo(String value) {
            addCriterion("cookies >=", value, "cookies");
            return (Criteria) this;
        }

        public Criteria andCookiesLessThan(String value) {
            addCriterion("cookies <", value, "cookies");
            return (Criteria) this;
        }

        public Criteria andCookiesLessThanOrEqualTo(String value) {
            addCriterion("cookies <=", value, "cookies");
            return (Criteria) this;
        }

        public Criteria andCookiesLike(String value) {
            addCriterion("cookies like", value, "cookies");
            return (Criteria) this;
        }

        public Criteria andCookiesNotLike(String value) {
            addCriterion("cookies not like", value, "cookies");
            return (Criteria) this;
        }

        public Criteria andCookiesIn(List<String> values) {
            addCriterion("cookies in", values, "cookies");
            return (Criteria) this;
        }

        public Criteria andCookiesNotIn(List<String> values) {
            addCriterion("cookies not in", values, "cookies");
            return (Criteria) this;
        }

        public Criteria andCookiesBetween(String value1, String value2) {
            addCriterion("cookies between", value1, value2, "cookies");
            return (Criteria) this;
        }

        public Criteria andCookiesNotBetween(String value1, String value2) {
            addCriterion("cookies not between", value1, value2, "cookies");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNull() {
            addCriterion("nickname is null");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNotNull() {
            addCriterion("nickname is not null");
            return (Criteria) this;
        }

        public Criteria andNicknameEqualTo(String value) {
            addCriterion("nickname =", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotEqualTo(String value) {
            addCriterion("nickname <>", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThan(String value) {
            addCriterion("nickname >", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("nickname >=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThan(String value) {
            addCriterion("nickname <", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThanOrEqualTo(String value) {
            addCriterion("nickname <=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLike(String value) {
            addCriterion("nickname like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotLike(String value) {
            addCriterion("nickname not like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameIn(List<String> values) {
            addCriterion("nickname in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotIn(List<String> values) {
            addCriterion("nickname not in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameBetween(String value1, String value2) {
            addCriterion("nickname between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotBetween(String value1, String value2) {
            addCriterion("nickname not between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdIsNull() {
            addCriterion("site_room_id is null");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdIsNotNull() {
            addCriterion("site_room_id is not null");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdEqualTo(String value) {
            addCriterion("site_room_id =", value, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdNotEqualTo(String value) {
            addCriterion("site_room_id <>", value, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdGreaterThan(String value) {
            addCriterion("site_room_id >", value, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdGreaterThanOrEqualTo(String value) {
            addCriterion("site_room_id >=", value, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdLessThan(String value) {
            addCriterion("site_room_id <", value, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdLessThanOrEqualTo(String value) {
            addCriterion("site_room_id <=", value, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdLike(String value) {
            addCriterion("site_room_id like", value, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdNotLike(String value) {
            addCriterion("site_room_id not like", value, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdIn(List<String> values) {
            addCriterion("site_room_id in", values, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdNotIn(List<String> values) {
            addCriterion("site_room_id not in", values, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdBetween(String value1, String value2) {
            addCriterion("site_room_id between", value1, value2, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteRoomIdNotBetween(String value1, String value2) {
            addCriterion("site_room_id not between", value1, value2, "siteRoomId");
            return (Criteria) this;
        }

        public Criteria andSiteUidIsNull() {
            addCriterion("site_uid is null");
            return (Criteria) this;
        }

        public Criteria andSiteUidIsNotNull() {
            addCriterion("site_uid is not null");
            return (Criteria) this;
        }

        public Criteria andSiteUidEqualTo(String value) {
            addCriterion("site_uid =", value, "siteUid");
            return (Criteria) this;
        }

        public Criteria andSiteUidNotEqualTo(String value) {
            addCriterion("site_uid <>", value, "siteUid");
            return (Criteria) this;
        }

        public Criteria andSiteUidGreaterThan(String value) {
            addCriterion("site_uid >", value, "siteUid");
            return (Criteria) this;
        }

        public Criteria andSiteUidGreaterThanOrEqualTo(String value) {
            addCriterion("site_uid >=", value, "siteUid");
            return (Criteria) this;
        }

        public Criteria andSiteUidLessThan(String value) {
            addCriterion("site_uid <", value, "siteUid");
            return (Criteria) this;
        }

        public Criteria andSiteUidLessThanOrEqualTo(String value) {
            addCriterion("site_uid <=", value, "siteUid");
            return (Criteria) this;
        }

        public Criteria andSiteUidLike(String value) {
            addCriterion("site_uid like", value, "siteUid");
            return (Criteria) this;
        }

        public Criteria andSiteUidNotLike(String value) {
            addCriterion("site_uid not like", value, "siteUid");
            return (Criteria) this;
        }

        public Criteria andSiteUidIn(List<String> values) {
            addCriterion("site_uid in", values, "siteUid");
            return (Criteria) this;
        }

        public Criteria andSiteUidNotIn(List<String> values) {
            addCriterion("site_uid not in", values, "siteUid");
            return (Criteria) this;
        }

        public Criteria andSiteUidBetween(String value1, String value2) {
            addCriterion("site_uid between", value1, value2, "siteUid");
            return (Criteria) this;
        }

        public Criteria andSiteUidNotBetween(String value1, String value2) {
            addCriterion("site_uid not between", value1, value2, "siteUid");
            return (Criteria) this;
        }

        public Criteria andAdminIsNull() {
            addCriterion("admin is null");
            return (Criteria) this;
        }

        public Criteria andAdminIsNotNull() {
            addCriterion("admin is not null");
            return (Criteria) this;
        }

        public Criteria andAdminEqualTo(Boolean value) {
            addCriterion("admin =", value, "admin");
            return (Criteria) this;
        }

        public Criteria andAdminNotEqualTo(Boolean value) {
            addCriterion("admin <>", value, "admin");
            return (Criteria) this;
        }

        public Criteria andAdminGreaterThan(Boolean value) {
            addCriterion("admin >", value, "admin");
            return (Criteria) this;
        }

        public Criteria andAdminGreaterThanOrEqualTo(Boolean value) {
            addCriterion("admin >=", value, "admin");
            return (Criteria) this;
        }

        public Criteria andAdminLessThan(Boolean value) {
            addCriterion("admin <", value, "admin");
            return (Criteria) this;
        }

        public Criteria andAdminLessThanOrEqualTo(Boolean value) {
            addCriterion("admin <=", value, "admin");
            return (Criteria) this;
        }

        public Criteria andAdminIn(List<Boolean> values) {
            addCriterion("admin in", values, "admin");
            return (Criteria) this;
        }

        public Criteria andAdminNotIn(List<Boolean> values) {
            addCriterion("admin not in", values, "admin");
            return (Criteria) this;
        }

        public Criteria andAdminBetween(Boolean value1, Boolean value2) {
            addCriterion("admin between", value1, value2, "admin");
            return (Criteria) this;
        }

        public Criteria andAdminNotBetween(Boolean value1, Boolean value2) {
            addCriterion("admin not between", value1, value2, "admin");
            return (Criteria) this;
        }

        public Criteria andDisableIsNull() {
            addCriterion("disable is null");
            return (Criteria) this;
        }

        public Criteria andDisableIsNotNull() {
            addCriterion("disable is not null");
            return (Criteria) this;
        }

        public Criteria andDisableEqualTo(Boolean value) {
            addCriterion("disable =", value, "disable");
            return (Criteria) this;
        }

        public Criteria andDisableNotEqualTo(Boolean value) {
            addCriterion("disable <>", value, "disable");
            return (Criteria) this;
        }

        public Criteria andDisableGreaterThan(Boolean value) {
            addCriterion("disable >", value, "disable");
            return (Criteria) this;
        }

        public Criteria andDisableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("disable >=", value, "disable");
            return (Criteria) this;
        }

        public Criteria andDisableLessThan(Boolean value) {
            addCriterion("disable <", value, "disable");
            return (Criteria) this;
        }

        public Criteria andDisableLessThanOrEqualTo(Boolean value) {
            addCriterion("disable <=", value, "disable");
            return (Criteria) this;
        }

        public Criteria andDisableIn(List<Boolean> values) {
            addCriterion("disable in", values, "disable");
            return (Criteria) this;
        }

        public Criteria andDisableNotIn(List<Boolean> values) {
            addCriterion("disable not in", values, "disable");
            return (Criteria) this;
        }

        public Criteria andDisableBetween(Boolean value1, Boolean value2) {
            addCriterion("disable between", value1, value2, "disable");
            return (Criteria) this;
        }

        public Criteria andDisableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("disable not between", value1, value2, "disable");
            return (Criteria) this;
        }

        public Criteria andPostDynamicIsNull() {
            addCriterion("post_dynamic is null");
            return (Criteria) this;
        }

        public Criteria andPostDynamicIsNotNull() {
            addCriterion("post_dynamic is not null");
            return (Criteria) this;
        }

        public Criteria andPostDynamicEqualTo(Boolean value) {
            addCriterion("post_dynamic =", value, "postDynamic");
            return (Criteria) this;
        }

        public Criteria andPostDynamicNotEqualTo(Boolean value) {
            addCriterion("post_dynamic <>", value, "postDynamic");
            return (Criteria) this;
        }

        public Criteria andPostDynamicGreaterThan(Boolean value) {
            addCriterion("post_dynamic >", value, "postDynamic");
            return (Criteria) this;
        }

        public Criteria andPostDynamicGreaterThanOrEqualTo(Boolean value) {
            addCriterion("post_dynamic >=", value, "postDynamic");
            return (Criteria) this;
        }

        public Criteria andPostDynamicLessThan(Boolean value) {
            addCriterion("post_dynamic <", value, "postDynamic");
            return (Criteria) this;
        }

        public Criteria andPostDynamicLessThanOrEqualTo(Boolean value) {
            addCriterion("post_dynamic <=", value, "postDynamic");
            return (Criteria) this;
        }

        public Criteria andPostDynamicIn(List<Boolean> values) {
            addCriterion("post_dynamic in", values, "postDynamic");
            return (Criteria) this;
        }

        public Criteria andPostDynamicNotIn(List<Boolean> values) {
            addCriterion("post_dynamic not in", values, "postDynamic");
            return (Criteria) this;
        }

        public Criteria andPostDynamicBetween(Boolean value1, Boolean value2) {
            addCriterion("post_dynamic between", value1, value2, "postDynamic");
            return (Criteria) this;
        }

        public Criteria andPostDynamicNotBetween(Boolean value1, Boolean value2) {
            addCriterion("post_dynamic not between", value1, value2, "postDynamic");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleIsNull() {
            addCriterion("auto_room_title is null");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleIsNotNull() {
            addCriterion("auto_room_title is not null");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleEqualTo(Boolean value) {
            addCriterion("auto_room_title =", value, "autoRoomTitle");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleNotEqualTo(Boolean value) {
            addCriterion("auto_room_title <>", value, "autoRoomTitle");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleGreaterThan(Boolean value) {
            addCriterion("auto_room_title >", value, "autoRoomTitle");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleGreaterThanOrEqualTo(Boolean value) {
            addCriterion("auto_room_title >=", value, "autoRoomTitle");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleLessThan(Boolean value) {
            addCriterion("auto_room_title <", value, "autoRoomTitle");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleLessThanOrEqualTo(Boolean value) {
            addCriterion("auto_room_title <=", value, "autoRoomTitle");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleIn(List<Boolean> values) {
            addCriterion("auto_room_title in", values, "autoRoomTitle");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleNotIn(List<Boolean> values) {
            addCriterion("auto_room_title not in", values, "autoRoomTitle");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleBetween(Boolean value1, Boolean value2) {
            addCriterion("auto_room_title between", value1, value2, "autoRoomTitle");
            return (Criteria) this;
        }

        public Criteria andAutoRoomTitleNotBetween(Boolean value1, Boolean value2) {
            addCriterion("auto_room_title not between", value1, value2, "autoRoomTitle");
            return (Criteria) this;
        }

        public Criteria andStreamUrlIsNull() {
            addCriterion("stream_url is null");
            return (Criteria) this;
        }

        public Criteria andStreamUrlIsNotNull() {
            addCriterion("stream_url is not null");
            return (Criteria) this;
        }

        public Criteria andStreamUrlEqualTo(String value) {
            addCriterion("stream_url =", value, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andStreamUrlNotEqualTo(String value) {
            addCriterion("stream_url <>", value, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andStreamUrlGreaterThan(String value) {
            addCriterion("stream_url >", value, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andStreamUrlGreaterThanOrEqualTo(String value) {
            addCriterion("stream_url >=", value, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andStreamUrlLessThan(String value) {
            addCriterion("stream_url <", value, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andStreamUrlLessThanOrEqualTo(String value) {
            addCriterion("stream_url <=", value, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andStreamUrlLike(String value) {
            addCriterion("stream_url like", value, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andStreamUrlNotLike(String value) {
            addCriterion("stream_url not like", value, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andStreamUrlIn(List<String> values) {
            addCriterion("stream_url in", values, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andStreamUrlNotIn(List<String> values) {
            addCriterion("stream_url not in", values, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andStreamUrlBetween(String value1, String value2) {
            addCriterion("stream_url between", value1, value2, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andStreamUrlNotBetween(String value1, String value2) {
            addCriterion("stream_url not between", value1, value2, "streamUrl");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdIsNull() {
            addCriterion("current_video_id is null");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdIsNotNull() {
            addCriterion("current_video_id is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdEqualTo(Long value) {
            addCriterion("current_video_id =", value, "currentVideoId");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdNotEqualTo(Long value) {
            addCriterion("current_video_id <>", value, "currentVideoId");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdGreaterThan(Long value) {
            addCriterion("current_video_id >", value, "currentVideoId");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdGreaterThanOrEqualTo(Long value) {
            addCriterion("current_video_id >=", value, "currentVideoId");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdLessThan(Long value) {
            addCriterion("current_video_id <", value, "currentVideoId");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdLessThanOrEqualTo(Long value) {
            addCriterion("current_video_id <=", value, "currentVideoId");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdIn(List<Long> values) {
            addCriterion("current_video_id in", values, "currentVideoId");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdNotIn(List<Long> values) {
            addCriterion("current_video_id not in", values, "currentVideoId");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdBetween(Long value1, Long value2) {
            addCriterion("current_video_id between", value1, value2, "currentVideoId");
            return (Criteria) this;
        }

        public Criteria andCurrentVideoIdNotBetween(Long value1, Long value2) {
            addCriterion("current_video_id not between", value1, value2, "currentVideoId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNull() {
            addCriterion("tenant_id is null");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNotNull() {
            addCriterion("tenant_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenantIdEqualTo(Long value) {
            addCriterion("tenant_id =", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotEqualTo(Long value) {
            addCriterion("tenant_id <>", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThan(Long value) {
            addCriterion("tenant_id >", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThanOrEqualTo(Long value) {
            addCriterion("tenant_id >=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThan(Long value) {
            addCriterion("tenant_id <", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThanOrEqualTo(Long value) {
            addCriterion("tenant_id <=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIn(List<Long> values) {
            addCriterion("tenant_id in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotIn(List<Long> values) {
            addCriterion("tenant_id not in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdBetween(Long value1, Long value2) {
            addCriterion("tenant_id between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotBetween(Long value1, Long value2) {
            addCriterion("tenant_id not between", value1, value2, "tenantId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}