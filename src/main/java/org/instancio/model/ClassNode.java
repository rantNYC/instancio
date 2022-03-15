package org.instancio.model;

import org.instancio.util.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ClassNode extends Node {
    private static final Logger LOG = LoggerFactory.getLogger(ClassNode.class);

    public ClassNode(final NodeContext nodeContext,
                     @Nullable final Field field,
                     final Class<?> klass,
                     @Nullable final Type genericType,
                     @Nullable final Node parent) {

        super(nodeContext, field, klass, genericType, parent);

        Verify.isNotArrayCollectionOrMap(klass);
    }

    @Override
    protected List<Node> collectChildren() {
        final GenericType effectiveType = getEffectiveType();
        final Class<?> effectiveClass = effectiveType.getRawType();
        final Type effectiveGenericType = effectiveType.getGenericType();

        if (effectiveClass.getPackage() == null || effectiveClass.getPackage().getName().startsWith(JAVA_PKG_PREFIX)) {
            return Collections.emptyList();
        }
        return makeChildren(getNodeContext(), effectiveClass, effectiveGenericType);
    }

    private List<Node> makeChildren(final NodeContext nodeContext, final Class<?> klass, @Nullable final Type genericType) {
        final NodeFactory nodeFactory = new NodeFactory();

        return Arrays.stream(klass.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .map(field -> {
                    Type passedOnGenericType = field.getGenericType();

                    Class<?> t = field.getType();
                    if (field.getGenericType() instanceof Class) {
                        t = (Class<?>) field.getGenericType();
                    } else if (field.getGenericType() instanceof TypeVariable) {
                        final Type mappedType = getTypeMap().get(field.getGenericType());
                        if (mappedType instanceof Class) {
                            t = (Class<?>) mappedType;
                        } else if (mappedType instanceof ParameterizedType) {
                            final ParameterizedType pType = (ParameterizedType) mappedType;
                            passedOnGenericType = pType;
                            t = (Class<?>) pType.getRawType();
                        }
                    }


                    LOG.trace("Passing generic type to child field node: {}", passedOnGenericType);
                    return nodeFactory.createNode(nodeContext, t, passedOnGenericType, field, this);
                })
                .collect(toList());
    }

}