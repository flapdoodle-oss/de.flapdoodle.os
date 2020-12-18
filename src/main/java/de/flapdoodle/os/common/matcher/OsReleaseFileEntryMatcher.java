package de.flapdoodle.os.common.matcher;

import de.flapdoodle.os.common.types.OsReleaseFile;

import java.util.Optional;

public class OsReleaseFileEntryMatcher implements Matcher<OsReleaseFile, OsReleaseFileMapEntry> {
  @Override
  public boolean match(Optional<OsReleaseFile> value, OsReleaseFileMapEntry match) {
    return value.map(map -> {
      String mapValue = map.attributes().get(match.key());
      return mapValue != null && match.valuePattern().matcher(mapValue).matches();
    }).orElse(false);
  }
}
