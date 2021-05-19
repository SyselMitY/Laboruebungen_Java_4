package cf.soisi.spring_employee;

import cf.soisi.spring_employee.model.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Integer> {
}
