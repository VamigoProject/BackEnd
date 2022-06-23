package vamigo.vamigoPJ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vamigo.vamigoPJ.entity.Reply;

import java.util.List;
@Repository
public interface ReplyRepository extends JpaRepository<Reply,Long> {
    List<Reply> findByUser_uid(Long id);
    List<Reply> findByReview_Id(Long id);
    void deleteByReview_Id(Long id);
    void deleteByUser_Uid(Long uid);
}
