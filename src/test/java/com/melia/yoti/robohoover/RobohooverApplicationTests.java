package com.melia.yoti.robohoover;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melia.yoti.robohoover.models.YotiInput;
import com.melia.yoti.robohoover.models.YotiOutput;
import com.melia.yoti.robohoover.repos.RoombaRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RobohooverApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    RoombaRepo roombaRepo;

    @Test
    public void input_json_posted_to_endpoint_returns_correct_output_json_and_writes_both_to_database_record() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        String inputJson = "{  \"roomSize\" : [5, 5],  \"coords\" : [0, 4],  \"patches\" : [[1, 2], [4,4]],  \"instructions\" : \"NNENWWWSSSSSSEEEEEESSNNNNNNN\"}";
        String outputJson = "{\"coords\":[4,4],\"patches\":1}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(inputJson, headers);

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/cleanRoom", request,
                String.class)).isEqualTo(outputJson);

        assertThat(roombaRepo.findAll().size()).isEqualTo(1);

        assertThat(roombaRepo.findAll().get(0).getOutput()).isEqualTo(objectMapper.readValue(outputJson, YotiOutput.class));
    }


}
