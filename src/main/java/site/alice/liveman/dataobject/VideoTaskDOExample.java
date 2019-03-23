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

public class VideoTaskDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VideoTaskDOExample() {
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

        public Criteria andChannelIdIsNull() {
            addCriterion("channel_id is null");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNotNull() {
            addCriterion("channel_id is not null");
            return (Criteria) this;
        }

        public Criteria andChannelIdEqualTo(Long value) {
            addCriterion("channel_id =", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotEqualTo(Long value) {
            addCriterion("channel_id <>", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThan(Long value) {
            addCriterion("channel_id >", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThanOrEqualTo(Long value) {
            addCriterion("channel_id >=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThan(Long value) {
            addCriterion("channel_id <", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThanOrEqualTo(Long value) {
            addCriterion("channel_id <=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIn(List<Long> values) {
            addCriterion("channel_id in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotIn(List<Long> values) {
            addCriterion("channel_id not in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdBetween(Long value1, Long value2) {
            addCriterion("channel_id between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotBetween(Long value1, Long value2) {
            addCriterion("channel_id not between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andVideoUuidIsNull() {
            addCriterion("video_uuid is null");
            return (Criteria) this;
        }

        public Criteria andVideoUuidIsNotNull() {
            addCriterion("video_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andVideoUuidEqualTo(String value) {
            addCriterion("video_uuid =", value, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andVideoUuidNotEqualTo(String value) {
            addCriterion("video_uuid <>", value, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andVideoUuidGreaterThan(String value) {
            addCriterion("video_uuid >", value, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andVideoUuidGreaterThanOrEqualTo(String value) {
            addCriterion("video_uuid >=", value, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andVideoUuidLessThan(String value) {
            addCriterion("video_uuid <", value, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andVideoUuidLessThanOrEqualTo(String value) {
            addCriterion("video_uuid <=", value, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andVideoUuidLike(String value) {
            addCriterion("video_uuid like", value, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andVideoUuidNotLike(String value) {
            addCriterion("video_uuid not like", value, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andVideoUuidIn(List<String> values) {
            addCriterion("video_uuid in", values, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andVideoUuidNotIn(List<String> values) {
            addCriterion("video_uuid not in", values, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andVideoUuidBetween(String value1, String value2) {
            addCriterion("video_uuid between", value1, value2, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andVideoUuidNotBetween(String value1, String value2) {
            addCriterion("video_uuid not between", value1, value2, "videoUuid");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdIsNull() {
            addCriterion("external_video_id is null");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdIsNotNull() {
            addCriterion("external_video_id is not null");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdEqualTo(String value) {
            addCriterion("external_video_id =", value, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdNotEqualTo(String value) {
            addCriterion("external_video_id <>", value, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdGreaterThan(String value) {
            addCriterion("external_video_id >", value, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdGreaterThanOrEqualTo(String value) {
            addCriterion("external_video_id >=", value, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdLessThan(String value) {
            addCriterion("external_video_id <", value, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdLessThanOrEqualTo(String value) {
            addCriterion("external_video_id <=", value, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdLike(String value) {
            addCriterion("external_video_id like", value, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdNotLike(String value) {
            addCriterion("external_video_id not like", value, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdIn(List<String> values) {
            addCriterion("external_video_id in", values, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdNotIn(List<String> values) {
            addCriterion("external_video_id not in", values, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdBetween(String value1, String value2) {
            addCriterion("external_video_id between", value1, value2, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoIdNotBetween(String value1, String value2) {
            addCriterion("external_video_id not between", value1, value2, "externalVideoId");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartIsNull() {
            addCriterion("external_video_part is null");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartIsNotNull() {
            addCriterion("external_video_part is not null");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartEqualTo(String value) {
            addCriterion("external_video_part =", value, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartNotEqualTo(String value) {
            addCriterion("external_video_part <>", value, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartGreaterThan(String value) {
            addCriterion("external_video_part >", value, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartGreaterThanOrEqualTo(String value) {
            addCriterion("external_video_part >=", value, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartLessThan(String value) {
            addCriterion("external_video_part <", value, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartLessThanOrEqualTo(String value) {
            addCriterion("external_video_part <=", value, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartLike(String value) {
            addCriterion("external_video_part like", value, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartNotLike(String value) {
            addCriterion("external_video_part not like", value, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartIn(List<String> values) {
            addCriterion("external_video_part in", values, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartNotIn(List<String> values) {
            addCriterion("external_video_part not in", values, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartBetween(String value1, String value2) {
            addCriterion("external_video_part between", value1, value2, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andExternalVideoPartNotBetween(String value1, String value2) {
            addCriterion("external_video_part not between", value1, value2, "externalVideoPart");
            return (Criteria) this;
        }

        public Criteria andVideoTitleIsNull() {
            addCriterion("video_title is null");
            return (Criteria) this;
        }

        public Criteria andVideoTitleIsNotNull() {
            addCriterion("video_title is not null");
            return (Criteria) this;
        }

        public Criteria andVideoTitleEqualTo(String value) {
            addCriterion("video_title =", value, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoTitleNotEqualTo(String value) {
            addCriterion("video_title <>", value, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoTitleGreaterThan(String value) {
            addCriterion("video_title >", value, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoTitleGreaterThanOrEqualTo(String value) {
            addCriterion("video_title >=", value, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoTitleLessThan(String value) {
            addCriterion("video_title <", value, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoTitleLessThanOrEqualTo(String value) {
            addCriterion("video_title <=", value, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoTitleLike(String value) {
            addCriterion("video_title like", value, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoTitleNotLike(String value) {
            addCriterion("video_title not like", value, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoTitleIn(List<String> values) {
            addCriterion("video_title in", values, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoTitleNotIn(List<String> values) {
            addCriterion("video_title not in", values, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoTitleBetween(String value1, String value2) {
            addCriterion("video_title between", value1, value2, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoTitleNotBetween(String value1, String value2) {
            addCriterion("video_title not between", value1, value2, "videoTitle");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlIsNull() {
            addCriterion("video_info_url is null");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlIsNotNull() {
            addCriterion("video_info_url is not null");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlEqualTo(String value) {
            addCriterion("video_info_url =", value, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlNotEqualTo(String value) {
            addCriterion("video_info_url <>", value, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlGreaterThan(String value) {
            addCriterion("video_info_url >", value, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlGreaterThanOrEqualTo(String value) {
            addCriterion("video_info_url >=", value, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlLessThan(String value) {
            addCriterion("video_info_url <", value, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlLessThanOrEqualTo(String value) {
            addCriterion("video_info_url <=", value, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlLike(String value) {
            addCriterion("video_info_url like", value, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlNotLike(String value) {
            addCriterion("video_info_url not like", value, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlIn(List<String> values) {
            addCriterion("video_info_url in", values, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlNotIn(List<String> values) {
            addCriterion("video_info_url not in", values, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlBetween(String value1, String value2) {
            addCriterion("video_info_url between", value1, value2, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andVideoInfoUrlNotBetween(String value1, String value2) {
            addCriterion("video_info_url not between", value1, value2, "videoInfoUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlIsNull() {
            addCriterion("media_url is null");
            return (Criteria) this;
        }

        public Criteria andMediaUrlIsNotNull() {
            addCriterion("media_url is not null");
            return (Criteria) this;
        }

        public Criteria andMediaUrlEqualTo(String value) {
            addCriterion("media_url =", value, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlNotEqualTo(String value) {
            addCriterion("media_url <>", value, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlGreaterThan(String value) {
            addCriterion("media_url >", value, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlGreaterThanOrEqualTo(String value) {
            addCriterion("media_url >=", value, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlLessThan(String value) {
            addCriterion("media_url <", value, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlLessThanOrEqualTo(String value) {
            addCriterion("media_url <=", value, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlLike(String value) {
            addCriterion("media_url like", value, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlNotLike(String value) {
            addCriterion("media_url not like", value, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlIn(List<String> values) {
            addCriterion("media_url in", values, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlNotIn(List<String> values) {
            addCriterion("media_url not in", values, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlBetween(String value1, String value2) {
            addCriterion("media_url between", value1, value2, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaUrlNotBetween(String value1, String value2) {
            addCriterion("media_url not between", value1, value2, "mediaUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlIsNull() {
            addCriterion("media_proxy_url is null");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlIsNotNull() {
            addCriterion("media_proxy_url is not null");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlEqualTo(String value) {
            addCriterion("media_proxy_url =", value, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlNotEqualTo(String value) {
            addCriterion("media_proxy_url <>", value, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlGreaterThan(String value) {
            addCriterion("media_proxy_url >", value, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlGreaterThanOrEqualTo(String value) {
            addCriterion("media_proxy_url >=", value, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlLessThan(String value) {
            addCriterion("media_proxy_url <", value, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlLessThanOrEqualTo(String value) {
            addCriterion("media_proxy_url <=", value, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlLike(String value) {
            addCriterion("media_proxy_url like", value, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlNotLike(String value) {
            addCriterion("media_proxy_url not like", value, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlIn(List<String> values) {
            addCriterion("media_proxy_url in", values, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlNotIn(List<String> values) {
            addCriterion("media_proxy_url not in", values, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlBetween(String value1, String value2) {
            addCriterion("media_proxy_url between", value1, value2, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaProxyUrlNotBetween(String value1, String value2) {
            addCriterion("media_proxy_url not between", value1, value2, "mediaProxyUrl");
            return (Criteria) this;
        }

        public Criteria andMediaFormatIsNull() {
            addCriterion("media_format is null");
            return (Criteria) this;
        }

        public Criteria andMediaFormatIsNotNull() {
            addCriterion("media_format is not null");
            return (Criteria) this;
        }

        public Criteria andMediaFormatEqualTo(String value) {
            addCriterion("media_format =", value, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andMediaFormatNotEqualTo(String value) {
            addCriterion("media_format <>", value, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andMediaFormatGreaterThan(String value) {
            addCriterion("media_format >", value, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andMediaFormatGreaterThanOrEqualTo(String value) {
            addCriterion("media_format >=", value, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andMediaFormatLessThan(String value) {
            addCriterion("media_format <", value, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andMediaFormatLessThanOrEqualTo(String value) {
            addCriterion("media_format <=", value, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andMediaFormatLike(String value) {
            addCriterion("media_format like", value, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andMediaFormatNotLike(String value) {
            addCriterion("media_format not like", value, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andMediaFormatIn(List<String> values) {
            addCriterion("media_format in", values, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andMediaFormatNotIn(List<String> values) {
            addCriterion("media_format not in", values, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andMediaFormatBetween(String value1, String value2) {
            addCriterion("media_format between", value1, value2, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andMediaFormatNotBetween(String value1, String value2) {
            addCriterion("media_format not between", value1, value2, "mediaFormat");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAudioBannedIsNull() {
            addCriterion("audio_banned is null");
            return (Criteria) this;
        }

        public Criteria andAudioBannedIsNotNull() {
            addCriterion("audio_banned is not null");
            return (Criteria) this;
        }

        public Criteria andAudioBannedEqualTo(Boolean value) {
            addCriterion("audio_banned =", value, "audioBanned");
            return (Criteria) this;
        }

        public Criteria andAudioBannedNotEqualTo(Boolean value) {
            addCriterion("audio_banned <>", value, "audioBanned");
            return (Criteria) this;
        }

        public Criteria andAudioBannedGreaterThan(Boolean value) {
            addCriterion("audio_banned >", value, "audioBanned");
            return (Criteria) this;
        }

        public Criteria andAudioBannedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("audio_banned >=", value, "audioBanned");
            return (Criteria) this;
        }

        public Criteria andAudioBannedLessThan(Boolean value) {
            addCriterion("audio_banned <", value, "audioBanned");
            return (Criteria) this;
        }

        public Criteria andAudioBannedLessThanOrEqualTo(Boolean value) {
            addCriterion("audio_banned <=", value, "audioBanned");
            return (Criteria) this;
        }

        public Criteria andAudioBannedIn(List<Boolean> values) {
            addCriterion("audio_banned in", values, "audioBanned");
            return (Criteria) this;
        }

        public Criteria andAudioBannedNotIn(List<Boolean> values) {
            addCriterion("audio_banned not in", values, "audioBanned");
            return (Criteria) this;
        }

        public Criteria andAudioBannedBetween(Boolean value1, Boolean value2) {
            addCriterion("audio_banned between", value1, value2, "audioBanned");
            return (Criteria) this;
        }

        public Criteria andAudioBannedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("audio_banned not between", value1, value2, "audioBanned");
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

        public Criteria andEncodeMethodIsNull() {
            addCriterion("encode_method is null");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodIsNotNull() {
            addCriterion("encode_method is not null");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodEqualTo(String value) {
            addCriterion("encode_method =", value, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodNotEqualTo(String value) {
            addCriterion("encode_method <>", value, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodGreaterThan(String value) {
            addCriterion("encode_method >", value, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodGreaterThanOrEqualTo(String value) {
            addCriterion("encode_method >=", value, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodLessThan(String value) {
            addCriterion("encode_method <", value, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodLessThanOrEqualTo(String value) {
            addCriterion("encode_method <=", value, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodLike(String value) {
            addCriterion("encode_method like", value, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodNotLike(String value) {
            addCriterion("encode_method not like", value, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodIn(List<String> values) {
            addCriterion("encode_method in", values, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodNotIn(List<String> values) {
            addCriterion("encode_method not in", values, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodBetween(String value1, String value2) {
            addCriterion("encode_method between", value1, value2, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andEncodeMethodNotBetween(String value1, String value2) {
            addCriterion("encode_method not between", value1, value2, "encodeMethod");
            return (Criteria) this;
        }

        public Criteria andVerticalIsNull() {
            addCriterion("vertical is null");
            return (Criteria) this;
        }

        public Criteria andVerticalIsNotNull() {
            addCriterion("vertical is not null");
            return (Criteria) this;
        }

        public Criteria andVerticalEqualTo(Boolean value) {
            addCriterion("vertical =", value, "vertical");
            return (Criteria) this;
        }

        public Criteria andVerticalNotEqualTo(Boolean value) {
            addCriterion("vertical <>", value, "vertical");
            return (Criteria) this;
        }

        public Criteria andVerticalGreaterThan(Boolean value) {
            addCriterion("vertical >", value, "vertical");
            return (Criteria) this;
        }

        public Criteria andVerticalGreaterThanOrEqualTo(Boolean value) {
            addCriterion("vertical >=", value, "vertical");
            return (Criteria) this;
        }

        public Criteria andVerticalLessThan(Boolean value) {
            addCriterion("vertical <", value, "vertical");
            return (Criteria) this;
        }

        public Criteria andVerticalLessThanOrEqualTo(Boolean value) {
            addCriterion("vertical <=", value, "vertical");
            return (Criteria) this;
        }

        public Criteria andVerticalIn(List<Boolean> values) {
            addCriterion("vertical in", values, "vertical");
            return (Criteria) this;
        }

        public Criteria andVerticalNotIn(List<Boolean> values) {
            addCriterion("vertical not in", values, "vertical");
            return (Criteria) this;
        }

        public Criteria andVerticalBetween(Boolean value1, Boolean value2) {
            addCriterion("vertical between", value1, value2, "vertical");
            return (Criteria) this;
        }

        public Criteria andVerticalNotBetween(Boolean value1, Boolean value2) {
            addCriterion("vertical not between", value1, value2, "vertical");
            return (Criteria) this;
        }

        public Criteria andFrameRateIsNull() {
            addCriterion("frame_rate is null");
            return (Criteria) this;
        }

        public Criteria andFrameRateIsNotNull() {
            addCriterion("frame_rate is not null");
            return (Criteria) this;
        }

        public Criteria andFrameRateEqualTo(Double value) {
            addCriterion("frame_rate =", value, "frameRate");
            return (Criteria) this;
        }

        public Criteria andFrameRateNotEqualTo(Double value) {
            addCriterion("frame_rate <>", value, "frameRate");
            return (Criteria) this;
        }

        public Criteria andFrameRateGreaterThan(Double value) {
            addCriterion("frame_rate >", value, "frameRate");
            return (Criteria) this;
        }

        public Criteria andFrameRateGreaterThanOrEqualTo(Double value) {
            addCriterion("frame_rate >=", value, "frameRate");
            return (Criteria) this;
        }

        public Criteria andFrameRateLessThan(Double value) {
            addCriterion("frame_rate <", value, "frameRate");
            return (Criteria) this;
        }

        public Criteria andFrameRateLessThanOrEqualTo(Double value) {
            addCriterion("frame_rate <=", value, "frameRate");
            return (Criteria) this;
        }

        public Criteria andFrameRateIn(List<Double> values) {
            addCriterion("frame_rate in", values, "frameRate");
            return (Criteria) this;
        }

        public Criteria andFrameRateNotIn(List<Double> values) {
            addCriterion("frame_rate not in", values, "frameRate");
            return (Criteria) this;
        }

        public Criteria andFrameRateBetween(Double value1, Double value2) {
            addCriterion("frame_rate between", value1, value2, "frameRate");
            return (Criteria) this;
        }

        public Criteria andFrameRateNotBetween(Double value1, Double value2) {
            addCriterion("frame_rate not between", value1, value2, "frameRate");
            return (Criteria) this;
        }

        public Criteria andResolutionIsNull() {
            addCriterion("resolution is null");
            return (Criteria) this;
        }

        public Criteria andResolutionIsNotNull() {
            addCriterion("resolution is not null");
            return (Criteria) this;
        }

        public Criteria andResolutionEqualTo(String value) {
            addCriterion("resolution =", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionNotEqualTo(String value) {
            addCriterion("resolution <>", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionGreaterThan(String value) {
            addCriterion("resolution >", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionGreaterThanOrEqualTo(String value) {
            addCriterion("resolution >=", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionLessThan(String value) {
            addCriterion("resolution <", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionLessThanOrEqualTo(String value) {
            addCriterion("resolution <=", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionLike(String value) {
            addCriterion("resolution like", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionNotLike(String value) {
            addCriterion("resolution not like", value, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionIn(List<String> values) {
            addCriterion("resolution in", values, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionNotIn(List<String> values) {
            addCriterion("resolution not in", values, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionBetween(String value1, String value2) {
            addCriterion("resolution between", value1, value2, "resolution");
            return (Criteria) this;
        }

        public Criteria andResolutionNotBetween(String value1, String value2) {
            addCriterion("resolution not between", value1, value2, "resolution");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
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