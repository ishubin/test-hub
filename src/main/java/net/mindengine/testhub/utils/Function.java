package net.mindengine.testhub.utils;

public interface Function<T1, T> {
    T apply(T1 t) throws Exception;
}