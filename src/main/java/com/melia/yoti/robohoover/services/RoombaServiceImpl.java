package com.melia.yoti.robohoover.services;

import com.melia.yoti.robohoover.Cleanable;
import com.melia.yoti.robohoover.Room;
import com.melia.yoti.robohoover.YotiRoboHoover;
import com.melia.yoti.robohoover.models.RoombaAudit;
import com.melia.yoti.robohoover.models.Square;
import com.melia.yoti.robohoover.models.YotiInput;
import com.melia.yoti.robohoover.models.YotiOutput;

import com.melia.yoti.robohoover.repos.RoombaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the Robot Hoover Cleaning Logic
 *
 * @Author Paul Melia
 *
 */
@Service
public class RoombaServiceImpl implements RoombaService {

    @Autowired
    RoombaRepo roombaRepo;

    /**
     * Returns the output of the cleaning algorithm and
     * saves both the input and output to an audit record in the database
     * @param yotiInput
     * @return a YotiOutput instance containing the cleaning result
     */
    public YotiOutput getOutput(YotiInput yotiInput) {
        YotiOutput yotiOutput = performClean(yotiInput);
        roombaRepo.save(new RoombaAudit(yotiInput, yotiOutput));
        return yotiOutput;
    }

    /**
     * Main algorithm method.
     * Takes a YotiInput object and reads its properties to set up both the room
     * and the robot hoover before beginning the cleaning
     * @param yotiInput
     * @return a new instance of the YotiOutput class with the result
     */
    private YotiOutput performClean(YotiInput yotiInput) {
        YotiRoboHoover roboHoover = new YotiRoboHoover(yotiInput.getCoords(), yotiInput.getInstructions(), yotiInput.getRoomSize());
        Cleanable yotiRoom = new Room(yotiInput.getRoomSize(), yotiInput.getPatches(),roboHoover);

        return new YotiOutput(roboHoover.getCoord(), yotiRoom.clean());
    }
}
