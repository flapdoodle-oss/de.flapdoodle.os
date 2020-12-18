package de.flapdoodle.os.common.types;

public abstract class OsReleaseFileConverter {

  public static OsReleaseFile convert(String content) {
    String[] lines = content.split("[\n\r]+");
    ImmutableOsReleaseFile.Builder builder = ImmutableOsReleaseFile.builder();
    for (String line : lines) {
      int idx = line.indexOf("=");
      if (idx!=-1) {
        builder.putAttributes(line.substring(0,idx), line.substring(idx+1));
      }
    }
    return builder.build();
  }
}
