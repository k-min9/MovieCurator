package ssafy.moviecurators.dto.simple;

import lombok.Data;
import ssafy.moviecurators.domain.accounts.User;

import java.time.OffsetDateTime;

@Data
public class SimpleUserDto {

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

    public SimpleUserDto(User user) {
        this.id = user.getId();
//        this.password = user.getPassword();
//        this.last_login = user.getLast_login();
//        this.is_superuser = user.is_superuser();
        this.username = user.getUsername();
//        this.first_name = user.getFirst_name();
//        this.last_name = user.getLast_name();
//        this.email = user.getEmail();
//        this.is_staff = user.is_staff();
//        this.is_active = user.is_active();
//        this.date_joined = user.getDate_joined();
        this.introduction = user.getIntroduction();
        this.nickname = user.getNickname();
        this.image = user.getImage();
        this.mileage = user.getMileage();
        this.exp = user.getExp();
    }
}
