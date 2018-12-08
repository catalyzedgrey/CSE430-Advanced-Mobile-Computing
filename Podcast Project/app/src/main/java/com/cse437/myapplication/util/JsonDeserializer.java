//package com.cse437.myapplication;
//
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParseException;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//public class JsonDeserializer implements com.google.gson.JsonDeserializer <List<Podcasts>> {
//    ArrayList<Podcasts> allPodcasts;
//
//    @Override
//    public List<Podcasts> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        allPodcasts = new ArrayList<Podcasts>();
//
//        JsonObject podcastObject = json.getAsJsonObject();
//
//        return allPodcasts;
//    }
//}
