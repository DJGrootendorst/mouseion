package com.dirkjg.mouseion.integrationtests;

import com.dirkjg.mouseion.Dtos.PaintingInputDto;
import com.dirkjg.mouseion.models.Painting;
import com.dirkjg.mouseion.repositories.PaintingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class PaintingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaintingRepository paintingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Painting painting;

    @BeforeEach
    public void setUp() {
        paintingRepository.deleteAll();

        painting = new Painting();
        painting.setTitle("Starry Night");
        painting.setYear(1889);

        paintingRepository.save(painting);
    }

    // INTEGRATION-TEST 1: Get all paintings
    @Test
    public void shouldReturnAllPaintings() throws Exception {
        // arrange: puts the painting in the database
        paintingRepository.save(painting);

        // act: perform GET paintings
        mockMvc.perform(get("/paintings"))

                // assert: check the response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Starry Night"))
                .andExpect(jsonPath("$[0].year").value(1889));
    }

    // INTEGRATION-TEST 2: Create new painting
    @Test
    public void shouldCreateCorrectPainting() throws Exception {

        // arrange: make a new PaintingInputDto for a new painting
        PaintingInputDto inputDto = new PaintingInputDto();
        inputDto.setTitle("Mona Lisa");
        inputDto.setYear(1503);

        // act: perform POST to make the painting
        mockMvc.perform(post("/painting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))

                // assert: check if the response has the correct painting
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Mona Lisa"))
                .andExpect(jsonPath("$.year").value(1503));
    }

}
