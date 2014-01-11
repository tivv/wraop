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

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.ProxyCreatorSupport;
import org.springframework.aop.target.SingletonTargetSource;

import java.util.Arrays;

/**
 * @author Vitalii Tymchyshyn
 */

public abstract class ProxyCreatorBasedWrapperFactorySpi<I, PC extends ProxyCreatorSupport> implements WrapperFactorySpi<I> {
    public final PC proxyCreator;

    public ProxyCreatorBasedWrapperFactorySpi(PC proxyCreator) {
        this.proxyCreator = proxyCreator;
    }

    @Override
    public synchronized I wrap(final I object, ClassLoader classLoader) {
        AdvisedSupport support = getAdvisedSupportFor(object);
        return (I) proxyCreator.getAopProxyFactory().createAopProxy(support).getProxy(classLoader);
    }

    protected AdvisedSupport getAdvisedSupportFor(final I object) {
        return new AdvisedSupport() {
                {
                    copyConfigurationFrom(proxyCreator, new SingletonTargetSource(object), Arrays.asList(proxyCreator.getAdvisors()));
                }
            };
    }

    @Override
    public void setInterfaces(Class... wrappedInterfaces) {
        proxyCreator.setInterfaces(wrappedInterfaces);
    }

    @Override
    public void addInterface(Class wrappedInterface) {
        proxyCreator.addInterface(wrappedInterface);
    }

    @Override
    public boolean addAspect(Object aspect) {
        if (aspect instanceof Advice) {
            proxyCreator.addAdvice((Advice) aspect);
            return true;
        } else if (aspect instanceof Advisor) {
            proxyCreator.addAdvisor((Advisor) aspect);
            return true;
        } else {
            return false;
        }

    }
}
