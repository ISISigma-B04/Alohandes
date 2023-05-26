package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

@Data("residence_spec") public class ResidenceSpec_ {
    @PK @FK(Operator_.class) String name;
    Integer bedroomNum;
    Integer administrationFee;
    String insuranceDesc;
}
