package de.flapdoodle.os.common.types;

import org.immutables.value.Value;

import java.util.Map;

@Value.Immutable
public interface OsReleaseFile {
  Map<String, String> attributes();
}
