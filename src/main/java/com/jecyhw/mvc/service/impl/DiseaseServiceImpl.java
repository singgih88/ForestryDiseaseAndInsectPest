package com.jecyhw.mvc.service.impl;

import com.jecyhw.mvc.domain.Disease;
import com.jecyhw.mvc.repository.BaseOperations;
import com.jecyhw.mvc.repository.DiseaseRepository;
import com.jecyhw.mvc.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jecyhw on 16-11-5.
 */
@Service
public class DiseaseServiceImpl extends BaseService<Disease> implements DiseaseService {

    @Autowired
    DiseaseRepository diseaseRepository;

    @Override
    protected BaseOperations<Disease> getRepository() {
        return diseaseRepository;
    }
}
