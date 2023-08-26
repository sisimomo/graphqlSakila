package com.github.sisimomo.graphqlsakila.commons.service.dto.scalar;

import java.math.BigDecimal;
import java.util.Locale;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.netflix.graphql.dgs.DgsScalar;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

@DgsScalar(name = "Decimal")
public class DecimalScalar implements Coercing<BigDecimal, Double> {

  @Override
  public Double serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext,
      @NotNull Locale locale) throws CoercingSerializeException {
    if (dataFetcherResult instanceof BigDecimal bigDecimal) {
      return bigDecimal.doubleValue();
    } else {
      throw new CoercingSerializeException("Not a valid Decimal");
    }
  }

  @Override
  public BigDecimal parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale)
      throws CoercingParseValueException {
    return new BigDecimal(input.toString());
  }

  @Override
  public @Nullable BigDecimal parseLiteral(@NotNull Value<?> input, @NotNull CoercedVariables variables,
      @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseLiteralException {
    if (input instanceof StringValue stringValue) {
      return parseValue(stringValue, graphQLContext, locale);
    }
    throw new CoercingParseLiteralException("Value is not a valid Decimal");
  }

}
