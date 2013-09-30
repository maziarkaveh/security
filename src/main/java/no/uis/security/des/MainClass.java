package no.uis.security.des;

import no.uis.security.des.userinterface.UI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
        UI ui = context.getBean(UI.class);
        ui.feistel();

    }
}
