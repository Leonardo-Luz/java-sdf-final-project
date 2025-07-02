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

    public static EntityManager connect(DatabaseEnum databaseEnum) {
        Dotenv dotenv = Dotenv.load();

        String url = "";
        String user = "";
        String password = "";

        Map<String, Object> config = new HashMap<>();

        switch (databaseEnum) {
            case PRODUCTION:
                url = dotenv.get("PRODUCTION_POSTGRES_DATABASE_URL");
                user = dotenv.get("PRODUCTION_POSTGRES_DATABASE_USER");
                password = dotenv.get("PRODUCTION_POSTGRES_DATABASE_PASSWORD");
                config.put("hibernate.hbm2ddl.auto", "update");
                break;
            case DEVELOPMENT:
                url = dotenv.get("DEVELOPMENT_POSTGRES_DATABASE_URL");
                user = dotenv.get("DEVELOPMENT_POSTGRES_DATABASE_USER");
                password = dotenv.get("DEVELOPMENT_POSTGRES_DATABASE_PASSWORD");
                config.put("hibernate.hbm2ddl.auto", "update");
                break;
            case TEST:
                url = dotenv.get("TEST_POSTGRES_DATABASE_URL");
                user = dotenv.get("TEST_POSTGRES_DATABASE_USER");
                password = dotenv.get("TEST_POSTGRES_DATABASE_PASSWORD");
                config.put("hibernate.hbm2ddl.auto", "create-drop");
                config.put("hibernate.show_sql", "true");
                config.put("hibernate.format_sql", "true");
                break;
        }

        config.put("javax.persistence.jdbc.url", url);
        config.put("javax.persistence.jdbc.user", user);
        config.put("javax.persistence.jdbc.password", password);
        config.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");

        entityManagerFactory = Persistence.createEntityManagerFactory("PostgresSQLDefaultPU", config);
        entityManager = entityManagerFactory.createEntityManager();

        return entityManager;
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
