package no.uis.security.hash;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 03.11.13
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public interface HashService {
    String hash(String str);
    String hash(byte[] content);
}
