package com.revature.models;

import com.revature.repositories.MemberRepo;

public class Staff extends Member {

    public Staff(Member m) {
        this.setAccountId(m.getAccountId());
        this.setUsername(m.getUsername());
        this.setPassword(m.getPassword());
        this.setName(m.getName());
        this.setAddress(m.getAddress());
        this.setPhoneNumber(m.getPhoneNumber());
        this.setFee(m.getFee());
        this.setStaff(m.getIsStaff());
    }

    public int chargeAccount(int days, int userId) {
        MemberRepo repo = new MemberRepo();
        int charge = days * 5;

        int fee = repo.updateFee(userId, charge);

        return fee;
    }
}
