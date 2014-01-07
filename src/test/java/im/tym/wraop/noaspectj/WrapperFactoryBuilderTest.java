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
package im.tym.wraop.noaspectj;

import im.tym.wraop.WrapperFactoryBuilder;
import im.tym.wraop.impl.SpiBasedWrapperFactory;
import im.tym.wraop.impl.SpringAOPWrapperFactorySpi;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * @author Vitalii Tymchyshyn
 */

public class WrapperFactoryBuilderTest {
    @Test
    public void testBuild() {
        SpiBasedWrapperFactory<Object> factory = (SpiBasedWrapperFactory<Object>) new WrapperFactoryBuilder().build();
        Assert.isInstanceOf(SpringAOPWrapperFactorySpi.class, factory.getSpi());
    }

    @Test
    public void testDetection() {
        Assert.isTrue(!WrapperFactoryBuilder.ASPECTJ_AVAILABLE);
        Assert.isTrue(WrapperFactoryBuilder.SPRING_AOP_AVAILABLE);
    }
}
