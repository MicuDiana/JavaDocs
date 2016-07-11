package ro.teamnet.zth.appl.dao;

import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.domain.Location;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 11.07.2016.
 */
public class LocationDao {

    EntityManager entityManager = new EntityManagerImpl();

    public Location findById(Long id) {

        return entityManager.findById(Location.class, id);
    }

    public Long getNextIdVal() {

        return entityManager.getNextIdVal("LOCATIONS", "location_id");
    }

    public Location insert(Location location) {

        return (Location) entityManager.insert(location);
    }

    public List<Location> findAll() {

        return entityManager.findAll(Location.class);
    }

    public Location update(Location location) {

        return entityManager.update(location);
    }

    public void delete(Location location) {

        entityManager.delete(location);
    }

    public List<Location> findByParams(Map<String, Object> params) {

        return entityManager.findByParams(Location.class, params);
    }
}