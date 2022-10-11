package com.stormeye.event.store.services;

/**
 * @author ian@meywood.com
 */
public interface StorageService<T,V> {

    V store(final T toStore);

}
