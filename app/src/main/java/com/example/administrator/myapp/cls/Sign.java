package com.example.administrator.myapp.cls;

public class Sign {
    protected int signid;
    protected String  signname;
    protected String Invitecode;
    protected String Sponsor;
    protected String starttime;
    protected String endtime;
    protected String signtitle;
    protected String remark;
    protected String address;
    private int profile;


    public Sign(String signname, String sponsor, String signtitle, String address, int profile) {
        this.signname = signname;
        Sponsor = sponsor;
        this.signtitle = signtitle;
        this.address = address;
        this.profile = profile;
    }



    public Sign(int signid, String signname, String invitecode, String sponsor, String starttime, String endtime, String signtitle, String remark, String address) {
        this.signid = signid;
        this.signname = signname;
        this.Invitecode = invitecode;
        this.Sponsor = sponsor;
        this.starttime = starttime;
        this.endtime = endtime;
        this.signtitle = signtitle;
        this.remark = remark;
        this.address = address;
    }
    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getSignid() {
        return signid;
    }

    public void setSignid(int signid) {
        this.signid = signid;
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname;
    }

    public String getInvitecode() {
        return Invitecode;
    }

    public void setInvitecode(String invitecode) {
        Invitecode = invitecode;
    }

    public String getSponsor() {
        return Sponsor;
    }

    public void setSponsor(String sponsor) {
        Sponsor = sponsor;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getSigntitle() {
        return signtitle;
    }

    public void setSigntitle(String signtitle) {
        this.signtitle = signtitle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
