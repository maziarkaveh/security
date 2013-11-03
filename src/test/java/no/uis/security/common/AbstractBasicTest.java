package no.uis.security.common;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(
        locations = {"classpath:/applicationContext.xml",
        })
public abstract class AbstractBasicTest extends AbstractJUnit4SpringContextTests {
}
