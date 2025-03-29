package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        // JPA 변경작업은 반드시 한트랜젝션내에서 수행되야해서 셋팅필수
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member();
        member.setId(1L);
        member.setName("HelloA");
        em.persist(member); //저장
        tx.commit(); //실제 DB 반영처리

        em.close();
        emf.close();
    }
}