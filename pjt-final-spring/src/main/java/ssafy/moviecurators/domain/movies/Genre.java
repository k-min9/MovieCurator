package ssafy.moviecurators.domain.movies;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class Genre {

    @Id @GeneratedValue
    private Integer id;

    @Size(max=50)
    private String name;

//    @ManyToMany(mappedBy = "genre_ids")
//    private List<Movie> movies = new ArrayList<>();

}
