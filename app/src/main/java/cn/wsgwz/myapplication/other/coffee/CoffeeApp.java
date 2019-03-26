package cn.wsgwz.myapplication.other.coffee;


import cn.wsgwz.myapplication.MainActivity;
import dagger.Component;


import javax.inject.Singleton;

public class CoffeeApp {
    static final String TAG = "CoffeeApp";

    @Singleton
    //@ActivityScope
    @Component(modules = {DripCoffeeModule.class, DripCoffeeModule2.class})
    public interface CoffeeShop3 {

        void inject(MainActivity mainActivity);

        CoffeeMaker maker();
    }


  /*  @Singleton
    //@ActivityScope
    @Component(modules = {DripCoffeeModule.class,User2Module.class,UserModule.class})
    public interface CoffeeShop2 {

        void inject(DataActivity dataActivity);
        //CoffeeMaker maker();

    }*/


    public static void main() {
        //CoffeeShop coffeeShop = DaggerCoffeeApp_CoffeeShop.builder().build();
        /*CoffeeShop coffeeShop = DaggerCoffeeApp_CoffeeShop.create();
        //coffeeShop.maker().brew();
        CoffeeMaker coffeeMaker = coffeeShop.maker();


        Log.d(TAG,coffeeMaker.hashCode()+"");
        Log.d(TAG,coffeeMaker.hashCode()+"");*/



        /*CoffeeShop coffeeShop2 = DaggerCoffeeApp_CoffeeShop.builder().build();

        Log.d(TAG,coffeeShop.hashCode()+"");
        Log.d(TAG,coffeeShop2.hashCode()+"");*/


    }
}
