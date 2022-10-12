package com.stormeye.event.store.services.storage.block.resource.resource;

import com.stormeye.event.store.services.storage.block.domain.Block;
import com.stormeye.event.store.services.storage.block.repository.BlockRepository;
import com.stormeye.event.store.services.storage.common.PageResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Blocks REST API
 *
 * @author ian@meywood.com
 */
@RestController
@OpenAPIDefinition(
        info = @Info(
                title = "Casper Block REST API",
                description = "The Blocks REST API",
                contact = @Contact(
                        name = "Stormeye2000",
                        url = "https://github.com/stormeye2000/cspr-producer-audit"
                )
        )
)
public class BlockResource {

    private final BlockRepository blockRepository;

    @Autowired
    public BlockResource(final BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    /**
     * Obtains a page of blocks
     *
     * @param page the page number
     * @param size the size of the request page
     * @return a page of blocks as JSON
     */
    @GetMapping(value = "/blocks", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponse<Block>> getBlocks(@RequestParam(value = "page", defaultValue = "1", required = false) final int page,
                                                  @RequestParam(value = "size", defaultValue = "10", required = false) final int size,
                                                  @RequestParam(value = "order_by", defaultValue = "timestamp", required = false) final String orderBy,
                                                  @RequestParam(value = "order_direction", defaultValue = "DESC", required = false) final Sort.Direction orderDirection) {


        var request = PageRequest.of(page - 1, size, getSort(orderBy, orderDirection));
        return ResponseEntity.ok(new PageResponse<>(blockRepository.findAll(request)));
    }

    @NotNull
    private static Sort getSort(String orderBy, Sort.Direction orderDirection) {
        if ("timestamp".equals(orderBy)) {
            return Sort.by(orderDirection, orderBy);
        } else return Sort.by(
                new Sort.Order(orderDirection, orderBy),
                new Sort.Order(Sort.Direction.ASC, "timestamp")
        );
    }

}
