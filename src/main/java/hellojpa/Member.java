package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {

    @Id
    private Long id;

    @Column(name = "name") // DB 컬럼명 설정
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING) //열거형은 무조건 STRING 타입으로!!, ORDINAL은 인덱스 저장으로 값 변경 시 변동되니 사용X
    private RoleType roleType;

    //@Temporal : 날짜타입 매핑 -> java 8 LocalDateTime 나오면서 미사용
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // JPA가 LocalDateTime을 인식해서 날짜타입 생성
    private LocalDateTime testLocalTime;

    @Lob //장문 문자열 타입
    private String description;

    public Member() {}
}
