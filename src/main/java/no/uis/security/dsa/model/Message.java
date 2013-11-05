package no.uis.security.dsa.model;

import no.uis.security.common.model.Entity;
import no.uis.security.hash.HashService;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 11:39
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity
@Table
public class Message extends Entity<Long> {
    @Lob
    private String message;
    @Column
    private String r;
    @Column
    private String s;

    @ManyToOne
    private UserKeys fromUserKeys;

    private Message() {
    }

    private Message(String message, String r, String s, UserKeys fromUserKeys) {
        this.message = message;
        this.r = r;
        this.s = s;
        this.fromUserKeys = fromUserKeys;
    }

    public static Message generateNewMessage(final String message, UserKeys userKeys, final HashService hashService) {
        GlobalPublicKey gpk = userKeys.getGlobalPublicKey();
        Random rand = new SecureRandom();
        BigInteger k = generateK(gpk.getQ(), rand);
        BigInteger r = generateR(k, gpk, rand);
        BigInteger s = generateS(userKeys.getPrivateKey(), r, gpk.getQ(), k, hashService, message);
        return new Message(message, r.toString(), s.toString(), userKeys);
    }

    public String getMessage() {
        return message;
    }

    public String getR() {
        return r;
    }

    public String getS() {
        return s;
    }

    public UserKeys getFromUserKeys() {
        return fromUserKeys;
    }

    private static BigInteger generateK(BigInteger q, Random rand) {
        BigInteger tempK;
        do {
            tempK = new BigInteger(q.bitLength(), rand);
        } while (tempK.compareTo(q) != -1 && tempK.compareTo(BigInteger.ZERO) != 1);
        return tempK;
    }

    private static BigInteger generateR(BigInteger k, GlobalPublicKey globalPublicKey, Random rand) {

        BigInteger r = globalPublicKey.getG().modPow(k, globalPublicKey.getP()).mod(globalPublicKey.getQ());
        return r;
    }

    private static BigInteger generateS(BigInteger privateKey, BigInteger r, BigInteger q, BigInteger k, final HashService hashService, String message) {

        BigInteger hash = new BigInteger(hashService.hash(message), 16);
        BigInteger s = (k.modInverse(q).multiply(hash.add(privateKey.multiply(r)))).mod(q);
        return s;
    }

    public boolean verify(UserKeys userKeys, HashService hashService) {
        GlobalPublicKey gpk = userKeys.getGlobalPublicKey();
        BigInteger q = gpk.getQ();
        BigInteger g = gpk.getG();
        BigInteger p = gpk.getP();
        BigInteger publicKey = userKeys.getPublicKey();
        BigInteger r = new BigInteger(this.r);
        BigInteger s = new BigInteger(this.s);

        if (r.compareTo(BigInteger.ZERO) <= 0 || r.compareTo(q) >= 0) {
            return false;
        }
        if (s.compareTo(BigInteger.ZERO) <= 0 || s.compareTo(q) >= 0) {
            return false;
        }


        BigInteger hash = new BigInteger(hashService.hash(this.message), 16);
        BigInteger w = s.modInverse(q);
        BigInteger u1 = hash.multiply(w).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);
        BigInteger v = ((g.modPow(u1, p).multiply(publicKey.modPow(u2, p))).mod(p)).mod(q);

        return v.compareTo(r) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (fromUserKeys != null ? !fromUserKeys.equals(message1.fromUserKeys) : message1.fromUserKeys != null)
            return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null) return false;
        if (r != null ? !r.equals(message1.r) : message1.r != null) return false;
        if (s != null ? !s.equals(message1.s) : message1.s != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (r != null ? r.hashCode() : 0);
        result = 31 * result + (s != null ? s.hashCode() : 0);
        result = 31 * result + (fromUserKeys != null ? fromUserKeys.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + getId() +
                ", from user keys id=" + fromUserKeys.getId() +
                ", from user id=" + fromUserKeys.getUser().getId() +
                ", global public key id=" + fromUserKeys.getGlobalPublicKey().getId() +
                ", r='" + r + '\'' +
                ", s='" + s + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
