package cz.loglim.jrest.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Dao {

    // Private
    private static EntityManagerFactory emf;
    static EntityManager em;

    public static void begin() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("unit1");
        }
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public static void end() {
        if(em.isOpen() && em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
        em.close();
    }

    public static void finish() {
        emf.close();
    }

}
