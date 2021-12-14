package ssafy.moviecurators.dto;

import lombok.Data;
import ssafy.moviecurators.domain.accounts.Curator;
import ssafy.moviecurators.dto.simple.SimpleUserDto;

@Data
public class CuratorDto {

    private Long id;
//    private SimpleUserDto from_user;
    private SimpleUserDto to_user;
//    private Integer score;


    public CuratorDto(Curator curator) {
        this.id = curator.getId();
        this.to_user = new SimpleUserDto(curator.getToUser());
    }
}
