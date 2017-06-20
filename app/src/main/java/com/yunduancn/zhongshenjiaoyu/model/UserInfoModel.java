package com.yunduancn.zhongshenjiaoyu.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/15.
 */

public class UserInfoModel implements Serializable{
    /**
     * {
     "verifiedMobile": "18079176112",
     "nickname": "m149645024677",
     "email": "user_nl3b85p23@edusoho.net",
     "approvalStatus": "未认证",
     "largeAvatar": "http://ceshi.yunduancn.cn/assets/img/default/avatar-large.png?7.5.12",
     "gender": "保密",
     "dateField1": null,
     "job": "",
     "signature": null,
     "varcharField1": ""
     }
     */

    private String verifiedMobile;
    private String nickname;
    private String email;
    private String approvalStatus;
    private String largeAvatar;
    private String gender;
    private String dateField1;
    private String job;
    private String signature;
    private String varcharField1;

    public String getVerifiedMobile() {
        return verifiedMobile;
    }

    public void setVerifiedMobile(String verifiedMobile) {
        this.verifiedMobile = verifiedMobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getLargeAvatar() {
        return largeAvatar;
    }

    public void setLargeAvatar(String largeAvatar) {
        this.largeAvatar = largeAvatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateField1() {
        return dateField1;
    }

    public void setDateField1(String dateField1) {
        this.dateField1 = dateField1;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getVarcharField1() {
        return varcharField1;
    }

    public void setVarcharField1(String varcharField1) {
        this.varcharField1 = varcharField1;
    }

    @Override
    public String toString() {
        return "UserInfoModel{" +
                "verifiedMobile='" + verifiedMobile + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", largeAvatar='" + largeAvatar + '\'' +
                ", gender='" + gender + '\'' +
                ", dateField1='" + dateField1 + '\'' +
                ", job='" + job + '\'' +
                ", signature='" + signature + '\'' +
                ", varcharField1='" + varcharField1 + '\'' +
                '}';
    }
}
