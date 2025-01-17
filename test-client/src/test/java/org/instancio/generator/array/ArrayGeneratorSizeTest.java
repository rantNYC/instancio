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
package org.instancio.generator.array;

import org.instancio.Instancio;
import org.instancio.features.Feature;
import org.instancio.features.FeatureTest;
import org.instancio.pojo.arrays.ArrayLong;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Bindings.all;

@FeatureTest({
        Feature.ARRAY_GENERATOR_MIN_LENGTH,
        Feature.ARRAY_GENERATOR_MAX_LENGTH,
        Feature.ARRAY_GENERATOR_LENGTH})
class ArrayGeneratorSizeTest {

    private static final int EXPECTED_SIZE = 50;

    @Test
    void size() {
        assertSize(spec -> spec.length(EXPECTED_SIZE), EXPECTED_SIZE);
    }

    @Test
    void sizeZero() {
        assertSize(spec -> spec.length(0), 0);
    }

    @Test
    void minLength() {
        assertSize(spec -> spec.minLength(EXPECTED_SIZE), EXPECTED_SIZE);
    }

    @Test
    void maxLength() {
        assertSize(spec -> spec.maxLength(1), 1);
    }

    private void assertSize(Function<ArrayGeneratorSpec<?>, ArrayGeneratorSpec<?>> fn, int expectedSize) {
        final ArrayLong result = Instancio.of(ArrayLong.class)
                .generate(all(long[].class), gen -> fn.apply(gen.array()))
                .generate(all(Long[].class), gen -> fn.apply(gen.array()))
                .create();

        assertThat(result.getPrimitive()).hasSize(expectedSize);
        assertThat(result.getWrapper()).hasSize(expectedSize);
    }
}
