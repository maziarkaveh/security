package no.uis.security.common.service;

import no.uis.security.common.repository.BasicRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public abstract class BasicServiceImpl<T, PK extends Serializable> implements BasicService<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    /**
     *  instance, set by constructor of child classes
     */

    protected BasicRepository<T, PK> dao;

    public BasicServiceImpl() {}

    public BasicServiceImpl(BasicRepository<T, PK> basicRepository) {
        this.dao = basicRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)

    public T get(PK id) {
        return dao.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)

    public boolean exists(PK id) {
        return dao.exists(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional

    public T save(T object) {
        return dao.store(object);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional

    public void remove(PK id) {
        dao.remove(id);
    }




    
}
