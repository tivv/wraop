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

import im.tym.wraop.impl.SpiBasedWrapperFactory;
import im.tym.wraop.impl.WrapperFactorySpi;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;

import java.awt.event.AWTEventListener;
import java.awt.event.AWTEventListenerProxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import static org.easymock.EasyMock.expect;
import static org.unitils.easymock.EasyMockUnitils.replay;

/**
 * @author Vitalii Tymchyshyn
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class SpiBasedWrapperFactoryTest {
    @Mock
    private WrapperFactorySpi<Object> factorySpi;

    private ClassLoader classLoader = new URLClassLoader(new URL[0]);

    private Object returned = new Object();
    private Object input1 = new Object();
    private Object input2 = new Object();

    private SpiBasedWrapperFactory<Object> factory;

    @Before
    public void makeFactory() {
        factory = new SpiBasedWrapperFactory<Object>(factorySpi);
    }

    @Test
    public void testWrap() throws Exception {
        expect(factorySpi.wrap(input1, input1.getClass().getClassLoader())).andReturn(returned);
        replay();
        Assert.assertSame(returned, factory.wrap(input1));
    }

    @Test
    public void testWrap_withClassLoader() throws Exception {
        expect(factorySpi.wrap(input1, classLoader)).andReturn(returned);
        replay();
        Assert.assertSame(returned, factory.wrap(input1, classLoader));

    }

    @Test
    public void testAddAspect() throws Exception {
        expect(factorySpi.addAspect(input1)).andReturn(true);
        replay();
        Assert.assertTrue(factory.addAspect(input1));

    }

    @Test
    public void testSetInterfaces() throws Exception {
        factorySpi.setInterfaces(Collection.class, Set.class);
        replay();
        factory.setInterfaces(Collection.class, Set.class);
    }

    @Test
    public void testAddInterface() throws Exception {
        factorySpi.addInterface(Set.class);
        replay();
        factory.addInterface(Set.class);
    }

    @Test
    public void testAddAspects() throws Exception {
        expect(factorySpi.addAspect(input1)).andReturn(true);
        expect(factorySpi.addAspect(input2)).andReturn(false);
        replay();
        Assert.assertEquals(1, factory.addAspects(input1, input2));
    }

    @Test
    public void testAddAspects_collection() throws Exception {
        expect(factorySpi.addAspect(input1)).andReturn(true);
        expect(factorySpi.addAspect(input2)).andReturn(false);
        replay();
        Assert.assertEquals(1, factory.addAspects(Arrays.asList(input1, input2)));
    }

    @Test
    public void testWithAspects() throws Exception {
        expect(factorySpi.addAspect(input1)).andReturn(true);
        expect(factorySpi.addAspect(input2)).andReturn(false);
        replay();
        Assert.assertSame(factory, factory.withAspects(input1, input2));
    }

    @Test
    public void testWithAspects_collection() throws Exception {
        expect(factorySpi.addAspect(input1)).andReturn(true);
        expect(factorySpi.addAspect(input2)).andReturn(false);
        replay();
        Assert.assertSame(factory, factory.withAspects(Arrays.asList(input1, input2)));
    }

    @Test
    public void testWithAspect() throws Exception {
        expect(factorySpi.addAspect(input1)).andReturn(true);
        replay();
        Assert.assertSame(factory, factory.withAspect(input1));
    }

    @Test
    public void testWrapAllInterfaces() throws Exception {
        Object input = new RandomAccess() {};
        factorySpi.setInterfaces(RandomAccess.class);
        expect(factorySpi.wrap(input, input.getClass().getClassLoader())).andReturn(returned);
        replay();
        Assert.assertSame(returned, factory.wrapAllInterfaces(input));
    }

    @Test
    public void testWrapAllInterfaces_classLoader() throws Exception {
        Object input = new RandomAccess() {};
        factorySpi.setInterfaces(RandomAccess.class);
        expect(factorySpi.wrap(input, classLoader)).andReturn(returned);
        replay();
        Assert.assertSame(returned, factory.wrapAllInterfaces(input, classLoader));
    }

    @Test
    public void testSetInterfaces_collection() throws Exception {
        factorySpi.setInterfaces(Collection.class, Set.class);
        replay();
        factory.setInterfaces(Arrays.<Class<?>>asList(Collection.class, Set.class));
    }

    @Test
    public void testWithAllInterfacesOf() throws Exception {
        Object input = new RandomAccess() {};
        factorySpi.setInterfaces(RandomAccess.class);
        replay();
        Assert.assertSame(factory, factory.withAllInterfacesOf(input));
    }

    @Test
    public void testWithAllInterfacesOfClass() throws Exception {
        factorySpi.setInterfaces(AWTEventListener.class, EventListener.class);
        replay();
        Assert.assertSame(factory, factory.withAllInterfacesOfClass(AWTEventListenerProxy.class));
    }

    @Test
    public void testWithInterface() throws Exception {
        factorySpi.addInterface(RandomAccess.class);
        replay();
        Assert.assertSame(factory, factory.withInterface(RandomAccess.class));
    }

    @Test
    public void testWithInterfaces() throws Exception {
        factorySpi.addInterface(RandomAccess.class);
        factorySpi.addInterface(Set.class);
        replay();
        Assert.assertSame(factory, factory.withInterfaces(RandomAccess.class, Set.class));
    }

    @Test
    public void testWithInterfaces_collection() throws Exception {
        factorySpi.addInterface(RandomAccess.class);
        factorySpi.addInterface(Set.class);
        replay();
        Assert.assertSame(factory, factory.withInterfaces(Arrays.asList(RandomAccess.class, Set.class)));
    }

    @Test
    public void testGetSpi() throws Exception {
        Assert.assertSame(factorySpi, factory.getSpi());
    }
}
