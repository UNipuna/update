package com.somethings.nipuna.rdb.Class;

public class AddressBranch {

    private String branch_code;
    private String branch_name;
    private String branch_address;
    private String branch_office_no;
    private String branch_email;
    private String branch_latitude;
    private String branch_longitude;

    public AddressBranch(String branch_code, String branch_name, String branch_address, String branch_office_no, String branch_email, String branch_latitude, String branch_longitude) {
        this.branch_code = branch_code;
        this.branch_name = branch_name;
        this.branch_address = branch_address;
        this.branch_office_no = branch_office_no;
        this.branch_email = branch_email;
        this.branch_latitude = branch_latitude;
        this.branch_longitude = branch_longitude;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBranch_address() {
        return branch_address;
    }

    public void setBranch_address(String branch_address) {
        this.branch_address = branch_address;
    }

    public String getBranch_office_no() {
        return branch_office_no;
    }

    public void setBranch_office_no(String branch_office_no) {
        this.branch_office_no = branch_office_no;
    }

    public String getBranch_email() {
        return branch_email;
    }

    public void setBranch_email(String branch_email) {
        this.branch_email = branch_email;
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
