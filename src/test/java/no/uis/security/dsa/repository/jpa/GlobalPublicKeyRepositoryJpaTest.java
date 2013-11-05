package no.uis.security.dsa.repository.jpa;

import no.uis.security.common.AbstractBasicTest;
import no.uis.security.dsa.model.GlobalPublicKey;
import no.uis.security.dsa.repository.GlobalPublicKeyRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 03.11.13
 * Time: 19:25
 * To change this template use File | Settings | File Templates.
 */
public class GlobalPublicKeyRepositoryJpaTest extends AbstractBasicTest {
    @Autowired
    private GlobalPublicKeyRepository globalPublicKeyRepositoryJpa;

    @Test
    public void testGetAll() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testStore() throws Exception {
        GlobalPublicKey instance = GlobalPublicKey.generateNewInstance();
        globalPublicKeyRepositoryJpa.store(instance);

    }

    @Test
    public void testRemove() throws Exception {

    }
    @Test
    public void lastGlobalPublicKey() throws Exception {
        GlobalPublicKey lastGlobalPublicKey = globalPublicKeyRepositoryJpa.getLastGlobalPublicKey();
        System.out.println(lastGlobalPublicKey);

    }
}
