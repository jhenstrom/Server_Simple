package server;



/**
 * Created by jhenstrom on 10/27/17.
 */

class Location {

    private String country;
    private String city;
    private String longitude;
    private String latitude;


    public Location(String country, String city, String longitude, String latitude)
    {
        this.country = country;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Location()
    {

    }

    public String getCountry(){ return country; }
    public String getCity(){ return city; }
    public String getLongitude(){ return longitude; }
    public String getLatitude(){ return latitude; }

    public void setCountry( String country)
    {
        this.country = country;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setLongitude(String longit)
    {
        this.longitude = longit;
    }

    public void setLatitude(String lat)
    {
        this.latitude = lat;
    }
}
