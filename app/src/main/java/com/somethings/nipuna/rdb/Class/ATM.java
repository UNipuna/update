package com.somethings.nipuna.rdb.Class;

public class ATM {

    private String atm_terminal_id;
    private String branch;
    private String location;
    private String branch_latitude;
    private String branch_longitude;

    public ATM(String atm_terminal_id, String branch, String location, String branch_latitude, String branch_longitude) {
        this.atm_terminal_id = atm_terminal_id;
        this.branch = branch;
        this.location = location;
        this.branch_latitude = branch_latitude;
        this.branch_longitude = branch_longitude;
    }

    public String getAtm_terminal_id() {
        return atm_terminal_id;
    }

    public void setAtm_terminal_id(String atm_terminal_id) {
        this.atm_terminal_id = atm_terminal_id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBranch_latitude() {
        return branch_latitude;
    }

    public void setBranch_latitude(String branch_latitude) {
        this.branch_latitude = branch_latitude;
    }

    public String getBranch_longitude() {
        return branch_longitude;
    }

    public void setBranch_longitude(String branch_longitude) {
        this.branch_longitude = branch_longitude;
    }
}
