package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //JPA 변경은 한트랜젝션안에서 수행
        try {
            //비영속상태
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");

            //영속상태
            em.persist(member);

            //영속 시 1차캐시에 담겨있는것 우선조회하고 없으면 DB조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());

            //JPQL
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(0) //페이징 첫
                    .setMaxResults(1) //페이징 라스트
                    .getResultList(); //목록화

            for(Member item : result) {
                System.out.println("item.name = " + item.getName());
            }

            tx.commit(); //DB 반영
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}