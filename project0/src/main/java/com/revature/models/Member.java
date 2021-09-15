package com.revature.models;

import com.revature.repositories.MemberRepo;

public class Member {
    private int accountId;
    private String username;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;
    private int fee;
    private boolean isStaff;

    public Member() {

    }

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login() {
        MemberRepo repo = new MemberRepo();

        Member m = repo.getByLogin(username, password);

        if (m.getUsername().equals(username) && m.getPassword().equals(password)) {
            this.accountId = m.getAccountId();
            this.username = m.getUsername();
            this.password = m.getPassword();
            this.name = m.getName();
            this.address = m.getAddress();
            this.phoneNumber = m.getPhoneNumber();
            this.fee = m.getFee();
            this.isStaff = m.getIsStaff();
            return true;
        }

        return false;
    }

    public void createNewAccount(String username, String password, String name, String address,
                                   String phoneNumber) {
        MemberRepo repo = new MemberRepo();

        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.fee = 0;
        this.isStaff = false;

        Member m = repo.addMember(this);
        this.accountId = m.getAccountId();
    }

    public int payFee(int payment) {
        MemberRepo repo = new MemberRepo();

        int leftover;
        if (payment > this.fee) {
            leftover = 0;
        }

        else {
            leftover = this.fee - payment;
        }

        this.fee = repo.updateFee(this.accountId, leftover);

        return this.fee;
    }

    public void checkFee() {
        MemberRepo repo = new MemberRepo();

        Member m = repo.getById(this.accountId);
        this.fee = m.getFee();
    }

    // Getters and Setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public boolean getIsStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }
}
