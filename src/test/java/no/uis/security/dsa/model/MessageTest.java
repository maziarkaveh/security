package no.uis.security.dsa.model;

 import no.uis.security.common.AbstractBasicTest;
import no.uis.security.hash.HashService;
 import org.junit.Assert;
 import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public class MessageTest extends AbstractBasicTest {
    @Autowired
    private HashService hashService;
    @Test
    public void testGenerateNewMessage() throws Exception {
        GlobalPublicKey globalPublicKey = GlobalPublicKey.generateNewInstance();
        User user = new User("Test", "Test", "Test");
        User user2 = new User("Test", "Test", "Test");
        UserKeys userKeys = UserKeys.generateNewInstance(globalPublicKey, user);
        UserKeys userKeys2 = UserKeys.generateNewInstance(globalPublicKey, user2);
        Message message = Message.generateNewMessage("hello this a test ", userKeys, hashService);
        boolean verify = message.verify(userKeys, hashService);
        Assert.assertTrue(verify);
        Assert.assertFalse(message.verify(userKeys2, hashService));
    }
}
