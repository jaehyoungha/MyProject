package kopo.poly.controller.rest;//package kopo.poly.controller.rest;
//
//import kopo.poly.dto.FileDTO;
//import kopo.poly.service.IS3UploadService;
//import kopo.poly.util.DateUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//public class UploadController{
//
//    //이미지 업로드//
//    @Resource(name = "S3UploadService")
//    private IS3UploadService s3UploadService;
//
//
//    @PostMapping(value = "/uploadS3")
//    public Map<String, Integer> uploadS3(@RequestParam(value = "fileUpload") MultipartFile multipartFile)throws Exception{
//        log.info(this.getClass().getName()+"s3파일 업로드 시작!");
//
//
//        // 원본 파일명
//            String originalFileName = multipartFile.getOriginalFilename();
//
//            // 파일 확장자 가져오기
//            String ext = originalFileName
//                    .substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
//
//            // 저장되는 파일이름 (영어, 숫자로 파일명 변경)
//            String saveFileName = DateUtil.getDateTime("24hhmmss") + "." + ext;
//
//            log.info("원본파일 이름! : " + originalFileName);
//            log.info("저장 파일 이름 !" + saveFileName);
//            //저장경로
//            String saveFilePath = s3UploadService.fileUpload(multipartFile, saveFileName);
//
//            //DB 저장
//            int res = 0;
//
//            FileDTO fDTO = new FileDTO();
//            fDTO.setFile_path(saveFilePath);
//            fDTO.setFile_ext(ext);
//            fDTO.setFile_orgName(originalFileName);
//
//
//            res =s3UploadService.fileInsert(fDTO);
//
//            Map<String, Integer> pMap = new HashMap<>();
//            pMap.put("res", res);
//
//        log.info(this.getClass().getName()+"s3파일 업로드 종료!");
//
//        return pMap;
//    }
//
//
//}