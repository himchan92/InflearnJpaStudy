package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        //변경작업시 트랜젝션 필수로 생성
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setId(101L); //PK 부여
            member.setName("HelloJPA");

            //영속상태 : commit 전까진 DB저장 안하고 모아놓고 commit 순간 한번에 수행하여 메모리 절약
            em.persist(member);

            // 1차 캐시 조회
            Member findMember1 = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);
            System.out.println("findMember.id = " + findMember1.getId());
            System.out.println("findMember.name = " + findMember2.getName());

            //true : 1차캐시로 한 트랜젝션내에서는 동일 참조관계
            System.out.println(findMember1 == findMember2);

//            Member findMember = em.find(Member.class, 1L); //PK 기준 값 조회
//            findMember.setName("Hello JPA"); // 변경작업 : JPA감지하여 UPDATE 수행지원

            //JPQL : DB테이블아닌 Entity명 기준 SQL 수행
//            List<Member> result = em.createQuery("select m from Member", Member.class)
//                    // 1 ~ 10 페이징 SQL 지원
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for(Member member : result) {
//                System.out.println("member.name = " + member.getName());
//            }


            //DB 반영 처리
            tx.commit();
        } catch (Exception e) {
            tx.rollback(); //예외발생시 Rollback
        } finally {
            em.close();
        }

        emf.close();
    }
}
