package kopo.poly.controller.rest;


import kopo.poly.dto.CenterDTO;
import kopo.poly.service.P_ICenterService;
import kopo.poly.util.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/page")
public class P_RestCenterController {

    private final P_ICenterService centerService;


    @GetMapping(value = "/getCenter")
    public List<CenterDTO> getCenterList(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".getCenterList (API 호출) Start!!");

        String facType = CmmUtil.nvl(request.getParameter("facType"));
        log.info("facType : "+facType);

        CenterDTO pDTO = new CenterDTO();
        pDTO.setFacType(facType);


        log.info("너냐 ?");
        List<CenterDTO> rList = centerService.getCenter(pDTO);
        log.info("나다 ?");
        log.info("rList : " + rList.get(0));

        log.info(this.getClass().getName() + ".getCenterList (API 호출) End!!");
        return rList;
    }

    @GetMapping("getString")
    public String getString() throws Exception {
        log.info("스트링 가져오기 시작 ");

        log.info("스트링 가져오기 끝 ");
        return centerService.getString();
    }
}
