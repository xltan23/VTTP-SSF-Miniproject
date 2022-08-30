package sg.edu.nus.iss.MiniProject1.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Workout {
    
    // Defining Workout
    private String name;
    private Integer duration;
    private Integer repetition;
    private Integer sets;
    private Integer intensity;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public Integer getRepetition() {
        return repetition;
    }
    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }
    public Integer getSets() {
        return sets;
    }
    public void setSets(Integer sets) {
        this.sets = sets;
    }
    public Integer getIntensity() {
        return intensity;
    }
    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    // Creating workout from JsonObject
    public static Workout createW(JsonObject jo) {
        Workout workout = new Workout();
        workout.setName(jo.getString("name"));
        workout.setDuration(jo.getInt("duration"));
        workout.setRepetition(jo.getInt("repetition"));
        workout.setSets(jo.getInt("sets"));
        workout.setIntensity(jo.getInt("intensity"));
        return workout;
    }

    // Creating workout from JsonString
    public static Workout createW(String jsonString) {
        StringReader sr = new StringReader(jsonString);
        JsonReader jr = Json.createReader(sr);
        return createW(jr.readObject());
    }

    // Creating JsonObject for workout
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("name", name)
                .add("duration", duration)
                .add("repetition", repetition)
                .add("sets", sets)
                .add("intensity", intensity)
                .build();
    }
}
