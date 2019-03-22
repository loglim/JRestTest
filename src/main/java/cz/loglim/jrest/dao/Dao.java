package cz.loglim.jrest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Dao {

    // Private
    private static EntityManagerFactory emf;

    public Dao() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("unit1");
        }
    }

    protected static EntityManager getNewEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("unit1");
        }

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em;
    }

    protected static void close(EntityManager entityManager) {
        if (entityManager.isOpen() && entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
        entityManager.close();
    }
}
