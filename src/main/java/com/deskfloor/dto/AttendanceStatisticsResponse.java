package com.deskfloor.dto;

public class AttendanceStatisticsResponse {

    private long present;
    private long absent;
    private long onLeave;

    public AttendanceStatisticsResponse() {
    }

    public long getPresent() {
        return present;
    }

    public void setPresent(long present) {
        this.present = present;
    }

    public long getAbsent() {
        return absent;
    }

    public void setAbsent(long absent) {
        this.absent = absent;
    }

    public long getOnLeave() {
        return onLeave;
    }

    public void setOnLeave(long onLeave) {
        this.onLeave = onLeave;
    }
}