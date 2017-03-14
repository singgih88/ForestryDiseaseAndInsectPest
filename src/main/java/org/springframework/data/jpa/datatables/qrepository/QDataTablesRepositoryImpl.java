package org.springframework.data.jpa.datatables.qrepository;

import static org.springframework.data.jpa.datatables.qrepository.DataTablesUtils.getPageable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jecyhw.mvc.domain.QPest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

/**
 * Repository implementation
 * 
 * @author Damien Arrachequesne
 */
public class QDataTablesRepositoryImpl<T, ID extends Serializable>
    extends QueryDslMongoRepository<T, ID> implements QDataTablesRepository<T, ID> {

  private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER =
      SimpleEntityPathResolver.INSTANCE;

  private final EntityPath<T> path;
  private final PathBuilder<T> builder;

  public QDataTablesRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations) {
    this(entityInformation, mongoOperations, DEFAULT_ENTITY_PATH_RESOLVER);
  }

  public QDataTablesRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations, EntityPathResolver resolver) {
    super(entityInformation, mongoOperations, resolver);

    this.path = resolver.createPath(entityInformation.getJavaType());
    this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
  }


  @Override
  public DataTablesOutput<T> findAll(DataTablesInput input) {
    return findAll(input, null, null, null);
  }

  @Override
  public DataTablesOutput<T> findAll(DataTablesInput input, Predicate additionalPredicate) {
    return findAll(input, additionalPredicate, null, null);
  }

  @Override
  public DataTablesOutput<T> findAll(DataTablesInput input, Predicate additionalPredicate,
      Predicate preFilteringPredicate) {
    return findAll(input, additionalPredicate, preFilteringPredicate, null);
  }

  @Override
  public <R> DataTablesOutput<R> findAll(DataTablesInput input, Converter<T, R> converter) {
    return findAll(input, null, null, converter);
  }

  @Override
  public <R> DataTablesOutput<R> findAll(DataTablesInput input, Predicate additionalPredicate,
      Predicate preFilteringPredicate, Converter<T, R> converter) {
    DataTablesOutput<R> output = new DataTablesOutput<R>();
    output.setDraw(input.getDraw());
    if (input.getLength() == 0) {
      return output;
    }

    try {
      long recordsTotal = preFilteringPredicate == null ? count() : count(preFilteringPredicate);
      if (recordsTotal == 0) {
        return output;
      }
      output.setRecordsTotal(recordsTotal);

      Predicate predicate = PredicateFactory.createPredicate(this.builder, input);
      Page<T> data = findAll(new BooleanBuilder().and(predicate).and(additionalPredicate)
          .and(preFilteringPredicate).getValue(), getPageable(input));

      @SuppressWarnings("unchecked")
      List<R> content =
          converter == null ? (List<R>) data.getContent() : data.map(converter).getContent();
      output.setData(content);
      output.setRecordsFiltered(data.getTotalElements());

    } catch (Exception e) {
      output.setError(e.toString());
    }

    return output;
  }
}
