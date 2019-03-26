package cn.wsgwz.myapplication.other.coffee;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.BindsOptionalOf;
import dagger.Module;
@Module
public abstract class PumpModule {
    @Binds
    //@ActivityScope
    @Singleton
    abstract Pump providePump(Thermosiphon pump);
}
