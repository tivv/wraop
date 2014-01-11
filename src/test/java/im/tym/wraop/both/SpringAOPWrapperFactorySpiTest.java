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
package im.tym.wraop.both;

import im.tym.wraop.WrapperFactoryBuilder;
import im.tym.wraop.data.ConstantReturningAdvice;
import im.tym.wraop.data.ToStringTransformer;
import im.tym.wraop.data.Transformer;
import im.tym.wraop.data.TrimTransformerAdvice;
import im.tym.wraop.impl.AspectJWrapperFactorySpi;
import im.tym.wraop.impl.ProxyCreatorBasedWrapperFactorySpi;
import im.tym.wraop.impl.SpringAOPWrapperFactorySpi;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Tymchyshyn
 */
@RunWith(Parameterized.class)
public class SpringAOPWrapperFactorySpiTest {
    public SpringAOPWrapperFactorySpiTest(ProxyCreatorBasedWrapperFactorySpi<Transformer<Object, String>, ?> factory) {
        this.factory = factory;
        factory.setInterfaces(Transformer.class);
    }

    @Parameterized.Parameters
    public static List<Object[]> data() {
        List<Object[]> rc = new ArrayList<Object[]>();
        rc.add(new Object[]{new SpringAOPWrapperFactorySpi<Transformer<Object, String>>()});
        if (WrapperFactoryBuilder.ASPECTJ_AVAILABLE) {
            rc.add(new Object[]{new AspectJWrapperFactorySpi<Transformer<Object, String>>()});
        }
        return rc;
    }

    private final ProxyCreatorBasedWrapperFactorySpi<Transformer<Object, String>, ?> factory;

    @Before
    public void clearAdvisorChain() {
        while (factory.proxyCreator.getAdvisors().length > 0) {
            factory.proxyCreator.removeAdvisor(0);
        }
    }

    @Test
    public void testAddNonAspect() {
        Assert.assertFalse(factory.addAspect(this));
    }

    @Test
    public void testAdvice() {
        Assert.assertTrue(factory.addAspect(new TrimTransformerAdvice()));
        Transformer<Object, String> transformer = factory.wrap(new ToStringTransformer(), this.getClass().getClassLoader());
        Assert.assertEquals("test", transformer.transform(" test "));
    }

    @Test
    public void testReconfigure() {
        Assert.assertTrue(factory.addAspect(new TrimTransformerAdvice()));
        Transformer<Object, String> transformer = factory.wrap(new ToStringTransformer(), this.getClass().getClassLoader());
        Assert.assertEquals("test", transformer.transform(" test "));
        Assert.assertTrue(factory.addAspect(new ConstantReturningAdvice("constant")));
        Transformer<Object, String> transformer2 = factory.wrap(new ToStringTransformer(), this.getClass().getClassLoader());
        Assert.assertEquals("test", transformer.transform(" test "));
        Assert.assertEquals("constant", transformer2.transform(" test "));
    }

    @Test
    public void testAdvisor() {
        Assert.assertTrue(factory.addAspect(new RegexpMethodPointcutAdvisor(".*", new TrimTransformerAdvice())));
        Transformer<Object, String> transformer = factory.wrap(new ToStringTransformer(), this.getClass().getClassLoader());
        Assert.assertEquals("test", transformer.transform(" test "));
    }
}
