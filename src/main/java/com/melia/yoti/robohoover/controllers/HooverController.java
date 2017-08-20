package com.melia.yoti.robohoover.controllers;

import com.melia.yoti.robohoover.models.HooverError;
import com.melia.yoti.robohoover.models.YotiInput;
import com.melia.yoti.robohoover.models.YotiOutput;
import com.melia.yoti.robohoover.services.RoombaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class HooverController {

    private static final Logger logger = LoggerFactory.getLogger(HooverController.class);

    @Autowired
    RoombaService roombaService;

    @PostMapping("/cleanRoom")
    public YotiOutput cleanRoom(@RequestBody YotiInput input) {
        return roombaService.getOutput(input);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public HooverError handleInternalError(Exception ex) {

        ex.printStackTrace();
        logger.info("  *** Returned Sanitised Response For Internal Error --> " + ex.getClass().getName() + " ***");

        return new HooverError("There was an internal error. Please check your input to the API");
    }
}
