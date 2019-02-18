package cz.loglim.jrest.dao;

import cz.loglim.jrest.res.Warehouse;
import cz.loglim.jrest.res.Warehouse;

import javax.persistence.*;
import java.util.List;

public class WarehouseDao extends Dao {

    public static Warehouse get(long id) {
        Warehouse Warehouse = null;
        try {
            Warehouse = em.createQuery("from Warehouse where warehouse_id=:id", Warehouse.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ignored) {
        }
        return Warehouse == null ? new Warehouse() : Warehouse;
    }

    public static List<Warehouse> getAll() {
        return em.createQuery("from Warehouse", Warehouse.class).getResultList();
    }

    public static boolean add(Warehouse entry) {
        boolean result = false;
        try {
            em.persist(entry);
            em.getTransaction().commit();
            result = true;
        } catch (PersistenceException exception) {
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public static boolean update(long id, Warehouse entry) {
        Warehouse old = get(id);
        if(old != null) {
            old.setCapacity(entry.getCapacity());
            old.setLastAccess(entry.getLastAccess());
            boolean result = false;
            try {
                em.merge(entry);
                em.getTransaction().commit();
                result = true;
            } catch (PersistenceException exception) {
                System.out.println(exception.getMessage());
            }
            return result;
        }
        else {
            return add(entry);
        }
    }

    public static boolean delete(long id) {
        Warehouse Warehouse = em.find(Warehouse.class, id);
        boolean result = false;
        if (Warehouse != null) {
            try {
                em.remove(Warehouse);
                em.getTransaction().commit();
                result = true;
            } catch (PersistenceException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return result;
    }
}
