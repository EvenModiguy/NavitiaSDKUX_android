package org.kisio.navitiasdkui.journey.search;


public class ResultModel {
    private String travelTime;
    private String travelDuration;
    private String walkInfo;
    private int viewType;

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getTravelDuration() {
        return travelDuration;
    }

    public void setTravelDuration(String travelDuration) {
        this.travelDuration = travelDuration;
    }

    public String getWalkInfo() {
        return walkInfo;
    }

    public void setWalkInfo(String walkInfo) {
        this.walkInfo = walkInfo;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
