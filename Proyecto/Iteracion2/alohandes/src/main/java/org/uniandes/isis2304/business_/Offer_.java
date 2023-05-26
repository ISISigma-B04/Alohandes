package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

@Data("offer") public class Offer_ {
    @PK @FK(Booking_.class) Long id;
    Integer costByNight;
}
