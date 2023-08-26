package com.github.sisimomo.graphqlsakila.commons.service.dto.scalar;

import java.time.Instant;
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

@DgsScalar(name = "DateTimeTimezone")
public class InstantScalar implements Coercing<Instant, String> {


  @Override
  public String serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext,
      @NotNull Locale locale) throws CoercingSerializeException {
    if (dataFetcherResult instanceof Instant instant) {
      return instant.toString();
    } else {
      throw new CoercingSerializeException("Not a valid DateTimeTimezone");
    }
  }

  @Override
  public Instant parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale)
      throws CoercingParseValueException {
    return Instant.parse(input.toString());
  }

  @Override
  public @Nullable Instant parseLiteral(@NotNull Value<?> input, @NotNull CoercedVariables variables,
      @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseLiteralException {
    if (input instanceof StringValue stringValue) {
      return parseValue(stringValue, graphQLContext, locale);
    }
    throw new CoercingParseLiteralException("Value is not a valid DateTimeTimezone");
  }

}
