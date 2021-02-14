package main.Controller;

import main.Api.Request.NewsRequest;
import main.Api.Request.UpdateNewsRequest;
import main.Api.Response.NewsListResponse;
import main.Api.Response.NewsResponse;
import main.Model.NewsGroup;
import main.Model.NewsItem;
import main.Service.NewsGroupService;
import main.Service.NewsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class NewsController {
    private NewsGroupService newsGroupService;
    private NewsService newsService;

    public NewsController(NewsGroupService newsGroupService, NewsService newsService) {
        this.newsGroupService = newsGroupService;
        this.newsService = newsService;
    }

    @RequestMapping("/groups")
    public ArrayList<NewsGroup> groups(Model model){

        return newsGroupService.getGroupList();
    }

    @PostMapping("/addnews")
    public NewsResponse save(@RequestBody NewsRequest newsRequest){
        return newsService.save(newsRequest);
    }

    @GetMapping("/news")
    public ArrayList<NewsListResponse> filter(@RequestParam String filter){

        return newsService.filteredNewsList(filter);
    }

    @GetMapping("/getsinglenews")
    public NewsItem getSingleNews(@RequestParam Integer newsId){
        return newsService.getSingleNews(newsId);
    }


    @PostMapping("/updatenews")
    public NewsResponse update(@RequestBody UpdateNewsRequest updatedNews){
        return newsService.update(updatedNews);
    }

    @PostMapping("/deletenews")
    public NewsResponse save(@RequestBody String newsId){
        return newsService.delete(newsId);
    }



}
