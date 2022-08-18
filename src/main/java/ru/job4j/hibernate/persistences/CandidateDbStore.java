package ru.job4j.hibernate.persistences;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.hibernate.entities.Candidate;

import java.util.List;
import java.util.Optional;

public class CandidateDbStore {

    public Candidate create(Candidate candidate, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(candidate);
        session.getTransaction().commit();
        session.close();
        return candidate;
    }

    public List<Candidate> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Candidate> rsl =
                session.createQuery("from Candidate", Candidate.class).list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public boolean update(Candidate candidate, int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean rsl = session.createQuery("update Candidate set name = "
                        + ":newName, experience = :newExp, salary = :newSalary"
                        + " where id = :fId")
                .setParameter("newName", candidate.getName())
                .setParameter("newExp", candidate.getExperience())
                .setParameter("newSalary", candidate.getSalary())
                .setParameter("fId", id)
                .executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public void delete(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Candidate where id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public Optional<Candidate> findById(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Optional<Candidate> rsl = Optional.ofNullable(
                session.createQuery("from Candidate where id = :fId", Candidate.class)
                        .setParameter("fId", id)
                        .uniqueResult()
        );
        session.get(Candidate.class, 1);
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public List<Candidate> findByName(String name, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Candidate> rsl = session.createQuery(
                        "from Candidate where name = :fName", Candidate.class
                ).setParameter("fName", name)
                .list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }
}
