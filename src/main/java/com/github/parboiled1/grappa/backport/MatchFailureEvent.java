/*
 * Copyright (C) 2015 Francis Galiegue <fgaliegue@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.parboiled1.grappa.backport;

import org.parboiled.MatcherContext;

/**
 * Event posted when a match has failed
 *
 * @param <V> type parameter of the matching context
 */
public final class MatchFailureEvent<V>
    extends MatchContextEvent<V>
{
    public MatchFailureEvent(final MatcherContext<V> context)
    {
        super(context);
    }
}
