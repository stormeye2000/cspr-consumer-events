package com.stormeye.event.store.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stormeye.event.store.services.storage.block.domain.Block;
import com.stormeye.event.store.services.storage.block.repository.BlockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author ian@meywood.com
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class BlockResourceTest {

    private static final String BLOCKS_JSON = "/com/stormeye/event/store/services/storage/block/blocks.json";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BlockRepository blockRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws IOException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        //  this.mockMvc.getDispatcherServlet().setThrowExceptionIfNoHandlerFound(true);

        blockRepository.deleteAll();

        createTestData();
    }

    @Test
    void configuration() {
        assertThat(context, is(notNullValue()));
        assertThat(mockMvc, is(notNullValue()));
    }


    @Test
    void getBlocks() throws Exception {

        mockMvc.perform(get("/events/blocks")
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(5)))
                .andExpect(jsonPath("$[0].blockHash", is("844bfa519039cbcaed0949dfb80b7ba244d38813e89950832b0c2a2a3063de19")))
                .andExpect(jsonPath("$[0].parentHash", is("8d0c807b66c73746a457782ea56c127c6820e69bd9802ee3c53200199d2aa3cb")))
                .andExpect(jsonPath("$[0].eraId", is(6704)))
                .andExpect(jsonPath("$[0].proposer", is("014b466f5c6c87bb1d2566d166120e320a724231374cd0775e0e347afed70a4745")))
                .andExpect(jsonPath("$[0].deployCount", is(0)))
                .andExpect(jsonPath("$[0].transferCount", is(0)))
                .andExpect(jsonPath("$[0].blockHeight", is(1175543)))

                .andExpect(jsonPath("$[4].blockHash", is("3f9a2258144a85a4dfa2817cbe2a2c75f8775a939f416a5f96be96a41f0c94bf")));
    }

    private void createTestData() throws IOException {


        var in = BlockResourceTest.class.getResourceAsStream(BLOCKS_JSON);
        var blocks = new ObjectMapper().readValue(in, new TypeReference<List<Block>>() {
        });

        assertThat(blocks, is(notNullValue()));
        assertThat(blocks, hasSize(30));

        blockRepository.saveAll(blocks);

        assertThat(blockRepository.count(), is(30L));
    }
}