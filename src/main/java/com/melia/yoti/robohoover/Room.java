package com.melia.yoti.robohoover;

import com.melia.yoti.robohoover.models.Square;

public class Room implements Cleanable{

    int[][] patches;
    int xDim;
    int yDim;
    Square[][] squares;
    int numberCleaned = 0;

    RoboHoover roboHoover;

    public Room(int[] roomSize, int[][] patches, RoboHoover roboHoover){
       this.xDim =  roomSize[0];
       this.yDim = roomSize[1];
       this.squares =  new Square[yDim][xDim];
       this.patches = patches;
       initialiseRoom();
       this.roboHoover = roboHoover;
    }

    public int getNumberCleaned() {
        return numberCleaned;
    }

    /**
     * Initialise the multi-dim array the represents the room with
     * instances of the Square class for each available co-ordinate
     */
    private void initialiseRoom() {
        for (int j = 0; j < yDim; ++j) {
            for (int i = 0; i < xDim; ++i) {
                squares[j][i] = new Square();
            }
        }
        addPatches();

    }

    /**
     * Use the patches array passed in via the input to set the
     * dirty property of the corresponding Square instances in
     * the room array
     */
    private void addPatches() {
        int[][] patchArray = patches;

        for (int p = 0; p < patches.length; ++p) {
            int[] patch = patchArray[p];
            squares[patch[1]][patch[0]].setDirty(true);
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
