package ssafy.moviecurators.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ssafy.moviecurators.api.GCSApiController;
import ssafy.moviecurators.domain.accounts.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)  // 기본적으로 트랜잭션 안에서만 데이터 변경하게 설정(그만큼 최적화 되어 읽는게 빨라짐)
@RequiredArgsConstructor  // 생성자 주입 처리
public class FileService {

    // GCS의 key.json 파일 내용이 등록되어서 주입됨
    private final Storage storage;

    public String imageUploadLocal(MultipartFile file, User user) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String uploadDir = "media/profile/" + user.getId();

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            // 장고 설정 200, 200, 화질 100
            Image processedImage = ImageIO.read(inputStream);

            BufferedImage scaledBI = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = scaledBI.createGraphics();
            g.drawImage(processedImage, 0, 0, 200, 200, null);
            g.dispose();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(scaledBI, "jpg", os);

            InputStream processedInputStream = new ByteArrayInputStream(os.toByteArray());

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(processedInputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ioe) {
            throw new IOException("이미지 저장/변환 불가: " + fileName, ioe);
        }

        String image = "/media/profile/" + user.getId() + "/" + fileName;
        return image;
    }


    @SuppressWarnings("deprecation")
    public String imageUploadGCS (MultipartFile file, User user) throws IOException {
        String bucketName = "moviecurator-profile";
        // GCS 자체 지원으로 폴더 추가할때 로직 필요 없음
        String uploadFileName = user.getId() + "/" + StringUtils.cleanPath(file.getOriginalFilename());
//        String uploadFileName = user.getId() + "/" + UUID.randomUUID().toString();  // 랜덤 문자열로 중복 최소화 하는 법
        //String uploadFileName = user.getId() + ".jpg";  // 강제 덮어씌우기로 용량 절약 (프론트쪽 리렌더링에 문제상 현 프로젝트는 적용 불가)

        try (InputStream inputStream = file.getInputStream()) {
            // 장고 설정 200, 200, 화질 100
            Image processedImage = ImageIO.read(inputStream);

            BufferedImage scaledBI = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = scaledBI.createGraphics();
            g.drawImage(processedImage, 0, 0, 200, 200, null);
            g.dispose();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(scaledBI, "jpg", os);

            InputStream processedInputStream = new ByteArrayInputStream(os.toByteArray());

            storage.create(BlobInfo.newBuilder(bucketName, uploadFileName).build(), processedInputStream);

        } catch (IOException ioe) {
            throw new IOException("이미지 저장/변환 불가 : " + uploadFileName, ioe);
        }

        // DB에 저장될 값
        String image = "/" + uploadFileName;
        return image;
    }

    @SuppressWarnings("deprecation")
    public BlobInfo uploadFileToGCS() throws IOException {

        String bucketName = "moviecurator-profile";
        String uploadFileName = "testUp/2.jpg";  // 폴더 추가할때 로직 필요 없음
        String localFileLocation = "download/3.jpg";

        BlobInfo blobInfo =storage.create(
                BlobInfo.newBuilder(bucketName, uploadFileName)
//                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
                        .build(),
                new FileInputStream(localFileLocation));

        return blobInfo;
    }


    public Blob downloadFileFromGCS(String bucketName, String downloadFileName, String localFileLocation) {
        Blob blob = storage.get(bucketName, downloadFileName);
        blob.downloadTo(Paths.get(localFileLocation));
        return blob;

        // 현재 GCS json 파일명을 못 읽어서 storage에 주입이 안된는 버그 발생시, 아래와 같은 수동 생성 필요
//        try {
//            String keyFileName = "moviecurator-profile-142d5790645e";
//            String keyFileName = "credentials.json";
//            InputStream keyFile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();
//            Storage storage = StorageOptions.newBuilder().setProjectId("moviecurator-profile")
//                    .setCredentials(GoogleCredentials.fromStream(keyFile)) // Key 파일 수동 등록
//                    .build().getService();
//
//            // 원래 함수
//            Blob blob = storage.get(bucketName, downloadFileName);
//            blob.downloadTo(Paths.get(localFileLocation));
//            return blob;
//
//        } catch (IOException e) {
//            log.error(e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
    }
}
