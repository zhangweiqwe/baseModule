package cn.wsgwz.myapplication.other.coffee;

import android.util.Log;

import javax.inject.Inject;

class Thermosiphon implements Pump {
    private static final String TAG = "Thermosiphon";
    private final Heater heater;

    @Inject
    Thermosiphon(Heater heater) {
        this.heater = heater;
    }

    @Override
    public void pump() {
        if (heater.isHot()) {
            Log.d(CoffeeApp.TAG, "=> => pumping => =>");
            //System.out.println("=> => pumping => =>");
        }
    }
}
