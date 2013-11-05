package no.uis.security.dsa.model;

import no.uis.security.common.model.Entity;
import no.uis.security.common.utils.RandomUtils;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
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
@NamedQueries({
        @NamedQuery(name = "getLastGlobalPublicKey", query = "from GlobalPublicKey order by generatedDate desc")
})
public class GlobalPublicKey extends Entity<Long> {
    @Transient
    private static int primeCenterie = 20;
    @Column(columnDefinition="varchar2(2000)")
    private String q;
    @Column(columnDefinition="varchar2(2000)")
    private String p;
    @Column(columnDefinition="varchar2(2000)")
    private String g;
    @Temporal(TemporalType.TIMESTAMP)
    private Date generatedDate;
    @Transient
    private static GlobalPublicKey globalPublicKey;


    private GlobalPublicKey() {

    }

    public GlobalPublicKey(String q, String p, String g) {
        this.q = q;
        this.p = p;
        this.g = g;
        this.generatedDate = Calendar.getInstance().getTime();
    }

    public static GlobalPublicKey generateNewInstance() {

        Random rand = new SecureRandom();
        BigInteger q, p, g;
        q = new BigInteger(160, primeCenterie, rand);
        p = generateP(q, rand);
        g = generateG(p, q, rand);


        return new GlobalPublicKey(q.toString(), p.toString(), g.toString());
    }


    private static BigInteger generateP(BigInteger q, Random rand) {
        BigInteger p;
        if (q == null) {
            throw new IllegalStateException();
        }
        int l = 512 + RandomUtils.random(0, 512 / 64) * 64;
        if (!(l >= 512 && l <= 1024 && l % 64 == 0)) {
            return generateP(q, rand);
        }
        BigInteger pMinusOne;
        do {
            p = new BigInteger(l, rand);
            pMinusOne = p.subtract(BigInteger.ONE);
            p = p.add(q.subtract(pMinusOne.mod(q)));
        } while (p.bitLength() != l || !p.isProbablePrime(primeCenterie));
        return p;
    }

    private static BigInteger generateG(BigInteger p, BigInteger q, Random rand) {

        BigInteger g = null;
        BigInteger pMinusOne = p.subtract(BigInteger.ONE);
        BigInteger pow = pMinusOne.divide(q);
        BigInteger h;
        do {

            h = new BigInteger(RandomUtils.random(1, pMinusOne.bitLength()), rand).mod(pMinusOne);
            if (!(h.compareTo(BigInteger.ONE) > 0 && h.compareTo(pMinusOne) < 0)) {
                continue;
            }
            g = h.modPow(pow, p);
        } while (!(g.compareTo(BigInteger.ONE) > 0));
        return g;
    }


    public BigInteger getQ() {
        return StringUtils.isEmpty(q) ? null : new BigInteger(q);
    }

    public BigInteger getP() {
        return StringUtils.isEmpty(p) ? null : new BigInteger(p);
    }

    public BigInteger getG() {
        return StringUtils.isEmpty(g) ? null : new BigInteger(g);
    }

    @Override
    public String toString() {
        return "GlobalPublicKey{" +
                "id=" + getId() +
                ", q=" + q +
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
