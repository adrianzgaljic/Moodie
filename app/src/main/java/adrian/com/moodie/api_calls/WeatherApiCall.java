package adrian.com.moodie.api_calls;

import org.json.JSONObject;

import adrian.com.moodie.listeners.ApiCallListener;
import adrian.com.moodie.listeners.HttpResponseListener;
import adrian.com.moodie.objects.Weather;

public class WeatherApiCall implements ApiCallListener {

    private String url;
    private HttpResponseListener httpResponseListener;

    public WeatherApiCall(String url, HttpResponseListener httpResponseListener){
        this.url = url;
        this.httpResponseListener = httpResponseListener;
    }



    public void execute(){
        ApiCall apiCall = new ApiCall(url,this);
        apiCall.execute();
    }


    @Override
    public void jsonObjectReady(JSONObject jsonObject) {
        try{
            JSONObject jsonWeather = jsonObject.getJSONObject("query").
                    getJSONObject("results").
                    getJSONObject("channel").getJSONObject("item").
                    getJSONObject("condition");

            int code = Integer.parseInt(jsonWeather.get("code").toString());
            int temp = Integer.parseInt(jsonWeather.get("temp").toString());

            httpResponseListener.onWeatherResult(new Weather(code,temp));
        } catch (Exception e){

        }

    }
}