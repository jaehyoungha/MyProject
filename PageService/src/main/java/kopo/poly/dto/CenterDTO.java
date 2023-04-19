package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CenterDTO {

    // 센터 번호
    private long centerSeq;
    // 센터 명
    private String centerName;
    // 센터 전화번호
    private String centerCall;
    //센터 주소
    private String centerAddr1;
    // 센터타입
    private String centerType;
    // 등록자 ID
    private String regId;
    // 등록일
    private String regDt;

    // 조회한 센터 List
    private List<CenterDTO> centerList;


    // 센터타입 코드
    String facType;
}
