package de.flapdoodle.os.common.attributes;

public abstract class Attributes {

  public static TextFile textFile(String fileName) {
    return ImmutableTextFile.of(fileName);
  }

  public static SystemProperty systemProperty(String name) {
    return ImmutableSystemProperty.of(name);
  }
}
