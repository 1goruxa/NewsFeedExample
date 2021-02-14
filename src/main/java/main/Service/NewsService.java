package main.Service;

import com.google.gson.Gson;

import main.Api.Request.NewsRequest;
import main.Api.Request.UpdateNewsRequest;
import main.Api.Response.NewsListResponse;
import main.Api.Response.NewsResponse;
import main.Model.NewsGroup;
import main.Model.NewsItem;
import main.Repository.GroupRepository;
import main.Repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private GroupRepository groupRepository;


    //Обновление новости
    public NewsResponse update(UpdateNewsRequest updateNews){
        NewsResponse newsResponse = new NewsResponse();

        if(updateNews.getName().equals("") || updateNews.getText().equals("")){
            newsResponse.setResult(false);
            newsResponse.setError("Не все поля заполнены, операция не выполнена");
        }else{
            newsResponse.setResult(true);
        }

        if (newsResponse.isResult()) {
            NewsGroup newsGroup = groupRepository.getOne(updateNews.getGroup());
            NewsItem newsItem = new NewsItem();
            newsItem.setId(updateNews.getId());
            newsItem.setGroup(newsGroup);
            newsItem.setName(updateNews.getName());
            newsItem.setText(updateNews.getText());

            newsItem.setDate(new Date());
            newsRepository.save(newsItem);
        }
        return newsResponse;
    }

    //Получение новости на редактирование
    public NewsItem getSingleNews(int newsId){
        Optional<NewsItem> newsItemOptional = newsRepository.findById(newsId);
        NewsItem newsItem = new NewsItem();
        if (newsItemOptional.isPresent()) {
            newsItem = newsItemOptional.get();
        }
        return newsItem;
    }

    //Удаление новости
    public NewsResponse delete(String newsId){
        NewsResponse newsResponse = new NewsResponse();
        System.out.println(newsId);
        Gson g = new Gson();
        NewsItem newsItem = g.fromJson(newsId, NewsItem.class);

        newsRepository.deleteById(newsItem.getId());
        newsResponse.setResult(true);

        return newsResponse;
    }

    //Сохранение новости
    public NewsResponse save(NewsRequest newsRequest){
        NewsResponse newsResponse = new NewsResponse();
        if(newsRequest.getName().equals("") || newsRequest.getText().equals("")){
            newsResponse.setResult(false);
            newsResponse.setError("Не все поля заполнены, операция не выполнена");
        }else{
            newsResponse.setResult(true);
        }

        if (newsResponse.isResult()){
            NewsItem newsItem = new NewsItem();
            NewsGroup newsGroup;
            newsItem.setDate(new Date());
            newsItem.setName(newsRequest.getName());
            newsItem.setText(newsRequest.getText());
            Optional<NewsGroup> optionalNewsGroup = groupRepository.findById(newsRequest.getGroup());
            newsGroup = optionalNewsGroup.get();
            newsItem.setGroup(newsGroup);
            newsRepository.save(newsItem);
        }
        return newsResponse;
    }

    //Получение списка новостей в соответсвии с фильтром (группа, строка поиска). По умолчанию - Все
    public ArrayList<NewsListResponse> filteredNewsList(String filter){
        ArrayList<NewsItem> itemArrayList;
        ArrayList<NewsListResponse> newsListResponses = new ArrayList<>();
        System.out.println(filter);

        if (filter.equals("all")){
            itemArrayList = newsRepository.findAll();
        } else{
            //Сначала проверим не пришла ли к нам группа, если что выведем сразу по ней
            Optional<NewsGroup> optionalNewsGroup = groupRepository.findOneByName(filter);
            if (optionalNewsGroup.isPresent()){
                NewsGroup newsGroup = optionalNewsGroup.get();
                itemArrayList = newsRepository.filterByGroup(newsGroup.getId());
            }else {
                //Если группы нет, то будем искать во всех заголовках и во всех текстах
                itemArrayList = newsRepository.filterByText(filter);
                System.out.println(itemArrayList.size());
            }
        }

        itemArrayList.forEach(i ->{
           newsListResponses.add(mapNewsItemToNewsListResponse(i));

        });

        return newsListResponses;
    }

    //Маппинг Новости в формат необходимый по API
    private NewsListResponse mapNewsItemToNewsListResponse(NewsItem newsItem){
        NewsListResponse newsListResponse = new NewsListResponse();
        newsListResponse.setId(newsItem.getId());
        newsListResponse.setName(newsItem.getName());
        newsListResponse.setText(newsItem.getText());

        newsListResponse.setGroup(newsItem.getGroup().getName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        newsListResponse.setDate(simpleDateFormat.format(newsItem.getDate()));

        return newsListResponse;
    }
}


