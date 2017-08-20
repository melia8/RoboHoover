package com.melia.yoti.robohoover.repos;

import com.melia.yoti.robohoover.models.RoombaAudit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoombaRepo extends MongoRepository<RoombaAudit, String> {
}
