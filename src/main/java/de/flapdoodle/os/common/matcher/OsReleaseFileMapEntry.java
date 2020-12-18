package de.flapdoodle.os.common.matcher;

import de.flapdoodle.os.common.types.OsReleaseFile;
import org.immutables.value.Value;

import java.util.Map;
import java.util.regex.Pattern;

@Value.Immutable
public interface OsReleaseFileMapEntry extends Match<OsReleaseFile> {
  @Value.Parameter
  String key();

  @Value.Parameter
  Pattern valuePattern();
}
