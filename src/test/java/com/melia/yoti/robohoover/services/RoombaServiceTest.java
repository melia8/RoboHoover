package com.melia.yoti.robohoover.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melia.yoti.robohoover.models.RoombaAudit;
import com.melia.yoti.robohoover.models.YotiInput;
import com.melia.yoti.robohoover.models.YotiOutput;
import com.melia.yoti.robohoover.repos.RoombaRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class RoombaServiceTest {

    @TestConfiguration
    static class config {

        @Bean
        RoombaService getRoombaService() {
            return new RoombaServiceImpl();
        }
    }

    @MockBean
    RoombaRepo roombaRepo;

    @Autowired
    RoombaService roombaService;

    private YotiInput strToYotiInput(String input) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(input, YotiInput.class);
    }

    private YotiOutput strToYotiOutput(String output) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(output, YotiOutput.class);
    }

    @Test
    public void sanityCheck() {
        assertThat(this.roombaService).isNotNull();
    }

    @Test
    public void input_with_0_instructions_and_0_patches_returns_same_robo_coord_and_0_patches() throws IOException {
        String input = "{  \"roomSize\" : [5, 5],  \"coords\" : [1, 2],  \"patches\" : [],  \"instructions\" : \"\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[1,2],\"patches\":0}"));
    }

    @Test
    public void robo_placed_on_patch_with_0_instructions_returns_same_coord_and_1_patch() throws IOException {
        String input = "{  \"roomSize\" : [5, 5],  \"coords\" : [1, 2],  \"patches\" : [[1, 2]],  \"instructions\" : \"\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[1,2],\"patches\":1}"));
    }

    @Test
    public void robo_can_move_north() throws IOException {
        String input = "{  \"roomSize\" : [5, 5],  \"coords\" : [1, 2],  \"patches\" : [[1, 2]],  \"instructions\" : \"N\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[1,3],\"patches\":1}"));
    }

    @Test
    public void robo_can_move_east() throws IOException {
        String input = "{  \"roomSize\" : [5, 5],  \"coords\" : [1, 2],  \"patches\" : [[1, 2]],  \"instructions\" : \"E\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[2,2],\"patches\":1}"));
    }

    @Test
    public void robo_can_move_south() throws IOException {
        String input = "{  \"roomSize\" : [5, 5],  \"coords\" : [1, 2],  \"patches\" : [[1, 2]],  \"instructions\" : \"S\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[1,1],\"patches\":1}"));
    }

    @Test
    public void robo_can_move_west() throws IOException {
        String input = "{  \"roomSize\" : [5, 5],  \"coords\" : [1, 2],  \"patches\" : [[1, 2]],  \"instructions\" : \"W\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[0,2],\"patches\":1}"));
    }

    @Test
    public void robo_driving_into_wall_has_no_effect_on_location() throws IOException {
        String input = "{  \"roomSize\" : [5, 5],  \"coords\" : [0, 4],  \"patches\" : [[1, 2]],  \"instructions\" : \"NNENWWWSSSSSSEEEEEESSNNNNNNN\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[4,4],\"patches\":0}"));
    }

    @Test
    public void robo_moving_onto_dirty_square_increments_clean_count() throws IOException {
        String input = "{  \"roomSize\" : [5, 5],  \"coords\" : [0, 4],  \"patches\" : [[1, 2], [2, 2], [3, 3]],  \"instructions\" : \"SSEENESW\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[2,2],\"patches\":3}"));
    }

    @Test
    public void robo_returning_to_cleaned_square_doesnt_increment_cleaned_count() throws IOException {
        String input = "{  \"roomSize\" : [5, 5],  \"coords\" : [0, 4],  \"patches\" : [[1, 2], [2, 2], [3, 3]],  \"instructions\" : \"SSEENESWNEWWS\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[1,2],\"patches\":3}"));
    }

    @Test
    public void can_perform_any_number_of_operations_in_room_of_one_square_without_movement() throws IOException {
        String input = "{  \"roomSize\" : [1, 1],  \"coords\" : [0, 0],  \"patches\" : [[0,0]],  \"instructions\" : \"SSEENESWNEWWS\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[0,0],\"patches\":1}"));
    }

    @Test
    public void can_clean_oblong_rooms() throws IOException {
        String input = "{  \"roomSize\" : [5, 1],  \"coords\" : [0, 0],  \"patches\" : [[2, 0], [4, 0]],  \"instructions\" : \"SSWWNWSENWEEEES\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[4,0],\"patches\":2}"));
    }

    @Test
    public void setting_duplicate_patches_makes_no_difference_to_count() throws IOException {
        String input = "{  \"roomSize\" : [5, 1],  \"coords\" : [0, 0],  \"patches\" : [ [2, 0], [4, 0], [4, 0], [2, 0] ],  \"instructions\" : \"SSWWNWSENWEEEES\"}";
        YotiOutput output = roombaService.getOutput(strToYotiInput(input));

        assertThat(output).isEqualTo(strToYotiOutput("{\"coords\":[4,0],\"patches\":2}"));
    }

    @Test
    public void audit_records_should_be_saved_by_the_repository() throws IOException {
        Mockito.reset(roombaRepo);

        String input1 = "{  \"roomSize\" : [5, 5],  \"coords\" : [1, 2],  \"patches\" : [],  \"instructions\" : \"\"}";
        roombaService.getOutput(strToYotiInput(input1));

        String input2 = "{  \"roomSize\" : [5, 5],  \"coords\" : [1, 2],  \"patches\" : [[1, 2]],  \"instructions\" : \"\"}";
        roombaService.getOutput(strToYotiInput(input2));

        String input3 = "{  \"roomSize\" : [5, 5],  \"coords\" : [1, 2],  \"patches\" : [[1, 2]],  \"instructions\" : \"N\"}";
        roombaService.getOutput(strToYotiInput(input3));

        Mockito.verify(roombaRepo, Mockito.times(3)).save(Mockito.any(RoombaAudit.class));
    }
}
