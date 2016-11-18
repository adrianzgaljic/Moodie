package adrian.com.moodie.listeners;

import org.json.JSONObject;

/**
 * Created by adrianzgaljic on 13/11/16.
 */

public interface ApiCallListener {

    void jsonObjectReady(JSONObject jsonObject);
}
