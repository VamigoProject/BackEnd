package vamigo.vamigoPJ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vamigo.vamigoPJ.entity.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByMail(String mail);
    List<User> findAll();

    Optional<User> findByUid(long uid);

    @Query(value = "SELECT u FROM User u WHERE u.nickname LIKE %:nickname%")
    List<User> findByNicknameContain(@Param("nickname") String nickname);

    List<User> findByNicknameContaining(String nickname);
    Optional<User> findByNickname(String nickname);




}
