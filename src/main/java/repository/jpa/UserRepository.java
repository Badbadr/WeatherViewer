package repository.jpa;

import entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import repository.config.HibernateConfig;

import java.util.Optional;

public class UserRepository {

    public Optional<User> findByLoginAndPassword(String login, String password) {
        try (Session session = HibernateConfig.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> q = builder.createQuery(User.class);
            Root<User> r = q.from(User.class);
            CriteriaQuery<User> cq = q.select(r).where(
                    builder.and(builder.equal(r.get("login"), login),
                        builder.equal(r.get("password"), password)
                    )
            );
            return Optional.ofNullable(session.createQuery(cq).getSingleResult());
        }
    }

    public User save(String login, String password) {
        try (Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            session.persist(user);
            session.getTransaction().commit();
            return user;
        }
    }
}
