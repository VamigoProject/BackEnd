package vamigo.vamigoPJ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vamigo.vamigoPJ.entity.EmailCheck;


import java.util.List;
import java.util.Optional;
@Repository
public interface EmailCheckRepository extends JpaRepository<EmailCheck,Long> {
    Optional<EmailCheck> findByMail(String mail);
    List<EmailCheck> findAll();
}
