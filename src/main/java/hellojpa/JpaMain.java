package hellojpa;

import jakarta.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        //영속성 컨택스트 : DB 트랜젝션 단위로 수행
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        // JPA 변경작업은 반드시 트랜젝션내에서 수행되야하니 셋팅 필수(@Transactional)
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //JPA 저장
            //---------- 비영속 상태 ----------//
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");

            //---------- 영속 상태(실제 DB 수행 x), 1차캐시 저장 ----------//
            //em.persist(member);

            //em.detach(member): // 준영속상태로 변경
            //em.remove(member); // 수행대상 삭제

            //JPA 조회
            Member findMember = em.find(Member.class, 1L); //1차 캐시에서 조회
            Member findMember2 = em.find(Member.class, 1L); //동일 조회문이나 앞서 1차캐시에서 이미 조회했기에 조회 안됨
            System.out.println(findMember == findMember2); //동일 보장 TRUE
            System.out.println("findMember id = " + findMember.getId());
            System.out.println("findMember name = " + findMember.getName());

            //JPQL : 객체지향 SQL, 테이블대신 엔티티객체대상 수행, 페이징 지원
            List<Member> findMembers = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(5) //페이징 첫
                .setMaxResults(8) //페이징 최대
                .getResultList(); //다건 조회

            for(Member item : findMembers) {
                System.out.println("item.name = " + item.getName());
            }

            //JPA 수정(변경감지)
            Member find = em.find(Member.class, 1L);
            System.out.println("member find = " + find);
            find.setName("HelloBoot");

            //------ 실제 DB 수행 ------//
            // 변경감지 체크단계로 위 setter 작업 등 변경 있으면 UPDATE 수행
            tx.commit();
        } catch (Exception e) {
            tx.rollback(); //오류나면 롤백처리
        } finally {
            em.close();
        }
        emf.close();
    }
}