package media_sci.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.models.Ingredients;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/27/2015.
 */
public class SortAdapter extends ArrayAdapter<Ingredients> {

    private Context context;
    private ArrayList<Ingredients> lst_ingredients;
    private int type;

    public SortAdapter(Context context, int layout,
                       ArrayList<Ingredients> lst_ingredients, int type) {
        super(context, layout, lst_ingredients);
        this.context = context;
        this.lst_ingredients = lst_ingredients;
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_sort, parent, false);
        }

        TextView tv_itemName = (TextView) convertView.findViewById(R.id.tv_sort_itemName);
        TextView tv_itemValue = (TextView) convertView.findViewById(R.id.tv_sort_itemValue);

        tv_itemName.setText(lst_ingredients.get(position).getItem_name_en());
        tv_itemName.setTypeface(Utility.GetFont(context));
        double itemValue = 0;
        String itemUnit = "";

        if (type == 1) {
            itemValue = lst_ingredients.get(position).getEnergy();
            itemUnit = StaticVarClass.kcal;
        } else if (type == 2) {
            itemValue = lst_ingredients.get(position).getFat();
            itemUnit = StaticVarClass.gram;
        } else if (type == 3) {
            itemValue = lst_ingredients.get(position).getCholest();
            itemUnit = StaticVarClass.milli_gram;
        } else if (type == 4) {
            itemValue = lst_ingredients.get(position).getSodium();
            itemUnit = StaticVarClass.milli_gram;
        } else if (type == 5) {
            itemValue = lst_ingredients.get(position).getProtein();
            itemUnit = StaticVarClass.gram;
        } else if (type == 6) {
            itemValue = lst_ingredients.get(position).getCarbo_tot();
            itemUnit = StaticVarClass.gram;
        }
        // tv_itemValue.setText(itemValue);
        tv_itemValue.setText(Utility.GetDecimalFormat(itemValue)
                + " " + itemUnit);

        return convertView;
    }
}
