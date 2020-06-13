package cn.jh.pojo;

import java.util.Date;

public class PersonInfo {
    private Long userID;
    private String name;
    private String profileImg;
    private String email;
    private String gender;
    //1.顾客 2.店家 3.超级管理员
    private Integer enableStatus;
    private Integer userType;
    private Date creatTime;
    private Date lastEditTime;

    public PersonInfo() {
    }

    public PersonInfo(Long userID, String name, String profileImg, String email, String gender, Integer enableStatus, Integer userType, Date creatTime, Date lastEditTime) {
        this.userID = userID;
        this.name = name;
        this.profileImg = profileImg;
        this.email = email;
        this.gender = gender;
        this.enableStatus = enableStatus;
        this.userType = userType;
        this.creatTime = creatTime;
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", email='" + email + '\'' + 
                ", gender='" + gender + '\'' +
                ", enableStatus=" + enableStatus +
                ", userType=" + userType +
                ", creatTime=" + creatTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
