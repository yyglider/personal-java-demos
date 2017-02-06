package java8.samples.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yaoyuan on 2017/1/4.
 */
public class Article {

    private final String title;
    private final String author;
    private final List<String> tags;

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", tags=" + tags +
                '}';
    }

    private Article(String title, String author, List<String> tags) {
        this.title = title;
        this.author = author;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getTags() {
        return tags;
    }

    public static Optional<Article> getFirstJavaArticle(List<Article> articles) {
        return articles.stream()
                .filter(article -> article.getTags().contains("Java"))
                .findFirst();
    }
    public static List<Article> getAllJavaArticles(List<Article> articles) {
        return articles.stream()
                .filter(article -> article.getTags().contains("Java"))
                .collect(Collectors.toList());
    }

    public static Map<String, List<Article>> groupByAuthor(List<Article> articles) {
        return articles.stream()
                .collect(Collectors.groupingBy(Article::getAuthor));
    }

    public static Set<String> getDistinctTags(List<Article> articles) {
        return articles.stream()
                .flatMap(article -> article.getTags().stream())
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        //String title, String author, List<String> tags
        String[] tags = new String[]{"Java","IT","Program"};
        List<String> A_tags = new ArrayList<>();
        for(String tag:tags){
            A_tags.add(tag);
        }
        Article article1 = new Article("title1","liming1",A_tags);
        Article article2 = new Article("title2","liming1",A_tags);
        Article article3 = new Article("title3","liming3",A_tags);
        Article article4 = new Article("title4","liming4",A_tags);
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        articles.add(article3);
        articles.add(article4);



        System.out.println(Article.getFirstJavaArticle(articles));
        System.out.println(Article.getAllJavaArticles(articles));
        System.out.println(Article.getDistinctTags(articles));
        System.out.println(Article.groupByAuthor(articles));
    }
}