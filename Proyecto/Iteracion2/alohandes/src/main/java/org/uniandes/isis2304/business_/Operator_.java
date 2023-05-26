package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.PK;

@Data("operator") public class Operator_ {
    Long nuip;
    @PK String name;
    String type;
}
