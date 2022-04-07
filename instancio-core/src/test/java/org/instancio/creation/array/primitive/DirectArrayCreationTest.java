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
package org.instancio.creation.array.primitive;

import org.instancio.Instancio;
import org.instancio.testsupport.utils.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class DirectArrayCreationTest {

    @Test
    void intArray() {
        int[] results = Instancio.create(int[].class);
        assertThat(new HashSet<>(ArrayUtils.toList(results))).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void integerArray() {
        Integer[] results = Instancio.create(Integer[].class);
        assertThat(new HashSet<>(ArrayUtils.toList(results))).hasSizeGreaterThanOrEqualTo(1);
    }
}
