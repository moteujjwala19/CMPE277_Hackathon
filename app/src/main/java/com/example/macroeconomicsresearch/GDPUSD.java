package com.example.macroeconomicsresearch;

public class GDPUSD {
    String year;
    Double country;

    public Double getCountry() {
        return country;
    }

    public void setCountry(Double country) {
        this.country = country;
    }




    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "GDPUSD{" +
                "year='" + year + '\'' +
                ", country=" + country +
                '}';
    }
}
