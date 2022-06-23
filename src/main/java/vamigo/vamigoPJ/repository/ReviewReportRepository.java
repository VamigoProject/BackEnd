package vamigo.vamigoPJ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vamigo.vamigoPJ.entity.ReviewReport;

import java.util.List;
import java.util.Optional;

public interface ReviewReportRepository extends JpaRepository<ReviewReport,Long> {
    List<ReviewReport> findByReview_Id(Long id);
    List<ReviewReport> findByUser_Uid(Long id);
    void deleteByReview_Id(Long id);
    void deleteByUser_Uid(Long id);
    Optional<ReviewReport> findByUser_uidAndReview_Id(Long uid, Long id);
}
