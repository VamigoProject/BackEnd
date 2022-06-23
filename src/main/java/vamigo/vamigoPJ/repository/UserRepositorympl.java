package vamigo.vamigoPJ.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vamigo.vamigoPJ.entity.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositorympl{
    private EntityManager em;

    private void save(User user){
        em.persist(user);
    }







}
