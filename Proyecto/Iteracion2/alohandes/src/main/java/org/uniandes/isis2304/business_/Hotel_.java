package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.PK;

@Data("hotel") public class Hotel_ {
    @PK Integer nit;
    Boolean restaurant;
    Boolean pool;
    Boolean parkingLot;
    Boolean wifi;
    Boolean cableTV;
}
