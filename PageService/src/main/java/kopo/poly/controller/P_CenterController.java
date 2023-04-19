package kopo.poly.controller;

import kopo.poly.service.P_ICenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/page")
@RequiredArgsConstructor
public class P_CenterController {

    private String title="";

    private String state="";

    private String msg="";

    private String url="";

    private String sweetalert="sweetalert";

    private final P_ICenterService centerService;

    @GetMapping("CenterPage")
    public String CenterPage() {
        log.info(this.getClass().getName() + ".CenterPage Start!!");
        log.info(this.getClass().getName() + ".CenterPage Start!!");
        return "Center/CenterList";
    }
}
