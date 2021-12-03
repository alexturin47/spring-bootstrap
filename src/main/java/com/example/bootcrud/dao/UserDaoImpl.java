package com.example.bootcrud.dao;


import com.example.bootcrud.entities.User;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;


@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager entityManager;

    @Override
    public User findByEmail(String email) {
        User user =null;
        try {
            user = entityManager.createQuery("select a from User a where a.email = ?1", User.class)
                    .setParameter(1, email).getSingleResult();
            System.out.println(user.getFirstname() + " " + user.getPassword() + " " + user.getEmail());
        } catch (Exception e) {
            System.out.printf("Пользователь с именем %s не найден/n", email );
        }

        return user;
    }

    @Override
    public List<User> index() {
        return entityManager.createQuery("select a from User a", User.class).getResultList();
    }

    @Override
    public void save(User user) {
        try {
            entityManager.persist(user);
        } catch (Exception e) {
            System.out.println("Ошибка добавления пользователя!");
            e.printStackTrace();
        }

    }

    @Override
    public User read(Long  id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void update(Long  id, User user) {
        try {
            entityManager.merge(user);
        } catch (HibernateException e) {
            System.out.println("Ошибка применения изменений! ");
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Long  id) {
        entityManager.remove(entityManager.find(User.class,id));
    }

}
