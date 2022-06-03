/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.scaleph.plugin.framework.property;

import java.util.Objects;

public class AllowableValue<T> implements DescribedValue<T> {

    private final T value;
    private final String name;
    private final String description;

    public AllowableValue(final T value) {
        this(value, value.toString());
    }

    public AllowableValue(final T value, final String name) {
        this(value, name, null);
    }

    public AllowableValue(final T value, final String name, final String description) {
        this.value = Objects.requireNonNull(value);
        this.name = Objects.requireNonNull(name);
        this.description = description;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}