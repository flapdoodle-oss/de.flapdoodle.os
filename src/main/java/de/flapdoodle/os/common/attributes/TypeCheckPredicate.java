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

import java.util.function.Predicate;

@Value.Immutable
public abstract class TypeCheckPredicate<T> implements Predicate<T> {
	@Value.Parameter
	protected abstract Class<T> typeClass();

	@Value.Parameter
	protected abstract Predicate<T> check();

	@Override
	public boolean test(T t) {
		if (typeClass().isInstance(t)) {
			return check().test(t);
		}
		return false;
	}

	public static <T> TypeCheckPredicate<T> of(Class<T> type, Predicate<T> check) {
		return ImmutableTypeCheckPredicate.of(type, check);
	}

	public static <T> TypeCheckPredicate<T> isInstanceOf(Class<T> type) {
		return of(type, it -> true);
	}
}
