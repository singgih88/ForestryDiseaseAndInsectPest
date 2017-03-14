package com.jecyhw;

import com.jecyhw.core.pivotviewer.DeepZoomFileTemplate;
import com.jecyhw.core.util.ObjectUtils;
import com.jecyhw.mvc.domain.BaseModel;
import com.jecyhw.mvc.domain.DeepZoomMetaData;
import com.jecyhw.mvc.domain.Picture;
import com.jecyhw.mvc.service.impl.BaseService;
import com.jecyhw.mvc.service.impl.DiseaseServiceImpl;
import com.jecyhw.mvc.service.impl.PestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.datatables.qrepository.QDataTablesRepositoryFactoryBean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
@EnableAsync
@EnableMongoRepositories(repositoryFactoryBeanClass = QDataTablesRepositoryFactoryBean.class)
public class ForestryDiseaseAndInsectPestApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForestryDiseaseAndInsectPestApplication.class, args);
	}

	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.initialize();
		return executor;
	}

	@Component
	static class StartupTask {
		static final private Logger logger = LoggerFactory.getLogger(StartupTask.class);

		@Autowired
		DeepZoomFileTemplate deepZoomFileTemplate;

		@Autowired
		PestServiceImpl pestService;

		@Autowired
		DiseaseServiceImpl diseaseService;

		@Async
		@EventListener(ContextRefreshedEvent.class)
		public void contextRefreshedEvent() {
			initPestPictures();
			initDiseasePictures();
		}


		private void initPestPictures() {
			logger.debug("initPestPictures start");
			createDeepZoomFile(pestService);
		}

		private void initDiseasePictures() {
			logger.debug("initDiseasePictures start");
			createDeepZoomFile(diseaseService);
		}

		private <T extends BaseModel> void  createDeepZoomFile(BaseService<T> baseService) {
			List<T> entities = baseService.picturesSizeNotEmpty();
			for (T entity : entities) {
				List<Picture> pictures = entity.getPictures();
				if (pictures.size() > 0) {
					Picture picture = pictures.get(0);
					if (!deepZoomFileTemplate.deepZoomFileExists(picture.getFileId())) {
						deepZoomFileTemplate.asyncCreateDeepZoomFile(picture.getFileId());
					}
					DeepZoomMetaData deepZoomMetaData = entity.getDeepZoom();
			 		if (ObjectUtils.isNull(deepZoomMetaData) || !picture.getFileId().equals(deepZoomMetaData.getFileId())) {
						entity.setDeepZoom(deepZoomFileTemplate.getDeepZoomFileMetaData(picture.getFileId()));
						baseService.save(entity);
					}
				}
			}
		}

	}
}
