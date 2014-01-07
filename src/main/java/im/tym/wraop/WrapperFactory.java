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
 * @author Vitalii Tymchyshyn
 */

public interface WrapperFactory<I> {
    I wrapAllInterfaces(I object);
    I wrapAllInterfaces(I object, ClassLoader classLoader);
    I wrap(I object);
    I wrap(I object, ClassLoader classLoader);
    int addAspects(Object... aspects);
    int addAspects(Collection<?> aspects);
    boolean addAspect(Object aspect);
    void setInterfaces(Class... wrappedInterfaces);
    void setInterfaces(Collection<Class<?>> wrappedInterfaces);
    WrapperFactory<I> withAspects(Object... aspects);
    WrapperFactory<I> withAspects(Collection<?> aspects);
    WrapperFactory<I> withAspect(Object aspect);
    WrapperFactory<I> withAllInterfacesOf(Object object);
    WrapperFactory<I> withInterface(Class<?> wrappedInterface);
    WrapperFactory<I> withInterfaces(Class... wrappedInterfaces);
    WrapperFactory<I> withInterfaces(Collection<Class<?>> wrappedInterfaces);
}
