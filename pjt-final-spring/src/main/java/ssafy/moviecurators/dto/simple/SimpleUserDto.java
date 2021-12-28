package ssafy.moviecurators.dto.simple;

import lombok.Data;
import ssafy.moviecurators.domain.accounts.User;

import java.time.OffsetDateTime;

/**
 * 유저 관련 DTO
 */
@Data
public class SimpleUserDto {

    private Long id;
    private String username;
    private String introduction;
    private String nickname;
    private String image;
    private Integer mileage;
    private Integer exp;

    public SimpleUserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.introduction = user.getIntroduction();
        this.nickname = user.getNickname();
        this.mileage = user.getMileage();
        this.exp = user.getExp();

        if (user.getImage().equals("")) {
            this.image = null;
        } else {
            this.image = user.getImage();
        }
            }
}
