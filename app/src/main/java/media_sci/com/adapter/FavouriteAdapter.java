package media_sci.com.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.models.Ingredients;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/17/2015.
 */
public class FavouriteAdapter extends ArrayAdapter<Ingredients> {
    ArrayList<Ingredients> lst_items;
    Context context;

    public FavouriteAdapter(Context context, int layout, ArrayList<Ingredients> lst_items) {
        super(context, layout, lst_items);
        this.lst_items = lst_items;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_favourite, parent, false);
        }
        TextView tv_itemName = (TextView) convertView.findViewById(R.id.tv_favouriteName);
        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_favouriteDate);

        tv_itemName.setText(lst_items.get(position).getItem_name_en());

        Typeface typeface = Utility.GetFont(context);
        tv_itemName.setTypeface(typeface);
        tv_date.setTypeface(typeface);

        // if date today write hours
        // if date yesterday write yesterday
        // if date before this write full date
        return convertView;
    }
}
