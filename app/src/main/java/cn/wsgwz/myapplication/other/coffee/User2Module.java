package cn.wsgwz.myapplication.other.coffee;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class User2Module {

    private User2 user;

    public User2Module(User2 user) {
        this.user = user;
    }

    @Provides
    @Singleton
    User2 provideUser() {
        return user;
    }
}
