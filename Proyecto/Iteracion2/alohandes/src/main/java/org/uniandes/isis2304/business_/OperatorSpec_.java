package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

@Data("operator_spec") public class OperatorSpec_ {
    @PK @FK(Operator_.class) String name;
    Integer capacity;
    Integer size;
    String location;
    Boolean shared;
}
