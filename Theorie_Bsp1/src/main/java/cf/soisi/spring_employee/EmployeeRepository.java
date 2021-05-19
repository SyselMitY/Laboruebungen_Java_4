package cf.soisi.spring_employee;

import cf.soisi.spring_employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    List<Employee> findByEmpLastName(String name);

    List<Employee> findEmployeeByEmpLastNameLike(String pattern);
}
