package com.gmp.user.entity;

public class Role {
    private String id;

    private String rolename;

    private String descriptinon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getDescriptinon() {
        return descriptinon;
    }

    public void setDescriptinon(String descriptinon) {
        this.descriptinon = descriptinon == null ? null : descriptinon.trim();
    }
}