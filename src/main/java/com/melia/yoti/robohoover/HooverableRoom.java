package com.melia.yoti.robohoover;

import com.melia.yoti.robohoover.models.Square;

public class HooverableRoom extends Room implements Cleanable {

    private RoboHoover roboHoover;
    private int numberCleaned;

    public HooverableRoom(int[] roomSize, int[][] patches, RoboHoover roboHoover){
        super(roomSize,patches);
        this.roboHoover = roboHoover;
    }

    public int getNumberCleaned() {
        return numberCleaned;
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

    @Override
    public int clean() {
        while (!roboHoover.isFinished()) {
            int[] roboCoord = roboHoover.getCoord();
            if (isCleaned(squares[roboCoord[1]][roboCoord[0]])) {
                ++numberCleaned;
            }
            roboHoover.move();
        }
        return numberCleaned;
    }
}
