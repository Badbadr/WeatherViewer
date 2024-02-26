package repository.jpa;

import entity.Location;
import org.hibernate.Session;
import repository.config.HibernateConfig;

import java.util.List;

public class LocationRepository {

    public List<Location> search(String name, int page, int pageSize) {
        return List.of();
    }

    public Location save(Location location) {
        try (Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.persist(location);
            session.getTransaction().commit();
            return location;
        }
    }
}
