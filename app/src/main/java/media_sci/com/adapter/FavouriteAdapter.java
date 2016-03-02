package media_sci.com.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.models.UserData;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/17/2015.
 */
public class FavouriteAdapter extends ArrayAdapter<String> {
    ArrayList<String> lst_items;
    Context context;
    int type;

    public FavouriteAdapter(Context context, int layout, ArrayList<String> lst_items
            , int type) {
        super(context, layout, lst_items);
        this.lst_items = lst_items;
        this.context = context;
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_favourite, parent, false);
        }
        TextView tv_itemName = (TextView) convertView.findViewById(R.id.tv_favouriteName);
        ImageView img_delete = (ImageView) convertView.findViewById(R.id.img_delete_fav);
        //TextView tv_date = (TextView) convertView.findViewById(R.id.tv_favouriteDate);

        if (type == StaticVarClass.Delete_Mode)
            img_delete.setVisibility(View.VISIBLE);

        else if (type == StaticVarClass.No_Delete_Mode)
            img_delete.setVisibility(View.GONE);

        UserData userData = new UserData(context);
        Utility.TextDirection(context, tv_itemName, StaticVarClass.TextView_Type);


        tv_itemName.setText(lst_items.get(position));


        Typeface typeface = Utility.GetFont(context);
        tv_itemName.setTypeface(typeface);
        //tv_date.setTypeface(typeface);

        // if date today write hours
        // if date yesterday write yesterday
        // if date before this write full date
        return convertView;
    }
}
