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
package org.instancio.internal.model;

import org.instancio.util.Verify;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MapNode extends Node {

    private final Node keyNode;
    private final Node valueNode;

    public MapNode(final NodeContext nodeContext,
                   final Class<?> klass,
                   final Node keyNode,
                   final Node valueNode,
                   @Nullable final Field field,
                   @Nullable final Type genericType,
                   @Nullable final Node parent) {

        super(nodeContext, klass, field, genericType, parent);

        Verify.isTrue(Map.class.isAssignableFrom(klass), "Not a map type: %s ", klass.getName());

        keyNode.setParent(this);
        valueNode.setParent(this);

        this.keyNode = Verify.notNull(keyNode, "keyNode is null");
        this.valueNode = Verify.notNull(valueNode, "valueNode is null");
    }

    /**
     * Children come from the {@link #getKeyNode()} and {@link #getValueNode()}.
     */
    @Override
    protected List<Node> collectChildren() {
        return Collections.emptyList();
    }

    public Node getKeyNode() {
        return keyNode;
    }

    public Node getValueNode() {
        return valueNode;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        MapNode other = (MapNode) o;
        return Objects.equals(this.getKeyNode(), other.getKeyNode())
                && Objects.equals(this.getValueNode(), other.getValueNode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKeyNode(), getValueNode());
    }

}
