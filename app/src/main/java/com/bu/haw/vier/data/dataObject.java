package com.bu.haw.vier.data;

/**
 * Created by jan on 22.10.14.
 */
public class dataObject {
    
    private double ax;
    private double ay;
    private double az;


    public dataObject(double az, double ax, double ay) {
        this.az = az;
        this.ax = ax;
        this.ay = ay;
    }
    
    
    public double getAx() {
        return ax;
    }

    public double getAy() {
        return ay;
    }

    public double getAz() {
        return az;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public void setAz(double az) {
        this.az = az;
    }
    
}
