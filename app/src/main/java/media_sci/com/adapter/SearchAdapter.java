package media_sci.com.adapter;

import android.content.Context;
import android.text.TextUtils;
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
public class SearchAdapter extends SearchablePinnedHeaderListViewAdapter<Ingredients> {

    Context context;
    ArrayList<Ingredients> lst_items;

    public SearchAdapter(Context context, final ArrayList<Ingredients> items) {
        this.context = context;
        setData(items);

    }


    public void setData(final ArrayList<Ingredients> items) {
        this.lst_items = items;
        final String[] generatedContactNames = generateContactNames(items);
        // this line response for view of header of list
        setSectionIndexer(new StringArrayAlphabetIndexer(generatedContactNames, true));
    }

    private String[] generateContactNames(final List<Ingredients> items) {
        final ArrayList<String> itemNames = new ArrayList<String>();
        if (items != null)
            for (final Ingredients itemEntity : items)
                itemNames.add(itemEntity.getItem_name_en());
        return itemNames.toArray(new String[itemNames.size()]);
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

        final Ingredients item = getItem(position);
        final String displayName = item.getItem_name_en();
        TextView name = (TextView) convertView.findViewById(R.id.listview_item__friendNameTextView);

        name.setText(displayName);

        TextView tv_header = (TextView) convertView.findViewById(R.id.header_text);

        name.setTypeface(Utility.GetFont(context));
        tv_header.setTypeface(Utility.GetFont(context));

        bindSectionHeader(tv_header, null, position);
        return convertView;
    }

    @Override
    public boolean doFilter(final Ingredients item, final CharSequence constraint) {
        if (TextUtils.isEmpty(constraint))
            return true;
        final String displayName = item.getItem_name_en();
        return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                .contains(constraint.toString().toLowerCase(Locale.getDefault()));
    }

    @Override
    public ArrayList<Ingredients> getOriginalList() {
        return lst_items;
    }
}
