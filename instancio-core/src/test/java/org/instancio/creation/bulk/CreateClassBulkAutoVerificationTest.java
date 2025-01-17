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
package org.instancio.creation.bulk;

import org.instancio.Instancio;
import org.instancio.pojo.generics.MiscFields;
import org.instancio.pojo.generics.TripletAFooBarBazStringListOfB;
import org.instancio.pojo.generics.basic.Item;
import org.instancio.pojo.generics.basic.Pair;
import org.instancio.pojo.generics.basic.Triplet;
import org.instancio.pojo.generics.container.OneItemContainer;
import org.instancio.pojo.generics.foobarbaz.Bar;
import org.instancio.pojo.generics.foobarbaz.Baz;
import org.instancio.pojo.generics.foobarbaz.Foo;
import org.instancio.pojo.person.Address;
import org.instancio.pojo.person.Person;
import org.instancio.testsupport.tags.CreateTag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Fail.fail;
import static org.instancio.testsupport.asserts.ReflectionAssert.assertThatObject;

/**
 * Various contrived generic types.
 */
@CreateTag
class CreateClassBulkAutoVerificationTest {
    private static final Logger LOG = LoggerFactory.getLogger(CreateClassBulkAutoVerificationTest.class);

    @Test
    void arrays() {
        bulkAssertFullyPopulated(
                TypeToCreate.of(int[].class),
                TypeToCreate.of(Integer[].class),
                TypeToCreate.of(Item[].class, Integer.class),
                TypeToCreate.of(Pair[].class, String.class, int.class),
                TypeToCreate.of(Triplet[].class, String.class, Address.class, Person.class),
                TypeToCreate.of(OneItemContainer[].class, Integer.class),
                TypeToCreate.of(MiscFields[].class, Integer.class, String.class, Boolean.class),
                TypeToCreate.of(MiscFields[].class, Person.class, Address.class, Boolean.class),
                TypeToCreate.of(TripletAFooBarBazStringListOfB[].class, Integer.class, String.class),
                TypeToCreate.of(List[].class, int.class),
                TypeToCreate.of(List[].class, Person.class),
                TypeToCreate.of(Map[].class, Integer.class, String.class),
                TypeToCreate.of(Map[].class, Person.class, Address.class));
    }

    @Test
    void collections() {
        bulkAssertFullyPopulated(
                TypeToCreate.of(Collection.class, String.class),
                TypeToCreate.of(Collection.class, Person.class));
    }

    @Test
    void lists() {
        bulkAssertFullyPopulated(
                TypeToCreate.of(List.class, String.class),
                TypeToCreate.of(List.class, Person.class));
    }

    @Test
    void maps() {
        bulkAssertFullyPopulated(
                TypeToCreate.of(Map.class, String.class, Integer.class),
                TypeToCreate.of(Map.class, String.class, Person.class),
                TypeToCreate.of(Map.class, Person.class, Address.class));
    }

    @Test
    void items() {
        bulkAssertFullyPopulated(
                TypeToCreate.of(Item.class, int[].class),
                TypeToCreate.of(Item.class, Integer[].class),
                TypeToCreate.of(Item.class, Integer.class),
                TypeToCreate.of(Item.class, Person.class));
    }

    @Test
    void pairs() {
        bulkAssertFullyPopulated(
                TypeToCreate.of(Pair.class, int[].class, Integer[].class),
                TypeToCreate.of(Pair.class, Boolean.class, Byte.class),
                TypeToCreate.of(Pair.class, Person.class, Address.class));
    }

    @Test
    void fooBarBaz() {
        bulkAssertFullyPopulated(
                TypeToCreate.of(Foo.class, String.class),
                TypeToCreate.of(Bar.class, Address.class),
                TypeToCreate.of(Baz.class, Person.class));
    }

    @Test
    void miscFields() {
        bulkAssertFullyPopulated(
                TypeToCreate.of(MiscFields.class, Integer.class, String.class, Boolean.class));
    }

    private static void bulkAssertFullyPopulated(TypeToCreate... typesToCreate) {
        List<TypeToCreate> failed = new ArrayList<>();
        for (TypeToCreate typeToCreate : typesToCreate) {
            try {
                final Object result = Instancio.of(typeToCreate.targetClass)
                        .withTypeParameters(typeToCreate.typeArgs)
                        .create();

                assertThatObject(result)
                        .as("Type '%s' failed: %s", typeToCreate.targetClass.getTypeName(), result)
                        .isFullyPopulated();
            } catch (Throwable ex) {
                LOG.error("Error creating: {}", typesToCreate, ex);
                failed.add(typeToCreate);
            }
        }

        if (!failed.isEmpty()) {
            LOG.error("Failures:");
            failed.forEach((typeToCreate) -> LOG.error("\n\n-> {}", typeToCreate));

            fail("Number of failures: %s", failed.size());
        }
    }

    static class TypeToCreate {
        private Class<?> targetClass;
        private Class<?>[] typeArgs;

        static TypeToCreate of(Class<?> targetClass, Class<?>... typeArgs) {
            TypeToCreate type = new TypeToCreate();
            type.targetClass = targetClass;
            type.typeArgs = typeArgs;
            return type;
        }

        @Override
        public String toString() {
            return String.format("'%s', type params: %s", targetClass, Arrays.toString(typeArgs));
        }
    }
}
