package de.flapdoodle.os.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@FunctionalInterface
public interface HasPecularities {
  List<Peculiarity<?>> pecularities();

  static List<Peculiarity<?>> asList(Peculiarity<?> ... peculiarities) {
    if (peculiarities.length==0) throw new IllegalArgumentException("is empty");
    return Collections.unmodifiableList(Arrays.asList(peculiarities));
  }
}
