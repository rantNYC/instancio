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
package org.instancio.settings;

import org.instancio.util.Verify;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Setting implements SettingKey {

    ARRAY_ELEMENTS_NULLABLE("array.elements.nullable", Boolean.class, false),
    ARRAY_MAX_LENGTH("array.max.length", Integer.class, Constants.MAX_SIZE),
    ARRAY_MIN_LENGTH("array.min.length", Integer.class, Constants.MIN_SIZE),
    ARRAY_NULLABLE("array.nullable", Boolean.class, false),
    BOOLEAN_NULLABLE("boolean.nullable", Boolean.class, false),
    BYTE_MAX("byte.max", Byte.class, (byte) 127),
    BYTE_MIN("byte.min", Byte.class, (byte) 1),
    BYTE_NULLABLE("byte.nullable", Boolean.class, false),
    CHARACTER_NULLABLE("character.nullable", Boolean.class, false),
    COLLECTION_ELEMENTS_NULLABLE("collection.elements.nullable", Boolean.class, false),
    COLLECTION_MAX_SIZE("collection.max.size", Integer.class, Constants.MAX_SIZE),
    COLLECTION_MIN_SIZE("collection.min.size", Integer.class, Constants.MIN_SIZE),
    COLLECTION_NULLABLE("collection.nullable", Boolean.class, false),
    DOUBLE_MAX("double.max", Double.class, (double) Constants.NUMERIC_MAX),
    DOUBLE_MIN("double.min", Double.class, 1d),
    DOUBLE_NULLABLE("double.nullable", Boolean.class, false),
    FLOAT_MAX("float.max", Float.class, (float) Constants.NUMERIC_MAX),
    FLOAT_MIN("float.min", Float.class, 1f),
    FLOAT_NULLABLE("float.nullable", Boolean.class, false),
    INTEGER_MAX("integer.max", Integer.class, Constants.NUMERIC_MAX),
    INTEGER_MIN("integer.min", Integer.class, 1),
    INTEGER_NULLABLE("integer.nullable", Boolean.class, false),
    LONG_MAX("long.max", Long.class, (long) Constants.NUMERIC_MAX),
    LONG_MIN("long.min", Long.class, 1L),
    LONG_NULLABLE("long.nullable", Boolean.class, false),
    MAP_KEYS_NULLABLE("map.keys.nullable", Boolean.class, false),
    MAP_MAX_SIZE("map.max.size", Integer.class, Constants.MAX_SIZE),
    MAP_MIN_SIZE("map.min.size", Integer.class, Constants.MIN_SIZE),
    MAP_NULLABLE("map.nullable", Boolean.class, false),
    MAP_VALUES_NULLABLE("map.values.nullable", Boolean.class, false),
    SHORT_MAX("short.max", Short.class, (short) Constants.NUMERIC_MAX),
    SHORT_MIN("short.min", Short.class, (short) 1),
    SHORT_NULLABLE("short.nullable", Boolean.class, false),
    STRING_ALLOW_EMPTY("string.allow.empty", Boolean.class, false),
    STRING_MAX_LENGTH("string.max.length", Integer.class, 10),
    STRING_MIN_LENGTH("string.min.length", Integer.class, 3),
    STRING_NULLABLE("string.nullable", Boolean.class, false);

    private final String key;
    private final Class<?> type;
    private final Object defaultValue;

    Setting(final String key, final Class<?> type, final Object defaultValue) {
        this.key = key;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public String key() {
        return key;
    }

    public Class<?> type() {
        return type;
    }

    public <T> T defaultValue() {
        return (T) defaultValue;
    }

    public static Setting getByKey(final String key) {
        return Verify.notNull(Constants.SETTING_KEY_MAP.get(key),
                "Invalid instancio property key: '%s'", key);
    }

    private static class Constants {
        private static final int NUMERIC_MAX = 10000;
        private static final int MIN_SIZE = 2;
        private static final int MAX_SIZE = 6;
        private static final Map<String, Setting> SETTING_KEY_MAP = Collections.unmodifiableMap(settingKeyMap());

        private static Map<String, Setting> settingKeyMap() {
            final Map<String, Setting> map = new HashMap<>();
            for (Setting setting : Setting.values()) {
                map.put(setting.key, setting);
            }
            return map;
        }
    }
}
