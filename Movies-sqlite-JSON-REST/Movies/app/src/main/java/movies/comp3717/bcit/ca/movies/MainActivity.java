package movies.comp3717.bcit.ca.movies;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import movies.comp3717.bcit.ca.movies.DataContract.DataEntry;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends ListActivity
{
    private SimpleCursorAdapter titlesAdapater;
    private DataDbHelper dataDBHelper;
    private final OkHttpClient client = new OkHttpClient();
    TextToSpeech t1;


    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        final Resources      res;
        final String[]       titles;
        final SQLiteDatabase db;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataDBHelper   = new DataDbHelper(this);
        res            = getResources();
        db             = dataDBHelper.getWritableDatabase();

        new RetrieveDataTask(db).execute("");

        titlesAdapater = new SimpleCursorAdapter(this,
                                                 android.R.layout.simple_list_item_1,
                                                 getAllTitles(db),
                                                 new String[] { DataEntry.COLUMN_NAME_VALUE },
                                                 new int[] { android.R.id.text1 });
        setListAdapter(titlesAdapater);


    }

    @Override
    public void onListItemClick(final ListView list,
                                final View     view,
                                final int      position,
                                final long     id)
    {

        c.moveToPosition(position);
        String s = c.getString(c.getColumnIndex(DBAdapter.KEY_ITEM));
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    t1.setLanguage(Locale.CANADA);
                    t1.speak(s, TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Log.e("tts", "status");
                }
            }
        });
    }

    public Cursor getAllTitles(final SQLiteDatabase db)
    {
        Cursor cursor;

        cursor = db.query(DataEntry.TABLE_NAME,
                          new String[]
                          {
                              DataEntry._ID,
                              DataEntry.COLUMN_NAME_VALUE
                          },
                          null,
                          null,
                          null,
                          null,
                          null);

        Log.d("X", Integer.toString(cursor.getCount()));

        return cursor;
    }

    class RetrieveDataTask
        extends AsyncTask<String, Void, String>
    {
        private Exception exception;
        private SQLiteDatabase database;

        RetrieveDataTask(final SQLiteDatabase db)
        {
            database = db;
        }

        protected String doInBackground(final String... urls)
        {
            try
            {
                Request request = new Request.Builder()
                        .url("http://api.fixer.io/latest?symbols=USD,CAD")
                        .build();

                Response response = client.newCall(request).execute();

                return response.body().string();
            }
            catch(final IOException e)
            {
                this.exception = e;
                Log.d("error", e.toString());
                return (null);
            }
        }

        protected void onPostExecute(final String data)
        {


            Log.d("data", data);
            try
            {
                JSONObject object = new JSONObject(data);

                final long       newRowId;
                final String     title;
                final ContentValues values = new ContentValues();
                    title  = object.getString("rates");
                    Log.d("rates", title);
                    values.put(DataEntry.COLUMN_NAME_VALUE, title);
                    newRowId = database.insert(
                            DataEntry.TABLE_NAME,
                            null,
                            values);
                    Log.d("row id = ", Long.toString(newRowId));
            }
            catch(final JSONException ex) {
                this.exception = ex;
                Log.d("has error", ex.toString());
            }
        }
    }
}
