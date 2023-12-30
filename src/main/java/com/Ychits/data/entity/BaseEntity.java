package com.Ychits.data.entity;

import com.Ychits.util.CommonUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity {

    String createdBy;
    String modifiedBy;
    String createdByUname;
    String modifiedByUname;
    String createdOn;
    String modifiedOn;

    public void createEntity(String userId, String userName) {
        this.createdOn = CommonUtil.getCurrentDate();
        this.createdBy = userId;
        this.createdByUname = userName;
    }

    public void updateEntity(String userId, String userName) {
        this.modifiedOn = CommonUtil.getCurrentDate();
        this.modifiedBy = userId;
        this.modifiedByUname = userName;
    }

    public String getCreatedOn() {
        return CommonUtil.getFormattedDate(createdOn);
    }

    public String getModifiedOn() {
        return CommonUtil.getFormattedDate(modifiedOn);
    }

    public String unFormattedCreatedDate() {
        return this.createdOn;
    }

    public String unFormattedModifiedDate() {
        return this.modifiedOn;
    }


}
