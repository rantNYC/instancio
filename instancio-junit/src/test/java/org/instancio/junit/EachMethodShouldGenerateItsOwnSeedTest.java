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

import org.instancio.internal.ThreadLocalRandomProvider;
import org.instancio.util.Sonar;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that each test method uses a different seed.
 * <p>
 * This test has a very small probability of failure if seeds happen to clash.
 */
@SuppressWarnings(Sonar.ADD_ASSERTION)
@ExtendWith(InstancioExtension.class)
class EachMethodShouldGenerateItsOwnSeedTest {

    private static final Set<Integer> seeds = new HashSet<>();

    @AfterAll
    static void afterAll() {
        assertThat(seeds).hasSize(5);
    }

    @Test
    void method1() {
        seeds.add(ThreadLocalRandomProvider.getInstance().get().getSeed());
    }

    @Test
    void method2() {
        seeds.add(ThreadLocalRandomProvider.getInstance().get().getSeed());
    }

    @Test
    void method3() {
        // same seed each time
        seeds.add(ThreadLocalRandomProvider.getInstance().get().getSeed());
        seeds.add(ThreadLocalRandomProvider.getInstance().get().getSeed());
    }

    // new seed per invocation
    @RepeatedTest(2)
    void method5() {
        seeds.add(ThreadLocalRandomProvider.getInstance().get().getSeed());
    }
}
