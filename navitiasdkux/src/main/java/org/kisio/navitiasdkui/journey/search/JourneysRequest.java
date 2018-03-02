package org.kisio.navitiasdkui.journey.search;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class JourneysRequest implements Parcelable {
    private String originId;
    private String originLabel;
    private String destinationId;
    private String destinationLabel;
    private DateTime datetime;
    private String datetimeRepresents;
    private List<String> allowedId;
    private List<String> forbiddenUris;
    private List<String> firstSectionModes;
    private List<String> lastSectionModes;
    private Integer count;
    private Integer minNbJourneys;
    private Integer maxNbJourneys;

    public JourneysRequest(String originId, String destinationId) {
        this.originId = originId;
        this.destinationId = destinationId;
        this.originLabel = "";
        this.destinationLabel = "";
        this.allowedId = new ArrayList<>();
        this.forbiddenUris = new ArrayList<>();
        this.firstSectionModes = new ArrayList<>();
        this.lastSectionModes = new ArrayList<>();
    }

    public String getOriginId() {
        return originId;
    }

    public JourneysRequest setOriginId(String originId) {
        this.originId = originId;
        return this;
    }

    public String getOriginLabel() {
        return originLabel;
    }

    public JourneysRequest setOriginLabel(String originLabel) {
        this.originLabel = originLabel;
        return this;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public JourneysRequest setDestinationId(String destinationId) {
        this.destinationId = destinationId;
        return this;
    }

    public String getDestinationLabel() {
        return destinationLabel;
    }

    public JourneysRequest setDestinationLabel(String destinationLabel) {
        this.destinationLabel = destinationLabel;
        return this;
    }

    public DateTime getDatetime() {
        return datetime;
    }

    public JourneysRequest setDatetime(DateTime datetime) {
        this.datetime = datetime;
        return this;
    }

    public String getDatetimeRepresents() {
        return datetimeRepresents;
    }

    public JourneysRequest setDatetimeRepresents(String datetimeRepresents) {
        this.datetimeRepresents = datetimeRepresents;
        return this;
    }

    public List<String> getAllowedId() {
        return allowedId;
    }

    public JourneysRequest setAllowedId(List<String> allowedId) {
        this.allowedId = allowedId;
        return this;
    }

    public List<String> getForbiddenUris() {
        return forbiddenUris;
    }

    public JourneysRequest setForbiddenUris(List<String> forbiddenUris) {
        this.forbiddenUris = forbiddenUris;
        return this;
    }

    public List<String> getFirstSectionModes() {
        return firstSectionModes;
    }

    public JourneysRequest setFirstSectionModes(List<String> firstSectionModes) {
        this.firstSectionModes = firstSectionModes;
        return this;
    }

    public List<String> getLastSectionModes() {
        return lastSectionModes;
    }

    public JourneysRequest setLastSectionModes(List<String> lastSectionModes) {
        this.lastSectionModes = lastSectionModes;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public JourneysRequest setCount(Integer count) {
        this.count = count;
        return this;
    }

    public Integer getMinNbJourneys() {
        return minNbJourneys;
    }

    public JourneysRequest setMinNbJourneys(Integer minNbJourneys) {
        this.minNbJourneys = minNbJourneys;
        return this;
    }

    public Integer getMaxNbJourneys() {
        return maxNbJourneys;
    }

    public JourneysRequest setMaxNbJourneys(Integer maxNbJourneys) {
        this.maxNbJourneys = maxNbJourneys;
        return this;
    }

    protected JourneysRequest(Parcel in) {
        originId = in.readString();
        originLabel = in.readString();
        destinationId = in.readString();
        destinationLabel = in.readString();
        datetime = (DateTime) in.readSerializable();
        datetimeRepresents = in.readString();
        if (allowedId != null) in.readStringList(allowedId);
        if (forbiddenUris != null) in.readStringList(forbiddenUris);
        if (firstSectionModes != null) in.readStringList(firstSectionModes);
        if (lastSectionModes != null) in.readStringList(lastSectionModes);
        count = in.readByte() == 0x00 ? null : in.readInt();
        minNbJourneys = in.readByte() == 0x00 ? null : in.readInt();
        maxNbJourneys = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originId);
        dest.writeString(originLabel);
        dest.writeString(destinationId);
        dest.writeString(destinationLabel);
        dest.writeSerializable(datetime);
        dest.writeString(datetimeRepresents);
        dest.writeStringList(allowedId);
        dest.writeStringList(forbiddenUris);
        dest.writeStringList(firstSectionModes);
        dest.writeStringList(lastSectionModes);
        if (count == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(count);
        }
        if (minNbJourneys == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(minNbJourneys);
        }
        if (maxNbJourneys == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(maxNbJourneys);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<JourneysRequest> CREATOR = new Parcelable.Creator<JourneysRequest>() {
        @Override
        public JourneysRequest createFromParcel(Parcel in) {
            return new JourneysRequest(in);
        }

        @Override
        public JourneysRequest[] newArray(int size) {
            return new JourneysRequest[size];
        }
    };
}
