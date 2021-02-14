package main.Repository;

import main.Model.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface NewsRepository extends JpaRepository<NewsItem, Integer> {

    ArrayList<NewsItem> findAll();

    @Query(value="SELECT * FROM news WHERE news_group = :groupId", nativeQuery = true)
    ArrayList<NewsItem> filterByGroup(int groupId);

    @Query(value="SELECT * FROM news WHERE name LIKE %:filter% OR text LIKE %:filter%", nativeQuery = true)
    ArrayList<NewsItem> filterByText(String filter);

}
