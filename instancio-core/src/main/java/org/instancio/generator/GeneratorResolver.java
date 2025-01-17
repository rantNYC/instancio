/*
 *  Copyright 2022 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.instancio.generator;

import org.instancio.Generator;
import org.instancio.generator.array.ArrayGenerator;
import org.instancio.generator.lang.BooleanGenerator;
import org.instancio.generator.lang.ByteGenerator;
import org.instancio.generator.lang.CharacterGenerator;
import org.instancio.generator.lang.DoubleGenerator;
import org.instancio.generator.lang.EnumGenerator;
import org.instancio.generator.lang.FloatGenerator;
import org.instancio.generator.lang.IntegerGenerator;
import org.instancio.generator.lang.LongGenerator;
import org.instancio.generator.lang.ShortGenerator;
import org.instancio.generator.lang.StringGenerator;
import org.instancio.generator.math.BigDecimalGenerator;
import org.instancio.generator.math.BigIntegerGenerator;
import org.instancio.generator.time.LocalDateGenerator;
import org.instancio.generator.time.LocalDateTimeGenerator;
import org.instancio.generator.util.CollectionGenerator;
import org.instancio.generator.util.DateGenerator;
import org.instancio.generator.util.HashSetGenerator;
import org.instancio.generator.util.MapGenerator;
import org.instancio.generator.util.TreeMapGenerator;
import org.instancio.generator.util.TreeSetGenerator;
import org.instancio.generator.util.UUIDGenerator;
import org.instancio.generator.util.concurrent.ConcurrentHashMapGenerator;
import org.instancio.generator.util.concurrent.ConcurrentSkipListMapGenerator;
import org.instancio.generator.util.concurrent.atomic.AtomicIntegerGenerator;
import org.instancio.generator.util.concurrent.atomic.AtomicLongGenerator;
import org.instancio.generator.xml.XMLGregorianCalendarGenerator;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GeneratorResolver {

    private final Map<Class<?>, Generator<?>> generators = new HashMap<>();
    private final GeneratorContext context;

    public GeneratorResolver(final GeneratorContext context) {
        this.context = context;

        // Core types
        generators.put(byte.class, new ByteGenerator(context));
        generators.put(short.class, new ShortGenerator(context));
        generators.put(int.class, new IntegerGenerator(context));
        generators.put(long.class, new LongGenerator(context));
        generators.put(float.class, new FloatGenerator(context));
        generators.put(double.class, new DoubleGenerator(context));
        generators.put(boolean.class, new BooleanGenerator(context));
        generators.put(char.class, new CharacterGenerator(context));
        generators.put(Byte.class, new ByteGenerator(context));
        generators.put(Short.class, new ShortGenerator(context));
        generators.put(Integer.class, new IntegerGenerator(context));
        generators.put(Long.class, new LongGenerator(context));
        generators.put(Float.class, new FloatGenerator(context));
        generators.put(Double.class, new DoubleGenerator(context));
        generators.put(Boolean.class, new BooleanGenerator(context));
        generators.put(Character.class, new CharacterGenerator(context));
        generators.put(String.class, new StringGenerator(context));

        generators.put(Number.class, new IntegerGenerator(context));
        generators.put(BigInteger.class, new BigIntegerGenerator(context));
        generators.put(BigDecimal.class, new BigDecimalGenerator(context));
        generators.put(AtomicInteger.class, new AtomicIntegerGenerator(context));
        generators.put(AtomicLong.class, new AtomicLongGenerator(context));
        generators.put(Date.class, new DateGenerator(context));
        generators.put(LocalDate.class, new LocalDateGenerator(context));
        generators.put(LocalDateTime.class, new LocalDateTimeGenerator(context));
        generators.put(UUID.class, new UUIDGenerator(context));
        generators.put(XMLGregorianCalendar.class, new XMLGregorianCalendarGenerator(context));

        // Collections
        generators.put(Collection.class, new CollectionGenerator<>(context));
        generators.put(List.class, new CollectionGenerator<>(context));
        generators.put(Map.class, new MapGenerator<>(context));
        generators.put(ConcurrentMap.class, new ConcurrentHashMapGenerator<>(context));
        generators.put(ConcurrentNavigableMap.class, new ConcurrentSkipListMapGenerator<>(context));
        generators.put(SortedMap.class, new TreeMapGenerator<>(context));
        generators.put(NavigableMap.class, new TreeMapGenerator<>(context));
        generators.put(Set.class, new HashSetGenerator<>(context));
        generators.put(SortedSet.class, new TreeSetGenerator<>(context));
        generators.put(NavigableSet.class, new TreeSetGenerator<>(context));
    }

    public Optional<Generator<?>> get(final Class<?> klass) {
        Generator<?> generator = generators.get(klass);
        if (generator == null) {
            if (klass.isArray()) {
                generator = new ArrayGenerator<>(context, klass);
            } else if (klass.isEnum()) {
                generator = new EnumGenerator(context, klass);
            }
        }
        return Optional.ofNullable(generator);
    }
}
