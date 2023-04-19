package kopo.poly.service;

import kopo.poly.dto.CenterDTO;

import java.util.List;

public interface ICenterService {


    String apiURL = "http://apis.data.go.kr/1383000/facility/selectList";

    // 센터 리스트 가져오기
    List<CenterDTO> getCenter(CenterDTO pDTO) throws Exception;

    // 즐겨찾기 센터 추가
    int insertCenter(CenterDTO pDTO) throws Exception;

    // 즐겨찾기 센터 리스트
    CenterDTO getCenterList(String userId) throws Exception;

    // 즐겨찾기 삭제
    int deleteUserInfo(long centerSeq) throws Exception;

}
