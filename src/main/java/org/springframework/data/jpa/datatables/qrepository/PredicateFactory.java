package org.springframework.data.jpa.datatables.qrepository;

import static org.springframework.data.jpa.datatables.qrepository.DataTablesUtils.*;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.util.StringUtils;


class PredicateFactory {

  private final static char ESCAPE_CHAR = '~';

  public static Predicate createPredicate(PathBuilder<?> entity, DataTablesInput input) {
    BooleanBuilder predicate = new BooleanBuilder();

    // check for each searchable column whether a filter value exists
    for (Column column : input.getColumns()) {
      String filterValue = column.getSearch().getValue();
      boolean isColumnSearchable = column.getSearchable() && StringUtils.hasText(filterValue);
      if (!isColumnSearchable) {
        continue;
      }
      if (filterValue.contains(OR_SEPARATOR)) {
        // the filter contains multiple values, add a 'WHERE .. IN' clause
        boolean nullable = false;
        List<String> values = new ArrayList<String>();
        for (String value : filterValue.split(ESCAPED_OR_SEPARATOR)) {
          if (NULL.equals(value)) {
            nullable = true;
          } else {
            values.add(ESCAPED_NULL.equals(value) ? NULL : value); // to match a 'NULL' string
          }
        }
        if (values.size() > 0 && isBoolean(values.get(0))) {
          List<Boolean> booleanValues = new ArrayList<Boolean>();
          for (int i = 0; i < values.size(); i++) {
            booleanValues.add(Boolean.valueOf(values.get(i)));
          }
          Predicate in = entity.getBoolean(column.getData()).in(booleanValues);
          if (nullable) {
            predicate = predicate.and(entity.getBoolean(column.getData()).isNull().or(in));
          } else {
            predicate = predicate.and(in);
          }
        } else {
          Predicate in = getStringExpression(entity, column.getData()).in(values);
          if (nullable) {
            predicate = predicate.and(entity.get(column.getData()).isNull().or(in));
          } else {
            predicate = predicate.and(in);
          }
        }
        continue;
      }
      // the filter contains only one value, add a 'WHERE .. LIKE' clause
      if (isBoolean(filterValue)) {
        predicate =
            predicate.and(entity.getBoolean(column.getData()).eq(Boolean.valueOf(filterValue)));
        continue;
      }

      StringExpression stringExpression = getStringExpression(entity, column.getData());
      if (NULL.equals(filterValue)) {
        predicate = predicate.and(stringExpression.isNull());
        continue;
      }

      String likeFilterValue =
          getLikeFilterValue(ESCAPED_NULL.equals(filterValue) ? NULL : filterValue);
      predicate = predicate.and(stringExpression.like(likeFilterValue));
    }

    // check whether a global filter value exists
    String globalFilterValue = input.getSearch().getValue();
    if (StringUtils.hasText(globalFilterValue)) {
      BooleanBuilder matchOneColumnPredicate = new BooleanBuilder();
      // add a 'WHERE .. LIKE' clause on each searchable column
      for (Column column : input.getColumns()) {
        if (column.getSearchable()) {
          matchOneColumnPredicate =
              matchOneColumnPredicate.or(getStringExpression(entity, column.getData())
                  .like(getLikeFilterValue(globalFilterValue)));
        }
      }
      predicate = predicate.and(matchOneColumnPredicate);
    }
    return predicate;
  }

  private static StringExpression getStringExpression(PathBuilder<?> entity, String columnData) {
    return Expressions.asString(entity.getString(columnData));
  }

  private static String getLikeFilterValue(String filterValue) {
    return "%" + filterValue.toLowerCase().replaceAll("~", "~~").replaceAll("%", "~%")
        .replaceAll("_", "~_") + "%";
  }

}
