package ua.edu.nuos.lab3_jakartaeedb.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ua.edu.nuos.lab3_jakartaeedb.model.Watch;

import java.util.List;

@Stateless
public class WatchDao implements CrudDAO<Watch>{
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public void create(Watch watch) {
        em.persist(watch);
    }

    @Override
    public List<Watch> findAll() {
        return em.createQuery("select w from Watch w",  Watch.class).getResultList();
    }

    @Override
    public Watch findById(long id) {
        return em.find(Watch.class, id);
    }

    @Override
    public void update(Watch watch) {
        em.merge(watch);
    }

    @Override
    public void delete(long id) {
        Watch watch = em.find(Watch.class, id);
        if (watch != null) {
            em.remove(watch);
        }
    }
}
