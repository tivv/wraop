/*
 *  Copyright 2014 Vitalii Tymchyshyn
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package im.tym.wraop;

import java.util.Collection;

/**
 * <p>This factory can produce proxies based on current settings. Settings include list of registered aspects and
 * list of interfaces resulting proxy should implement.</p>
 * <p>There are different methods to configure, including setters and builder-style.</p>
 * <p>Supported Aspect types:
 * <ul>
 *     <li>{@link org.aopalliance.aop.Advice}</li>
 *     <li>{@link org.springframework.aop.Advisor}</li>
 *     <li>{@link org.aspectj.lang.annotation.Aspect} - annotated class instances</li>
 * </ul></p>
 * <p>The factory can be used to wrap many objects one-by-one. It's thread safe (synchronized).</p>
 * @author Vitalii Tymchyshyn
 */

public interface WrapperFactory<I> {
    /**
     * Equal to {@link #withAllInterfacesOf(Object)}.{@link #wrap(Object)}. Sets list of interfaces to all
     * the interfaces of parameter and then wraps the parameter. Note that if you are going to wrap multiple
     * objects, it's faster to call {@link #withAllInterfacesOfClass(Class)} once and then use {@link #wrap(Object)}
     * @param object object to get interfaces from and then to wrap
     * @return wrapped object (may be object itself if there are no aspects registered)
     */
    I wrapAllInterfaces(I object);
    /**
     * Equal to {@link #withAllInterfacesOf(Object)}.{@link #wrap(Object,ClassLoader)}. Sets list of interfaces to all
     * the interfaces of parameter and then wraps the parameter. Note that if you are going to wrap multiple
     * objects, it's faster to call {@link #withAllInterfacesOfClass(Class)} once and then use {@link #wrap(Object, ClassLoader)}
     * @param object object to get interfaces from and then to wrap
     * @param classLoader class loader to use while wrapping (e.g. for proxy class creation)
     * @return wrapped object (may be object itself if there are no aspects registered)
     */
    I wrapAllInterfaces(I object, ClassLoader classLoader);

    /**
     * Wrap the object with the current settings. Uses {@code object.getClass().getClassLoader()} as class loader.
     * Note that you need at least to set interfaces or use
     * {@link #wrapAllInterfaces(Object)} to make it in single call.
     * @param object object to get interfaces from and then to wrap
     * @return wrapped object (may be object itself if there are no aspects registered)
     */
    I wrap(I object);
    /**
     * Wrap the object with the current settings with given class loader. Note that you need at least to set interfaces or use
     * {@link #wrapAllInterfaces(Object)} to make it in single call.
     * @param object object to get interfaces from and then to wrap
     * @param classLoader class loader to use while wrapping (e.g. for proxy class creation)
     * @return wrapped object (may be object itself if there are no aspects registered)
     */
    I wrap(I object, ClassLoader classLoader);

    /**
     * Adds given aspects, You can use {@link #withAspects(Object...)} for builder-style
     * @param aspects aspects to add
     * @return how many items were detected as aspects. All other will be skipped.
     */
    int addAspects(Object... aspects);
    /**
     * Adds given aspects, You can use {@link #withAspects(java.util.Collection)} for builder-style
     * @param aspects aspects to add
     * @return how many items were detected as aspects. All other will be skipped.
     */
    int addAspects(Collection<?> aspects);

    /**
     * Tries to add given aspect. You can use {@link #withAspect(Object)} for builder-style
     * @param aspect aspect to add
     * @return true if aspect was detected and added, false if it's not an aspect library knows.
     */
    boolean addAspect(Object aspect);

    /**
     * Sets interfaces resulting wrappers will implement. Fully replaces previous setting if any.
     * @param wrappedInterfaces interfaces resulting wrappers will implement.
     */
    void setInterfaces(Class... wrappedInterfaces);
    /**
     * Sets interfaces resulting wrappers will implement. Fully replaces previous setting if any.
     * @param wrappedInterfaces interfaces resulting wrappers will implement.
     */
    void setInterfaces(Collection<Class<?>> wrappedInterfaces);
    /**
     * Adds given aspects, You can use {@link #addAspects(Object...)} to get detection results. Non-aspects will be skipped.
     * @param aspects aspects to add
     * @return this factory, to chain calls
     */
    WrapperFactory<I> withAspects(Object... aspects);
    /**
     * Adds given aspects, You can use {@link #addAspects(java.util.Collection)} to get detection results. Non-aspects will be skipped.
     * @param aspects aspects to add
     * @return this factory, to chain calls
     */
    WrapperFactory<I> withAspects(Collection<?> aspects);
    /**
     * Tries to add given aspect. You can use {@link #addAspect(Object)} to get detection results. Non-aspects will be skipped
     * @param aspect aspect to add
     * @return this factory, to chain calls
     */
    WrapperFactory<I> withAspect(Object aspect);

    /**
     * Sets interfaces resulting wrappers will implement to all interfaces of given object class. Fully replaces previous setting if any.
     * You can use {@link #wrapAllInterfaces(Object)} to set interfaces and wrap in one step.
     * @param object object to get interfaces from
     * @return this factory, to chain calls
     */
    WrapperFactory<I> withAllInterfacesOf(Object object);
    /**
     * Sets interfaces resulting wrappers will implement to all interfaces of given class. Fully replaces previous setting if any.
     * You can use {@link #wrapAllInterfaces(Object)} to set interfaces and wrap in one step.
     * @param clazz class to get interfaces from
     * @return this factory, to chain calls
     */
    WrapperFactory<I> withAllInterfacesOfClass(Class clazz);

    /**
     * Adds an interface to the list resulting wrapper will implement.
     * @param wrappedInterface interface to add
     * @return this factory, to chain calls
     */
    WrapperFactory<I> withInterface(Class<?> wrappedInterface);
    /**
     * Adds interfaces to the list resulting wrapper will implement.
     * @param wrappedInterfaces interfaces to add
     * @return this factory, to chain calls
     */
    WrapperFactory<I> withInterfaces(Class... wrappedInterfaces);
    /**
     * Adds interfaces to the list resulting wrapper will implement.
     * @param wrappedInterfaces interfaces to add
     * @return this factory, to chain calls
     */
    WrapperFactory<I> withInterfaces(Collection<Class<?>> wrappedInterfaces);
}
