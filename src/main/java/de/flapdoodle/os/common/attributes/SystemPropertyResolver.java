package de.flapdoodle.os.common.attributes;

import java.util.Optional;

public class SystemPropertyResolver implements AttributeExtractor<String, SystemProperty> {
  @Override
  public Optional<String> extract(SystemProperty attribute) {
    return Optional.ofNullable(System.getProperty(attribute.name()));
  }
}
