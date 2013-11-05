package no.uis.security.common.service;


public interface BasicService<T, PK extends java.io.Serializable> {

    java.util.List<T> getAll();

    T get(PK pk);

    boolean exists(PK pk);

    T save(T t);

    void remove(PK pk);


}