package ssafy.moviecurators.dto;

import lombok.Data;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.dto.simple.SimpleUserDto;

import java.util.List;


import static java.util.stream.Collectors.toList;

/**
 * 유저 프로필 관련 DTO
 */
@Data
public class UserProfileDto {

    private Long id;
    private String username;
    private String introduction;
    private String nickname;
    private String image;
    private Integer mileage;
    private Integer exp;
    private List<SimpleUserDto> followers;
    private List<SimpleUserDto> following;
    private Integer articles_count;
    private Integer comments_count;


    public UserProfileDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.introduction = user.getIntroduction();
        this.nickname = user.getNickname();
        this.mileage = user.getMileage();
        this.exp = user.getExp();
        this.followers = user.getTo_user().stream().map(curator -> new SimpleUserDto(curator.getFromUser())).collect(toList());
        this.following = user.getFrom_user().stream().map(curator -> new SimpleUserDto(curator.getToUser())).collect(toList());
        this.articles_count = user.getArticles().size();
        this.comments_count = user.getComments().size();

        if (user.getImage().equals("")) {
            this.image = null;
        } else {
            this.image = user.getImage();
        }
    }
}
