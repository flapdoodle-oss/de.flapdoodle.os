package de.flapdoodle.os.common.attributes;

import org.immutables.value.Value;

@Value.Immutable
public interface SystemProperty extends Attribute<String> {
  @Value.Parameter
  String name();
}
