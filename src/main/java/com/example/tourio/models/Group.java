package com.example.tourio.models;

import java.time.LocalDateTime;

public class Group {
    private String name;
    private Tour tour;
    private String description;
    private LocalDateTime date_start;
    private LocalDateTime date_end;

    public Group(String name, Tour tour, String description, LocalDateTime date_start, LocalDateTime date_end) {
        this.name = name;
        this.tour = tour;
        this.description = description;
        this.date_start = date_start;
        this.date_end = date_end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate_start() {
        return date_start;
    }

    public void setDate_start(LocalDateTime date_start) {
        this.date_start = date_start;
    }

    public LocalDateTime getDate_end() {
        return date_end;
    }

    public void setDate_end(LocalDateTime date_end) {
        this.date_end = date_end;
    }


}
