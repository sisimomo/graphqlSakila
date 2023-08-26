package com.github.sisimomo.graphqlsakila.commons.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BytesToUuidUtils {

  public static String getUuidFromByteArray(byte[] bytes) {
    ByteBuffer bb = ByteBuffer.wrap(bytes);
    long high = bb.getLong();
    long low = bb.getLong();
    UUID uuid = new UUID(high, low);
    return uuid.toString();
  }

}
