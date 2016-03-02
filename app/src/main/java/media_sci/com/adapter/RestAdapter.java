package media_sci.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.models.Restaurant;
import media_sci.com.models.UserData;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

public class RestAdapter extends ArrayAdapter<Restaurant> {

    Context context;
    private ArrayList<Restaurant> lst_rest;

    public RestAdapter(Context context, int layout, ArrayList<Restaurant> lst_rest) {
        super(context, layout, lst_rest);
        this.context = context;
        this.lst_rest = lst_rest;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_category, parent, false);
        }

        ImageView img_category = (ImageView) convertView.findViewById(R.id.img_category);
        TextView tv_catName = (TextView) convertView.findViewById(R.id.tv_category_name);
        ImageView img_arrow = (ImageView) convertView.findViewById(R.id.img_category_arrow);

        // CustomImageLoader.getInstance().loadImage(lst_category.get(position).getImg_url()
        //       , img_category, null);

        UserData userData = new UserData(context);

        if (userData.GetLanguage() == StaticVarClass.English)
            tv_catName.setText(lst_rest.get(position).getName_en());

        else if (userData.GetLanguage() == StaticVarClass.Arabic)
            tv_catName.setText(lst_rest.get(position).getName_ar());

      //  tv_catName.setText(lst_rest.get(position).getName_en());

        tv_catName.setTypeface(Utility.GetFont(context));
        return convertView;

    }
}
