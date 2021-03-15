package labor11;


public class Main {
    public static void main(String[] args) {
        Repository repository = Repository.getINSTANCE();
        Customer c = new Customer("sossi@soisi.cf", "Sossi", "Soisi", new Address("Kirchdorf", "Isperstraße", 3204));
        System.out.println(repository.persist(c));
    }
}
