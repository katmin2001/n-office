package com.fis.crm.service.impl;

import com.fis.crm.domain.Authority;
import com.fis.crm.repository.AuthorityRepository;
import com.fis.crm.service.AuthorityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<String> getAllAuthorities() {
        List<Authority> authorities = authorityRepository.findAll();
        List<String> results = new ArrayList<>();
        authorities.forEach(x->{
            results.add(x.getName());
        });
        return results;
    }
}
