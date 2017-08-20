package com.melia.yoti.robohoover.models;

import org.springframework.data.annotation.Id;

public class RoombaAudit {
    @Id
    private String _id;
    private YotiInput input;
    private YotiOutput output;

    public RoombaAudit(YotiInput input, YotiOutput output) {
        this.input = input;
        this.output = output;
    }

    public String get_id() {
        return _id;
    }

    public YotiInput getInput() {
        return input;
    }

    public YotiOutput getOutput() {
        return output;
    }

    public void setInput(YotiInput input) {
        this.input = input;
    }

    public void setOutput(YotiOutput output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "RoombaAudit{" +
                "_id='" + _id + '\'' +
                ", input=" + input +
                ", output=" + output +
                '}';
    }
}