package vamigo.vamigoPJ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vamigo.vamigoPJ.entity.Likes;
import vamigo.vamigoPJ.entity.Review;
import vamigo.vamigoPJ.entity.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface LikesRepository extends JpaRepository<Likes,Long> {
    List<Likes> findByUser_uid(Long id);
    List<Likes> findByReview_Id(Long id);
    void deleteByReview_Id(Long id);
    void deleteByUser_uidAndReview_id(Long uid, Long reviewUid);
    void deleteByUser_Uid(Long uid);
    Optional<Likes> findByUser_uidAndReview_id(Long uid, Long reviewId);
}
