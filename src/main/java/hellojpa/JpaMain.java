package hellojpa;

import jakarta.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        // JPA 변경작업은 반드시 트랜젝션내에서 수행되야하니 셋팅 필수(@Transactional)
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //JPA 저장
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");
            em.persist(member);

            //JPA 조회
            Member findMember = em.find(Member.class, 1L); //1L 조회
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

            //JPA 수정
            findMember.setName("HelloJPA");

            tx.commit(); //실제 DB 반영처리, 위 setter 시 변경감지 체크후 있으면 UPDATE 수행
        } catch (Exception e) {
            tx.rollback(); //오류나면 롤백처리
        } finally {
            em.close();
        }
        emf.close();
    }
}