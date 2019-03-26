package cn.wsgwz.myapplication.other.coffee.pen;

import dagger.Module;
import dagger.Provides;

@Module
public class Pen {

    @Provides
    public String description() {
        return "没有色";
    }
}
