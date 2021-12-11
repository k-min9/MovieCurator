package ssafy.moviecurators.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ssafy.moviecurators.domain.accounts.Curator;
import ssafy.moviecurators.domain.accounts.User;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.domain.movies.Likes;
import ssafy.moviecurators.dto.simple.SimpleUserDto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Curator> followers;  // Curator to_user
    private List<Curator> following;  // Curator from_user
//    private List<Article> articles;
//    private List<Comment> comments;
//    private List<Likes> likes;

    // 추가
    private Integer comments_count;
    private Integer articles_count;

    public UserProfileDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.introduction = user.getIntroduction();
        this.nickname = user.getNickname();
        this.image = user.getImage();
        this.mileage = user.getMileage();
        this.exp = user.getExp();

        // 연결
        this.followers = user.getTo_user();//.stream().map(u -> new SimpleUserDto(u)).collect(toList());
        this.following = user.getFrom_user();

//        this.articles = articles;
//        this.comments = comments;
//        this.likes = likes;

        // 추가
        this.comments_count = 456;
        this.articles_count = 123;
    }
}
