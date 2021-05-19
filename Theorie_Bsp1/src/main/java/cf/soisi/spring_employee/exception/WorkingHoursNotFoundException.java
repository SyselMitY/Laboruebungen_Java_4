package cf.soisi.spring_employee.exception;

public class WorkingHoursNotFoundException extends RuntimeException {
    public WorkingHoursNotFoundException(String s) {
        super(s);
    }
}
