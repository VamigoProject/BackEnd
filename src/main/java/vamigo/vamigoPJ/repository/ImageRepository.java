package vamigo.vamigoPJ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vamigo.vamigoPJ.entity.Image;
import vamigo.vamigoPJ.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    //@Query(value = "SELECT u FROM Image u WHERE u.name LIKE %name%")
    Optional<Image> findById(Long id);
}
