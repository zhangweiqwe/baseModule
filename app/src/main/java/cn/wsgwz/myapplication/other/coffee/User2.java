package cn.wsgwz.myapplication.other.coffee;

import javax.inject.Inject;

public class User2 {
    private String name;

    @Inject
    public User2(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User2{" +
                "name='" + name + '\'' +
                '}';
    }
}
