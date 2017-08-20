package com.melia.yoti.robohoover.services;

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
     * Initialise the multi-dim array the represents the room with
     * instances of the Square class for each available co-ordinate
     * @param room
     * @param xRoomDim
     * @param yRoomDim
     */
    private void initialiseRoom(Square[][] room, int xRoomDim, int yRoomDim) {
        for (int j = 0; j < yRoomDim; ++j) {
            for (int i = 0; i < xRoomDim; ++i) {
                room[j][i] = new Square();
            }
        }

    }

    /**
     * Use the patches array passed in via the input to set the
     * dirty property of the corresponding Square instances in
     * the room array
     * @param room
     * @param patches
     */
    private void addPatches(Square[][] room, int[][] patches) {
        int[][] patchArray = patches;

        for (int p = 0; p < patches.length; ++p) {
            int[] patch = patchArray[p];
            room[patch[1]][patch[0]].setDirty(true);
        }

    }

    /**
     * Set the dirty state of the Square instance
     * As the robot cleaner passes over
     * @param square
     * @return true if square was cleaned.
     */
    private boolean isCleaned(Square square) {
        if (square.isDirty()) {
            square.setDirty(false);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Main algorithm method.
     * Takes a YotiInput object and reads its properties to set up both the room
     * and the robot hoover before beginning the cleaning
     * @param yotiInput
     * @return a new instance of the YotiOutput class with the result
     */
    private YotiOutput performClean(YotiInput yotiInput) {
        int xRoomDim = yotiInput.getRoomSize()[0];
        int yRoomDim = yotiInput.getRoomSize()[1];
        int numberCleaned = 0;

        Square[][] room = new Square[yRoomDim][xRoomDim];
        initialiseRoom(room, xRoomDim, yRoomDim);
        addPatches(room, yotiInput.getPatches());

        YotiRoboHoover roboHoover = new YotiRoboHoover(yotiInput.getCoords(), yotiInput.getInstructions(), yotiInput.getRoomSize());

        while (!roboHoover.isFinished()) {
            int[] roboCoord = roboHoover.getCoord();
            if (isCleaned(room[roboCoord[1]][roboCoord[0]])) {
                ++numberCleaned;
            }
            roboHoover.move();
        }

        return new YotiOutput(roboHoover.getCoord(), numberCleaned);
    }
}
