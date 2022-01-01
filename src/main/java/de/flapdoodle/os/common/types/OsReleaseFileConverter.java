/*
 * Copyright (C) 2020
 *   Michael Mosmann <michael@mosmann.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.os.common.types;

import java.util.function.Function;

public class OsReleaseFileConverter implements Function<String, OsReleaseFile> {

  @Override
  public OsReleaseFile apply(String s) {
    return convert(s);
  }

  @Override public String toString() {
    return getClass().getSimpleName();
  }
  
  public static final OsReleaseFileConverter INSTANCE=new OsReleaseFileConverter();

  public static OsReleaseFile convert(String content) {
    String[] lines = content.split("[\n\r]+");
    ImmutableOsReleaseFile.Builder builder = ImmutableOsReleaseFile.builder();
    for (String line : lines) {
      int idx = line.indexOf("=");
      if (idx!=-1) {
        String key = line.substring(0, idx).trim();
        String value = line.substring(idx + 1).trim();
        if (value.charAt(0)=='\"' && value.charAt(value.length()-1)=='\"') {
          value=value.substring(1,value.length()-1);
        }
        builder.putAttributes(key, value);
      }
    }
    return builder.build();
  }
}
