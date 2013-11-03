package no.uis.security.dsa.model;

import no.uis.security.common.model.Entity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 03.11.13
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity
@Table(name = "GlobalPublicKey")

public class GlobalPublicKey extends Entity<Long> {
    @Transient

    private int primeCenterie = 20;
    @Column
    private String q;
    @Column
    private String p;
    @Column
    private String g;
    @Transient
    private static GlobalPublicKey globalPublicKey;

    private GlobalPublicKey() {
        BigInteger q, p;
        q = new BigInteger(160, primeCenterie, rand);
        p = new BigInteger(160, primeCenterie, rand);;
        this.g = new BigInteger(160, primeCenterie, rand).toString();
        this.p = p.toString();
        this.q = q.toString();
    }

    public static GlobalPublicKey getInstance() {
        if (globalPublicKey == null) {
            globalPublicKey = new GlobalPublicKey();
        }
        return globalPublicKey;
    }

    @Transient
    private Random rand = new SecureRandom();


    private BigInteger generateP(BigInteger q, int l) {
        if (l % 64 != 0) {
            throw new IllegalArgumentException("L value is wrong");
        }
        BigInteger pTemp;
        BigInteger pTemp2;
        do {
            pTemp = new BigInteger(l, primeCenterie, rand);
            pTemp2 = pTemp.subtract(BigInteger.ONE);
            pTemp = pTemp.subtract(pTemp2.remainder(q));
        } while (!pTemp.isProbablePrime(primeCenterie) || pTemp.bitLength() != l);
        return pTemp;
    }

    private BigInteger generateG(BigInteger p, BigInteger q) {
        BigInteger aux = p.subtract(BigInteger.ONE);
        BigInteger pow = aux.divide(q);
        BigInteger gTemp;
        do {
            gTemp = new BigInteger(aux.bitLength(), rand);
        } while (gTemp.compareTo(aux) != -1 && gTemp.compareTo(BigInteger.ONE) != 1);
        return gTemp.modPow(pow, p);
    }


    public BigInteger getQ() {
        return new BigInteger(q);
    }

    public BigInteger getP() {
        return new BigInteger(p);
    }

    public BigInteger getG() {
        return new BigInteger(g);
    }

    @Override
    public String toString() {
        return "GlobalPublicKey{" +
                "q=" + q +
                ", p=" + p +
                ", g=" + g +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalPublicKey that = (GlobalPublicKey) o;

        if (g != null ? !g.equals(that.g) : that.g != null) return false;
        if (p != null ? !p.equals(that.p) : that.p != null) return false;
        if (q != null ? !q.equals(that.q) : that.q != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = q != null ? q.hashCode() : 0;
        result = 31 * result + (p != null ? p.hashCode() : 0);
        result = 31 * result + (g != null ? g.hashCode() : 0);
        return result;
    }
}
