package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

@Data("client_preference") public class ClientPreference_ {
    @PK @FK(Client_.class) Long nuip;
    Boolean furnished;
    Boolean shared;
    Boolean wifi;
    Boolean kitchenette;
}
