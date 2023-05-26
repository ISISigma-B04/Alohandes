package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

@Data("house_room_spec") public class HouseRoomSpec_ {
    @PK @FK(Operator_.class) String name;
    Boolean food;
    Boolean privateBathroom;
}
