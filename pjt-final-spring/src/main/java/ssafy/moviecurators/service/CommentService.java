package ssafy.moviecurators.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.moviecurators.domain.movies.Comment;
import ssafy.moviecurators.repository.CommentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)  // 기본적으로 트랜잭션 안에서만 데이터 변경하게 설정(그만큼 최적화 되어 읽는게 빨라짐)
@RequiredArgsConstructor  // 생성자 주입 처리
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> commentList(Long id) {
        return commentRepository.commentList(id);
    }
}
