package comp3717.bcit.ca.volley;

/**
 * Created by Lydia on 2016-04-05.
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {
    public static String[] userId;
    public static String[] id;
    public static String[] title;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_UID = "userId";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";

    private JSONArray users = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            JSONArray albums = new JSONArray(json);

            userId = new String[albums.length()];
            id = new String[albums.length()];
            title = new String[albums.length()];


            for(int i=0;i<albums.length();i++) {

                JSONObject jo = albums.getJSONObject(i);
                userId[i] = jo.getString(KEY_UID);
                id[i] = jo.getString(KEY_ID);
                title[i] = jo.getString(KEY_TITLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
