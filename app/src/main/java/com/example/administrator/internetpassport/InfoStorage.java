package com.example.administrator.internetpassport;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "UserInfo")
public class InfoStorage {
    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String sex;
    @DatabaseField
    private String nationality;
    @DatabaseField
    private String phone_number;
    @DatabaseField
    private String address;

    public InfoStorage() {}

    public InfoStorage(String id, String name, String sex, String nationality, String phone_number, String address) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.nationality = nationality;
        this.phone_number = phone_number;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getSex() {
        return sex;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
