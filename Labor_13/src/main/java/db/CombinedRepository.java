package db;

import model.Dozent;
import model.Kunde;
import model.Kurs;
import model.Kurstyp;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
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
        final List<Kunde> list = kundeRepository.findAll();
        list.sort(Comparator.comparing(Kunde::getKundeId));
        return list;
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

    public void insertDozent(Dozent dozent) {
        if(!dozentRepository.persist(dozent))
            throw new KursDBException();
    }

    @Override
    public List<Dozent> getDozenten() throws KursDBException {
        final List<Dozent> list = dozentRepository.findAll();
        list.sort(Comparator.comparing(Dozent::getDozId));
        return list;
    }

    @Override
    public List<Kurstyp> getKurstypen() throws KursDBException {
        final List<Kurstyp> list = kurstypRepository.findAll();
        list.sort(Comparator.comparing(Kurstyp::getTypId));
        return list;
    }

    @Override
    public List<Kurs> getKurse() throws KursDBException {
        final List<Kurs> list = kursRepository.findAll();
        list.sort(Comparator.comparing(Kurs::getKursId));
        return list;
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
        if(!kursRepository.persist(kurs))
            throw new KursDBException();
    }

    @Override
    public List<Kunde> getKundenFromKurs(Kurs kurs) throws KursDBException {
        return new ArrayList<>(kundeRepository.findByKurs(kurs));
    }

    @Override
    public void bucheKurs(Kunde kunde, Kurs kurs) throws KursDBException {
        kundeRepository.bucheKurs(kunde, kurs);
    }

    @Override
    public void storniereKurs(Kunde kunde, Kurs kurs) throws KursDBException {
        kundeRepository.storniereKurs(kunde,kurs);
    }

    @Override
    public void close() throws Exception {
        kurstypRepository.close();
        kursRepository.close();
        dozentRepository.close();
        kundeRepository.close();
    }
}
