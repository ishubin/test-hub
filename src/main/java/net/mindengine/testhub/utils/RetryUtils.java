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
