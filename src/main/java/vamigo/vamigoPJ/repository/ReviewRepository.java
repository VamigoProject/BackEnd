package vamigo.vamigoPJ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vamigo.vamigoPJ.DTO.rankingInterface;
import vamigo.vamigoPJ.entity.Review;

import java.util.List;
import java.util.Optional;

//@Query(value = "SELECT r.category, r.work FROM Review r WHERE create_date > week GROUP BY r.work ORDER BY COUNT(r.work) DESC")
@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByUser_uid(Long id);

    @Query(value = "select category, work from review where create_date > date_add(now(),interval -1 week) group by work order by count(work) desc limit 10",nativeQuery = true)
    List<rankingInterface> reviewRanking();
    List<Review> findByWorkid(Integer workid);
    @Query(value = "select avg(rating) from review where workid = ?1 order by avg(rating);",nativeQuery = true)
    Float ratingAvg(Integer workid);

    Optional<Review> findById(Long id);

    void deleteByUser_Uid(Long uid);
    List<Review> findByCategoryAndUser_uid(String category,Long uid);

    List<Review> findByWorkAndCategory(String work, String category);
}
