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
package org.instancio.api.features;

import org.instancio.Instancio;
import org.instancio.pojo.basic.IntegerHolder;
import org.instancio.pojo.collections.TwoStringCollections;
import org.instancio.pojo.person.Person;
import org.instancio.pojo.person.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Bindings.all;
import static org.instancio.Bindings.allInts;
import static org.instancio.Bindings.allStrings;
import static org.instancio.Bindings.field;

class UserSuppliedClassGeneratorsTest {

    @Test
    @DisplayName("Binding core type should bind both, primitive and wrapper class")
    void userSuppliedIntegerClassGenerator() {
        final int expectedValue = 123;
        final IntegerHolder result = Instancio.of(IntegerHolder.class)
                .supply(allInts(), () -> expectedValue)
                .create();

        assertThat(result.getWrapper()).isEqualTo(expectedValue);
        assertThat(result.getPrimitive()).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("All String fields should use custom prefix except person.name")
    void userSuppliedStringClassGenerator() {
        final String prefix = "custom-prefix-for-ALL-String-fields-";
        final String expectedName = "Jane Doe";

        Person person = Instancio.of(Person.class)
                .supply(field("name"), () -> expectedName)
                .generate(allStrings(), gen -> gen.string().prefix(prefix))
                .create();

        assertThat(person.getName()).isEqualTo(expectedName);
        assertThat(person.getAddress().getCity()).startsWith(prefix);
        assertThat(person.getAddress().getAddress()).startsWith(prefix);
        assertThat(person.getAddress().getCountry()).startsWith(prefix);

        person.getAddress().getPhoneNumbers().forEach(phone -> {
            assertThat(phone.getCountryCode()).startsWith(prefix);
            assertThat(phone.getNumber()).startsWith(prefix);
        });

        for (Pet pet : person.getPets()) {
            assertThat(pet.getName()).startsWith(prefix);
        }
    }

    @Test
    @DisplayName("All Collection declarations should be assigned a HashSet with a new instance each time")
    void userSuppliedCollectionClassGeneratorWithGeneratorReturningANewInstanceEachTime() {
        final TwoStringCollections result = Instancio.of(TwoStringCollections.class)
                .supply(all(Collection.class), () -> new HashSet<>()) // new instance
                .create();

        assertThat(result.getOne()).isInstanceOf(Set.class).isEmpty();
        assertThat(result.getTwo()).isInstanceOf(Set.class).isEmpty();
        assertThat(result.getOne())
                .as("Expecting a different instance")
                .isNotSameAs(result.getTwo());
    }

    @Test
    @DisplayName("All Collection declarations should be assigned a HashSet with the sane instance each time")
    void userSuppliedCollectionClassGeneratorWithGeneratorReturningSameInstanceEachTime() {
        final Set<String> expectedValue = new HashSet<>();
        final TwoStringCollections result = Instancio.of(TwoStringCollections.class)
                .supply(all(Collection.class), () -> expectedValue) // same instance
                .create();

        assertThat(result.getOne()).isSameAs(result.getTwo())
                .isSameAs(expectedValue);
    }


}
