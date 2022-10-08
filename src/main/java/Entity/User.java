package Entity;

import JDBC.SQLHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class User {
    private Integer ID;
    private String firstName;
    private String lastName;
    private Date dob;
    private String address;

}
