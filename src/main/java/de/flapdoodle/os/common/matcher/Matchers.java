package de.flapdoodle.os.common.matcher;

import java.util.regex.Pattern;

public class Matchers {
  public static MatchPattern matchPattern(String pattern) {
    return ImmutableMatchPattern.of(Pattern.compile(pattern));
  }

  public static OsReleaseFileMapEntry osReleaseFileEntry(String key, String valuePattern) {
    return ImmutableOsReleaseFileMapEntry.of(key, Pattern.compile(valuePattern));
  }
}
