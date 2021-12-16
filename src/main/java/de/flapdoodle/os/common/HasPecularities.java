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
package de.flapdoodle.os.common;

import de.flapdoodle.os.common.collections.Immutables;
import de.flapdoodle.os.common.types.Either;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface HasPecularities {
  List<Either<Peculiarity<?>, Any>> pecularities();

  static List<Either<Peculiarity<?>, Any>> asList(Peculiarity<?> ... peculiarities) {
    return Stream.of(peculiarities).map(it -> Either.<Peculiarity<?>,Any>left(it)).collect(Collectors.toList());
  }
}
