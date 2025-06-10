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

            //영속상태: DB에 INSERT 수행 전
            em.persist(member);

            //영속 시 1차캐시에 담겨있는것 우선조회하고 없으면 DB조회
            //한트랜젝션내에서는 동일성보장
            Member findMember = em.find(Member.class, 1L);

            //변경감지 UPDATE 수행지원
            findMember.setName("HelloMyBatis");

            Member findMember2 = em.find(Member.class, 1L);
            System.out.println("findMember == findMember2 " + (findMember == findMember2));
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

            //삭제대상 조회
            Member target = em.find(Member.class, 1L);

            //삭제 수행
            em.remove(target);

            //DB INSERT 수행
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}