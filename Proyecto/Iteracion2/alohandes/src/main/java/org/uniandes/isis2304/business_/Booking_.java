package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

import java.sql.Date;

@Data("booking") public class Booking_ {
    @PK Long id;
    @FK(Client_.class) Long nuip;
    @FK(Operator_.class) String name;
    Long cost;
    Date start;
    Date end;
}
