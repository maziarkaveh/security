package no.uis.security.rsa.service;

public interface RandomGenerator<T> {


    T next();

    T next(int numBits);
}
