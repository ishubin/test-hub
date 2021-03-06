/*******************************************************************************
 * Copyright 2016 Ivan Shubin https://github.com/ishubin/test-hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.mindengine.testhub.tests.components;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;

public class MockSessionBasedProxyServiceInvocationHandler implements InvocationHandler {
    private final String serviceName;

    MockSessionBasedProxyServiceInvocationHandler(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Optional<String> mockSessionId = CurrentMockSession.get();
        if (mockSessionId.isPresent()) {
            Object mock = MockRegistry.pickMock(mockSessionId.get(), serviceName);
            if (mock != null) {
                return method.invoke(mock, args);
            } else {
                throw new RuntimeException("Mock " + method.getName() + " in " + serviceName + " service is not defined for session " + mockSessionId.get());
            }
        } else {
            throw new RuntimeException("You haven't provided a mock session id");
        }
    }
}
