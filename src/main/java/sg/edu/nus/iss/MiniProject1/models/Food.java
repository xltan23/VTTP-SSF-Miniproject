package sg.edu.nus.iss.MiniProject1.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Food {

    private Integer id;
    private String title;
    private String image;
    private String imageType;
    private Integer calories;
    private String protein;
    private String fat;
    private String carbs;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImageType() {
        return imageType;
    }
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
    public Integer getCalories() {
        return calories;
    }
    public void setCalories(Integer calories) {
        this.calories = calories;
    }
    public String getProtein() {
        return protein;
    }
    public void setProtein(String protein) {
        this.protein = protein;
    }
    public String getFat() {
        return fat;
    }
    public void setFat(String fat) {
        this.fat = fat;
    }
    public String getCarbs() {
        return carbs;
    }
    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public static Food create(JsonObject jo) {
        Food food = new Food();
        food.setId(jo.getInt("id"));
        food.setTitle(jo.getString("title"));
        food.setImage(jo.getString("image"));
        food.setImageType(jo.getString("imageType"));
        food.setCalories(jo.getInt("calories"));
        food.setProtein(jo.getString("protein"));
        food.setFat(jo.getString("fat"));
        food.setCarbs(jo.getString("carbs"));
        return food;
    }

    public static Food create(String jsonString) {
        StringReader sr = new StringReader(jsonString);
        JsonReader jr = Json.createReader(sr);
        return create(jr.readObject());
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("title", title)
                .add("image", image)
                .add("imageType", imageType)
                .add("calories", calories)
                .add("protein", protein)
                .add("fat", fat)
                .add("carbs", carbs)
                .build();
    }
}