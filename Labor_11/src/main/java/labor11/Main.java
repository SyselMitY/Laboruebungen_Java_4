package labor11;


import labor11.model.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Repository repository = Repository.getINSTANCE();
        repository.insertTestData();
   }
}
