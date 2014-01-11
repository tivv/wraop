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

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.PerClauseKind;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJProxyUtils;
import org.springframework.aop.aspectj.annotation.*;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.OrderComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Note: Some code was taken from {@link AspectJProxyFactory}.
 * @author Vitalii Tymchyshyn
 */

public class AspectJWrapperFactorySpi<I> extends ProxyCreatorBasedWrapperFactorySpi<I, AspectJProxyFactory> {
    private final AspectJAdvisorFactory aspectFactory = new ReflectiveAspectJAdvisorFactory();

    private final List<Advisor> aspectJAdvisors = new ArrayList<Advisor>();

    public AspectJWrapperFactorySpi() {
        super(new AspectJProxyFactory());
    }

    @Override
    public synchronized boolean addAspect(Object aspect) {
        if (super.addAspect(aspect)) {
            return true;
        }
        if (!aspect.getClass().isAnnotationPresent(Aspect.class)) {
            return false;
        }
        Class aspectClass = aspect.getClass();
        String aspectName = aspectClass.getName();
        AspectMetadata am = createAspectMetadata(aspectClass, aspectName);
        if (am == null) {
            return false;
        }
        if (am.getAjType().getPerClause().getKind() != PerClauseKind.SINGLETON) {
            throw new IllegalArgumentException(
                    "Aspect class [" + aspectClass.getName() + "] does not define a singleton aspect");
        }
        return addAdvisorsFromAspectInstanceFactory(
                new SingletonMetadataAwareAspectInstanceFactory(aspect, aspectName));
    }

    /**
     * Create an {@link AspectMetadata} instance for the supplied aspect type.
     */
    private AspectMetadata createAspectMetadata(Class aspectClass, String aspectName) {
        AspectMetadata am = new AspectMetadata(aspectClass, aspectName);
        if (!am.getAjType().isAspect()) {
            return null;
        }
        return am;
    }

    private boolean addAdvisorsFromAspectInstanceFactory(MetadataAwareAspectInstanceFactory instanceFactory) {
        boolean hadAspects = aspectJAdvisors.addAll(this.aspectFactory.getAdvisors(instanceFactory));
        if (hadAspects) {
            AspectJProxyUtils.makeAdvisorChainAspectJCapableIfNecessary(aspectJAdvisors);
            OrderComparator.sort(aspectJAdvisors);
        }
        return hadAspects;
    }

    @Override
    protected AdvisedSupport getAdvisedSupportFor(I object) {
        AdvisedSupport support = super.getAdvisedSupportFor(object);
        if (!aspectJAdvisors.isEmpty()) {
            support.addAdvisors(AopUtils.findAdvisorsThatCanApply(aspectJAdvisors, object.getClass()));
        }
        return support;
    }
}
