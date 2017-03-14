package com.jecyhw.mvc.repository;

import org.springframework.data.jpa.datatables.qrepository.QDataTablesRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by jecyhw on 16-11-5.
 */
@NoRepositoryBean
public interface BaseOperations<T> extends QDataTablesRepository<T, String> {
    List<T> findByPicturesExists(boolean exists);
    T findByChineseName(String chineseName);
    List<T> findBySource(String source);
}
