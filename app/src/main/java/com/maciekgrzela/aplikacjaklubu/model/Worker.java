package com.maciekgrzela.aplikacjaklubu.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Worker {
    private int identifier;
    private String firstName;
    private String lastName;
    private int age;
    private String nationality;
    private String email;
    private boolean isJournalist;
    private boolean isExecutive;
    private boolean isStaff;
    private String password;
    private Date dateOfBirth;
    private LocalDateTime createdAt;

    public Worker(String firstName, String lastName, int age, String nationality, String email, boolean isJournalist, boolean isExecutive, boolean isStaff, String password, Date dateOfBirth, LocalDateTime createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.nationality = nationality;
        this.email = email;
        this.isJournalist = isJournalist;
        this.isExecutive = isExecutive;
        this.isStaff = isStaff;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
    }

    public Worker(){}

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isJournalist() {
        return isJournalist;
    }

    public void setJournalist(boolean journalist) {
        isJournalist = journalist;
    }

    public boolean isExecutive() {
        return isExecutive;
    }

    public void setExecutive(boolean executive) {
        isExecutive = executive;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
