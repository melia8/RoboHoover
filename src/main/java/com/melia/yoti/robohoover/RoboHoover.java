package com.melia.yoti.robohoover;


public interface RoboHoover {

    void move();

    int[] getCoord();

    boolean isFinished();
}
