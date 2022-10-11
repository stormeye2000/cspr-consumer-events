package com.stormeye.event.store.services;

import com.casper.sdk.model.event.blockadded.BlockAdded;
import com.stormeye.event.store.domain.Block;
import org.springframework.stereotype.Component;

/**
 * @author ian@meywood.com
 */
@Component
public class BlockAddedStorageService implements StorageService<BlockAdded, Block> {

    private final BlockRepository blockRepository;

    public BlockAddedStorageService(final BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Override
    public Block store(final BlockAdded toStore) {

        return this.blockRepository.save(
                new Block(null,
                        toStore.getBlockHash(),
                        toStore.getBlock().getHeader().getParentHash(),
                        toStore.getBlock().getHeader().getTimeStamp(),
                        toStore.getBlock().getHeader().getStateRootHash(),
                        toStore.getBlock().getBody().getDeployHashes().size(),
                        toStore.getBlock().getBody().getTransferHashes().size(),
                        toStore.getBlock().getHeader().getEraId(),
                        toStore.getBlock().getBody().getProposer()
                )
        );
    }
}
