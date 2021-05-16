package db;

import model.Dozent;
import model.Kunde;
import model.Kurs;
import model.Kurstyp;

import javax.persistence.EntityManager;
import java.util.List;

public class CombinedRepository implements IKurssystemService,AutoCloseable {

    private static CombinedRepository INSTANCE;

    private CombinedRepository() {
        dozentRepository = DozentRepository.getInstance();
        kundeRepository = KundeRepository.getInstance();
        kursRepository = KursRepository.getInstance();
        kurstypRepository = KurstypRepository.getInstance();
    }

    public static CombinedRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CombinedRepository();
        }
        return INSTANCE;
    }

    private DozentRepository dozentRepository;
    private KundeRepository kundeRepository;
    private KursRepository kursRepository;
    private KurstypRepository kurstypRepository;

    @Override
    public List<Kunde> getKunden() throws KursDBException {
        return kundeRepository.findAll();
    }

    @Override
    public void insertKunde(Kunde k) throws KursDBException {
        if (!kundeRepository.persist(k)) {
            throw new KursDBException();
        }
    }

    @Override
    public void deleteKunde(Kunde k) throws KursDBException {
        if(!kundeRepository.delete(k))
            throw new KursDBException();
    }

    @Override
    public List<Dozent> getDozenten() throws KursDBException {
        return dozentRepository.findAll();
    }

    @Override
    public List<Kurstyp> getKurstypen() throws KursDBException {
        return kurstypRepository.findAll();
    }

    @Override
    public List<Kurs> getKurse() throws KursDBException {
        return kursRepository.findAll();
    }

    @Override
    public void insertKurstyp(Kurstyp kt) throws KursDBException {
        if (!kurstypRepository.persist(kt))
            throw new KursDBException();
    }

    @Override
    public void deleteKurstyp(Kurstyp kt) throws KursDBException {
        if(!kurstypRepository.delete(kt))
            throw new KursDBException();
    }

    @Override
    public void insertKurs(Kurs kurs) throws KursDBException {
        kursRepository.persist(kurs);
    }

    @Override
    public List<Kunde> getKundenFromKurs(Kurs kurs) throws KursDBException {
        return kundeRepository.findByKurs(kurs);
    }

    @Override
    public void bucheKurs(Kunde kunde, Kurs kurs) throws KursDBException {
        kunde.addKurs(kurs);
    }

    @Override
    public void storniereKurs(Kunde kunde, Kurs kurs) throws KursDBException {
        kunde.removeKurs(kurs);
    }

    @Override
    public void close() throws Exception {
        JPAUtil.close();
        kurstypRepository.close();
        kursRepository.close();
        dozentRepository.close();
        kundeRepository.close();
    }
}
