package ifrs.edu.br;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Database
 */
public class Database {
    private static EntityManager entityManager = null;
    private static EntityManagerFactory entityManagerFactory = null;

    public static void connect() {
        Dotenv dotenv = Dotenv.load();

        Map<String, Object> config = new HashMap<>();
        config.put("javax.persistence.jdbc.url", dotenv.get("POSTGRES_DATABASE_JDBC"));
        config.put("javax.persistence.jdbc.user", dotenv.get("POSTGRES_DATABASE_USER"));
        config.put("javax.persistence.jdbc.password", dotenv.get("POSTGRES_DATABASE_PASSWORD"));

        entityManagerFactory = Persistence.createEntityManagerFactory("PostgresSQLDefaultPU", config);
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static void close() {
        if (entityManager != null)
            entityManager.close();

        if (entityManagerFactory != null)
            entityManagerFactory.close();
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void setEntityManager(EntityManager entityManager) {
        Database.entityManager = entityManager;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        Database.entityManagerFactory = entityManagerFactory;
    }
}
