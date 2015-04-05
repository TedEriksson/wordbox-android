package uk.co.vism.wordbox.network;

import com.google.gson.JsonObject;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Rest(rootUrl = "https://wordbox.herokuapp.com", converters = {GsonHttpMessageConverter.class})
public interface RestClient {
    @Get("/users/{userId}")
    JsonObject getUser(int userId);
}