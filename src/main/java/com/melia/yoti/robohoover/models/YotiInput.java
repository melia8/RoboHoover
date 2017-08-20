package com.melia.yoti.robohoover.models;

import java.util.Arrays;

public class YotiInput {
    int[] roomSize;
    int[] coords;
    int[][] patches;
    String instructions;

    public YotiInput() {
    }

    public YotiInput(int[] roomSize, int[] coords, int[][] patches, String instructions) {
        this.roomSize = roomSize;
        this.coords = coords;
        this.patches = patches;
        this.instructions = instructions;
    }

    public int[] getRoomSize() {
        return roomSize;
    }

    public int[] getCoords() {
        return coords;
    }

    public int[][] getPatches() {
        return patches;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        return "YotiInput{" +
                "roomSize=" + Arrays.toString(roomSize) +
                ", coords=" + Arrays.toString(coords) +
                ", patches=" + Arrays.toString(patches) +
                ", instructions='" + instructions + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YotiInput yotiInput = (YotiInput) o;

        if (!Arrays.equals(roomSize, yotiInput.roomSize)) return false;
        if (!Arrays.equals(coords, yotiInput.coords)) return false;
        if (!Arrays.deepEquals(patches, yotiInput.patches)) return false;
        return instructions != null ? instructions.equals(yotiInput.instructions) : yotiInput.instructions == null;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(roomSize);
        result = 31 * result + Arrays.hashCode(coords);
        result = 31 * result + Arrays.deepHashCode(patches);
        result = 31 * result + (instructions != null ? instructions.hashCode() : 0);
        return result;
    }
}
