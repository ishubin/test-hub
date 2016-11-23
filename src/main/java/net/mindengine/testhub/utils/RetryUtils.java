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
package net.mindengine.testhub.utils;

public class RetryUtils {
    public static <T> T withRetry(Supplier<T> supplier) {
        return withRetry(3, supplier);
    }

    public static <T> T withRetry(int times, Supplier<T> supplier) {
        if (times < 1) {
            throw new IllegalArgumentException("times should be higher than 0");
        }

        Exception lastException = null;
        int timer = times;
        while (timer-- > 0) {
            try {
                return supplier.get();
            } catch (Exception e) {
                lastException = e;
            }
        }
        throw new RuntimeException("Timed out after rertying " + times + " times", lastException);
    }
}
