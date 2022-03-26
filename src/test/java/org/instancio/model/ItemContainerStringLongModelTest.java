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

import org.instancio.internal.model.Node;
import org.instancio.pojo.generics.basic.Item;
import org.instancio.pojo.generics.container.ItemContainer;
import org.instancio.testsupport.templates.ModelTestTemplate;
import org.instancio.testsupport.utils.NodeUtils;

import static org.instancio.testsupport.asserts.NodeAssert.assertNode;
import static org.instancio.testsupport.utils.CollectionUtils.getOnlyElement;

class ItemContainerStringLongModelTest extends ModelTestTemplate<ItemContainer<String, Long>> {

    @Override
    protected void verify(Node rootNode) {
        assertNode(rootNode)
                .hasKlass(ItemContainer.class)
                .hasChildrenOfSize(2);

        final String itemValueXField = "itemValueX";
        final Node itemValueX = assertNode(NodeUtils.getChildNode(rootNode, itemValueXField))
                .hasParent(rootNode)
                .hasFieldName(itemValueXField)
                .hasKlass(Item.class)
                .hasTypeMappedTo(Item.class, "K", "X")
                .hasTypeMapWithSize(1)
                .hasChildrenOfSize(1)
                .getAs(Node.class);

        assertNode(getOnlyElement(itemValueX.getChildren()))
                .hasParent(itemValueX)
                .hasFieldName("value")
                .hasKlass(String.class)
                .hasNoChildren();

        final String itemValueYField = "itemValueY";
        final Node itemValueY = assertNode(NodeUtils.getChildNode(rootNode, itemValueYField))
                .hasParent(rootNode)
                .hasFieldName(itemValueYField)
                .hasKlass(Item.class)
                .hasTypeMappedTo(Item.class, "K", "Y")
                .hasTypeMapWithSize(1)
                .hasChildrenOfSize(1)
                .getAs(Node.class);

        assertNode(getOnlyElement(itemValueY.getChildren()))
                .hasParent(itemValueY)
                .hasFieldName("value")
                .hasKlass(Long.class)
                .hasNoChildren();
    }
}