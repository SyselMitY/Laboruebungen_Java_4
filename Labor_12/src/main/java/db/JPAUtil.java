package db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JPAUtil {

    private static EntityManagerFactory EMF = null;

    public static EntityManagerFactory getEMF() {
        if(EMF == null) {
            try (InputStream is = JPAUtil.class.getResourceAsStream("/secret/connection.properties")) {
                if (is != null) {
                    Properties properties = new Properties();
                    properties.load(is);
                    EMF = Persistence.createEntityManagerFactory("jpa-test-unit",properties);
                } else {
                    EMF = Persistence.createEntityManagerFactory("jpa-test-unit");
                }
            } catch (IOException e) {
                System.err.println("EntityManagerFactory creation failed: " + e);
                throw new ExceptionInInitializerError(e);
            }
        }
        return EMF;
    }

    public static void close() {
        if (EMF.isOpen()) {
            EMF.close();
            EMF = null;
        }
    }

}
