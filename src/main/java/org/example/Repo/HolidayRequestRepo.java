package org.example.Repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.example.Entity.HolidayRequest;

import javax.enterprise.context.ApplicationScoped;

//PanacheRepository for HolidayRequests
@ApplicationScoped
public class HolidayRequestRepo implements PanacheRepository<HolidayRequest> {
}
