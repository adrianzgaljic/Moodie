package adrian.com.moodie.api_calls;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import adrian.com.moodie.listeners.HttpResponseListener;

/**
 * Created by adrianzgaljic on 14/11/16.
 */

public class GraphApiCall {

    private Map<String , Integer> reactions = new HashMap<>();
    public int arrayLength;
    private int noOfCalls = 0;


    public void execute(final AccessToken accessToken, HttpResponseListener httpResponseListener) {
        getNameGraphRequest(accessToken, httpResponseListener);
    }

    private void getReactionsGraphRequest(String postID, AccessToken accessToken, final String userName, final HttpResponseListener httpResponseListener){
        new GraphRequest(
                accessToken,
                postID+"/reactions",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            noOfCalls++;
                            JSONObject jsonObject = response.getJSONObject();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.d("TAG", "graph array "+jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String name = object.get("name").toString();
                                if (name.equals(userName)){
                                    String reaction = object.get("type").toString();
                                    int no = reactions.containsKey(reaction) ? reactions.get(reaction) : 0;
                                    reactions.put(reaction,++no);
                                }

                            }
                            if (noOfCalls==arrayLength){
                                httpResponseListener.onFacebookReactionsResult(reactions);
                            }


                        } catch (Exception e){
                            Log.d("TAG", "error: "+e.toString());
                        }
                    }
                }
        ).executeAsync();
    }


    private void getNameGraphRequest(final AccessToken accessToken, final HttpResponseListener httpResponseListener){
        new GraphRequest(
                accessToken,
                "/me",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject();
                            String userName = jsonObject.get("name").toString();
                            getPostsGraphRequest(accessToken, userName, httpResponseListener);

                        } catch (Exception e){
                            Log.d("tag", "error: " + e.toString());
                        }
                    }
                }
        ).executeAsync();
    }

    private void getPostsGraphRequest(final AccessToken accessToken, final String userName, final HttpResponseListener httpResponseListener){
        new GraphRequest(
                accessToken,
                "/me/feed",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject();
                            Log.d("TAG", "graph obj "+jsonObject.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.d("TAG", "graph array "+jsonArray.toString());
                            arrayLength = jsonArray.length();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String postID = object.get("id").toString();
                                getReactionsGraphRequest(postID,accessToken, userName, httpResponseListener);
                            }


                        } catch (Exception e){
                            Log.d("tag", "error: " + e.toString());
                        }
                    }
                }
        ).executeAsync();
    }


}
