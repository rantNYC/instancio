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
package org.instancio.generator.misc;

import org.instancio.generator.AbstractGenerator;
import org.instancio.generator.GeneratorContext;
import org.instancio.internal.random.RandomProvider;
import org.instancio.internal.reflection.instantiation.Instantiator;

public class InstantiatingGenerator extends AbstractGenerator<Object> {

    private final Instantiator instantiator;
    private final Class<?> targetType;

    public InstantiatingGenerator(final GeneratorContext context, final Instantiator instantiator, final Class<?> targetType) {
        super(context);
        this.targetType = targetType;
        this.instantiator = instantiator;
    }

    @Override
    public Object generate(final RandomProvider random) {
        return instantiator.instantiate(targetType);
    }
}
