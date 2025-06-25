package ifrs.edu.br.dao;

import javax.persistence.EntityManager;

import ifrs.edu.br.Database;

public abstract class DAO<T> {
    protected EntityManager entityManager;
    private Class<T> entityClass;

    public DAO(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    public DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.entityManager = Database.getEntityManager();
    }

    public void insert(T object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }

    public T find(int id) {
        return entityManager.find(entityClass, id);
    }

    public void update(T object) {
        entityManager.getTransaction().begin();
        entityManager.merge(object);
        entityManager.getTransaction().commit();
    }

    public void delete(int id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.getReference(entityClass, id));
        entityManager.getTransaction().commit();
    }

    public abstract void clear();
}
