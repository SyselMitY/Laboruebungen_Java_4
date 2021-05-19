package cf.soisi.spring_employee.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "working_hours")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkingHours implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wh_id")
    private Integer whId;
    @Basic
    @Column(name = "wh_hours")
    private Integer whHours;
    @Basic
    @Column(name = "wh_date")
    private LocalDate whDate;
    @ManyToOne
    @JoinColumn(name = "wh_emp_id")
    private Employee whEmp;
}
