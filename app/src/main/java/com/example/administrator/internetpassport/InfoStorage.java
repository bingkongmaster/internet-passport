package com.example.administrator.internetpassport;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "UserInfo")
public class InfoStorage {
    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String password;
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
    @DatabaseField
    private String email;
    @DatabaseField
    private String birthday;

    public InfoStorage() {}

    public InfoStorage(String id, String password, String name, String sex, String nationality, String phone_number, String address, String email, String birthday) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.nationality = nationality;
        this.phone_number = phone_number;
        this.address = address;
        this.email = email;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
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

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {this.password = password;}

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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
