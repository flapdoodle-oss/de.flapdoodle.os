package de.flapdoodle.os.common.attributes;

import org.immutables.value.Value;

import java.nio.charset.Charset;
import java.util.function.Function;

@Value.Immutable
public interface MappedTextFile<T> extends Attribute<T> {
  @Value.Parameter
  String name();

  @Value.Parameter
  Function<String, T> converter();

  @Value.Default
  default Charset charset() {
    return Charset.defaultCharset();
  }
}
