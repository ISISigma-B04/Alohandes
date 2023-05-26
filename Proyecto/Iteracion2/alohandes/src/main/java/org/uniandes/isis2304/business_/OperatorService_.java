package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

@Data("operator_service") public class OperatorService_ {
    @PK @FK(Operator_.class) String name;
    Boolean furnished;
    Boolean wifi;
    Boolean kitchenette;
}
