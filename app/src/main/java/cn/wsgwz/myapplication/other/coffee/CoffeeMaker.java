package cn.wsgwz.myapplication.other.coffee;

import android.util.Log;

import dagger.Lazy;

import javax.inject.Inject;
import javax.inject.Named;

public class CoffeeMaker {
    private static final String TAG = "CoffeeMaker";


    private final Lazy<Heater> heater; // Create a possibly costly heater only when we use it.
    private final Pump pump;

    @Named("s1")
    @Inject
    String str2;

    @Inject
    public CoffeeMaker(Lazy<Heater> heater, Pump pump) {
        this.heater = heater;
        this.pump = pump;
    }

    public void brew() {
        heater.get().on();
        pump.pump();
        Log.d(CoffeeApp.TAG, " [_]P coffee! [_]P " + str2 + ".." + heater.hashCode() + "-" + pump.hashCode() + "-" + str2.hashCode()  );
        //System.out.println(" [_]P coffee! [_]P ");
        heater.get().off();
    }
}
