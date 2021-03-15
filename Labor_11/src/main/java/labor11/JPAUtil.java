package labor11;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.InputStream;
import java.util.Properties;

public class JPAUtil {
    private static final EntityManagerFactory EMF;

    static {
        try (InputStream is = JPAUtil.class.getResourceAsStream("/secret/connection.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            EMF = Persistence.createEntityManagerFactory("jpa-test-unit",properties);
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
