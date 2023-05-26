package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.PK;

@Data("client") public class Client_ {
    @PK Long nuip;
    String name;
    String clientType;
}
