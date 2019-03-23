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

public class ChannelDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChannelDOExample() {
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

        public Criteria andDefaultAccountIdIsNull() {
            addCriterion("default_account_id is null");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdIsNotNull() {
            addCriterion("default_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdEqualTo(String value) {
            addCriterion("default_account_id =", value, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdNotEqualTo(String value) {
            addCriterion("default_account_id <>", value, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdGreaterThan(String value) {
            addCriterion("default_account_id >", value, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("default_account_id >=", value, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdLessThan(String value) {
            addCriterion("default_account_id <", value, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdLessThanOrEqualTo(String value) {
            addCriterion("default_account_id <=", value, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdLike(String value) {
            addCriterion("default_account_id like", value, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdNotLike(String value) {
            addCriterion("default_account_id not like", value, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdIn(List<String> values) {
            addCriterion("default_account_id in", values, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdNotIn(List<String> values) {
            addCriterion("default_account_id not in", values, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdBetween(String value1, String value2) {
            addCriterion("default_account_id between", value1, value2, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDefaultAccountIdNotBetween(String value1, String value2) {
            addCriterion("default_account_id not between", value1, value2, "defaultAccountId");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidIsNull() {
            addCriterion("dynamic_post_accountId is null");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidIsNotNull() {
            addCriterion("dynamic_post_accountId is not null");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidEqualTo(String value) {
            addCriterion("dynamic_post_accountId =", value, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidNotEqualTo(String value) {
            addCriterion("dynamic_post_accountId <>", value, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidGreaterThan(String value) {
            addCriterion("dynamic_post_accountId >", value, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidGreaterThanOrEqualTo(String value) {
            addCriterion("dynamic_post_accountId >=", value, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidLessThan(String value) {
            addCriterion("dynamic_post_accountId <", value, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidLessThanOrEqualTo(String value) {
            addCriterion("dynamic_post_accountId <=", value, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidLike(String value) {
            addCriterion("dynamic_post_accountId like", value, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidNotLike(String value) {
            addCriterion("dynamic_post_accountId not like", value, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidIn(List<String> values) {
            addCriterion("dynamic_post_accountId in", values, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidNotIn(List<String> values) {
            addCriterion("dynamic_post_accountId not in", values, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidBetween(String value1, String value2) {
            addCriterion("dynamic_post_accountId between", value1, value2, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andDynamicPostAccountidNotBetween(String value1, String value2) {
            addCriterion("dynamic_post_accountId not between", value1, value2, "dynamicPostAccountid");
            return (Criteria) this;
        }

        public Criteria andChannelUrlIsNull() {
            addCriterion("channel_url is null");
            return (Criteria) this;
        }

        public Criteria andChannelUrlIsNotNull() {
            addCriterion("channel_url is not null");
            return (Criteria) this;
        }

        public Criteria andChannelUrlEqualTo(String value) {
            addCriterion("channel_url =", value, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelUrlNotEqualTo(String value) {
            addCriterion("channel_url <>", value, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelUrlGreaterThan(String value) {
            addCriterion("channel_url >", value, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelUrlGreaterThanOrEqualTo(String value) {
            addCriterion("channel_url >=", value, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelUrlLessThan(String value) {
            addCriterion("channel_url <", value, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelUrlLessThanOrEqualTo(String value) {
            addCriterion("channel_url <=", value, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelUrlLike(String value) {
            addCriterion("channel_url like", value, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelUrlNotLike(String value) {
            addCriterion("channel_url not like", value, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelUrlIn(List<String> values) {
            addCriterion("channel_url in", values, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelUrlNotIn(List<String> values) {
            addCriterion("channel_url not in", values, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelUrlBetween(String value1, String value2) {
            addCriterion("channel_url between", value1, value2, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelUrlNotBetween(String value1, String value2) {
            addCriterion("channel_url not between", value1, value2, "channelUrl");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNull() {
            addCriterion("channel_name is null");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNotNull() {
            addCriterion("channel_name is not null");
            return (Criteria) this;
        }

        public Criteria andChannelNameEqualTo(String value) {
            addCriterion("channel_name =", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotEqualTo(String value) {
            addCriterion("channel_name <>", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThan(String value) {
            addCriterion("channel_name >", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThanOrEqualTo(String value) {
            addCriterion("channel_name >=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThan(String value) {
            addCriterion("channel_name <", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThanOrEqualTo(String value) {
            addCriterion("channel_name <=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLike(String value) {
            addCriterion("channel_name like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotLike(String value) {
            addCriterion("channel_name not like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameIn(List<String> values) {
            addCriterion("channel_name in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotIn(List<String> values) {
            addCriterion("channel_name not in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameBetween(String value1, String value2) {
            addCriterion("channel_name between", value1, value2, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotBetween(String value1, String value2) {
            addCriterion("channel_name not between", value1, value2, "channelName");
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

        public Criteria andDefaultAreaIsNull() {
            addCriterion("default_area is null");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaIsNotNull() {
            addCriterion("default_area is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaEqualTo(String value) {
            addCriterion("default_area =", value, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaNotEqualTo(String value) {
            addCriterion("default_area <>", value, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaGreaterThan(String value) {
            addCriterion("default_area >", value, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaGreaterThanOrEqualTo(String value) {
            addCriterion("default_area >=", value, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaLessThan(String value) {
            addCriterion("default_area <", value, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaLessThanOrEqualTo(String value) {
            addCriterion("default_area <=", value, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaLike(String value) {
            addCriterion("default_area like", value, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaNotLike(String value) {
            addCriterion("default_area not like", value, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaIn(List<String> values) {
            addCriterion("default_area in", values, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaNotIn(List<String> values) {
            addCriterion("default_area not in", values, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaBetween(String value1, String value2) {
            addCriterion("default_area between", value1, value2, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andDefaultAreaNotBetween(String value1, String value2) {
            addCriterion("default_area not between", value1, value2, "defaultArea");
            return (Criteria) this;
        }

        public Criteria andNeedRecordIsNull() {
            addCriterion("need_record is null");
            return (Criteria) this;
        }

        public Criteria andNeedRecordIsNotNull() {
            addCriterion("need_record is not null");
            return (Criteria) this;
        }

        public Criteria andNeedRecordEqualTo(Boolean value) {
            addCriterion("need_record =", value, "needRecord");
            return (Criteria) this;
        }

        public Criteria andNeedRecordNotEqualTo(Boolean value) {
            addCriterion("need_record <>", value, "needRecord");
            return (Criteria) this;
        }

        public Criteria andNeedRecordGreaterThan(Boolean value) {
            addCriterion("need_record >", value, "needRecord");
            return (Criteria) this;
        }

        public Criteria andNeedRecordGreaterThanOrEqualTo(Boolean value) {
            addCriterion("need_record >=", value, "needRecord");
            return (Criteria) this;
        }

        public Criteria andNeedRecordLessThan(Boolean value) {
            addCriterion("need_record <", value, "needRecord");
            return (Criteria) this;
        }

        public Criteria andNeedRecordLessThanOrEqualTo(Boolean value) {
            addCriterion("need_record <=", value, "needRecord");
            return (Criteria) this;
        }

        public Criteria andNeedRecordIn(List<Boolean> values) {
            addCriterion("need_record in", values, "needRecord");
            return (Criteria) this;
        }

        public Criteria andNeedRecordNotIn(List<Boolean> values) {
            addCriterion("need_record not in", values, "needRecord");
            return (Criteria) this;
        }

        public Criteria andNeedRecordBetween(Boolean value1, Boolean value2) {
            addCriterion("need_record between", value1, value2, "needRecord");
            return (Criteria) this;
        }

        public Criteria andNeedRecordNotBetween(Boolean value1, Boolean value2) {
            addCriterion("need_record not between", value1, value2, "needRecord");
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