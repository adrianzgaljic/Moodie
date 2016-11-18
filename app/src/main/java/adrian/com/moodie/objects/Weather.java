package adrian.com.moodie.objects;

/**
 * Created by adrianzgaljic on 13/11/16.
 */

public class Weather {

    private int descriptionCode;
    private int temperature;

    public Weather(int descriptionCode, int temperature) {
        this.descriptionCode = descriptionCode;
        this.temperature = temperature;
    }

    public int getDescriptionCode() {
        return descriptionCode;
    }


    @Override
    public String toString() {
        return "Weather{" +
                "descriptionCode=" + descriptionCode +
                ", temperature=" + temperature +
                '}';
    }

}
