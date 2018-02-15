package org.kisio.navitiasdkui.journey;


import java.util.List;

public class ListModel {
    private int viewType;
    private String travelTime;
    private String travelDuration;
    private String walkInfo;
    private String time;
    private String type;
    private String address;
    private String icon;
    private String transit;
    private String line;
    private String lineColor;
    private String origin;
    private String disruption;
    private String disruptionTime;
    private String wait;
    private String startTime;
    private String endTime;
    private String destination;
    private List<String> stations;
    private boolean isCarpool;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTransit() {
        return transit;
    }

    public void setTransit(String transit) {
        this.transit = transit;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDisruption() {
        return disruption;
    }

    public void setDisruption(String disruption) {
        this.disruption = disruption;
    }

    public String getDisruptionTime() {
        return disruptionTime;
    }

    public void setDisruptionTime(String disruptionTime) {
        this.disruptionTime = disruptionTime;
    }

    public String getWait() {
        return wait;
    }

    public void setWait(String wait) {
        this.wait = wait;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public boolean isCarpool() {
        return isCarpool;
    }

    public void setCarpool(boolean carpool) {
        isCarpool = carpool;
    }
}
