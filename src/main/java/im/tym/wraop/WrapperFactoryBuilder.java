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

import im.tym.wraop.impl.*;

/**
 * This is helper class that detects which kind of aspects are available and creates correct implementation
 * of {@link WrapperFactory}. By default it will make you dummy wrapper factory if you've got no aspect
 * support in you classpath, but you can use {@link #setCanProduceNoopWrapper(boolean)} to produce an
 * error in this case
 * @author Vitalii Tymchyshyn
 */

public class WrapperFactoryBuilder {
    /**
     * If AspectJ is available
     */
    public static final boolean ASPECTJ_AVAILABLE = isAvailable("org.aspectj.lang.annotation.Aspect");
    /**
     * If Spring AOP is available
     */
    public static final boolean SPRING_AOP_AVAILABLE = isAvailable("org.springframework.aop.framework.ProxyFactory");
    private boolean canProduceNoopWrapper = true;

    /**
     *
     * @param <I> result class for your proxies
     * @return correct wrapper factory implementation to use
     * @throws IllegalStateException if no aspects are available and you've set {@link #setCanProduceNoopWrapper(boolean)}
     * to false
     */
    public <I> WrapperFactory<I> build() {
        WrapperFactorySpi<I> spi;
        if (ASPECTJ_AVAILABLE) {
            spi = new AspectJWrapperFactorySpi<I>();
        } else if (SPRING_AOP_AVAILABLE) {
            spi = new SpringAOPWrapperFactorySpi<I>();
        } else if (canProduceNoopWrapper) {
            spi = new NoopWrapperFactorySpi<I>();
        } else {
            throw new IllegalStateException("No AOP libraries are available");
        }
        return new SpiBasedWrapperFactory<I>(spi);
    }

    /**
     *
     * @return if to fail when there are no aspect libraries available. Default is not to fail, but to return dummy
     * implementation
     */
    public boolean isCanProduceNoopWrapper() {
        return canProduceNoopWrapper;
    }

    /**
     *
     * @param canProduceNoopWrapper if to fail when there are no aspect libraries available (false) or to return
     *                              dummy factory (true, default)
     */
    public void setCanProduceNoopWrapper(boolean canProduceNoopWrapper) {
        this.canProduceNoopWrapper = canProduceNoopWrapper;
    }

    private static boolean isAvailable(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
