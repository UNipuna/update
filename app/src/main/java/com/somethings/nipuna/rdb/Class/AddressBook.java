package com.somethings.nipuna.rdb.Class;

public class AddressBook {
    private String emp_id;
    private String emp_number;
    private String emp_name;
    private String emp_designation;
    private String emp_email;
    private String emp_branch_code;
    private String emp_branch_name;
    private String emp_office_no;
    private String emp_mobile_no;
//    private String emp_status;

    public AddressBook(String emp_id, String emp_number, String emp_name, String emp_designation, String emp_email, String emp_branch_code, String emp_branch_name, String emp_office_no, String emp_mobile_no) {
        this.emp_id = emp_id;
        this.emp_number = emp_number;
        this.emp_name = emp_name;
        this.emp_designation = emp_designation;
        this.emp_email = emp_email;
        this.emp_branch_code = emp_branch_code;
        this.emp_branch_name = emp_branch_name;
        this.emp_office_no = emp_office_no;
        this.emp_mobile_no = emp_mobile_no;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_number() {
        return emp_number;
    }

    public void setEmp_number(String emp_number) {
        this.emp_number = emp_number;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_designation() {
        return emp_designation;
    }

    public void setEmp_designation(String emp_designation) {
        this.emp_designation = emp_designation;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public String getEmp_branch_code() {
        return emp_branch_code;
    }

    public void setEmp_branch_code(String emp_branch_code) {
        this.emp_branch_code = emp_branch_code;
    }

    public String getEmp_branch_name() {
        return emp_branch_name;
    }

    public void setEmp_branch_name(String emp_branch_name) {
        this.emp_branch_name = emp_branch_name;
    }

    public String getEmp_office_no() {
        return emp_office_no;
    }

    public void setEmp_office_no(String emp_office_no) {
        this.emp_office_no = emp_office_no;
    }

    public String getEmp_mobile_no() {
        return emp_mobile_no;
    }

    public void setEmp_mobile_no(String emp_mobile_no) {
        this.emp_mobile_no = emp_mobile_no;
    }
}
