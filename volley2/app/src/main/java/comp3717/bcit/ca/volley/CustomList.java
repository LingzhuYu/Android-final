package comp3717.bcit.ca.volley;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Lydia on 2016-04-05.
 */
public class CustomList extends ArrayAdapter<String> {
    private String[] userId;
    private String[] id;
    private String[] title;
    private Activity context;

    public CustomList(Activity context, String[] userId, String[] id, String[] title) {
        super(context, R.layout.list_view_layout, userId);
        this.context = context;
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);
        TextView textViewuserId = (TextView) listViewItem.findViewById(R.id.textViewuserId);
        TextView textViewid = (TextView) listViewItem.findViewById(R.id.textViewid);
        TextView textViewtitle = (TextView) listViewItem.findViewById(R.id.textViewtitle);

        textViewuserId.setText(userId[position]);
        textViewid.setText(id[position]);
        textViewtitle.setText(title[position]);

        return listViewItem;
    }
}