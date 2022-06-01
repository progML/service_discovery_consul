package ru.itmo.soalab2.model;

import java.time.LocalDateTime;

public class HumanFromClient {
    private int id;
    private String height;
    private LocalDateTime birthday;

    public HumanFromClient(int id, String height, LocalDateTime birthday) {
        this.id = id;
        this.height = height;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }
}
