package no.uis.security.dsa.service.impl;

import no.uis.security.common.service.BasicServiceImpl;
import no.uis.security.dsa.model.User;
import no.uis.security.dsa.repository.UserRepository;
import no.uis.security.dsa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl extends BasicServiceImpl<User, Long> implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }
}
