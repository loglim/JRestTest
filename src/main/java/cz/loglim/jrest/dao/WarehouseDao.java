package cz.loglim.jrest.dao;

import cz.loglim.jrest.res.Warehouse;

import javax.persistence.*;
import java.util.List;

public class WarehouseDao extends Dao {

    public static Warehouse get(long id) {
        EntityManager em = getNewEntityManager();
        Warehouse warehouse = null;
        try {
            warehouse = em.createQuery("from Warehouse where warehouse_id=:id", Warehouse.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ignored) {
        }
        close(em);
        return warehouse == null ? new Warehouse() : warehouse;
    }

    public static List<Warehouse> getAll() {
        EntityManager em = getNewEntityManager();
        List<Warehouse> resultList = em.createQuery("from Warehouse", Warehouse.class).getResultList();
        close(em);
        return resultList;
    }

    public static boolean add(Warehouse warehouse) {
        EntityManager em = getNewEntityManager();
        boolean result = false;
        try {
            em.persist(warehouse);
            em.getTransaction().commit();
            result = true;
        } catch (PersistenceException exception) {
            System.out.println(exception.getMessage());
        }
        close(em);
        return result;
    }

    public static boolean update(long id, Warehouse warehouse) {
        EntityManager em = getNewEntityManager();
        Warehouse old = get(id);
        boolean result = false;
        if (old.getId() != null) {
            old.setCapacity(warehouse.getCapacity());
            old.setLastAccess(warehouse.getLastAccess());
            try {
                em.merge(old);
                em.getTransaction().commit();
                result = true;
            } catch (PersistenceException exception) {
                System.out.println(exception.getMessage());
            }
        }
        close(em);
        return result;
    }

    public static boolean delete(long id) {
        EntityManager em = getNewEntityManager();
        Warehouse warehouse = em.find(Warehouse.class, id);
        boolean result = false;
        if (warehouse != null) {
            try {
                em.remove(warehouse);
                em.getTransaction().commit();
                result = true;
            } catch (PersistenceException exception) {
                System.out.println(exception.getMessage());
            }
        }
        close(em);
        return result;
    }
}
