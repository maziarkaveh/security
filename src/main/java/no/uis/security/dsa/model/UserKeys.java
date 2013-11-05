package no.uis.security.dsa.model;

import no.uis.security.common.model.Entity;
import no.uis.security.common.utils.RandomUtils;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 03.11.13
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity
@Table
public class UserKeys extends Entity<Long> {
    @ManyToOne
    private User user;
    @ManyToOne
    private GlobalPublicKey globalPublicKey;
    @Column(columnDefinition="varchar2(2000)")
    private String privateKey;
    @Column(columnDefinition="varchar2(2000)")
    private String publicKey;

    public User getUser() {
        return user;
    }

    public BigInteger getPublicKey() {
        return new BigInteger(publicKey);
    }

    public BigInteger getPrivateKey() {
        return new BigInteger(privateKey);
    }

    private UserKeys() {
    }

    private UserKeys(User user, GlobalPublicKey globalPublicKey, String privateKey, String publicKey) {
        this.user = user;
        this.globalPublicKey = globalPublicKey;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public GlobalPublicKey getGlobalPublicKey() {
        return globalPublicKey;
    }

    public static UserKeys generateNewInstance(final GlobalPublicKey globalPublicKey, final User user) {
        int l = globalPublicKey.getQ().bitLength();
        Random secureRandom = new SecureRandom();
        BigInteger privateKey = new BigInteger(RandomUtils.random(1, l), secureRandom).mod(globalPublicKey.getQ());
        BigInteger publicKey = globalPublicKey.getG().modPow(privateKey, globalPublicKey.getP());
        return new UserKeys(user, globalPublicKey, privateKey.toString(), publicKey.toString());
    }

    @Override
    public String toString() {
        return "UserKeys{" +
                "id=" + getId() +
                "\tuser=" + user +
                ", globalPublicKey=" + globalPublicKey +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserKeys userKeys = (UserKeys) o;

        if (globalPublicKey != null ? !globalPublicKey.equals(userKeys.globalPublicKey) : userKeys.globalPublicKey != null)
            return false;
        if (privateKey != null ? !privateKey.equals(userKeys.privateKey) : userKeys.privateKey != null) return false;
        if (publicKey != null ? !publicKey.equals(userKeys.publicKey) : userKeys.publicKey != null) return false;
        if (user != null ? !user.equals(userKeys.user) : userKeys.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (globalPublicKey != null ? globalPublicKey.hashCode() : 0);
        result = 31 * result + (privateKey != null ? privateKey.hashCode() : 0);
        result = 31 * result + (publicKey != null ? publicKey.hashCode() : 0);
        return result;
    }
}
