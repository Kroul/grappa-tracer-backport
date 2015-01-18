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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.parboiled.Context;
import org.parboiled.MatcherContext;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * One parsing run trace event
 *
 * <p>You should normally not use this class directly. It is intended to be used
 * by an instance of a {@link TracingParseRunnerListener}.</p>
 *
 * <p>This class collects the following information about a parse event:</p>
 *
 * <ul>
 *     <li>the {@link TraceEventType event type};</li>
 *     <li>the timestamp of this event, in nanoseconds (see {@link
 *     System#nanoTime()});</li>
 *     <li>the index into the input buffer (see {@link
 *     Context#getCurrentIndex()};</li>
 *     <li>the name of the matcher;</li>
 *     <li>the matcher path;</li>
 *     <li>the match level.</li>
 * </ul>
 */
@SuppressWarnings("TypeMayBeWeakened")
@ParametersAreNonnullByDefault
public final class TraceEvent
{
    private final TraceEventType type;
    private long nanoseconds;
    private final int index;
    private final String matcher;
    private final String path;
    private final int level;

    @SuppressWarnings("ConstantConditions")
    public static TraceEvent before(final MatcherContext<?> context)
    {
        return new TraceEvent(TraceEventType.BEFORE_MATCH,
            context.getCurrentIndex(), context.getMatcher().toString(),
            context.getPath().toString(), context.getLevel());
    }

    @SuppressWarnings("ConstantConditions")
    public static TraceEvent failure(final MatcherContext<?> context)
    {
        return new TraceEvent(TraceEventType.MATCH_FAILURE,
            context.getCurrentIndex(), context.getMatcher().toString(),
            context.getPath().toString(), context.getLevel());
    }

    @SuppressWarnings("ConstantConditions")
    public static TraceEvent success(final MatcherContext<?> context)
    {
        return new TraceEvent(TraceEventType.MATCH_SUCCESS,
            context.getCurrentIndex(), context.getMatcher().toString(),
            context.getPath().toString(), context.getLevel());
    }

    @JsonCreator
    public TraceEvent(@JsonProperty("type") final TraceEventType type,
        @JsonProperty("nanoseconds") final long nanoseconds,
        @JsonProperty("index") final int index,
        @JsonProperty("matcher") final String matcher,
        @JsonProperty("path") final String path,
        @JsonProperty("level") final int level)
    {
        this.type = type;
        this.nanoseconds = nanoseconds;
        this.index = index;
        this.matcher = matcher;
        this.path = path;
        this.level = level;
    }

    @JsonIgnore
    private TraceEvent(final TraceEventType type, final int index,
        final String matcher, final String path, final int level)
    {
        this.type = type;
        this.index = index;
        this.matcher = matcher;
        this.path = path;
        this.level = level;
    }

    @JsonIgnore
    @SuppressWarnings("ConstantConditions")
    public TraceEvent(final TraceEventType type,
        final MatcherContext<?> context)
    {
        nanoseconds = System.nanoTime();
        this.type = type;
        index = context.getCurrentIndex();
        // TODO: .getMatcher() normally never returns null
        matcher = context.getMatcher().toString();
        path = context.getPath().toString();
        level = context.getLevel();
    }

    public TraceEventType getType()
    {
        return type;
    }

    public long getNanoseconds()
    {
        return nanoseconds;
    }

    void setNanoseconds(final long nanoseconds)
    {
        this.nanoseconds = nanoseconds;
    }

    public int getIndex()
    {
        return index;
    }

    public String getMatcher()
    {
        return matcher;
    }

    public String getPath()
    {
        return path;
    }

    public int getLevel()
    {
        return level;
    }

    @Override
    @Nonnull
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
            .add("type", type)
            .add("nanoseconds", nanoseconds)
            .add("index", index)
            .add("matcher", matcher)
            .add("path", path)
            .add("level", level)
            .toString();
    }
}
