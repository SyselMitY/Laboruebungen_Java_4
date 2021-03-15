package labor11;


public class Main {
    public static void main(String[] args) {
        CustomerRepository cr = CustomerRepository.getINSTANCE();
        Customer c = new Customer("sossi@soisi.cf", "Sossi", "Soisi", new Address("Kirchdorf", "Isperstraße", 3204));
        cr.persist(c);
    }
}
