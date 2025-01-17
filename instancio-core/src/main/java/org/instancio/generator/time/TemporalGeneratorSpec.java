/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.instancio.generator.time;

import org.instancio.generator.GeneratorSpec;

import java.time.temporal.Temporal;

/**
 * Generator spec for {@link Temporal} types.
 */
public interface TemporalGeneratorSpec<T extends Temporal> extends GeneratorSpec<T> {

    /**
     * Generate a date in the past.
     *
     * @return spec builder
     */
    TemporalGeneratorSpec<T> past();

    /**
     * Generate a date in the future.
     *
     * @return spec builder
     */
    TemporalGeneratorSpec<T> future();

    /**
     * Generate a date between the given range.
     *
     * @param startInclusive start date (inclusive)
     * @param endExclusive   end date (exclusive)
     * @return spec builder
     */
    TemporalGeneratorSpec<T> range(T startInclusive, T endExclusive);
}
