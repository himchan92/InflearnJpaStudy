package hellojpa;

import jakarta.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        //JPA 모든변경작업은 한 트랜젝션내에서 수행 필수
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);

//            Member member = em.find(Member.class, 1L);
//            member.setName("HelloJPA");
//            System.out.println("findMember id = " + member.getId());
//            System.out.println("findMember name = " + member.getName());

            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(5) //페이징 5부터
                .setMaxResults(8) //페이징 8까지
                .getResultList(); //ENTITY 대상 수행

            for(Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            tx.commit(); // DB 실 반영 처리
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}