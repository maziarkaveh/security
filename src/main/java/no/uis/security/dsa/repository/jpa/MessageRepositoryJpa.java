package no.uis.security.dsa.repository.jpa;

import no.uis.security.common.repository.BasicRepositoryJpa;
import no.uis.security.dsa.model.Message;
import no.uis.security.dsa.repository.MessageRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 03.11.13
 * Time: 19:21
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class MessageRepositoryJpa extends BasicRepositoryJpa<Message, Long> implements MessageRepository {
    public MessageRepositoryJpa() {
        super(Message.class);
    }


}
