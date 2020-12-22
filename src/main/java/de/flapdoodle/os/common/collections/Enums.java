package de.flapdoodle.os.common.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Enums {

  private Enums() {}

  public static <T extends Enum<T>> List<T> valuesAsList(Class<T> enumType){
    return Collections.unmodifiableList(Arrays.asList(enumType.getEnumConstants()));
  }
}
