package com.melia.yoti.robohoover.models;

public class Square {
    private boolean dirty = false;

    public Square() { }

    public boolean isDirty() {
        return this.dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
