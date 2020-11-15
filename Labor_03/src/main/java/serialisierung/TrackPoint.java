package serialisierung;

import java.io.Serializable;
import java.time.LocalTime;

public class TrackPoint implements Serializable {
    private LocalTime time;
    private double longitude;
    private double latitude;
    private double height;

    public TrackPoint(LocalTime time, double longitude, double latitude, double height) {
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.height = height;
    }

    public LocalTime getTime() {
        return time;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getHeight() {
        return height;
    }

    public static TrackPoint of(String s) {
        String[] splitted = s.split(";");
        if(splitted.length != 4)
            throw new IllegalArgumentException("Wrong number of columns");

        LocalTime newTime = LocalTime.parse(splitted[0]);
        double newLongitude = Double.parseDouble(splitted[1]);
        double newLatitude = Double.parseDouble(splitted[2]);
        double newHeight = Double.parseDouble(splitted[3]);
        return new TrackPoint(newTime, newLongitude, newLatitude, newHeight);
    }
}
