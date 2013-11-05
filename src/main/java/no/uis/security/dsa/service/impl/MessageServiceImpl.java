package no.uis.security.dsa.service.impl;

import no.uis.security.common.service.BasicServiceImpl;
import no.uis.security.dsa.model.GlobalPublicKey;
import no.uis.security.dsa.model.Message;
import no.uis.security.dsa.model.User;
import no.uis.security.dsa.model.UserKeys;
import no.uis.security.dsa.repository.MessageRepository;
import no.uis.security.dsa.repository.UserKeysRepository;
import no.uis.security.dsa.service.GlobalPublicKeyService;
import no.uis.security.dsa.service.MessageService;
import no.uis.security.dsa.service.UserKeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MessageServiceImpl extends BasicServiceImpl<Message, Long> implements MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
       super(messageRepository);
        this.messageRepository = messageRepository;
    }
}
