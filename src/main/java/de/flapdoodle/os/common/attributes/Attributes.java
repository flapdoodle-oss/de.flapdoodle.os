package de.flapdoodle.os.common.attributes;

import java.util.function.Function;

public abstract class Attributes {

  public static TextFile textFile(String fileName) {
    return ImmutableTextFile.of(fileName);
  }

  public static <T> MappedTextFile<T> mappedTextFile(String fileName, Function<String, T> converter) {
    return ImmutableMappedTextFile.of(fileName, converter);
  }

  public static SystemProperty systemProperty(String name) {
    return ImmutableSystemProperty.of(name);
  }
}
