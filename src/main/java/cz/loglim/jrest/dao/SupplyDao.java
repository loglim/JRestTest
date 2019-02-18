package cz.loglim.jrest.dao;

import cz.loglim.jrest.res.Supply;

import javax.persistence.*;
import java.util.List;

public class SupplyDao extends Dao {

    public static Supply get(long id) {
        Supply Supply = null;
        try {
            Supply = em.createQuery("from Supply where supply_id=:id", Supply.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ignored) {
        }
        return Supply;
    }

    public static List<Supply> getAll() {
        return em.createQuery("from Supply", Supply.class).getResultList();
    }

    public static boolean add(Supply Supply) {
        boolean result = false;
        try {
            em.persist(Supply);
            em.getTransaction().commit();
            result = true;
        } catch (PersistenceException exception) {
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public static boolean update(long id, Supply Supply) {
        Supply old = get(id);
        if(old != null) {
            old.setItemId(Supply.getItemId());
            old.setWarehouseId(Supply.getWarehouseId());
            old.setQuantity(Supply.getQuantity());
            boolean result = false;
            try {
                em.merge(Supply);
                em.getTransaction().commit();
                result = true;
            } catch (PersistenceException exception) {
                System.out.println(exception.getMessage());
            }
            return result;
        }
        else {
            return add(Supply);
        }
    }

    public static boolean delete(long id) {
        Supply Supply = em.find(Supply.class, id);
        boolean result = false;
        if (Supply != null) {
            try {
                em.remove(Supply);
                em.getTransaction().commit();
                result = true;
            } catch (PersistenceException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return result;
    }
}
