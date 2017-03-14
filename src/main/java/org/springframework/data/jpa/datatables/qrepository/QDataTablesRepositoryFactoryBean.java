package org.springframework.data.jpa.datatables.qrepository;

import java.io.Serializable;


import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * {@link FactoryBean} creating DataTablesRepositoryFactory instances.
 * 
 * @author Damien Arrachequesne
 */
public class QDataTablesRepositoryFactoryBean<R extends MongoRepository<T, ID>, T, ID extends Serializable>
    extends MongoRepositoryFactoryBean<R, T, ID> {

  @Override
  protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
    return new DataTablesRepositoryFactory<T, ID>(operations);

  }

  private static class DataTablesRepositoryFactory<T, ID extends Serializable>
      extends MongoRepositoryFactory {

    DataTablesRepositoryFactory(MongoOperations mongoOperations) {
      super(mongoOperations);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
      Class<?> repositoryInterface = metadata.getRepositoryInterface();
      if (QDataTablesRepository.class.isAssignableFrom(repositoryInterface)) {
        return QDataTablesRepositoryImpl.class;
      } else {
        return super.getRepositoryBaseClass(metadata);
      }

    }
  }
}
