package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

import java.sql.Timestamp;

@Data("hostel_spec") public class HostelSpec_ {
    @PK @FK(Operator_.class) String name;
    Long nit;
    Timestamp openingHours;
    Timestamp closingHours;
}
