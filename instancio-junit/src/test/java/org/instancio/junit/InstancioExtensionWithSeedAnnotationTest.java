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
package org.instancio.junit;

import org.instancio.Instancio;
import org.instancio.internal.ThreadLocalRandomProvider;
import org.instancio.util.Sonar;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class InstancioExtensionWithSeedAnnotationTest {

    @AfterAll
    static void afterAll() {
        assertThat(ThreadLocalRandomProvider.getInstance().get())
                .as("Expected thread local value to be removed after test is done")
                .isNull();
    }

    @Test
    @Seed(1234)
    @SuppressWarnings(Sonar.ADD_ASSERTION)
    @DisplayName("Dummy test method to verify thread local is cleared in afterAll()")
    void dummy() {
        Instancio.create(String.class);
    }
}
