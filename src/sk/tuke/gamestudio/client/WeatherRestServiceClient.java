package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.weather.Weather;
import sk.tuke.gamestudio.entity.weather.WeatherMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class WeatherRestServiceClient {


    private static final String URL="http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String API_KEY= "&units=metric&appid=ca506e986674248695e0e3bd290af347";

    public WeatherMap getWeather(String city) {
        try {
            Client client = ClientBuilder.newClient();
            return client.target(URL+city+API_KEY)

                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<WeatherMap>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Error loading weather", e);
        }
    }

    public void printWeather(WeatherMap weatherMap){
        System.out.println("Current weather in "+weatherMap.getName()+": ");
        for(Weather w:weatherMap.getWeather())
            System.out.println(w.getDescription());
        System.out.println(weatherMap.getMain().getTemp()+" degrees");

    }


}
