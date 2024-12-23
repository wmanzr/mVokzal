package RUT.vokzal.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public abstract class BaseRepository<T, ID> {
    
    private Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    protected void create(T entity) {
        entityManager.persist(entity);
    }

    @Transactional
    protected T update(T entity) {
        return entityManager.merge(entity);
    }

    protected T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    protected List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                            .getResultList();
    }
}