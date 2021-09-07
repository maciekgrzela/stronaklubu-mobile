package com.maciekgrzela.aplikacjaklubu.model;

import java.time.LocalDateTime;
import java.util.Date;

public class News {

    public int newsId;
    public int workerId;
    public String title;
    public String contentPath;
    public String imagePath;
    public String tags;
    public LocalDateTime createdAt;
    public Date lastCommented;
    public int viewers;
    public String workerFirstName;
    public String workerLastName;
    public int workerAge;
    public String workerNationality;

    public News(int newsId, int workerId, String title, String contentPath, String imagePath, String tags, LocalDateTime createdAt, Date lastCommented, int viewers, String workerFirstName, String workerLastName, int workerAge, String workerNationality) {
        this.newsId = newsId;
        this.workerId = workerId;
        this.title = title;
        this.contentPath = contentPath;
        this.imagePath = imagePath;
        this.tags = tags;
        this.createdAt = createdAt;
        this.lastCommented = lastCommented;
        this.viewers = viewers;
        this.workerFirstName = workerFirstName;
        this.workerLastName = workerLastName;
        this.workerAge = workerAge;
        this.workerNationality = workerNationality;
    }

    public News(){}

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastCommented() {
        return lastCommented;
    }

    public void setLastCommented(Date lastCommented) {
        this.lastCommented = lastCommented;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public String getWorkerFirstName() {
        return workerFirstName;
    }

    public void setWorkerFirstName(String workerFirstName) {
        this.workerFirstName = workerFirstName;
    }

    public String getWorkerLastName() {
        return workerLastName;
    }

    public void setWorkerLastName(String workerLastName) {
        this.workerLastName = workerLastName;
    }

    public int getWorkerAge() {
        return workerAge;
    }

    public void setWorkerAge(int workerAge) {
        this.workerAge = workerAge;
    }

    public String getWorkerNationality() {
        return workerNationality;
    }

    public void setWorkerNationality(String workerNationality) {
        this.workerNationality = workerNationality;
    }
}
