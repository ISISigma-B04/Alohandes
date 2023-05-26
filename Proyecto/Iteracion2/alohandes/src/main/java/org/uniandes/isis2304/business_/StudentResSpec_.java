package org.uniandes.isis2304.business_;

import org.uniandes.isis2304.core.Data;
import org.uniandes.isis2304.core.FK;
import org.uniandes.isis2304.core.PK;

@Data("student_res_spec") public class StudentResSpec_ {
    @PK @FK(Operator_.class) String name;
    Integer restaurant;
    Integer studyRoom;
    Integer recreationRoom;
    Integer gym;
}
