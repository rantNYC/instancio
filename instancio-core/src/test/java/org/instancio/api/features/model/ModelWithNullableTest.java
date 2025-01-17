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
package org.instancio.api.features.model;

import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.pojo.basic.SupportedNumericTypes;
import org.instancio.testsupport.tags.NonDeterministicTag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Bindings.all;
import static org.instancio.Bindings.allInts;
import static org.instancio.Bindings.field;

@NonDeterministicTag
class ModelWithNullableTest {

    @Test
    void verifyNullable() {
        final Model<SupportedNumericTypes> model = Instancio.of(SupportedNumericTypes.class)
                .withNullable(field("bigInteger"))
                .withNullable(all(BigDecimal.class))
                .withNullable(allInts())
                .toModel();

        assertThat(collectResults(model, SupportedNumericTypes::getBigInteger)).containsNull();
        assertThat(collectResults(model, SupportedNumericTypes::getBigDecimal)).containsNull();
        assertThat(collectResults(model, SupportedNumericTypes::getIntegerWrapper)).containsNull();
    }

    private static Set<Object> collectResults(final Model<SupportedNumericTypes> model,
                                              final Function<SupportedNumericTypes, Object> collectFn) {

        final Set<Object> results = new HashSet<>();
        for (int i = 0; i < 500; i++) {
            final SupportedNumericTypes result = Instancio.create(model);
            results.add(collectFn.apply(result));
        }
        return results;
    }

}
