package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String userId;
    private String userName;
    private String userPwd;
    private String phoneNumber;
    private String userEmail;
    private String addr1;
    private String addr2;
    private String regId;
    private String regDt;
    private String chgID;
    private String chgDt;
    private String filePath;
    private String roles;


}
