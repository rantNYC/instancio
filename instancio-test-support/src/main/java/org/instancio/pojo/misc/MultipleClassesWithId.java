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
package org.instancio.pojo.misc;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

//
//        A
//      / |  \
//    ID  B    C
//        |   /|\
//       ID  B D ID
//           |  \
//           ID  ID
@Getter
public class MultipleClassesWithId {

    private A a;

    @Getter
    @ToString
    public static class ID {
        private UUID id;
    }

    @Getter
    @ToString
    public static class A {
        private ID id;
        private B b;
        private C c;
    }

    @Getter
    @ToString
    public static class B {
        private ID id;
    }

    @Getter
    @ToString
    public static class C {
        private ID id;
        private B b;
        private D d;
    }

    @Getter
    @ToString
    public static class D {
        private ID id;
    }
}