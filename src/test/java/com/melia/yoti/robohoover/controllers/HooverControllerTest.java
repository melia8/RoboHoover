package com.melia.yoti.robohoover.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melia.yoti.robohoover.models.YotiInput;
import com.melia.yoti.robohoover.models.YotiOutput;
import com.melia.yoti.robohoover.repos.RoombaRepo;
import com.melia.yoti.robohoover.services.RoombaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HooverControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    RoombaRepo roombaRepo;

    @MockBean
    RoombaService roombaService;

    @Autowired
    HooverController hooverController;

    @Test
    public void sanityCheck(){
        assertThat(hooverController).isNotNull();
    }

    @Test
    public void posting_input_json_as_body_of_request_should_return_output() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String inputStr = "{  \"roomSize\" : [1, 1],  \"coords\" : [0, 0],  \"patches\" : [],  \"instructions\" : \"SSEENESWNEWWS\"}";
        YotiInput input = objectMapper.readValue(inputStr, YotiInput.class);

        String outputStr = "{\"coords\":[0,0],\"patches\":0}";
        YotiOutput output = objectMapper.readValue(outputStr, YotiOutput.class);

        given(roombaService.getOutput(input)).willReturn(output);

        mvc.perform(post("/cleanRoom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputStr))
                .andExpect(status().isOk())
                .andExpect(content().json(outputStr));
    }
}
