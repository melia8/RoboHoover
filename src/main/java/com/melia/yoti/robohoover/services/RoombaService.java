package com.melia.yoti.robohoover.services;

import com.melia.yoti.robohoover.models.YotiInput;
import com.melia.yoti.robohoover.models.YotiOutput;

public interface RoombaService {

    YotiOutput getOutput(YotiInput input);
}
