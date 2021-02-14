package main.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity(name="news")
public class NewsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String text;

    @Column(name = "pub_date")
    private Date date;

    @JsonIgnore
    @ManyToOne(targetEntity=NewsGroup.class,optional=false)
    @JoinColumn(name="news_group", referencedColumnName = "id")
    private NewsGroup group;

    @Override
    public String toString() {
        return "NewsItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", group=" + group +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public NewsGroup getGroup() {
        return group;
    }

    public void setGroup(NewsGroup group) {
        this.group = group;
    }
}
