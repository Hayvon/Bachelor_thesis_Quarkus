package org.example.Repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.example.Entity.User;

import javax.inject.Singleton;

@Singleton
public class UserRepo implements PanacheRepository<User> {
    public void ping() {

    };
}
