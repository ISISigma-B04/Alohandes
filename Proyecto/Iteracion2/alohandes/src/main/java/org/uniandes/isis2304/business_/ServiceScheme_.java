package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

@Data("service_scheme") public class ServiceScheme_ {
    @PK @FK(HouseRoomSpec_.class) String name;
    String serviceName;
    Integer serviceCost;
}
