package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public User getUserById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public void removeUserById(long id) {
        User user = getUserById(id);
        em.remove(user);
        em.flush();
        em.clear();
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = em.createQuery("select u from User u where u.username = ?1", User.class)
                .setParameter(1, username).getSingleResult();

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
