package com.melia.yoti.robohoover.models;

import java.util.Arrays;

public class YotiOutput {
    int[] coords;
    int patches;

    public YotiOutput() { }

    public YotiOutput(int[] coords, int patches) {
        this.coords = coords;
        this.patches = patches;
    }

    public int[] getCoords() {
        return coords; }

    public int getPatches() {
        return patches; }

    @Override
    public String toString() {
        return "YotiOutput{" +
                "coords=" + Arrays.toString(coords) +
                ", patches=" + patches +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YotiOutput that = (YotiOutput) o;

        if (patches != that.patches) return false;
        return Arrays.equals(coords, that.coords);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(coords);
        result = 31 * result + patches;
        return result;
    }
}

