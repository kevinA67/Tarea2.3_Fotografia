package Models;

import android.graphics.Bitmap;

import java.sql.Blob;

public class Images {

    private Integer id;
    private String description;
    private Bitmap image;

    public Images(){
    }

    public Images(Integer id, String description, Bitmap firma) {
        this.id = id;
        this.description = description;
        this.image = firma;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
