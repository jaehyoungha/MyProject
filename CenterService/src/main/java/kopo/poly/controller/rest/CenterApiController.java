package kopo.poly.controller.rest;

import kopo.poly.dto.CenterDTO;
import kopo.poly.service.ICenterService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/center")
public class CenterApiController {

    private final ICenterService centerService;

    @PostMapping(value = "/CenterList", consumes = "application/json")
    public ResponseEntity<List<CenterDTO>> getCenterList(@RequestBody CenterDTO pDTO, HttpServletRequest request) throws Exception {
        log.info("센터 리스트 가져오기 호출 !");
        log.info(this.getClass().getName() + ".getCenterList Start!!");

        if (pDTO == null) {
            log.info("pDTO is null");
        }

        List<CenterDTO> rList = Optional.ofNullable(centerService.getCenter(pDTO))
                        .orElseGet(ArrayList::new);;

        log.info("rList.size() : "+rList.size());
        log.info(this.getClass().getName() + ".getCenterList End!!");
        return ResponseEntity.ok().body(rList);
    }

    @GetMapping(value = "/getString")
    public String getString () {
        log.info("getString 호출 !");
        return "어려워 뒤지겠네";
    }

}
