// Copyright 2023 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.plastic;

import java.lang.reflect.Method;

/**
 * <p>
 * Interface that can be implemented to provide access to field values based on their name.
 * Usually implemented with {@linkplain} FieldValueProviderTransformation}.
 * <p/>
 * <p>
 * The name of its abstract method is intended to avoid clashes with other existing methods
 * in the class.
 * </p>
 * @see FieldValueProviderTransformation
 * @since 5.8.4
 */
public interface PropertyValueProvider
{
    /**
     * Returns the value of a given field.
     * @param fieldName the field name.
     * @return the field value.
     */
    Object __propertyValueProvider__get(String fieldName);
    
    /**
     * <p>
     * Returns the value of a given field in a given object if it belongs to a class
     * that implements {@linkplain PropertyValueProvider}. Otherwise, it throws an exception.
     * </p>
     * <p>
     * This is an utility method to avoid having to make casts very time you need to call
     * {@linkplain #__propertyValueProvider__get(String)}.
     * </p>
     * @param object an object.
     * @param fieldName the field name.
     * @return the field value.
     */
    static Object get(Object object, String fieldName)
    {
        if (object instanceof PropertyValueProvider)
        {
            return ((PropertyValueProvider) object).__propertyValueProvider__get(fieldName);
        }
        else
        {
            throw new RuntimeException("Class " + object.getClass().getName() + " doesn't implement " + PropertyValueProvider.class.getSimpleName());
        }
    }
    
}
