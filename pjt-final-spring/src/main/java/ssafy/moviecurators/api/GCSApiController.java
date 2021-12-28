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

    @Data
    static class DownloadReqDto {
        private String bucketName;
        private String downloadFileName;
        private String localFileLocation;
    }


}
