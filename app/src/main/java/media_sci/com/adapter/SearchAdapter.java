package media_sci.com.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lb.library.SearchablePinnedHeaderListViewAdapter;
import lb.library.StringArrayAlphabetIndexer;
import media_sci.com.models.Ingredients;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/7/2015.
 */
public class SearchAdapter extends SearchablePinnedHeaderListViewAdapter<String> {

    Context context;
    //ArrayList<Ingredients> lst_items;
    ArrayList<String> items;

    public SearchAdapter(Context context, ArrayList<String> items) {
        this.context = context;

        setData(items);
        this.items = items;

    }


    public void setData(final ArrayList<String> items) {
        // this.lst_items = items;
        // final String[] generatedContactNames = generateContactNames(items);
        // this line response for view of header of list
        Log.e("SetData_start", Utility.GetTimeNow());
        setSectionIndexer(new StringArrayAlphabetIndexer(
                items.toArray(new String[items.size()]), true));
        Log.e("SetData_End", Utility.GetTimeNow());
    }

    private String[] generateContactNames(final List<Ingredients> items) {
        /*
        final ArrayList<String> itemNames = new ArrayList<String>();
        if (items != null)
            for (final Ingredients itemEntity : items)
                itemNames.add(itemEntity.getItem_name_en());
        return itemNames.toArray(new String[itemNames.size()]);*/
        String[] itemName = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {

        }
        return itemName;
    }

    @Override
    public CharSequence getSectionTitle(int sectionIndex) {
        return ((StringArrayAlphabetIndexer.AlphaBetSection) getSections()[sectionIndex]).getName();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.adapter_search, parent, false);

        }

        //  final Ingredients item = getItem(position);
        final String displayName = items.get(position);
        TextView name = (TextView) convertView.findViewById(R.id.tv_search_itemName);

        name.setText(displayName);


        TextView tv_header = (TextView) convertView.findViewById(R.id.header_text);

        name.setTypeface(Utility.GetFont(context));
        tv_header.setTypeface(Utility.GetFont(context));

        bindSectionHeader(tv_header, null, position);
        return convertView;
    }


    @Override
    public boolean doFilter(String item, CharSequence constraint) {
        if (TextUtils.isEmpty(constraint))
            return true;
        final String displayName = item;
        return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                .contains(constraint.toString().toLowerCase(Locale.getDefault()));
    }

    @Override
    public ArrayList<String> getOriginalList() {
        return items;
    }


}
