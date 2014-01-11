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
package im.tym.wraop.data;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Vitalii Tymchyshyn
 */

public class ConstantReturningAdvice implements MethodInterceptor {
    private final Object returnValue;

    public ConstantReturningAdvice(Object returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        invocation.proceed();
        return returnValue;
    }
}