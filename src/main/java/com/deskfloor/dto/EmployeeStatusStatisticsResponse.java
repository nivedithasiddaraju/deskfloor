package com.deskfloor.dto;

public class EmployeeStatusStatisticsResponse {

    private long active;
    private long inactive;

    public EmployeeStatusStatisticsResponse() {
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public long getInactive() {
        return inactive;
    }

    public void setInactive(long inactive) {
        this.inactive = inactive;
    }
}