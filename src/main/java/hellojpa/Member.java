package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity // JPA ENTITY 인식, 애플리케이션 로딩시점에 ddl-auto create 이면 drop - create 수행(운영서버에서는 금지)
public class Member {
    //제약조건은 테이블 생성에만 영향주고 JPA 실행에는 영향 x

    @Id //PK설정 : Long형, null안됨, 유일성, 불변성으로 생성 권장
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment 지원
    private Long id;

    @Column(name = "name", nullable = false) //DB 컬럼명 설정
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING) //ENUM타입 매핑, 열거형은 반드시 STRING 타입
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP) //날짜타입매핑
    private Date lastModifiedDate;

    @Lob //길이 큰 데이터 타입 //BLOB, CLOB 타입 매핑
    private String description;

    public Member() {}
}
