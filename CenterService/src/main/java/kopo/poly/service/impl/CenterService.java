package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.CenterDTO;
import kopo.poly.repository.CenterRepository;
import kopo.poly.repository.entity.CenterEntity;
import kopo.poly.service.ICenterService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.NetworkUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CenterService implements ICenterService {

    @Value("${center.api.key}")
    private String apiKey;

    private final CenterRepository centerRepository;

    @Override
    public List<CenterDTO> getCenter(CenterDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getCenter Start!!");

        String f = pDTO.getFacType();
        log.info("facType : "+f);

        log.info("facType : "+f);

        // apiUrl에 붙일 파라미터
        String apiParam = "?serviceKey=" + apiKey + "&type=json" + "&pageNo=1&numOfRows=10000" + "&facType=" + f;
        log.info("apiParam : " + apiParam);

        // api 호출 후 결과값
        String json = NetworkUtil.get(ICenterService.apiURL + apiParam);
        log.info("json : " + json);
        // JSON 파싱을 위한 객체 생성
        JSONParser jsonParser = new JSONParser();
        // 결과값을 JSON 객체로 변경
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        log.info("jsonObject : " + jsonObject);

        // JSON Key 꺼내기
        // []은 JSONArray, {}은 JSONObject
        JSONArray body = (JSONArray) jsonObject.get("body");
        log.info("body : " + body);
        JSONObject items = (JSONObject) body.get(0);
        log.info("items : " + items);
        JSONObject ObjectItems = (JSONObject) items.get("items");
        log.info("Object items : " + ObjectItems);


        // JSON을 다시 String(구조는 JSON.)으로 바꾸기
        String itemsJson = ObjectItems.toString();
        log.info("JSON으로 바꾼 items : " + itemsJson);


        // JSON을 Map 구조로 변경
        Map<String,Object> rMap = new ObjectMapper().readValue(itemsJson, LinkedHashMap.class);
        log.info("rMap.item : " +rMap.get("item"));

        // 바꾼 Map에서 센터 정보를 담고 있는 item으로 꺼내어서 List<Map<>> 구조로 저장
        List<Map<String,String>> item = (List<Map<String, String>>) rMap.get("item");

        // 센터정보를 저장할 객체 생성
        List<CenterDTO> pList = new LinkedList<>();

        for (Map<String,String> centerList : item) {

            // item의 센터 정보 꺼내기
            String centerCall = CmmUtil.nvl((String) centerList.get("phone")); // 센터전화번호
            String addr =CmmUtil.nvl((String) centerList.get("address")); // 센터 주소
            String centerName = CmmUtil.nvl((String) centerList.get("facname")); // 센터명
            String centerType = CmmUtil.nvl((String) centerList.get("facType")); // 센터명

            // 꺼낸 센터정보 확인
            log.info("센터 전화번호 :" + centerCall);
            log.info("addr : " + addr);
            log.info("centerName : " + centerName);
            log.info("centerType : " + centerType);


            // 센터정보 DTO에 담기
            CenterDTO cDTO = new CenterDTO();
            cDTO.setCenterCall(centerCall);
            cDTO.setCenterAddr1(addr);
            cDTO.setCenterName(centerName);
            cDTO.setCenterType(centerType);

            // List에 DTO 담기
            pList.add(cDTO);


            //DTO 비워주기 메모리 비우기
            cDTO = null;
        }

        log.info("pList :" + pList.get(0).getCenterType());

        // 센터 정보를 담은 List를 ListDTO에 저장
        CenterDTO lDTO = new CenterDTO();
        lDTO.setCenterList(pList);
        log.info("잘 담겼니 : " + lDTO.getCenterList().get(0).getCenterName());


        log.info(this.getClass().getName() + ".getCenter End!!");

        return pList;
    }

    // 즐겨찾기 추가
    @Override
    public int insertCenter(CenterDTO pDTO) throws Exception {
        log.info(this.getClass().getName() +  ".insertCenter Start!!");

        int res = 0;

        // DTO에 담긴 내용 꺼내서 가져오기
        String regId = CmmUtil.nvl(pDTO.getRegId());
        String centerName = CmmUtil.nvl(pDTO.getCenterName()); // 센터 이름
        String centerCall = CmmUtil.nvl(pDTO.getCenterCall()); // 센터 전화번호
        String centerAddr1 = CmmUtil.nvl(pDTO.getCenterAddr1()); // 센터 주소


        CenterEntity cEntity = centerRepository.findByCenterName(centerName);

        if (cEntity != null) {
            res = 2;
        } else {
            // 회원가입을 위해 Entity 생성 //반드시 Builder
            CenterEntity pEntity = CenterEntity.builder()
                    .centerName(centerName).centerCall(centerCall)
                    .centerAddr1(centerAddr1)
                    .reg_id(regId).reg_dt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                    .build();


            //DB에 저장
            centerRepository.save(pEntity);

            // 즐겨찾기 중복 여부 재조회
            cEntity = centerRepository.findByCenterName(centerName);

            // cEntity에 결과값이 있다면 저장 성공 : res = 1;
            if (cEntity != null) {
                res = 1 ;
            }
        }

        log.info(this.getClass().getName() +  ".insertCenter End!!");

        return res;
    }

    // 즐겨찾기 리스트 가져오기
    @Override
    public CenterDTO getCenterList(String userId) throws Exception {
        log.info(this.getClass().getName() + ".getCenterList Start!!");

        CenterDTO rDTO = new CenterDTO();


        log.info(this.getClass().getName() + ".getCenterList End!!");
        return rDTO;
    }

    // 즐겨찾기 삭제
    @Override
    public int deleteUserInfo(long centerSeq) throws Exception {
        log.info(this.getClass().getName() + ".deleteUserInfo Start!!");

        int res = 0;
        log.info("centerSeq : " + centerSeq );

        res = centerRepository.deleteByCenterSeq(centerSeq);
        log.info("삭제 결과는 : " + res);


        log.info(this.getClass().getName() + ".deleteUserInfo End!!");

        return res;
    }

}
