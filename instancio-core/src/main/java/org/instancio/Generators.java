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
package org.instancio;

import org.instancio.generator.GeneratorContext;
import org.instancio.generator.array.ArrayGenerator;
import org.instancio.generator.array.ArrayGeneratorSpec;
import org.instancio.generator.array.OneOfArrayGenerator;
import org.instancio.generator.array.OneOfArrayGeneratorSpec;
import org.instancio.generator.lang.ByteGenerator;
import org.instancio.generator.lang.DoubleGenerator;
import org.instancio.generator.lang.FloatGenerator;
import org.instancio.generator.lang.IntegerGenerator;
import org.instancio.generator.lang.LongGenerator;
import org.instancio.generator.lang.NumberGeneratorSpec;
import org.instancio.generator.lang.ShortGenerator;
import org.instancio.generator.lang.StringGenerator;
import org.instancio.generator.lang.StringGeneratorSpec;
import org.instancio.generator.math.BigDecimalGenerator;
import org.instancio.generator.math.BigIntegerGenerator;
import org.instancio.generator.time.LocalDateGenerator;
import org.instancio.generator.time.LocalDateTimeGenerator;
import org.instancio.generator.time.TemporalGeneratorSpec;
import org.instancio.generator.util.CollectionGeneratorSpec;
import org.instancio.generator.util.CollectionGeneratorSpecImpl;
import org.instancio.generator.util.MapGeneratorSpec;
import org.instancio.generator.util.MapGeneratorSpecImpl;
import org.instancio.generator.util.OneOfCollectionGenerator;
import org.instancio.generator.util.OneOfCollectionGeneratorSpec;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * This class provides access to built-in generators.
 * <p>
 * It can be used to customise random values generated by  built-in generators.
 * This includes numbers, collection sizes, string lengths, etc.
 */
public class Generators {

    private final GeneratorContext context;

    public Generators(final GeneratorContext context) {
        this.context = context;
    }

    /**
     * Customises generated {@link String} values.
     *
     * @return customised generator
     */
    public StringGeneratorSpec string() {
        return new StringGenerator(context);
    }

    /**
     * Customises generated {@link Byte} values.
     *
     * @return customised generator
     */
    public NumberGeneratorSpec<Byte> bytes() {
        return new ByteGenerator(context);
    }

    /**
     * Customises generated {@link Short} values.
     *
     * @return customised generator
     */
    public NumberGeneratorSpec<Short> shorts() {
        return new ShortGenerator(context);
    }

    /**
     * Customises generated {@link Integer} values.
     *
     * @return customised generator
     */
    public NumberGeneratorSpec<Integer> ints() {
        return new IntegerGenerator(context);
    }

    /**
     * Customises generated {@link Long} values.
     *
     * @return customised generator
     */
    public NumberGeneratorSpec<Long> longs() {
        return new LongGenerator(context);
    }

    /**
     * Customises generated {@link Float} values.
     *
     * @return customised generator
     */
    public NumberGeneratorSpec<Float> floats() {
        return new FloatGenerator(context);
    }

    /**
     * Customises generated {@link Double} values.
     *
     * @return customised generator
     */
    public NumberGeneratorSpec<Double> doubles() {
        return new DoubleGenerator(context);
    }

    /**
     * Customises generated {@link BigInteger} values.
     *
     * @return customised generator
     */
    public NumberGeneratorSpec<BigInteger> bigInteger() {
        return new BigIntegerGenerator(context);
    }

    /**
     * Customises generated {@link BigDecimal} values.
     *
     * @return customised generator
     */
    public NumberGeneratorSpec<BigDecimal> bigDecimal() {
        return new BigDecimalGenerator(context);
    }

    /**
     * Customises generated {@link LocalDate} values.
     *
     * @return customised generator
     */
    public TemporalGeneratorSpec<LocalDate> localDate() {
        return new LocalDateGenerator(context);
    }

    /**
     * Customises generated {@link LocalDateTime} values.
     *
     * @return customised generator
     */
    public TemporalGeneratorSpec<LocalDateTime> localDateTime() {
        return new LocalDateTimeGenerator(context);
    }

    /**
     * Picks a random value from the given choices.
     *
     * @param choices to choose from
     * @param <T>     element type
     * @return generator for making a selection
     */
    @SafeVarargs
    public final <T> OneOfArrayGeneratorSpec<T> oneOf(T... choices) {
        return new OneOfArrayGenerator<T>(context).oneOf(choices);
    }

    /**
     * Picks a random value from the given choices.
     *
     * @param choices to choose from
     * @param <T>     element type
     * @return generator for making a selection
     */
    public final <T> OneOfCollectionGeneratorSpec<T> oneOf(Collection<T> choices) {
        return new OneOfCollectionGenerator<T>(context).oneOf(choices);
    }

    /**
     * Customises generated arrays.
     *
     * @param <T> array component type
     * @return customised generator
     */
    public <T> ArrayGeneratorSpec<T> array() {
        return new ArrayGenerator<>(context);
    }

    /**
     * Customises generated collections.
     *
     * @param <T> element type
     * @return customised generator
     */
    public <T> CollectionGeneratorSpec<T> collection() {
        return new CollectionGeneratorSpecImpl<>(context);
    }

    /**
     * Customises generated maps.
     *
     * @param <K> key type
     * @param <V> value type
     * @return customised generator
     */
    public <K, V> MapGeneratorSpec<K, V> map() {
        return new MapGeneratorSpecImpl<>(context);
    }
}
