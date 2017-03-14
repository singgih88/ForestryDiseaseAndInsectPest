package com.jecyhw.mvc.service.impl;

import com.jecyhw.mvc.domain.Pest;
import com.jecyhw.mvc.repository.BaseOperations;
import com.jecyhw.mvc.repository.PestRepository;
import com.jecyhw.mvc.service.PestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jecyhw on 16-9-8.
 */
@Service
public class PestServiceImpl extends BaseService<Pest> implements PestService {

    @Autowired
    PestRepository pestRepository;

    @Override
    protected BaseOperations<Pest> getRepository() {
        return pestRepository;
    }
}
