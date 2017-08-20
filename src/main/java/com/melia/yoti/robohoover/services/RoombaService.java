package com.melia.yoti.robohoover.services;

import com.melia.yoti.robohoover.models.YotiInput;
import com.melia.yoti.robohoover.models.YotiOutput;
import com.melia.yoti.robohoover.repos.RoombaRepo;

public interface RoombaService {
    YotiOutput getOutput(YotiInput input);
}
