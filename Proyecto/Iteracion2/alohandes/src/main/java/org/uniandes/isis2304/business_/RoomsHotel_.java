package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

@Data("rooms_hotel") public class RoomsHotel_ {
    @PK @FK(Hotel_.class) Integer nit;
    @PK @FK(HotelRoomSpec_.class) String name;
}
