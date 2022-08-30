package sg.edu.nus.iss.MiniProject1.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class WorkoutSummary {
    
    // Defining WorkoutSummary 
    private String name;
    private String time;
    private Integer totalIntensity;
    private Integer caloriesBurnt;
    private Integer recProtein;
    private Integer recCarbs;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public Integer getTotalIntensity() {
        return totalIntensity;
    }
    public void setTotalIntensity(Integer totalIntensity) {
        this.totalIntensity = totalIntensity;
    }
    public Integer getCaloriesBurnt() {
        return caloriesBurnt;
    }
    public void setCaloriesBurnt(Integer caloriesBurnt) {
        this.caloriesBurnt = caloriesBurnt;
    }
    public Integer getRecProtein() {
        return recProtein;
    }
    public void setRecProtein(Integer recProtein) {
        this.recProtein = recProtein;
    }
    public Integer getRecCarbs() {
        return recCarbs;
    }
    public void setRecCarbs(Integer recCarbs) {
        this.recCarbs = recCarbs;
    }

    // Creating WorkoutSummary from JsonObject
    public static WorkoutSummary createWS(JsonObject jo) {
        WorkoutSummary ws = new WorkoutSummary();
        ws.setName(jo.getString("name"));
        ws.setTime(jo.getString("time"));
        ws.setTotalIntensity(jo.getInt("totalIntensity"));
        ws.setCaloriesBurnt(jo.getInt("caloriesBurnt"));
        ws.setRecProtein(jo.getInt("recProtein"));
        ws.setRecCarbs(jo.getInt("recCarbs"));
        return ws;
    }

    // Creating WorkoutSummary from JsonString
    public static WorkoutSummary createWS(String jsonString) {
        StringReader sr = new StringReader(jsonString);
        JsonReader jr = Json.createReader(sr);
        return createWS(jr.readObject());
    }
    
    // Creating JsonObject for WorkoutSummary
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("name", name)
                .add("time", time)
                .add("totalIntensity", totalIntensity)
                .add("caloriesBurnt", caloriesBurnt)
                .add("recProtein", recProtein)
                .add("recCarbs", recCarbs)
                .build();
    }
}
