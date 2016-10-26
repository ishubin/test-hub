package net.mindengine.testhub.utils;

import java.util.concurrent.locks.ReentrantLock;


public class LockUtils {
    public interface Supplier<T> {
        T get() throws Exception;
    }

    public static <T> T withLock(ReentrantLock lock, Supplier<T> supplier) {
        lock.lock();
        try {
            return supplier.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
