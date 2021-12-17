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
package de.flapdoodle.os.common.attributes;

import org.immutables.value.Value;

import java.nio.charset.Charset;
import java.util.function.Function;

@Value.Immutable
public interface MappedTextFile<T> extends Attribute<T> {
  @Value.Parameter
  String name();

  @Value.Parameter
  Function<String, T> converter();

  @Value.Default
  default Charset charset() {
    return Charset.defaultCharset();
  }

  static <T> TypeCheckPredicate<MappedTextFile<T>> nameIs(String name) {
    return TypeCheckPredicate.of((Class) MappedTextFile.class, (MappedTextFile<T> it) -> it.name().equals(name));
  }
}
