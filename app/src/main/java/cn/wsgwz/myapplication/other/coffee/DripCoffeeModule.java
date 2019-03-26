package cn.wsgwz.myapplication.other.coffee;

import android.util.Log;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

@Module(includes = PumpModule.class)
public class DripCoffeeModule {
    @Provides
    //@ActivityScope
    @Singleton
    Heater provideHeater() {

       // Log.d(CoffeeApp.TAG,"provideHeater");

        return new ElectricHeater();
    }



    @Named("s1")
    @Provides
    //@ActivityScope
    //@Singleton
    String str23() {
        return "str23";
    }



    @Provides
    //@ActivityScope
    //@Singleton
    String str2323() {
        return "str2323+++";
    }

}
