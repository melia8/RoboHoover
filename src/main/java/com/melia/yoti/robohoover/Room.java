package com.melia.yoti.robohoover;

import com.melia.yoti.robohoover.models.Square;

public class Room {

    int[][] patches;
    int xDim;
    int yDim;
    Square[][] squares;

    public Room(int[] roomSize, int[][] patches){
       this.xDim =  roomSize[0];
       this.yDim = roomSize[1];
       this.squares =  new Square[yDim][xDim];
       this.patches = patches;
       initialiseRoom();
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

}
