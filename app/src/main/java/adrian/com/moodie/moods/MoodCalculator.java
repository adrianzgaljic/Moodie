package adrian.com.moodie.moods;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * Created by adrianzgaljic on 13/11/16.
 */

public class MoodCalculator {

    public String getMood(int weatherCode, Map<String, Integer> reactions){

        int happy = 0;
        int sad = 0;
        int inlove = 0;
        int excited = 0;

        if (reactions.containsKey("LIKE")){
            happy += reactions.get("LIKE");
        }
        if (reactions.containsKey("HAHA")){
            happy += reactions.get("HAHA");
        }
        if (reactions.containsKey("LOVE")){
            inlove += reactions.get("LOVE");
        }
        if (reactions.containsKey("WOW")){
            excited += reactions.get("WOW");
        }
        if (reactions.containsKey("SAD")){
            sad += reactions.get("SAD");
        }
        if (reactions.containsKey("ANGRY")){
            sad += reactions.get("ANGRY");
            excited += reactions.get("ANGRY");
        }


        if (weatherCode<13){
            happy--;
            sad++;
            inlove--;
        } else if ((weatherCode>12 && weatherCode<17)||(weatherCode>41 && weatherCode<44)){
            happy++;
            inlove++;
            excited += 2;
        } else if ((weatherCode>16 && weatherCode<31) || (weatherCode>36 && weatherCode<41) || weatherCode>43){
            happy--;
            sad++;
        } else if (weatherCode>30 && weatherCode<35){
            happy++;
            sad--;
            excited++;
        }

        if (happy>=sad && happy>=inlove && happy>=excited){
            return "HAPPY";
        } else if (sad>=happy && sad>=inlove && sad>=excited){
            return "SAD";
        } else if (inlove>=happy && inlove>=sad && inlove>=excited){
            return "INLOVE";
        } else {
            return "EXCITED";
        }


    }

}
