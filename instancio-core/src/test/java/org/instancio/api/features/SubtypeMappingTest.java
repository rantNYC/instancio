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
import org.instancio.pojo.basic.StringHolderAlternativeImpl;
import org.instancio.pojo.collections.lists.ListLong;
import org.instancio.pojo.collections.lists.TwoListsOfItemString;
import org.instancio.pojo.generics.basic.ItemAlternativeImpl;
import org.instancio.pojo.interfaces.ArrayOfItemInterfaceString;
import org.instancio.pojo.interfaces.ArrayOfStringHolderInterface;
import org.instancio.pojo.interfaces.ItemInterface;
import org.instancio.pojo.interfaces.ListOfItemInterfaceString;
import org.instancio.pojo.interfaces.ListOfStringHolderInterface;
import org.instancio.pojo.interfaces.MapOfItemInterfaceString;
import org.instancio.pojo.interfaces.StringHolderInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Bindings.field;

class SubtypeMappingTest {

    @Test
    @DisplayName("Subtype mapping from List to Vector")
    void subtypeMappingWithSingleList() {
        final ListLong result = Instancio.of(ListLong.class)
                .map(List.class, Vector.class)
                .create();

        assertThat(result.getList()).isNotEmpty().isInstanceOf(Vector.class);
    }

    @Test
    @DisplayName("Given subtype mapping from List to Vector and a generator, the generator takes precedence")
    void subtypeMappingWithMultipleLists() {
        final TwoListsOfItemString result = Instancio.of(TwoListsOfItemString.class)
                .map(List.class, Vector.class)
                .generate(field("list1"), gen -> gen.collection().type(LinkedList.class))
                .create();

        assertThat(result.getList1()).isNotEmpty().isInstanceOf(LinkedList.class);
        assertThat(result.getList2()).isNotEmpty().isInstanceOf(Vector.class);
    }

    @Nested
    class CollectionElementSubtypeMappingTest {

        @Test
        @DisplayName("Subtype mapping of a generic class (as collection element)")
        void subtypeMappingOfGenericClass() {
            final ListOfItemInterfaceString result = Instancio.of(ListOfItemInterfaceString.class)
                    .map(ItemInterface.class, ItemAlternativeImpl.class)
                    .create();

            assertThat(result.getList()).isNotEmpty()
                    .hasOnlyElementsOfType(ItemAlternativeImpl.class)
                    .allSatisfy(item -> assertThat(item.getValue()).isNotBlank());
        }

        @Test
        @DisplayName("Subtype mapping of non-generic class (as collection element)")
        void subtypeMappingOfNonGenericClass() {
            final ListOfStringHolderInterface result = Instancio.of(ListOfStringHolderInterface.class)
                    .map(StringHolderInterface.class, StringHolderAlternativeImpl.class)
                    .create();

            assertThat(result.getList()).isNotEmpty()
                    .hasOnlyElementsOfType(StringHolderAlternativeImpl.class)
                    .allSatisfy(item -> assertThat(item.getValue()).isNotBlank());
        }
    }

    @Nested
    class ArrayElementSubtypeMappingTest {

        @Test
        @DisplayName("Subtype mapping of a generic class (as array element)")
        void subtypeMappingOfGenericClass() {
            final ArrayOfItemInterfaceString result = Instancio.of(ArrayOfItemInterfaceString.class)
                    .map(ItemInterface.class, ItemAlternativeImpl.class)
                    .create();

            assertThat(result.getArray()).isNotEmpty()
                    .hasOnlyElementsOfType(ItemAlternativeImpl.class)
                    .allSatisfy(item -> assertThat(item.getValue()).isNotBlank());
        }

        @Test
        @DisplayName("Subtype mapping of non-generic class (as array element)")
        void subtypeMappingOfNonGenericClass() {
            final ArrayOfStringHolderInterface result = Instancio.of(ArrayOfStringHolderInterface.class)
                    .map(StringHolderInterface.class, StringHolderAlternativeImpl.class)
                    .create();

            assertThat(result.getArray()).isNotEmpty()
                    .hasOnlyElementsOfType(StringHolderAlternativeImpl.class)
                    .allSatisfy(item -> assertThat(item.getValue()).isNotBlank());
        }
    }

    @Nested
    class MapKeyValueSubtypeMappingTest {

        @Test
        @DisplayName("Subtype mapping of a generic class (as map key and map value)")
        void subtypeMappingOfGenericClass() {
            final MapOfItemInterfaceString result = Instancio.of(MapOfItemInterfaceString.class)
                    .map(ItemInterface.class, ItemAlternativeImpl.class)
                    .create();

            assertThat(result.getMap().keySet()).isNotEmpty()
                    .hasOnlyElementsOfType(ItemAlternativeImpl.class)
                    .allSatisfy(item -> assertThat(item.getValue()).isNotBlank());

            assertThat(result.getMap().values()).isNotEmpty()
                    .hasOnlyElementsOfType(ItemAlternativeImpl.class)
                    .allSatisfy(item -> assertThat(item.getValue()).isNotBlank());
        }
    }
}
