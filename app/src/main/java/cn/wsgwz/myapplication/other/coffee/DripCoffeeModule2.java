package cn.wsgwz.myapplication.other.coffee;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DripCoffeeModule2 {
    //@Named("str1")
    /*@Provides
    @Singleton
    String str() {
        return "23211";
    }*/


    @Named("s2")
    @Provides
    //@ActivityScope
    //@Singleton
    String str23dsa() {
        return "str23dsa";
    }

}
