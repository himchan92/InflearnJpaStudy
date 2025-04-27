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
            // 비영속 상태(JPA 적용 전)
            Member member = new Member();
            // update문 없이 setter 후 commit 하면 JPA 내부적으로 변경감지하여 update 수행
            member.setId(100L);
            member.setName("HelloJPA");

            // 영속상태(JPA 적용, 1차캐시 저장, DB 저장 X)
            em.persist(member);

            em.flush(); // DB에 즉시 반영처리

            Member findMember = em.find(Member.class, 100L); //1차캐시 조회 -> 동일 소스면 중복 호출안하는 이점
            System.out.println("findMember id = " + member.getId());
            System.out.println("findMember name = " + member.getName());

            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(5) //페이징 5부터
                .setMaxResults(8) //페이징 8까지
                .getResultList(); //ENTITY 대상 수행

            for(Member item : result) {
                System.out.println("member.name = " + item.getName());
            }

            tx.commit(); // 영속상태 -> DB 반영 처리

            /*
                영속성 컨택스트(엔티티 매니저) 특징
                - 1차 캐시 : find 호출시 DB보다 1차캐시쪽에서 우선 탐색하여 있으면 조회 없으면 DB조회
                - 플러시 : 영속성컨택스트 -> DB, 직접호출(.flush()), 자동호출(commit), 미리보고싶으면 flush() 호출
             */
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}