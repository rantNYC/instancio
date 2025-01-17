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
package org.instancio.api.features.seed;

import org.instancio.Instancio;
import org.instancio.TypeToken;
import org.instancio.testsupport.Constants;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleSeedTest {

    private static final int SEED = 123;
    private static final int SAMPLE_SIZE = 10;

    @Test
    void generateValuesWithTheSameSeed() {
        final Set<UUID> allResults = new HashSet<>();

        Set<UUID> uuids = null;
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            uuids = Instancio.of(new TypeToken<Set<UUID>>() {})
                    .withSeed(SEED)
                    .create();

            assertThat(uuids).hasSizeBetween(Constants.MIN_SIZE, Constants.MAX_SIZE);
            allResults.addAll(uuids);
        }
        assertThat(allResults).isEqualTo(uuids);
    }

    @Test
    void generateValuesWithRandomSeed() {
        final Set<UUID> allResults = new HashSet<>();

        for (int i = 0; i < SAMPLE_SIZE; i++) {
            Set<UUID> uuids = Instancio.of(new TypeToken<Set<UUID>>() {}).create();
            assertThat(uuids).hasSizeBetween(Constants.MIN_SIZE, Constants.MAX_SIZE);
            allResults.addAll(uuids);
        }

        assertThat(allResults).hasSizeBetween(
                SAMPLE_SIZE * Constants.MIN_SIZE,
                SAMPLE_SIZE * Constants.MAX_SIZE);
    }
}
