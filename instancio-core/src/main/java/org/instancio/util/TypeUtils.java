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
package org.instancio.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeUtils {
    private TypeUtils() {
        // non-instantiable
    }

    public static Class<?> getArrayClass(final Type type) {
        if (type instanceof Class) {
            final Class<?> arrayClass = (Class<?>) type;
            Verify.isTrue(arrayClass.isArray(), "Not an array: %s", type);
            return arrayClass;
        }
        if (type instanceof GenericArrayType) {
            final GenericArrayType arrayType = (GenericArrayType) type;
            final Type genericComponent = arrayType.getGenericComponentType();
            return Array.newInstance(TypeUtils.getRawType(genericComponent), 0).getClass();
        }
        throw new IllegalArgumentException("Not an array: " + type);
    }

    public static Type[] getTypeArguments(final Type parameterizedType) {
        final ParameterizedType pType = (ParameterizedType) parameterizedType;
        return pType.getActualTypeArguments();
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getRawType(final Type type) {
        if (type instanceof Class) {
            return (Class<T>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<T>) ((ParameterizedType) type).getRawType();
        } else if (type instanceof GenericArrayType) {
            final Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            return getRawType(genericComponentType);
        }
        throw new UnsupportedOperationException("Unhandled type: " + type.getClass().getSimpleName());
    }

    public static Class<?> getGenericSuperclassRawTypeArgument(final Class<?> klass) {
        final ParameterizedType genericSuperclass = (ParameterizedType) klass.getGenericSuperclass();
        final Type genericType = genericSuperclass.getActualTypeArguments()[0];
        return getRawType(genericType);

    }
}
