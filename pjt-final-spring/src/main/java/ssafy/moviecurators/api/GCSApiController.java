package ssafy.moviecurators.api;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ssafy.moviecurators.service.FileService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class GCSApiController {

    private final FileService fileService;

    /**
     * GCS 파일 다운로드
     * 요청 JSON 예시
     * {
     *     "bucketName" : "moviecurator-profile",  // 버킷 이름
     *     "downloadFileName" : "1.jpg",  // 버킷 속 파일 이름
     *     "localFileLocation" : "download/3.jpg"  // 저장위치와 저장될 파일 이름
     * }
     * */

    @PostMapping("/gcs/download")
    public ResponseEntity localDownloadFromStorage(@RequestBody DownloadReqDto downloadReqDto){

        Blob fileFromGCS = fileService.downloadFileFromGCS(downloadReqDto.getBucketName(),
                                                            downloadReqDto.getDownloadFileName(),
                                                            downloadReqDto.getLocalFileLocation());
        return ResponseEntity.ok(fileFromGCS.toString());
    }

    @PostMapping("/gcs/upload")
    public ResponseEntity localUploadFromStorage() throws IOException {

        BlobInfo fileFromGCS = fileService.uploadFileToGCS();
        return ResponseEntity.ok(fileFromGCS.toString());
    }

    // DTO는 로직 없으니 @Data 편하게 사용, static 필수
    @Data
    static class DownloadReqDto {
        private String bucketName;
        private String downloadFileName;
        private String localFileLocation;
    }


}
