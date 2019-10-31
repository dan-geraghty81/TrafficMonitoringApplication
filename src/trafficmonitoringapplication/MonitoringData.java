/**
 * Class: MonitoringData.java
 *
 * @author Daniel Geraghty
 *
 * Developed: August 2019
 * 
 * Version: 1.0
 *
 * Purpose: Class to define the data structure of the incoming data to the server
 *
 * Assessment 2 - ICTPRG523
 */
package trafficmonitoringapplication;

public class MonitoringData
{
    private int dataID;
    private String time;
    private String location;
    private String lanes;
    private String totVehicles;
    private String avgVehicles;
    private String velocity;
    
    public MonitoringData(int dataID, String time, String location, String lanes, String totVehicles, String avgVehicles, String velocity)
    {
        this.dataID = dataID;
        this.time = time;
        this.location = location;
        this.lanes = lanes;
        this.totVehicles = totVehicles;
        this.avgVehicles = avgVehicles;
        this.velocity = velocity;
    }
    
    public int getDataID()
    {
        return dataID;
    }
    
    public String getTime()
    {
        return time;
    }
    
    public String getLocation()
    {
        return location;
    }
    
    public String getLanes()
    {
        return lanes;
    }
    
    public String getTotVehicles()
    {
        return totVehicles;
    }
    
    public String getAvgVehicles()
    {
        return avgVehicles;
    }
    
    public String getVelocity()
    {
        return velocity;
    }
    public String getObject()
    {
        String str = "" + dataID + ", " + time + ", " + location
                + ", " + lanes+ ", " + totVehicles + ", " + avgVehicles + ", " + velocity;
        return str;
    }
}
