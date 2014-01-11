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
package im.tym.wraop.full;

import im.tym.wraop.data.ConstantReturningAspect;
import im.tym.wraop.data.ToStringTransformer;
import im.tym.wraop.data.Transformer;
import im.tym.wraop.impl.AspectJWrapperFactorySpi;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Vitalii Tymchyshyn
 */

public class AspectJWrapperFactorySpiTest {
    private AspectJWrapperFactorySpi<Transformer<Object, String>> factory;

    @Before
    public void createFactory() {
        factory = new AspectJWrapperFactorySpi<Transformer<Object, String>>();
    }

    @Test
    public void testAspectInstance() {
        Assert.assertTrue(factory.addAspect(new ConstantReturningAspect("constant")));
        Transformer<Object, String> transformer = factory.wrap(new ToStringTransformer(), this.getClass().getClassLoader());
        Assert.assertEquals("constant", transformer.transform(" test "));

    }
}
