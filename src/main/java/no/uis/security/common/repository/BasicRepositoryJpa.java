package no.uis.security.common.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class BasicRepositoryJpa<T, PK extends Serializable> implements BasicRepository<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

//    public static final String PERSISTENCE_UNIT_NAME = "ApplicationEntityManager";

    private BasicRepositoryJpa() {
    }

    /**
     * Entity manager, injected by Spring using @PersistenceContext annotation on setEntityManager()
     */

    private EntityManager entityManager;
    private Class<T> persistentClass;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing or using dependency injection.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public BasicRepositoryJpa(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }


    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext(unitName = "ApplicationEntityManager")
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing or using dependency injection.
     *
     * @param persistentClass the class type you'd like to persist
     * @param entityManager   the configured EntityManager for JPA implementation.
     */
    public BasicRepositoryJpa(final Class<T> persistentClass, EntityManager entityManager) {
        this.persistentClass = persistentClass;
        this.entityManager = entityManager;
    }

    public List<T> getAll() {
        return this.getEntityManager().createQuery(
                "select obj from " + this.persistentClass.getName() + " obj")
                .getResultList();
    }

    public List<T> getAllDistinct() {
        return this.getEntityManager().createQuery(
                "select distinct obj from " + this.persistentClass.getName() + " obj")
                .getResultList();
    }

    public T get(PK id) {
        T entity = this.getEntityManager().find(this.persistentClass, id);

        if (entity == null) {
            String msg = "Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...";
            log.warn(msg);
            throw new EntityNotFoundException(msg);
        }

        return entity;
    }

    public boolean exists(PK id) {
        T entity = this.getEntityManager().find(this.persistentClass, id);
        return entity != null;
    }
    @Transactional
    public T store(T object) {
        return this.getEntityManager().merge(object);
    }

    public void remove(PK id) {
        getEntityManager().remove(this.get(id));
    }

    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        TypedQuery<T> namedQuery = this.getEntityManager().createNamedQuery(queryName, persistentClass);
        for (Map.Entry<String, Object> stringObjectEntry : queryParams.entrySet()) {
            namedQuery.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        return namedQuery.getResultList();

    }

    public List<T> findByNamedQuery(String queryName) {
        TypedQuery<T> namedQuery = this.getEntityManager().createNamedQuery(queryName, persistentClass);
        return namedQuery.getResultList();

    }






    /**
     * @param criteria
     * @return
     */
    final private List<T> getFinderResult(Criteria criteria) {
        List<T> pojos = new ArrayList<T>();
        for (Object o : criteria.list()) {
            if (persistentClass.isInstance(o) ) {
                pojos.add((T) o);
            } else if (o instanceof List) {
                for (Object anObject : (List) o) {
                    if (persistentClass.isInstance(anObject)) {
                        pojos.add((T) anObject);
                    }
                }
            }
        }
        return pojos;
    }



}
