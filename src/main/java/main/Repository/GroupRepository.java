package main.Repository;

import main.Model.NewsGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<NewsGroup, Integer> {

    ArrayList<NewsGroup> findAll();

    Optional<NewsGroup> findOneByName(String filter);
}
