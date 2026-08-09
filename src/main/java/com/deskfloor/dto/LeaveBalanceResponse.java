package com.deskfloor.dto;

import com.deskfloor.enums.LeaveType;

public class LeaveBalanceResponse {

    private LeaveType leaveType;
    private int totalDays;
    private int usedDays;
    private int remainingDays;

    public LeaveBalanceResponse() {
    }

    public LeaveBalanceResponse(
            LeaveType leaveType,
            int totalDays,
            int usedDays,
            int remainingDays) {

        this.leaveType = leaveType;
        this.totalDays = totalDays;
        this.usedDays = usedDays;
        this.remainingDays = remainingDays;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getUsedDays() {
        return usedDays;
    }

    public void setUsedDays(int usedDays) {
        this.usedDays = usedDays;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }
}