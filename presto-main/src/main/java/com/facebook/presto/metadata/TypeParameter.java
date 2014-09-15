/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.metadata;

import com.facebook.presto.spi.type.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public final class TypeParameter
{
    private final String name;
    private final boolean comparableRequired;

    @JsonCreator
    public TypeParameter(@JsonProperty("name") String name, @JsonProperty("comparableRequired") boolean comparableRequired)
    {
        this.name = checkNotNull(name, "name is null");
        this.comparableRequired = comparableRequired;
    }

    @JsonProperty
    public String getName()
    {
        return name;
    }

    @JsonProperty
    public boolean isComparableRequired()
    {
        return comparableRequired;
    }

    public boolean canBind(Type type)
    {
        return !comparableRequired || type.isComparable();
    }

    @Override
    public String toString()
    {
        return comparableRequired ? name + ":comparable" : name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeParameter other = (TypeParameter) o;

        return Objects.equals(this.name, other.name) &&
                Objects.equals(this.comparableRequired, other.comparableRequired);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, comparableRequired);
    }
}