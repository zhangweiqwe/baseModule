package cn.wsgwz.myapplication.other.coffee;

import android.util.Log;

class ElectricHeater implements Heater {
    private static final String TAG = "ElectricHeater";
    boolean heating;

    @Override public void on() {
        Log.d(CoffeeApp.TAG,"~ ~ ~ heating ~ ~ ~");
        //System.out.println("~ ~ ~ heating ~ ~ ~");
        this.heating = true;
    }

    @Override public void off() {
        this.heating = false;
    }

    @Override public boolean isHot() {
        return heating;
    }
}
