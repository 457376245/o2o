package cn.jh.pojo;

import java.util.Date;

public class Area {
    private Integer areaId;
    private String areaName;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaId=" + areaId +
                ", name='" + areaName + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }

    public Area(Integer areaId, String areaName, Integer priority, Date createTime, Date lastEditTime) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.priority = priority;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    public Area() {
    }

    public int getAreaId() {
        return areaId;
    }

    public void setId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
