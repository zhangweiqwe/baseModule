package cn.wsgwz.myapplication.other.coffee;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.wsgwz.baselibrary.other.coffee.User;
import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    private User user;

    public UserModule(User user) {
        this.user = user;
    }

    @Provides
    @Singleton
    User provideUser() {
        return user;
    }
}
