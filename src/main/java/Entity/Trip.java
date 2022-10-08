package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
public class Trip {
    private Integer ID;
    private String name;
    private Date startDate;
    private Date endDate;

}
