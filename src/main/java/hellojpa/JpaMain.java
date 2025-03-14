package hellojpa;

import hellojpa.domain.Member;
import hellojpa.domain.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        //변경작업시 트랜젝션 필수로 생성
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("TeamA"); //실무에서는 setter 대신 Builder 패턴 많이 사용
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

            //영속성 클리어
            em.flush();
            em.clear();

            /*
                연관관계 매핑 어노테이션 기본설정은 즉시로딩
                - 실무에서는 즉시로딩 금지(SQL 예상못한 수행으로 장애우려 및 성능저하)
                - 실무에서는 @ManyToOne, @OneToOne -> 지연로딩 설정 권장
             */
            Member m = em.find(Member.class, member.getId());
            System.out.println("m = " + m.getTeam().getClass());

//            Member findMember = em.find(Member.class, member.getId());
//            List<Member> members = findMember.getTeam().getMembers();
//
//            for(Member m : members) {
//                System.out.println("m = " + m.getUsername());
//            }

            // TeamB로 변경
//            Team newTeam = em.find(Team.class, 100L);
//            findMember.setTeam(newTeam); //변경감지 -> UPDATE 수행지원

            tx.commit();
        } catch (Exception e) {
            tx.rollback(); //예외발생시 Rollback
        } finally {
            em.close();
        }
        emf.close();
    }
}
