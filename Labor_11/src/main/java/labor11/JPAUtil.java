package labor11;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory EMF;

    static {
        try {
            EMF = Persistence.createEntityManagerFactory("jpa-test-unit");
        } catch (Exception e) {
            System.err.println("EntityManagerFactory creation failed: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManagerFactory getEMF() {
        return EMF;
    }

    public static void close() {
        if(EMF.isOpen())
            EMF.close();
    }
}
