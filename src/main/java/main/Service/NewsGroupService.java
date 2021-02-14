package main.Service;

import main.Model.NewsGroup;
import main.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NewsGroupService {
    @Autowired
    private GroupRepository groupRepository;

    public ArrayList<NewsGroup> getGroupList(){

        return groupRepository.findAll();
    }
}
