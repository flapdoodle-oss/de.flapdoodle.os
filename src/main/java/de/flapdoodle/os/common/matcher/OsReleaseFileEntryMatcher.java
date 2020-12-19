/**
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
