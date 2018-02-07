package org.kisio.NavitiaSDKUX.controller;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class JourneySolutionsInParameters implements Parcelable {
    public String originId;
    public String originLabel = "";
    public String destinationId;
    public String destinationLabel = "";
    public DateTime datetime;
    public String datetimeRepresents;
    public List<String> allowedId = new ArrayList<>();
    public List<String> forbiddenUris = new ArrayList<>();
    public List<String> firstSectionModes = new ArrayList<>();
    public List<String> lastSectionModes = new ArrayList<>();
    public Integer count;
    public Integer minNbJourneys;
    public Integer maxNbJourneys;

    public static final Creator<JourneySolutionsInParameters> CREATOR = new Creator<JourneySolutionsInParameters>() {
        @Override
        public JourneySolutionsInParameters createFromParcel(Parcel in) {
            return new JourneySolutionsInParameters(in);
        }

        @Override
        public JourneySolutionsInParameters[] newArray(int size) {
            return new JourneySolutionsInParameters[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(originId);
        parcel.writeString(originLabel);
        parcel.writeString(destinationId);
        parcel.writeString(destinationLabel);
        parcel.writeString(datetime != null ? datetime.toString() : null);
        parcel.writeString(datetimeRepresents);
        parcel.writeStringList(allowedId);
        parcel.writeStringList(forbiddenUris);
        parcel.writeStringList(firstSectionModes);
        parcel.writeStringList(lastSectionModes);
        if (count != null) {
            parcel.writeInt(count);
        } else {
            parcel.writeValue(null);
        }
        if (minNbJourneys != null) {
            parcel.writeInt(minNbJourneys);
        } else {
            parcel.writeValue(null);
        }
        if (maxNbJourneys != null) {
            parcel.writeInt(maxNbJourneys);
        } else {
            parcel.writeValue(null);
        }
    }

    public JourneySolutionsInParameters(String originId, String destinationId) {
        this.originId = originId;
        this.destinationId = destinationId;
    }

    public JourneySolutionsInParameters(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        originId = in.readString();
        originLabel = in.readString();
        destinationId = in.readString();
        destinationLabel = in.readString();

        final String stringDatetime = in.readString();
        if (stringDatetime != null) {
            datetime = DateTime.parse(stringDatetime);
        }

        datetimeRepresents = in.readString();
        in.readStringList(allowedId);
        in.readStringList(forbiddenUris);
        in.readStringList(firstSectionModes);
        in.readStringList(lastSectionModes);
        count = getOptionnalInt(in);
        minNbJourneys = getOptionnalInt(in);
        maxNbJourneys = getOptionnalInt(in);
    }

    private Integer getOptionnalInt(Parcel in) {
        final int value = in.readInt();
        if (value == -1) {
            return null;
        }
        return value;
    }
}
