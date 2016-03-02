package media_sci.com.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/23/2015.
 */
public class SimpleAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> lst_unitName;
    private int Type;

    public SimpleAdapter(Context context, int layout, ArrayList<String> lst_unitName,
                         int Type) {
        super(context, layout, lst_unitName);
        this.context = context;
        this.lst_unitName = lst_unitName;
        this.Type = Type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_simple, parent, false);
        }
        TextView tv_simple = (TextView) convertView.findViewById(R.id.tv_simple);
        tv_simple.setText(lst_unitName.get(position));
        tv_simple.setTypeface(Utility.GetFont(context));

        Utility.TextDirection(context, tv_simple, StaticVarClass.TextView_Type);

        if (Type == StaticVarClass.Black_Color)
            tv_simple.setTextColor(context.getResources().getColor(R.color.black));
        else if (Type == StaticVarClass.Pink_Color) {
            tv_simple.setTextColor(context.getResources().getColor(R.color.pink));
            tv_simple.setGravity(Gravity.CENTER);
        }

        return convertView;
    }
}
