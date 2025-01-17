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

import org.instancio.internal.nodes.MapNode;
import org.instancio.internal.nodes.Node;
import org.instancio.pojo.generics.MapWithoutTypes;
import org.instancio.testsupport.templates.NodeTestTemplate;
import org.instancio.testsupport.utils.CollectionUtils;

import java.util.Map;

import static org.instancio.testsupport.asserts.NodeAssert.assertNode;

class MapWithoutTypesNodeTest extends NodeTestTemplate<MapWithoutTypes> {

    @Override
    protected void verify(Node rootNode) {
        assertNode(rootNode)
                .hasTargetClass(MapWithoutTypes.class)
                .hasChildrenOfSize(1);

        final MapNode map = assertNode(CollectionUtils.getOnlyElement(rootNode.getChildren()))
                .hasFieldName("map")
                .hasNoChildren()
                .hasTargetClass(Map.class)
                .getAs(MapNode.class);

        assertNode(map.getKeyNode()).hasTargetClass(Object.class);
        assertNode(map.getValueNode()).hasTargetClass(Object.class);
    }
}