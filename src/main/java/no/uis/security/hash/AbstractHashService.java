package no.uis.security.hash;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 03.11.13
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractHashService implements HashService {
    public String hash(String str) {
        return hash(str.getBytes());
    }
}
