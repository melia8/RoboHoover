package com.melia.yoti.robohoover.repos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melia.yoti.robohoover.models.RoombaAudit;
import com.melia.yoti.robohoover.models.YotiInput;
import com.melia.yoti.robohoover.models.YotiOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RoombaRepoTest {

    @Autowired
    RoombaRepo roombaRepo;

    @Before
    public void clear_audit_records_before_each_test(){
        roombaRepo.deleteAll();
    }

    @Test
    public void sanityCheck() {
        assertThat(roombaRepo).isNotNull();
    }

    @Test
    public void should_write_and_retrieve_audit_record() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        YotiInput yotiInput = objectMapper.readValue("{  \"roomSize\" : [5, 5],  \"coords\" : [1, 2],  \"patches\" : [ [1, 0], [2, 2], [2, 3] ],  \"instructions\" : \"NNESEESWNWW\"}", YotiInput.class);
        YotiOutput yotiOutput = objectMapper.readValue("{  \"coords\" : [1, 3],  \"patches\" : 1}", YotiOutput.class);
        RoombaAudit roombaAudit = roombaRepo.save(new RoombaAudit(yotiInput, yotiOutput));

        RoombaAudit roombaAuditFoundInDb = roombaRepo.findOne(roombaAudit.get_id());

        assertThat(objectMapper.writeValueAsString(roombaAudit)).isEqualTo(objectMapper.writeValueAsString(roombaAuditFoundInDb));
    }

    @Test
    public void should_retrieve_list_of_audit_records() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        YotiInput yotiInput1 = objectMapper.readValue("{  \"roomSize\" : [5, 5],  \"coords\" : [1, 2],  \"patches\" : [ [1, 0], [2, 2], [2, 3] ],  \"instructions\" : \"NNESEESWNWW\"}", YotiInput.class);
        YotiOutput yotiOutput1 = objectMapper.readValue("{  \"coords\" : [1, 3],  \"patches\" : 1}", YotiOutput.class);
        RoombaAudit roombaAudit1 = roombaRepo.save(new RoombaAudit(yotiInput1, yotiOutput1));

        YotiInput yotiInput2 = objectMapper.readValue("{  \"roomSize\" : [5, 5],  \"coords\" : [4, 2],  \"patches\" : [ [1, 0], [2, 2], [2, 3], [3, 3], [4, 4] ],  \"instructions\" : \"NNNEEEWWW\"}", YotiInput.class);
        YotiOutput yotiOutput2 = objectMapper.readValue("{  \"coords\" : [3, 3],  \"patches\" : 4}", YotiOutput.class);
        RoombaAudit roombaAudit2 = roombaRepo.save(new RoombaAudit(yotiInput2, yotiOutput2));

        List<String> jsonResultList = new ArrayList();

        for (RoombaAudit roombaAudit : roombaRepo.findAll()){
            jsonResultList.add(objectMapper.writeValueAsString(roombaAudit));
        }

        assertThat(jsonResultList).containsExactlyInAnyOrder(objectMapper.writeValueAsString(roombaAudit1), objectMapper.writeValueAsString(roombaAudit2));
    }
}
