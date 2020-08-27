package org.example.Repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.example.Entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

//PanacheRepository for User
@ApplicationScoped
public class UserRepo implements PanacheRepository<User> {
}
