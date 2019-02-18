package cz.loglim.jrest.dao;

import cz.loglim.jrest.res.Item;

import javax.persistence.*;
import java.util.List;

public class ItemDao extends Dao {

    public static Item get(long id) {
        Item item = null;
        try {
            item = em.createQuery("from Item where item_id=:id", Item.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ignored) {
        }
        return item == null ? new Item() : item;
    }

    public static List<Item> getAll() {
        return em.createQuery("from Item", Item.class).getResultList();
    }

    public static boolean add(Item item) {
        boolean result = false;
        try {
            em.persist(item);
            em.getTransaction().commit();
            result = true;
        } catch (PersistenceException exception) {
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public static boolean update(long id, Item item) {
        Item old = get(id);
        if(old != null) {
            old.setPrice(item.getPrice());
            old.setName(item.getName());
            boolean result = false;
            try {
                em.merge(item);
                em.getTransaction().commit();
                result = true;
            } catch (PersistenceException exception) {
                System.out.println(exception.getMessage());
            }
            return result;
        }
        else {
            return add(item);
        }
    }

    public static boolean delete(long id) {
        Item item = em.find(Item.class, id);
        boolean result = false;
        if (item != null) {
            try {
                em.remove(item);
                em.getTransaction().commit();
                result = true;
            } catch (PersistenceException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return result;
    }
}
