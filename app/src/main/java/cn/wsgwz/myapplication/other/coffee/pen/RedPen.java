package cn.wsgwz.myapplication.other.coffee.pen;

import dagger.Module;
import dagger.Provides;

@Module
public class RedPen extends Pen{

    @Provides
    public String say() {
        return "红色";
    }


}
