package com.stormeye.event.store.services;

import com.casper.sdk.model.event.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ian@meywood.com
 */
@Service
public class StorageFactory {

    @SuppressWarnings("rawtypes")
    private final Map<DataType, StorageService> storageServiceMap = new HashMap<>();

    @Autowired
    public StorageFactory(final BlockAddedStorageService blockAddedStorageService) {
        storageServiceMap.put(DataType.BLOCK_ADDED, blockAddedStorageService);
    }


    public <T,V> StorageService<T,V> getStorageService(final DataType dataType) {
        //noinspection unchecked
        return storageServiceMap.get(dataType);
    }


}
