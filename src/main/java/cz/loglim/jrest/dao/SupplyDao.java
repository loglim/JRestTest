package cz.loglim.jrest.dao;

import cz.loglim.jrest.res.Supply;

import javax.persistence.*;
import java.util.List;

public class SupplyDao extends Dao {

    public static Supply get(long id) {
        EntityManager em = getNewEntityManager();
        Supply supply = null;
        try {
            supply = em.createQuery("from Supply where supply_id=:id", Supply.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ignored) {
        }
        close(em);
        return supply;
    }

    public static List<Supply> getAll() {
        EntityManager em = getNewEntityManager();
        List<Supply> resultList = em.createQuery("from Supply", Supply.class).getResultList();
        close(em);
        return resultList;
    }

    public static boolean add(Supply supply) {
        EntityManager em = getNewEntityManager();
        boolean result = false;
        try {
            em.persist(supply);
            em.getTransaction().commit();
            result = true;
        } catch (PersistenceException exception) {
            System.out.println(exception.getMessage());
        }
        close(em);
        return result;
    }

    public static boolean update(long id, Supply supply) {
        EntityManager em = getNewEntityManager();
        Supply old = get(id);
        boolean result = false;
        if (old.getItemId() != null && old.getWarehouseId() != null) {
            old.setItemId(supply.getItemId());
            old.setWarehouseId(supply.getWarehouseId());
            old.setQuantity(supply.getQuantity());
            try {
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
        Supply supply = em.find(Supply.class, id);
        boolean result = false;
        if (supply != null) {
            try {
                em.remove(supply);
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
