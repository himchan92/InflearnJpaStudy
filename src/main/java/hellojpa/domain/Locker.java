package hellojpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Locker {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    //1:1 매핑 : Member  LOCKER_ID(FK)와 매핑
    @OneToOne(mappedBy = "locker")
    private Member member;
}
