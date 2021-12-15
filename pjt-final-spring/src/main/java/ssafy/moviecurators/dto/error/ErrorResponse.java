package ssafy.moviecurators.dto.error;

import lombok.Data;

@Data
public class ErrorResponse {

    // 장고와 형태 일치. 메시지 내용
    private String error;

    public ErrorResponse(String errorMessage) {
        this.error = errorMessage;
    }
}
