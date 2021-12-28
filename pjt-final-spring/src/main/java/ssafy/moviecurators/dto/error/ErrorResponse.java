package ssafy.moviecurators.dto.error;

import lombok.Data;

/**
 * 에러 메세지 관련
 */
@Data
public class ErrorResponse {

    private String error;

    public ErrorResponse(String errorMessage) {
        this.error = errorMessage;
    }
}
