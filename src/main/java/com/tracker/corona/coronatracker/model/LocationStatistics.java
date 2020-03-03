package com.tracker.corona.coronatracker.model;

/**
 * @author mkarki
 */
public class LocationStatistics {
    private String state;
    private String country;
    private int latestTotalCases;
    private int differenceFromPrevDay;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int getDifferenceFromPrevDay() {
        return differenceFromPrevDay;
    }

    public void setDifferenceFromPrevDay(int differenceFromPrevDay) {
        this.differenceFromPrevDay = differenceFromPrevDay;
    }

    @Override
    public String toString() {
        return "LocationStatistics{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                ", diffFromPrevDay=" + differenceFromPrevDay +
                '}';
    }
}
