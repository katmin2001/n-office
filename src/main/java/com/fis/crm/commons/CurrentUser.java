package com.fis.crm.commons;

import com.fis.crm.domain.User;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.security.SpringSecurityAuditorAware;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
    public final SpringSecurityAuditorAware springSecurityAuditorAware;
    public final UserRepository userRepository;

    public CurrentUser(SpringSecurityAuditorAware springSecurityAuditorAware, UserRepository userRepository) {
        this.springSecurityAuditorAware = springSecurityAuditorAware;
        this.userRepository = userRepository;
    }

    public User getCurrentUser(){
        String username = springSecurityAuditorAware.getCurrentAuditor().get();
        return userRepository.findByLogin(username);
    }
}
