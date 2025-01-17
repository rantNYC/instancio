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
package org.instancio.nodes;

import org.instancio.internal.nodes.ArrayNode;
import org.instancio.internal.nodes.Node;
import org.instancio.pojo.generics.basic.Item;
import org.instancio.pojo.generics.container.ItemArrayContainer;
import org.instancio.testsupport.templates.NodeTestTemplate;
import org.instancio.testsupport.utils.NodeUtils;

import static org.instancio.testsupport.asserts.NodeAssert.assertNode;

class ItemArrayStringIntegerContainerNodeTest extends NodeTestTemplate<ItemArrayContainer<String, Integer>> {

    @Override
    protected void verify(Node rootNode) {
        assertNode(rootNode)
                .hasTargetClass(ItemArrayContainer.class)
                .hasChildrenOfSize(2);

        // Item<X>[] itemArrayX
        assertItemArrayX(rootNode);

        // Item<Y>[] itemArrayX
        assertItemArrayY(rootNode);
    }

    private void assertItemArrayX(Node rootNode) {
        final String itemArrayField = "itemArrayX";
        final ArrayNode array = assertNode(NodeUtils.getChildNode(rootNode, itemArrayField))
                .hasFieldName(itemArrayField)
                .hasTargetClass(Item[].class)
                .hasGenericTypeName("org.instancio.pojo.generics.basic.Item<X>[]")
                .hasNoChildren()
                .hasEmptyTypeMap()
                .getAs(ArrayNode.class);

        assertElementNode(array, "X");
    }

    private void assertItemArrayY(Node rootNode) {
        final String itemArrayField = "itemArrayY";
        final ArrayNode array = assertNode(NodeUtils.getChildNode(rootNode, itemArrayField))
                .hasFieldName(itemArrayField)
                .hasTargetClass(Item[].class)
                .hasGenericTypeName("org.instancio.pojo.generics.basic.Item<Y>[]")
                .hasNoChildren()
                .hasEmptyTypeMap()
                .getAs(ArrayNode.class);

        assertElementNode(array, "Y");
    }

    private void assertElementNode(ArrayNode arrayNode, String expectedType) {
        final Node elementNode = arrayNode.getElementNode();
        assertNode(elementNode)
                .hasTargetClass(Item.class)
                .hasNullField()
                .hasParent(arrayNode)
                .hasTypeMappedTo(Item.class, "K", expectedType)
                .hasTypeMapWithSize(1)
                .hasChildrenOfSize(1);
    }
}