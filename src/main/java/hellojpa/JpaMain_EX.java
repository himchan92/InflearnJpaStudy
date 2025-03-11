package hellojpa;

import jakarta.persistence.*;

public class JpaMain_EX {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        //변경작업시 트랜젝션 필수로 생성
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member_EX memberEX = new Member_EX();
            // 최초 셋팅 : 1차 캐시 등록
            memberEX.setId(101L);
            memberEX.setName("HelloMyBatis");

            //em.clear(); //영속성 컨택스트 클리어 = 1차캐시 삭제
            //em.close(); //영속성 관리 종료 = 변경해도 결과 그대로

            //영속상태 : commit 전까진 DB 저장 안하고 모아놓고 commit 순간 한번에 수행하여 메모리 절약
            // JPA INSERT 수행(commit 안해서 실제 DB 반영은 X)
            em.persist(memberEX);

            // JPA 변경감지로 UPDATE 수행 지원(Dirty Checking)
            // 최초 persist 시 1차 캐시에 저장 후 setter 시 1차 캐시값과 비교 하여 차이있을 시 Dirty Checking 수행
            memberEX.setName("HelloJPA");

            Member_EX memberEX2 = em.find(Member_EX.class, 101L);
            em.remove(memberEX2); // JPA DELETE SQL 수행

            // 1차 캐시 조회
//            Member_EX findMember1 = em.find(Member_EX.class, 101L);
//            Member_EX findMember2 = em.find(Member_EX.class, 101L);
//            System.out.println("findMember.id = " + findMember1.getId());
//            System.out.println("findMember.name = " + findMember2.getName());

            //true : 1차캐시로 한 트랜젝션내에서는 동일 참조관계
//            System.out.println(findMember1 == findMember2);

//            Member_EX findMember = em.find(Member_EX.class, 1L); //PK 기준 값 조회
//            findMember.setName("Hello JPA"); // 변경작업 : JPA감지하여 UPDATE 수행지원

            //JPQL : DB테이블아닌 Entity명 기준 SQL 수행
//            List<Member_EX> result = em.createQuery("select m from Member_EX", Member_EX.class)
//                    // 1 ~ 10 페이징 SQL 지원
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for(Member_EX memberEX : result) {
//                System.out.println("memberEX.name = " + memberEX.getName());
//            }

            //em.flush(); // COMMIT 직전 변경내용을 DB에 동기화 수행

            // DB 반영
            // 플러시 자동 수행(영속성 컨택스트 변경내용을 DB에 반영)
            //tx.commit();
        } catch (Exception e) {
            tx.rollback(); //예외발생시 Rollback
        } finally {
            em.close();
        }

        emf.close();
    }
}
