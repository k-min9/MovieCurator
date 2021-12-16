package ssafy.moviecurators.dto;

import lombok.Data;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.dto.simple.SimpleUserDto;

import java.util.List;


import static java.util.stream.Collectors.toList;

@Data
public class UserProfileDto {

    private Long id;
//    private String password;
//    private OffsetDateTime last_login;
//    private boolean is_superuser;
    private String username;
//    private String first_name;
//    private String last_name;
//    private String email;
//    private boolean is_staff;
//    private boolean is_active;
//    private OffsetDateTime date_joined;
    private String introduction;
    private String nickname;
    private String image;
    private Integer mileage;
    private Integer exp;

    // 연결
    private List<SimpleUserDto> followers;  // Curator to_user
    private List<SimpleUserDto> following;  // Curator from_user
//    private List<SimpleArticleDto> articles;
//    private List<SimpleCommentDto> comments;
//    private List<Likes> likes;

    // 추가
    private Integer articles_count;
    private Integer comments_count;


    public UserProfileDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.introduction = user.getIntroduction();
        this.nickname = user.getNickname();
        if (user.getImage().equals("")) {
            this.image = null;
        } else {
            this.image = user.getImage();
        }
        this.mileage = user.getMileage();
        this.exp = user.getExp();

        // 연결
        this.followers = user.getTo_user().stream().map(curator -> new SimpleUserDto(curator.getFromUser())).collect(toList());
        this.following = user.getFrom_user().stream().map(curator -> new SimpleUserDto(curator.getToUser())).collect(toList());

//        this.articles = user.getArticles().stream().map(article -> new SimpleArticleDto(article)).collect(Collectors.toList());
//        this.comments = user.getComments().stream().map(comment -> new SimpleCommentDto(comment)).collect(Collectors.toList());
//        this.likes = likes;

        // 추가
        this.articles_count = user.getArticles().size();
        this.comments_count = user.getComments().size();
    }
}
