package hiber.dao;

import hiber.model.User;
import jakarta.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    @Override
    public List<User> getListUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public User getUserByCar(String model, int series) {

        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User as u inner join fetch u.car as c where c.series=:series and c.model=:model", User.class);
        query.setParameter("model", model);
        query.setParameter("series", series);
        return query.setMaxResults(1).getSingleResult();
    }


}
