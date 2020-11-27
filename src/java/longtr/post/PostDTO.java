/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.post;

import java.io.Serializable;
import java.util.List;
import longtr.article.ArticleDTO;

/**
 *
 * @author Admin
 */
public class PostDTO implements Serializable {

    private ArticleDTO articleDto;
    private List<String> imageDto;
    private String name;
    private int numOfLike;
    private int numOfDislike;

    public PostDTO(ArticleDTO articleDto, List<String> imageDto, String name) {
        this.articleDto = articleDto;
        this.imageDto = imageDto;
        this.name = name;
    }

    public PostDTO(ArticleDTO articleDto, List<String> imageDto, String name, int numOfLike, int nunOfDislike) {
        this.articleDto = articleDto;
        this.imageDto = imageDto;
        this.name = name;
        this.numOfLike = numOfLike;
        this.numOfDislike = nunOfDislike;
    }
    
    public int getNumOfLike() {
        return numOfLike;
    }

    public void setNumOfLike(int numOfLike) {
        this.numOfLike = numOfLike;
    }

    public int getNumOfDislike() {
        return numOfDislike;
    }

    public void setNumOfDislike(int numOfDislike) {
        this.numOfDislike = numOfDislike;
    }
    

    public PostDTO() {
    }

    public ArticleDTO getArticleDto() {
        return articleDto;
    }

    public void setArticleDto(ArticleDTO articleDto) {
        this.articleDto = articleDto;
    }

    public List<String> getImageDto() {
        return imageDto;
    }

    public void setImageDto(List<String> imageDto) {
        this.imageDto = imageDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
