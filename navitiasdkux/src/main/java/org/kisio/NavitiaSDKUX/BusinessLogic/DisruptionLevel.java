package org.kisio.NavitiaSDKUX.BusinessLogic;

public enum DisruptionLevel {
    none, information, nonblocking, blocking;

    public String getIconColor() {
        switch (this) {
            case blocking:
                return "A94442";
            case nonblocking:
                return "8A6D3B";
            case information:
                return "31708F";
            default:
                return "888888";
        }
    }

    public String getIconName() {
        return "disruption-" + this.name();
    }
}
