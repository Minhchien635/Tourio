package com.tourio.dto;

import com.tourio.models.Location;
import com.tourio.models.TourPrice;
import javafx.beans.property.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class TourDTO {
    private long tourId;
    private long typeId;
    private StringProperty name;
    private StringProperty description;
    private StringProperty type;
    private ArrayList<TourPrice> prices;
    private ArrayList<Location> locations;

    public TourDTO(long id, String name, String type, ArrayList<TourPrice> tourPrices, String description, ArrayList<Location> locations) {
        this.tourId = id;
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.prices = tourPrices;
        this.description = new SimpleStringProperty(description);
        this.locations = locations;
    }

    public TourDTO(long id, String name) {
        this.tourId = id;
        this.name = new SimpleStringProperty(name);
    }
}
