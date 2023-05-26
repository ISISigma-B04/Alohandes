package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

@Data("apartment_spec") public class ApartmentSpec_ {
    @PK @FK(Operator_.class) String name;
    Boolean includedServices;
    Boolean includedTV;
    Integer administrationFee;
}
