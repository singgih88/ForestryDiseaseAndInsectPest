package com.jecyhw.mvc.repository;

import com.jecyhw.core.file.GridFsFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by jecyhw on 17-2-15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DiseaseRepositoryTest {


    @Autowired
    DiseaseRepository diseaseRepository;

    @Autowired
    GridFsFile gridFsFile;

    @Test
    public void test() {


    }

}
