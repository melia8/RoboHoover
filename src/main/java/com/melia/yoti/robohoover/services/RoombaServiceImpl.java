package com.melia.yoti.robohoover.services;

import com.melia.yoti.robohoover.YotiRoboHoover;
import com.melia.yoti.robohoover.models.RoombaAudit;
import com.melia.yoti.robohoover.models.Square;
import com.melia.yoti.robohoover.models.YotiInput;
import com.melia.yoti.robohoover.models.YotiOutput;

import com.melia.yoti.robohoover.repos.RoombaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoombaServiceImpl implements RoombaService {

    @Autowired
    RoombaRepo roombaRepo;

    public YotiOutput getOutput(YotiInput yotiInput) {
        YotiOutput yotiOutput = performClean(yotiInput);
        roombaRepo.save(new RoombaAudit(yotiInput,yotiOutput));
        return yotiOutput;
    }

    private void initialiseRoom(Square[][] room, int xRoomDim, int yRoomDim) {
        for(int j = 0; j < yRoomDim; ++j) {
            for(int i = 0; i < xRoomDim; ++i) {
                room[j][i] = new Square();
            }
        }

    }

    private void addPatches(Square[][] room, int[][] patches) {
        int[][] patchArray = patches;

        for(int p = 0; p < patches.length; ++p) {
            int[] patch = patchArray[p];
            room[patch[1]][patch[0]].setDirty(true);
        }

    }

    private boolean isCleaned(Square square) {
        if (square.isDirty()) {
            square.setDirty(false);
            return true;
        } else {
            return false;
        }
    }

    private YotiOutput performClean(YotiInput yotiInput) {
        int xRoomDim = yotiInput.getRoomSize()[0];
        int yRoomDim = yotiInput.getRoomSize()[1];
        int numberCleaned = 0;

        Square[][] room = new Square[yRoomDim][xRoomDim];
        initialiseRoom(room, xRoomDim, yRoomDim);
        addPatches(room, yotiInput.getPatches());

        YotiRoboHoover roboHoover = new YotiRoboHoover(yotiInput.getCoords(), yotiInput.getInstructions(), yotiInput.getRoomSize());

        while(!roboHoover.isFinished()){
            int[] roboCoord = roboHoover.getCoord();
            if (isCleaned(room[roboCoord[1]][roboCoord[0]])) {
                ++numberCleaned;
            }
            roboHoover.move();
        }

        return new YotiOutput(roboHoover.getCoord(), numberCleaned);
    }
}
