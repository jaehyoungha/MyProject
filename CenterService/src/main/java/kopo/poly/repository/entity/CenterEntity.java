package kopo.poly.repository.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CENTER")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
public class CenterEntity {

    @Id
    @Column(name = "CENTER_SEQ") // 센터 시퀀스
    private long centerSeq;

    @NonNull
    @Column(name = "CENTER_NAME") // 복지센터명
    private String centerName;

    @NonNull
    @Column(name = "CENTER_CALL") // 전화번호
    private String centerCall;

    @NonNull
    @Column(name = "CENTER_ADDR1", nullable = false) // 센터주소
    private String centerAddr1;

    @Column(name = "REG_ID", updatable = false) // 등록자 ID
    private String reg_id;

    @Column(name = "REG_DT", updatable = false) // 등록일
    private String reg_dt;


}
