package comp3717.bcit.ca.exchangerate;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import comp3717.bcit.ca.exchangerate.DataContract.DataEntry;

import java.io.IOException;

public class MainActivity extends ListActivity
{
    private SimpleCursorAdapter titlesAdapater;
    private DataDbHelper dataDBHelper;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        final Resources res;
        final String[]       titles;
        final SQLiteDatabase db;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataDBHelper   = new DataDbHelper(this);
        res            = getResources();
        db             = dataDBHelper.getWritableDatabase();

        titles         = res.getStringArray(R.array.movie_titles);
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
                                final View view,
                                final int      position,
                                final long     id)
    {
        final String title;

//        title = titlesAdapater.getItem(position);
        Toast.makeText(this, Integer.toString(position), Toast.LENGTH_SHORT).show();
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
                        .url("https://api.mongolab.com/api/1/databases/comp3717/collections/test?apiKey=7NxSEeut--6JUnTVlcldQ-ZqUo9oM9-6")
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
            final JSONArray array;

            Log.d("data", data);

            try
            {
                array = new JSONArray(data);

                for(int i = 0; i < array.length(); i++)
                {
                    final long       newRowId;
                    final JSONObject object;
                    final String     title;
                    final ContentValues values = new ContentValues();

                    object = array.getJSONObject(i);
                    title  = object.getString("name");
                    Log.d("title", title);
                    values.put(DataEntry.COLUMN_NAME_VALUE, title);
                    newRowId = database.insert(
                            DataEntry.TABLE_NAME,
                            null,
                            values);
                    Log.d("row id = ", Long.toString(newRowId));
                }

                titlesAdapater.getCursor().requery();
                titlesAdapater.notifyDataSetChanged();
            }
            catch(final JSONException ex)
            {
                this.exception = ex;
                Log.d("error", ex.toString());
            }
        }
    }
}
