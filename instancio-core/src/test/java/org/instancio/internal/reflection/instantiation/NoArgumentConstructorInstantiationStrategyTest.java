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
package org.instancio.internal.reflection.instantiation;

import org.instancio.pojo.basic.IntegerHolder;
import org.instancio.pojo.basic.IntegerHolderWithPrivateDefaultConstructor;
import org.instancio.pojo.basic.IntegerHolderWithoutDefaultConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NoArgumentConstructorInstantiationStrategyTest {

    private final InstantiationStrategy strategy = new NoArgumentConstructorInstantiationStrategy();

    @ValueSource(classes = {
            ArrayList.class,
            TreeSet.class,
            IntegerHolder.class,
            IntegerHolderWithPrivateDefaultConstructor.class
    })
    @ParameterizedTest
    void createInstanceSuccessful(Class<?> klass) {
        assertThat(strategy.createInstance(klass)).isNotNull();
    }

    @Test
    void createInstanceFails() {
        final Class<?> klass = IntegerHolderWithoutDefaultConstructor.class;
        assertThatThrownBy(() -> strategy.createInstance(klass))
                .isInstanceOf(InstantiationStrategyException.class)
                .hasMessage("Error instantiating %s", klass);
    }
}
