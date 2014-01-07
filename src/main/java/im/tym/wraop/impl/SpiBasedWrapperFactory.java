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
package im.tym.wraop.impl;

import im.tym.wraop.WrapperFactory;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Vitalii Tymchyshyn
 */

public class SpiBasedWrapperFactory<I> implements WrapperFactory<I> {
    private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
    private final WrapperFactorySpi<I> spi;

    public SpiBasedWrapperFactory(WrapperFactorySpi<I> spi) {
        this.spi = spi;
    }

    @Override
    public I wrap(I object, ClassLoader classLoader) {
        return spi.wrap(object, classLoader);
    }

    @Override
    public boolean addAspect(Object aspect) {
        return spi.addAspect(aspect);
    }

    @Override
    public void setInterfaces(Class... wrappedInterfaces) {
        spi.setInterfaces(wrappedInterfaces);
    }

    public void addInterface(Class wrappedInterface) {
        spi.addInterface(wrappedInterface);
    }

    @Override
    public I wrap(I object) {
        return wrap(object, object.getClass().getClassLoader());
    }

    @Override
    public int addAspects(Object... aspects) {
        return addAspects(Arrays.asList(aspects));
    }

    @Override
    public int addAspects(Collection<?> aspects) {
        int numAspects = 0;
        for (Object aspect: aspects) {
            if (addAspect(aspect)) {
                numAspects++;
            }
        }
        return numAspects;
    }

    @Override
    public WrapperFactory<I> withAspects(Object... aspects) {
        addAspects(aspects);
        return this;
    }

    @Override
    public WrapperFactory<I> withAspects(Collection<?> aspects) {
        addAspects(aspects);
        return this;
    }

    @Override
    public WrapperFactory<I> withAspect(Object aspect) {
        addAspect(aspect);
        return this;
    }

    @Override
    public I wrapAllInterfaces(I object) {
        return wrapAllInterfaces(object, object.getClass().getClassLoader());
    }

    @Override
    public I wrapAllInterfaces(I object, ClassLoader classLoader) {
        synchronized (spi) {
            return withAllInterfacesOf(object).wrap(object, classLoader);
        }
    }

    @Override
    public void setInterfaces(Collection<Class<?>> wrappedInterfaces) {
        setInterfaces(wrappedInterfaces.toArray(EMPTY_CLASS_ARRAY));
    }

    @Override
    public WrapperFactory<I> withAllInterfacesOf(Object object) {
        setInterfaces(ClassUtils.getAllInterfacesForClass(object.getClass()));
        return this;
    }

    @Override
    public WrapperFactory<I> withInterface(Class<?> wrappedInterface) {
        spi.addInterface(wrappedInterface);
        return this;
    }

    @Override
    public WrapperFactory<I> withInterfaces(Class... wrappedInterfaces) {
        for (Class<?> wrappedInterface: wrappedInterfaces) {
            spi.addInterface(wrappedInterface);
        }
        return this;
    }

    @Override
    public WrapperFactory<I> withInterfaces(Collection<Class<?>> wrappedInterfaces) {
        for (Class<?> wrappedInterface: wrappedInterfaces) {
            spi.addInterface(wrappedInterface);
        }
        return this;
    }

    public WrapperFactorySpi<I> getSpi() {
        return spi;
    }
}
