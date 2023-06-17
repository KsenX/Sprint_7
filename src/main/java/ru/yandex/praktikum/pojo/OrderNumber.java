package ru.yandex.praktikum.pojo;

public class OrderNumber {
    public OrderNumber(String track) {
        this.track = track;
    }

    private String track;

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
