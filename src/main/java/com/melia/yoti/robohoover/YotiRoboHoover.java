package com.melia.yoti.robohoover;

public class YotiRoboHoover implements RoboHoover {
    private int[] initialCoord;
    private int[] coord;
    private int[] roomDims;
    private String instructions;
    private char[] instructionCharArray;
    private boolean finished;
    int instructionPointer = 0;

    public YotiRoboHoover(int[] initialCoord, String instructions, int[] roomDims) {
        this.initialCoord = initialCoord;
        this.coord = initialCoord;
        this.instructions = instructions;
        this.instructionCharArray = instructions.toCharArray();
        this.roomDims = roomDims;
    }

    private void moveNorth() {
        int yVal = coord[1];
        if (yVal + 1 < roomDims[1]) {
            coord[1] = yVal + 1;
        }

    }

    private void moveSouth() {
        int yVal = coord[1];
        if (yVal - 1 > -1) {
            coord[1] = yVal - 1;
        }

    }

    private void moveEast() {
        int xVal = coord[0];
        if (xVal + 1 < roomDims[0]) {
            coord[0] = xVal + 1;
        }

    }

    private void moveWest() {
        int xVal = coord[0];
        if (xVal - 1 > -1) {
            coord[0] = xVal - 1;
        }

    }

    public void move() {
        switch(getNextInstruction()) {
            case 'E':
                moveEast();
                break;
            case 'N':
                moveNorth();
                break;
            case 'S':
                moveSouth();
                break;
            case 'W':
                moveWest();
        }

    }

    public boolean isFinished() {
        return finished;
    }

    public int[] getCoord() {
        return coord;
    }

    public void setCoord(int[] coord) {
        this.coord = coord;
    }

    private char getNextInstruction() {
        if (instructionPointer >= instructionCharArray.length) {
            finished = true;
            return ' ';
        } else {
            return instructionCharArray[instructionPointer++];
        }
    }
}

