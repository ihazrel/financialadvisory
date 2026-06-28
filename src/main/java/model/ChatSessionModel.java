package model;

import java.util.Date;

public class ChatSessionModel {

    private Integer chatId;
    private Integer userId;
    private String name;
    private Date dateStart;
    private Date dateUpdated;

    public ChatSessionModel() {}

    public ChatSessionModel(Integer chatId, Integer userId, String name, Date dateStart) {
        this.chatId = chatId;
        this.userId = userId;
        this.name = name;
        this.dateStart = dateStart;
    }

    public ChatSessionModel(Integer chatId, Integer userId, String name, Date dateStart, Date dateUpdated) {
        this.chatId = chatId;
        this.userId = userId;
        this.name = name;
        this.dateStart = dateStart;
        this.dateUpdated = dateUpdated;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
