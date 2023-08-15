package ru.kata.spring.boot_security.demo.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getListRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public Role getRole(String name) {
        return entityManager.createQuery("select r from Role r where r.name =: name", Role.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }
}
