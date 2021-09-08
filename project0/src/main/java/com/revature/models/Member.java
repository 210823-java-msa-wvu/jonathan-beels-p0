package com.revature.models;

import com.revature.repositories.MemberRepo;

public class Member {
    private int accountId;
    private String username;
    private String password;
    private String name;
    private String address;
    private int phoneNumber;
    private int fee;
    private boolean isStaff;

    public Member() {
        
    }

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(String username, String password) {
        MemberRepo repo = new MemberRepo();

        Member m = repo.getByLogin(username, password);

        if (m.getUsername().equals(username) && m.getPassword().equals(password)) {
            return true;
        }

        return false;
    }

    public void createNewAccount(String username, String password, String name, String address,
                                   int phoneNumber) {
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
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
