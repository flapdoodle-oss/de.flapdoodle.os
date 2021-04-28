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
package de.flapdoodle.os.common.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public abstract class IO {

  private static Logger logger= LoggerFactory.getLogger(IO.class);

  private IO() {

  }

  public static Optional<String> readString(Path path, Charset charset) {
    if (Files.exists(path)) {
      try {
        return Optional.of(new String(Files.readAllBytes(path), charset));
      } catch (IOException e) {
        logger.error("could not read "+path, e);
      }
    }
    return Optional.empty();
  }
}
