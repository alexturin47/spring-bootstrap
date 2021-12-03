package com.example.bootcrud.dao;


import com.example.bootcrud.entities.Role;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager entityManager;

    @Override
    public Role findByname(String name) {
        Role role = null;

        Query query = entityManager.createQuery("select a from Role a where a.name = ?1", Role.class)
                .setParameter(1, name);
        try {
            role = (Role)query.getSingleResult();
        } catch (Exception e) {

        }
        return role;
    }

    @Override
    public HashSet<Role> index() {
        return new HashSet<>(entityManager.createQuery("select a from Role a", Role.class).getResultList());
    }

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role read(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public void update(Long id, Role role) {
        entityManager.merge(role);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(Role.class, id));
    }
}
