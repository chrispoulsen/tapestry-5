// Copyright 2007, 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.util;

import org.apache.tapestry5.commons.Messages;
import org.apache.tapestry5.commons.internal.util.MessagesImpl;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;

import java.util.Collection;

final class PublicUtilMessages
{
    private static final Messages MESSAGES = MessagesImpl.forClass(PublicUtilMessages.class);

    static String duplicateKey(Object key, Object newValue, Object existingValue)
    {
        return MESSAGES.format("duplicate-key", key, newValue, existingValue);
    }

    static <V> String missingValue(V value, Collection<V> values)
    {
        return MESSAGES.format("missing-value", value, InternalUtils.joinSorted(values));
    }
}
