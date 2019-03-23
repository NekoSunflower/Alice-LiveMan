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

public class SystemSettingDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SystemSettingDOExample() {
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

        public Criteria andBannedYoutubeChannelIsNull() {
            addCriterion("banned_youtube_channel is null");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelIsNotNull() {
            addCriterion("banned_youtube_channel is not null");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelEqualTo(String value) {
            addCriterion("banned_youtube_channel =", value, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelNotEqualTo(String value) {
            addCriterion("banned_youtube_channel <>", value, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelGreaterThan(String value) {
            addCriterion("banned_youtube_channel >", value, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelGreaterThanOrEqualTo(String value) {
            addCriterion("banned_youtube_channel >=", value, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelLessThan(String value) {
            addCriterion("banned_youtube_channel <", value, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelLessThanOrEqualTo(String value) {
            addCriterion("banned_youtube_channel <=", value, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelLike(String value) {
            addCriterion("banned_youtube_channel like", value, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelNotLike(String value) {
            addCriterion("banned_youtube_channel not like", value, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelIn(List<String> values) {
            addCriterion("banned_youtube_channel in", values, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelNotIn(List<String> values) {
            addCriterion("banned_youtube_channel not in", values, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelBetween(String value1, String value2) {
            addCriterion("banned_youtube_channel between", value1, value2, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedYoutubeChannelNotBetween(String value1, String value2) {
            addCriterion("banned_youtube_channel not between", value1, value2, "bannedYoutubeChannel");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsIsNull() {
            addCriterion("banned_keywords is null");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsIsNotNull() {
            addCriterion("banned_keywords is not null");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsEqualTo(String value) {
            addCriterion("banned_keywords =", value, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsNotEqualTo(String value) {
            addCriterion("banned_keywords <>", value, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsGreaterThan(String value) {
            addCriterion("banned_keywords >", value, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("banned_keywords >=", value, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsLessThan(String value) {
            addCriterion("banned_keywords <", value, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsLessThanOrEqualTo(String value) {
            addCriterion("banned_keywords <=", value, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsLike(String value) {
            addCriterion("banned_keywords like", value, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsNotLike(String value) {
            addCriterion("banned_keywords not like", value, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsIn(List<String> values) {
            addCriterion("banned_keywords in", values, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsNotIn(List<String> values) {
            addCriterion("banned_keywords not in", values, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsBetween(String value1, String value2) {
            addCriterion("banned_keywords between", value1, value2, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andBannedKeywordsNotBetween(String value1, String value2) {
            addCriterion("banned_keywords not between", value1, value2, "bannedKeywords");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdIsNull() {
            addCriterion("one_drive_client_id is null");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdIsNotNull() {
            addCriterion("one_drive_client_id is not null");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdEqualTo(String value) {
            addCriterion("one_drive_client_id =", value, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdNotEqualTo(String value) {
            addCriterion("one_drive_client_id <>", value, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdGreaterThan(String value) {
            addCriterion("one_drive_client_id >", value, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdGreaterThanOrEqualTo(String value) {
            addCriterion("one_drive_client_id >=", value, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdLessThan(String value) {
            addCriterion("one_drive_client_id <", value, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdLessThanOrEqualTo(String value) {
            addCriterion("one_drive_client_id <=", value, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdLike(String value) {
            addCriterion("one_drive_client_id like", value, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdNotLike(String value) {
            addCriterion("one_drive_client_id not like", value, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdIn(List<String> values) {
            addCriterion("one_drive_client_id in", values, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdNotIn(List<String> values) {
            addCriterion("one_drive_client_id not in", values, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdBetween(String value1, String value2) {
            addCriterion("one_drive_client_id between", value1, value2, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientIdNotBetween(String value1, String value2) {
            addCriterion("one_drive_client_id not between", value1, value2, "oneDriveClientId");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretIsNull() {
            addCriterion("one_drive_client_secret is null");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretIsNotNull() {
            addCriterion("one_drive_client_secret is not null");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretEqualTo(String value) {
            addCriterion("one_drive_client_secret =", value, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretNotEqualTo(String value) {
            addCriterion("one_drive_client_secret <>", value, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretGreaterThan(String value) {
            addCriterion("one_drive_client_secret >", value, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretGreaterThanOrEqualTo(String value) {
            addCriterion("one_drive_client_secret >=", value, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretLessThan(String value) {
            addCriterion("one_drive_client_secret <", value, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretLessThanOrEqualTo(String value) {
            addCriterion("one_drive_client_secret <=", value, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretLike(String value) {
            addCriterion("one_drive_client_secret like", value, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretNotLike(String value) {
            addCriterion("one_drive_client_secret not like", value, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretIn(List<String> values) {
            addCriterion("one_drive_client_secret in", values, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretNotIn(List<String> values) {
            addCriterion("one_drive_client_secret not in", values, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretBetween(String value1, String value2) {
            addCriterion("one_drive_client_secret between", value1, value2, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveClientSecretNotBetween(String value1, String value2) {
            addCriterion("one_drive_client_secret not between", value1, value2, "oneDriveClientSecret");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenIsNull() {
            addCriterion("one_drive_token is null");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenIsNotNull() {
            addCriterion("one_drive_token is not null");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenEqualTo(String value) {
            addCriterion("one_drive_token =", value, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenNotEqualTo(String value) {
            addCriterion("one_drive_token <>", value, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenGreaterThan(String value) {
            addCriterion("one_drive_token >", value, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenGreaterThanOrEqualTo(String value) {
            addCriterion("one_drive_token >=", value, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenLessThan(String value) {
            addCriterion("one_drive_token <", value, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenLessThanOrEqualTo(String value) {
            addCriterion("one_drive_token <=", value, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenLike(String value) {
            addCriterion("one_drive_token like", value, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenNotLike(String value) {
            addCriterion("one_drive_token not like", value, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenIn(List<String> values) {
            addCriterion("one_drive_token in", values, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenNotIn(List<String> values) {
            addCriterion("one_drive_token not in", values, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenBetween(String value1, String value2) {
            addCriterion("one_drive_token between", value1, value2, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andOneDriveTokenNotBetween(String value1, String value2) {
            addCriterion("one_drive_token not between", value1, value2, "oneDriveToken");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeIsNull() {
            addCriterion("pre_re_encode is null");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeIsNotNull() {
            addCriterion("pre_re_encode is not null");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeEqualTo(Boolean value) {
            addCriterion("pre_re_encode =", value, "preReEncode");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeNotEqualTo(Boolean value) {
            addCriterion("pre_re_encode <>", value, "preReEncode");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeGreaterThan(Boolean value) {
            addCriterion("pre_re_encode >", value, "preReEncode");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("pre_re_encode >=", value, "preReEncode");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeLessThan(Boolean value) {
            addCriterion("pre_re_encode <", value, "preReEncode");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeLessThanOrEqualTo(Boolean value) {
            addCriterion("pre_re_encode <=", value, "preReEncode");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeIn(List<Boolean> values) {
            addCriterion("pre_re_encode in", values, "preReEncode");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeNotIn(List<Boolean> values) {
            addCriterion("pre_re_encode not in", values, "preReEncode");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeBetween(Boolean value1, Boolean value2) {
            addCriterion("pre_re_encode between", value1, value2, "preReEncode");
            return (Criteria) this;
        }

        public Criteria andPreReEncodeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("pre_re_encode not between", value1, value2, "preReEncode");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionIsNull() {
            addCriterion("default_resolution is null");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionIsNotNull() {
            addCriterion("default_resolution is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionEqualTo(String value) {
            addCriterion("default_resolution =", value, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionNotEqualTo(String value) {
            addCriterion("default_resolution <>", value, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionGreaterThan(String value) {
            addCriterion("default_resolution >", value, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionGreaterThanOrEqualTo(String value) {
            addCriterion("default_resolution >=", value, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionLessThan(String value) {
            addCriterion("default_resolution <", value, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionLessThanOrEqualTo(String value) {
            addCriterion("default_resolution <=", value, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionLike(String value) {
            addCriterion("default_resolution like", value, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionNotLike(String value) {
            addCriterion("default_resolution not like", value, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionIn(List<String> values) {
            addCriterion("default_resolution in", values, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionNotIn(List<String> values) {
            addCriterion("default_resolution not in", values, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionBetween(String value1, String value2) {
            addCriterion("default_resolution between", value1, value2, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andDefaultResolutionNotBetween(String value1, String value2) {
            addCriterion("default_resolution not between", value1, value2, "defaultResolution");
            return (Criteria) this;
        }

        public Criteria andBaseUrlIsNull() {
            addCriterion("base_url is null");
            return (Criteria) this;
        }

        public Criteria andBaseUrlIsNotNull() {
            addCriterion("base_url is not null");
            return (Criteria) this;
        }

        public Criteria andBaseUrlEqualTo(String value) {
            addCriterion("base_url =", value, "baseUrl");
            return (Criteria) this;
        }

        public Criteria andBaseUrlNotEqualTo(String value) {
            addCriterion("base_url <>", value, "baseUrl");
            return (Criteria) this;
        }

        public Criteria andBaseUrlGreaterThan(String value) {
            addCriterion("base_url >", value, "baseUrl");
            return (Criteria) this;
        }

        public Criteria andBaseUrlGreaterThanOrEqualTo(String value) {
            addCriterion("base_url >=", value, "baseUrl");
            return (Criteria) this;
        }

        public Criteria andBaseUrlLessThan(String value) {
            addCriterion("base_url <", value, "baseUrl");
            return (Criteria) this;
        }

        public Criteria andBaseUrlLessThanOrEqualTo(String value) {
            addCriterion("base_url <=", value, "baseUrl");
            return (Criteria) this;
        }

        public Criteria andBaseUrlLike(String value) {
            addCriterion("base_url like", value, "baseUrl");
            return (Criteria) this;
        }

        public Criteria andBaseUrlNotLike(String value) {
            addCriterion("base_url not like", value, "baseUrl");
            return (Criteria) this;
        }

        public Criteria andBaseUrlIn(List<String> values) {
            addCriterion("base_url in", values, "baseUrl");
            return (Criteria) this;
        }

        public Criteria andBaseUrlNotIn(List<String> values) {
            addCriterion("base_url not in", values, "baseUrl");
            return (Criteria) this;
        }

        public Criteria andBaseUrlBetween(String value1, String value2) {
            addCriterion("base_url between", value1, value2, "baseUrl");
            return (Criteria) this;
        }

        public Criteria andBaseUrlNotBetween(String value1, String value2) {
            addCriterion("base_url not between", value1, value2, "baseUrl");
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