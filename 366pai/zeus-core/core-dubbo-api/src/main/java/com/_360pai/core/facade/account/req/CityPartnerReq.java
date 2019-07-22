package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;

/**
 * create by liuhaolei on 2018/11/1
 */
public class CityPartnerReq {

    public static class SaveCityPanrnerReq extends RequestModel {

        private String cityId;

        private String officeSize;

        private String contactName;

        private String contactPhone;

        private String position;

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getOfficeSize() {
            return officeSize;
        }

        public void setOfficeSize(String officeSize) {
            this.officeSize = officeSize;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }

}
