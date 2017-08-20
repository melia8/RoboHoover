package com.melia.yoti.robohoover.controllers;

import com.melia.yoti.robohoover.models.YotiInput;
import com.melia.yoti.robohoover.models.YotiOutput;
import com.melia.yoti.robohoover.services.RoombaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HooverController {

    @Autowired
    RoombaService roombaService;

    @PostMapping("/cleanRoom")
    public YotiOutput cleanRoom(@RequestBody YotiInput input){
       return roombaService.getOutput(input);
    }
}
