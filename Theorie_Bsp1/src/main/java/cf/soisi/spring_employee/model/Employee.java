package cf.soisi.spring_employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Integer empId;

    @Basic
    @Column(name = "emp_lastName")
    private String empLastName;

    @Basic
    @Column(name = "emp_firstName")
    private String empFirstName;

    @OneToMany(mappedBy = "whEmp")
    @JsonIgnore
    private
    List<WorkingHours> workingHoursList;
}
