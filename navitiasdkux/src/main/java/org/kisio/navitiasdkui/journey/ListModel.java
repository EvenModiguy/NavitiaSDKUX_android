package org.kisio.navitiasdkui.journey;

import java.util.List;

public class ListModel {
    private int viewType;
    private String travelTime;
    private String travelDuration;
    private boolean travelDurationIsLessThanAHour;
    private String[] walkInfo;
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
    private boolean hasArrow;
    private boolean isCarpool;
    private String href;

    public ListModel() {
    }

    public ListModel(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public ListModel setViewType(int viewType) {
        this.viewType = viewType;
        return this;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public ListModel setTravelTime(String travelTime) {
        this.travelTime = travelTime;
        return this;
    }

    public String getTravelDuration() {
        return travelDuration;
    }

    public ListModel setTravelDuration(String travelDuration) {
        this.travelDuration = travelDuration;
        return this;
    }

    public boolean isTravelDurationIsLessThanAHour() {
        return travelDurationIsLessThanAHour;
    }

    public ListModel isTravelDurationIsLessThanAHour(boolean travelDurationIsLessThanAHour) {
        this.travelDurationIsLessThanAHour = travelDurationIsLessThanAHour;
        return this;
    }

    public String[] getWalkInfo() {
        return walkInfo;
    }

    public ListModel setWalkInfo(String[] walkInfo) {
        this.walkInfo = walkInfo;
        return this;
    }

    public String getTime() {
        return time;
    }

    public ListModel setTime(String time) {
        this.time = time;
        return this;
    }

    public String getType() {
        return type;
    }

    public ListModel setType(String type) {
        this.type = type;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public ListModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public ListModel setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getTransit() {
        return transit;
    }

    public ListModel setTransit(String transit) {
        this.transit = transit;
        return this;
    }

    public String getLine() {
        return line;
    }

    public ListModel setLine(String line) {
        this.line = line;
        return this;
    }

    public String getLineColor() {
        return lineColor;
    }

    public ListModel setLineColor(String lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public ListModel setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getDisruption() {
        return disruption;
    }

    public ListModel setDisruption(String disruption) {
        this.disruption = disruption;
        return this;
    }

    public String getDisruptionTime() {
        return disruptionTime;
    }

    public ListModel setDisruptionTime(String disruptionTime) {
        this.disruptionTime = disruptionTime;
        return this;
    }

    public String getWait() {
        return wait;
    }

    public ListModel setWait(String wait) {
        this.wait = wait;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public ListModel setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public ListModel setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public ListModel setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public List<String> getStations() {
        return stations;
    }

    public ListModel setStations(List<String> stations) {
        this.stations = stations;
        return this;
    }

    public boolean hasArrow() {
        return hasArrow;
    }

    public ListModel hasArrow(boolean hasArrow) {
        this.hasArrow = hasArrow;
        return this;
    }

    public boolean isCarpool() {
        return isCarpool;
    }

    public ListModel isCarpool(boolean carpool) {
        isCarpool = carpool;
        return this;
    }

    public String getHref() {
        return href;
    }

    public ListModel setHref(String href) {
        this.href = href;
        return this;
    }
}
