package media_sci.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.models.Category;
import media_sci.com.shaklak_aklak.R;


/**
 * Created by Bassem on 12/9/2015.
 */
public class CategoryAdapter extends ArrayAdapter<Category> {

    Context context;
    ArrayList<Category> lst_category;

    public CategoryAdapter(Context context, int layout, ArrayList<Category> lst_category) {
        super(context, layout, lst_category);
        this.context = context;
        this.lst_category = lst_category;
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
        tv_catName.setText(lst_category.get(position).getName_en());

        return convertView;

    }
}
