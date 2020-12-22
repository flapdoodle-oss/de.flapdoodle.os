package de.flapdoodle.os.common.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Immutables {

  private Immutables() {}

  public static <T> List<T> asList(T ...values) {
    return Collections.unmodifiableList(Arrays.asList(values));
  }

  public static <T> List<T> asNonEmptyList(T ...values) {
    if (values.length==0) throw new IllegalArgumentException("is empty");
    return asList(values);
  }
}
