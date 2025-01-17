/*
 *  Copyright 2022 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.instancio.generator.util;

import org.instancio.generator.GeneratorContext;
import org.instancio.internal.random.RandomProvider;
import org.instancio.internal.random.RandomProviderImpl;
import org.instancio.settings.Setting;
import org.instancio.settings.Settings;
import org.instancio.testsupport.tags.NonDeterministicTag;
import org.instancio.testsupport.tags.SettingsTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Percentage.withPercentage;
import static org.instancio.testsupport.asserts.GeneratedHintsAssert.assertHints;

@SettingsTag
@NonDeterministicTag
class MapGeneratorTest {

    private static final int MIN_SIZE = 101;
    private static final int MAX_SIZE = 102;
    private static final int SAMPLE_SIZE = 10_000;
    private static final int PERCENTAGE_THRESHOLD = 10;
    private static final Settings settings = Settings.defaults()
            .set(Setting.MAP_MIN_SIZE, MIN_SIZE)
            .set(Setting.MAP_MAX_SIZE, MAX_SIZE)
            .set(Setting.MAP_NULLABLE, true)
            .set(Setting.MAP_KEYS_NULLABLE, true)
            .set(Setting.MAP_VALUES_NULLABLE, true);

    private static final RandomProvider random = new RandomProviderImpl();
    private static final GeneratorContext context = new GeneratorContext(settings, random);

    @Test
    @DisplayName("Should generate either an empty map or null")
    void generateNullableMap() {
        final MapGenerator<?, ?> generator = new MapGenerator<>(context);
        final Set<Object> results = new HashSet<>();
        final int[] counts = new int[2];

        for (int i = 0; i < SAMPLE_SIZE; i++) {
            final Map<?, ?> result = generator.generate(random);
            results.add(result);
            counts[result == null ? 0 : 1]++;
        }

        assertThat(results).containsNull()
                .hasAtLeastOneElementOfType(HashMap.class)
                .hasSize(2); // null and empty map

        assertThat(counts[1])
                .as("Expecting 5/6 of results to be non-null")
                .isCloseTo((5 * SAMPLE_SIZE) / 6, withPercentage(PERCENTAGE_THRESHOLD));

        assertHints(generator.getHints())
                .dataStructureSizeBetween(MIN_SIZE, MAX_SIZE)
                .nullableResult(true)
                .nullableMapKeys(true)
                .nullableMapValues(true)
                .ignoreChildren(false)
                .nullableElements(false);
    }

}
