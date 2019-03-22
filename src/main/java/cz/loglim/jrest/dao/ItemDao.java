package cz.loglim.jrest.dao;

import cz.loglim.jrest.res.Item;

import javax.persistence.*;
import java.util.List;

public class ItemDao extends Dao {

    public ItemDao() {
        super();
    }

    public static Item get(long id) {
        EntityManager em = getNewEntityManager();
        Item item = null;
        try {
            item = em.createQuery("from Item where item_id=:id", Item.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ignored) {
        }
        close(em);
        return item == null ? new Item() : item;
    }

    public static List<Item> getAll() {
        EntityManager em = getNewEntityManager();
        List<Item> resultList = em.createQuery("from Item", Item.class).getResultList();
        close(em);
        return resultList;
    }

    public static boolean add(Item item) {
        EntityManager em = getNewEntityManager();
        boolean result = false;
        try {
            em.persist(item);
            em.getTransaction().commit();
            result = true;
        } catch (PersistenceException exception) {
            System.out.println(exception.getMessage());
        }
        close(em);
        return result;
    }

    public static boolean update(long id, Item item) {
        EntityManager em = getNewEntityManager();
        Item old = get(id);
        boolean result = false;
        if (old.getId() != null) {
            old.setPrice(item.getPrice());
            old.setName(item.getName());
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
        close(em);
        return result;
    }
}
