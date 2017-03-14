package com.jecyhw;

import com.jecyhw.mvc.domain.*;
import com.jecyhw.mvc.repository.BaseOperations;
import com.jecyhw.mvc.repository.DiseaseRepository;
import com.jecyhw.mvc.repository.PestRepository;
import com.jecyhw.mvc.repository.UserRepository;
import com.jecyhw.mvc.service.PestService;
import com.jecyhw.mvc.service.impl.BaseService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.qrepository.QDataTablesRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForestryDiseaseAndInsectPestApplicationTests {

	@Autowired
	PestRepository pestRepository;

	@Autowired
	DiseaseRepository diseaseRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	public void contextLoads() {
		List<Pest> pests = pestRepository.findByPicturesExists(true);
		int count = 0;
		for (Pest pest : pests) {
			count += pest.getPictures().size();
		}
		System.out.println(count);

		List<Disease> diseases = diseaseRepository.findByPicturesExists(true);
		count = 0;
		for (Disease disease : diseases) {
			count += disease.getPictures().size();
		}
		System.out.println(count);
//		User user = userRepository.findByUserName("jecyhw");
//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//		userRepository.save(user);
	}

}
