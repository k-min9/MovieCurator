package ssafy.moviecurators.dto;

import lombok.Data;
import ssafy.moviecurators.domain.accounts.Curator;
import ssafy.moviecurators.dto.simple.SimpleUserDto;

/**
 * 큐레이터(유저) 관련 DTO
 */
@Data
public class CuratorDto {

    private Long id;
    private SimpleUserDto to_user;

    public CuratorDto(Curator curator) {
        this.id = curator.getId();
        this.to_user = new SimpleUserDto(curator.getToUser());
    }
}
