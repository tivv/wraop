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

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * @author Vitalii Tymchyshyn
 */

public class AspectJWrapperFactorySpi<I> extends ProxyCreatorBasedWrapperFactorySpi<I, AspectJProxyFactory> {
    public AspectJWrapperFactorySpi() {
        super(new AspectJProxyFactory());
    }

    @Override
    protected I getProxy(ClassLoader classLoader) {
        return proxyCreator.getProxy(classLoader);
    }

    @Override
    public synchronized boolean addAspect(Object aspect) {
        proxyCreator.addAspect(aspect);
        return true;
    }
}
