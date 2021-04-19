package db;

import model.Run;
import model.Runner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RunnerAppRepository implements AutoCloseable {
    private final static RunnerAppRepository INSTANCE = new RunnerAppRepository();

    private RunnerAppRepository() {

    }

    public static RunnerAppRepository getINSTANCE() {
        return INSTANCE;
    }

    public Optional<Runner> findRunnerById(Integer id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return Optional.ofNullable(em.find(Runner.class, id));
        } finally {
            em.close();
        }
    }

    public Optional<Run> findRunById(Integer id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return Optional.ofNullable(em.find(Run.class, id));
        } finally {
            em.close();
        }
    }

    public boolean persist(Object object) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(object);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive())
                tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public List<Runner> findAllRunners() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("select r from Runner r", Runner.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Run> findAllRuns() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            return em.createQuery("select r from Run r", Run.class).getResultList();
        } finally {
            em.close();
        }
    }


    public List<Runner> longRun(int distance) {
        if (distance <= 0)
            throw new IllegalArgumentException("Distance must be positive");
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            TypedQuery<Runner> query = em.createQuery("select r " +
                    "from Runner r " +
                    "inner join r.runs run " +
                    "group by r " +
                    "having max(run.distance) > :distance", Runner.class);
            query.setParameter("distance", distance);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Long totalDistance(Runner runner, LocalDate begin, LocalDate end) {
        if(end.isBefore(begin) || runner == null)
            throw new IllegalArgumentException();

        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("select sum(r.distance) " +
                    "from Run r " +
                    "where r.runner = :runner AND r.date >= :begin AND r.date < :end", Long.class);
            query.setParameter("runner", runner);
            query.setParameter("begin", begin);
            query.setParameter("end", end);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public void insertTestData() {
        Runner r1 = new Runner("Huber", "Karl", LocalDate.of(1990, 6, 7), "M", 75);
        Runner r2 = new Runner("Schmidt", "Eva", LocalDate.of(1997, 10, 26), "W", 55);
        Run l1 = new Run(LocalDate.of(2019, 6, 11), 55, 10350);
        l1.setRunner(r1);
        Run l2 = new Run(LocalDate.of(2019, 6, 12), 172, 42195);
        l2.setRunner(r1);
        Run l3 = new Run(LocalDate.of(2019, 6, 13), 58, 8320);
        l3.setRunner(r2);
        Run l4 = new Run(LocalDate.of(2019, 7, 14), 83, 15000);
        l4.setRunner(r2);
        Run l5 = new Run(LocalDate.of(2019, 7, 15), 115, 21000);
        l5.setRunner(r2);
        INSTANCE.persist(r1);
        INSTANCE.persist(r2);
        INSTANCE.persist(l1);
        INSTANCE.persist(l2);
        INSTANCE.persist(l3);
        INSTANCE.persist(l4);
        INSTANCE.persist(l5);
    }

    public static void main(String[] args) {
        RunnerAppRepository.getINSTANCE().insertTestData();
    }

    public void close() {
        JPAUtil.close();
    }
}
