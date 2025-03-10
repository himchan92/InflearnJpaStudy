package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member_EX {

    @Id
    //@GeneratedValue : IDENTITY(MySQL auto increment, DB에 PK 생성 위임0), SEQUENCE(Oracle 시퀀스)
    private Long id;

    @Column(name = "name") // DB 컬럼명 설정
    private String name;

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

    public Member_EX() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LocalDateTime getTestLocalTime() {
        return testLocalTime;
    }

    public void setTestLocalTime(LocalDateTime testLocalTime) {
        this.testLocalTime = testLocalTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
