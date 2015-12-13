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
import media_sci.com.models.Category;
import media_sci.com.shaklak_aklak.R;

/**
 * Created by Bassem on 12/7/2015.
 */
public class SearchAdapter extends SearchablePinnedHeaderListViewAdapter<Category> {

    Context context;
    ArrayList<Category> lst_category;

    public SearchAdapter(Context context, final ArrayList<Category> categories) {
        this.context = context;
        setData(categories);

    }


    public void setData(final ArrayList<Category> contacts) {
        this.lst_category = contacts;
        final String[] generatedContactNames = generateContactNames(contacts);
        // this line response for view of header of list
        setSectionIndexer(new StringArrayAlphabetIndexer(generatedContactNames, true));
    }

    private String[] generateContactNames(final List<Category> contacts) {
        final ArrayList<String> contactNames = new ArrayList<String>();
        if (contacts != null)
            for (final Category contactEntity : contacts)
                contactNames.add(contactEntity.getName_en());
        return contactNames.toArray(new String[contactNames.size()]);
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

        final Category category = getItem(position);
        final String displayName = category.getName_en();
        TextView name = (TextView) convertView.findViewById(R.id.listview_item__friendNameTextView);
        name.setText(displayName);
        TextView tv_header = (TextView) convertView.findViewById(R.id.header_text);

        bindSectionHeader(tv_header, null, position);
        return convertView;
    }

    @Override
    public boolean doFilter(final Category item, final CharSequence constraint) {
        if (TextUtils.isEmpty(constraint))
            return true;
        final String displayName = item.getName_en();
        return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                .contains(constraint.toString().toLowerCase(Locale.getDefault()));
    }

    @Override
    public ArrayList<Category> getOriginalList() {
        return lst_category;
    }
}
