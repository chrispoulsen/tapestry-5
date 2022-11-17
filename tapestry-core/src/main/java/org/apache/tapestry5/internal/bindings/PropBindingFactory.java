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

package org.apache.tapestry5.internal.bindings;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.beanmodel.PropertyConduit;
import org.apache.tapestry5.beanmodel.services.PropertyConduitSource;
import org.apache.tapestry5.commons.Location;
import org.apache.tapestry5.commons.internal.services.StringInterner;
import org.apache.tapestry5.commons.internal.util.TapestryException;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.pageload.PageClassLoaderContext;
import org.apache.tapestry5.services.pageload.PageClassLoaderContextManager;

/**
 * Binding factory for reading and updating JavaBean properties.
 *
 * Expression are evaluated via a {@link PropertyConduit}, which is generated by {@link PropertyConduitSource} (which
 * therefore defines the expression language).
 */
public class PropBindingFactory implements BindingFactory
{
    private final PropertyConduitSource source;

    private final StringInterner interner;
    
    private final PageClassLoaderContextManager pageClassLoaderContextManager;

    public PropBindingFactory(PropertyConduitSource propertyConduitSource, StringInterner interner,
            PageClassLoaderContextManager pageClassLoaderContextManager)
    {
        source = propertyConduitSource;
        this.interner = interner;
        this.pageClassLoaderContextManager = pageClassLoaderContextManager;
    }

    public Binding newBinding(String description, ComponentResources container,
                              ComponentResources component, String expression, Location location)
    {
        
        Object target = container.getComponent();
        Class targetClass = target.getClass();
        targetClass = getClassLoaderAppropriateClass(targetClass);

        PropertyConduit conduit = source.create(targetClass, expression);

        String toString = interner.format("PropBinding[%s %s(%s)]", description, container
                .getCompleteId(), expression);

        return new PropBinding(location, target, conduit, expression, toString);
    }

    private Class getClassLoaderAppropriateClass(Class targetClass)
    {
        final String className = targetClass.getName();
        try 
        {
            final PageClassLoaderContext context = pageClassLoaderContextManager.get(className);
            targetClass = context.getProxyFactory()
                    .getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) 
        {
            throw new TapestryException(e.getMessage(), e);
        }
        return targetClass;
    }
}
