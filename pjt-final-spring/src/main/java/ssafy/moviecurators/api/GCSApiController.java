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

    /*
     * GCS 파일 다운로드
     * 요청 JSON 예시
     * {
     *     "bucketName" : "moviecurator-profile",  // 버킷 이름
     *     "downloadFileName" : "1.jpg",  // 버킷 속 파일 이름
     *     "localFileLocation" : "download/3.jpg"  // 저장위치와 저장될 파일 이름
     * }
     * */

    /**
     * Storage(GCS)에서 사진을 다운로드[POST]
     * @param downloadReqDto GCS에서 사진을 JSON형태로 받아옴
     * @return
     */
    @PostMapping("/gcs/download")
    public ResponseEntity localDownloadFromStorage(@RequestBody DownloadReqDto downloadReqDto){

        Blob fileFromGCS = fileService.downloadFileFromGCS(downloadReqDto.getBucketName(),
                                                            downloadReqDto.getDownloadFileName(),
                                                            downloadReqDto.getLocalFileLocation());
        return ResponseEntity.ok(fileFromGCS.toString());
    }

    /**
     * Storage(GCS)에 사진을 업로드[POST]
     * @return 사진을 GCS에 JSON형태로 http response body에 넣어서 보냄
     * @throws IOException
     */
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
