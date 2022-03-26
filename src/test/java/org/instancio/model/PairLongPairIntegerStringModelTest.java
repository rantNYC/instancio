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
package org.instancio.model;

import org.instancio.internal.model.ClassNode;
import org.instancio.internal.model.Node;
import org.instancio.pojo.generics.PairLongPairIntegerString;
import org.instancio.pojo.generics.basic.Pair;
import org.instancio.testsupport.fixtures.Types;
import org.instancio.testsupport.templates.ModelTestTemplate;
import org.instancio.testsupport.utils.NodeUtils;

import static org.instancio.testsupport.asserts.NodeAssert.assertNode;

class PairLongPairIntegerStringModelTest extends ModelTestTemplate<PairLongPairIntegerString> {

    @Override
    protected void verify(Node rootNode) {
        assertNode(rootNode)
                .hasKlass(PairLongPairIntegerString.class)
                .hasChildrenOfSize(1);

        // Pair<Long, Pair<Integer, String>>
        final String fieldName = "pairLongPairIntegerString";

        final Node outerPair = assertNode(NodeUtils.getChildNode(rootNode, fieldName))
                .hasParent(rootNode)
                .hasFieldName(fieldName)
                .hasKlass(Pair.class)
                .hasTypeMappedTo(Pair.class, "L", Long.class)
                .hasTypeMappedTo(Pair.class, "R", Types.PAIR_INTEGER_STRING.get())
                .hasTypeMapWithSize(2)
                .hasChildrenOfSize(2)
                .getAs(ClassNode.class);

        assertNode(NodeUtils.getChildNode(outerPair, "left"))
                .hasFieldName("left")
                .hasParent(outerPair)
                .hasKlass(Long.class)
                .hasNoChildren();

        final Node innerPair = assertNode(NodeUtils.getChildNode(outerPair, "right"))
                .hasFieldName("right")
                .hasParent(outerPair)
                .hasKlass(Pair.class)
                .hasTypeMappedTo(Pair.class, "L", Integer.class)
                .hasTypeMappedTo(Pair.class, "R", String.class)
                .hasTypeMapWithSize(2)
                .hasChildrenOfSize(2)
                .getAs(ClassNode.class);

        assertInnerPair(innerPair);
    }

    private void assertInnerPair(Node innerPair) {
        assertNode(NodeUtils.getChildNode(innerPair, "left"))
                .hasFieldName("left")
                .hasParent(innerPair)
                .hasKlass(Integer.class)
                .hasNoChildren();

        assertNode(NodeUtils.getChildNode(innerPair, "right"))
                .hasFieldName("right")
                .hasParent(innerPair)
                .hasKlass(String.class)
                .hasNoChildren();
    }
}