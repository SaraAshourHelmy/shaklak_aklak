package media_sci.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.models.Ingredients;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/14/2015.
 */
public class ItemAdapter extends ArrayAdapter<Ingredients> {

    Context context;
    ArrayList<Ingredients> lst_items;

    public ItemAdapter(Context context, int layout, ArrayList<Ingredients> lst_items) {
        super(context, layout, lst_items);
        this.context = context;
        this.lst_items = lst_items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_item, parent, false);
        }

        TextView tv_itemName = (TextView) convertView.findViewById(R.id.tv_item_name);
        ImageView img_arrow = (ImageView) convertView.findViewById(R.id.img_item_arrow);

        tv_itemName.setText(lst_items.get(position).getItem_name_en());
        tv_itemName.setTypeface(Utility.GetFont(context));
        return convertView;

    }
}
