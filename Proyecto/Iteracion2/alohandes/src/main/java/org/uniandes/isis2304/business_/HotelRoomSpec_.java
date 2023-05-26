package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

@Data("hotel_room_spec") public class HotelRoomSpec_ {
    @PK @FK(Operator_.class) String name;
    Integer roomNumber;
    String roomType;
    Boolean bathtub;
    Boolean jacuzzi;
    Boolean livingRoom;
}
