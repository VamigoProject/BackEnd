package vamigo.vamigoPJ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vamigo.vamigoPJ.entity.Follow;

import vamigo.vamigoPJ.entity.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findByMyUid(User user);

    List<Follow> findByTargetUid(User user);

    void deleteByMyUid(User user);
    void deleteByTargetUid(User user);




}
