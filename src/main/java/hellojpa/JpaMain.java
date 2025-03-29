package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        // JPA 변경작업은 반드시 한트랜젝션내에서 수행되야해서 셋팅필수
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");
            em.persist(member); //저장
            tx.commit(); //실제 DB 반영처리
        } catch (Exception e) {
            tx.rollback(); //오류나면 롤백처리
        } finally {
            em.close();
        }
        emf.close();
    }
}