package cz.loglim.jrest;

import cz.loglim.jrest.model.Item;

import javax.persistence.*;
import java.util.List;

public class DAO {

    // Private
    private static EntityManagerFactory emf;

    private static void initialize() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("unit1");
        }
    }

    static void finish() {
        emf.close();
    }

    public static Item getItem(long id) {
        initialize();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Item item = null;
        try {
            item = em.createQuery("from Item where Id=:id", Item.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ignored) {
        }
        em.getTransaction().commit();
        em.close();
        return item;
    }

    public static List<Item> getItems() {
        initialize();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Item> items = em.createQuery("from Item", Item.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return items;
    }

    public static boolean addItem(Item item) {
        initialize();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        boolean result = false;
        try {
            em.persist(item);
            em.getTransaction().commit();
            result = true;
        } catch (PersistenceException exception) {
            System.out.println(exception.getMessage());
        }
        em.close();
        return result;
    }

    public static boolean updateItem(long id, Item item) {
        initialize();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Item old = getItem(id);
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
        em.close();
        return result;
    }

    public static boolean deleteItem(long id) {
        initialize();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
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
        em.close();
        return result;
    }
}
