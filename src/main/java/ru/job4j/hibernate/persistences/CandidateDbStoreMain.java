package ru.job4j.hibernate.persistences;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.entities.Candidate;

public class CandidateDbStoreMain {

    public static void main(String[] args) {
        CandidateDbStore store = new CandidateDbStore();
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
        try {
            SessionFactory sf =
                    new MetadataSources(registry)
                            .buildMetadata()
                            .buildSessionFactory();
            store.create(new Candidate("Maxim", 2, 1300), sf);
            store.create(new Candidate("Oleg", 7, 2400), sf);
            store.create(new Candidate("Natalia", 1, 800), sf);
            System.out.println(store.findAll(sf));
            System.out.println("*****************************************");
            System.out.println(store.findById(2, sf));
            System.out.println("*****************************************");
            System.out.println(store.findById(4, sf));
            System.out.println("*****************************************");
            System.out.println(store.findByName("Maxim", sf));
            System.out.println("*****************************************");
            Candidate newCandidate = new Candidate("Igor", 11, 3050);
            store.update(newCandidate, 6, sf);
            System.out.println("*****************************************");
            System.out.println(store.findById(6, sf));
            System.out.println("*****************************************");
            store.delete(6, sf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

