package media_sci.com.shaklak_aklak;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import lb.library.PinnedHeaderListView;
import media_sci.com.adapter.SearchAdapter;
import media_sci.com.models.Category;

/**
 * Created by Bassem on 12/8/2015.
 */
public class SearchActivity extends Activity implements AdapterView.OnItemClickListener {

    ArrayList<Category> lst_category = new ArrayList<>();
    int indexListSize;
    float sideIndexX, sideIndexY;
    private List<Object[]> alphabet = new ArrayList<Object[]>();
    private ImageView img_search_food;
    private EditText et_search_food;
    private LinearLayout lnr_sideIndex;
    private SearchAdapter categoryAdapter;
    private PinnedHeaderListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SetupTools();
        SetupIndexedList();
        SetSideIndex();
    }

    private void SetupTools() {
        SetCategories();
        img_search_food = (ImageView) findViewById(R.id.img_search_food);
        et_search_food = (EditText) findViewById(R.id.et_search_food);
        lnr_sideIndex = (LinearLayout) findViewById(R.id.lnr_sideIndex);

        categoryAdapter = new SearchAdapter(this, lst_category);

        // focus on edit text when touch
        et_search_food.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_search_food.setFocusableInTouchMode(true);
                return false;
            }
        });

        // listen to text change
        et_search_food.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.e("text", "" + s);
                SearchCategory(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void SetupIndexedList() {
        mListView = (PinnedHeaderListView) findViewById(android.R.id.list);
        LayoutInflater mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        //int pinnedHeaderBackgroundColor = getResources().getColor(getResIdFromAttribute(this, android.R.attr.colorBackground));
        //categoryAdapter.setPinnedHeaderBackgroundColor(pinnedHeaderBackgroundColor);
        categoryAdapter.setPinnedHeaderTextColor(getResources().getColor(R.color.colorAccent));
        mListView.setPinnedHeaderView(mInflater.inflate(R.layout.header_list, mListView, false));
        mListView.setAdapter(categoryAdapter);
        mListView.setOnScrollListener(categoryAdapter);
        mListView.setEnableHeaderTransparencyChanges(false);
    }

    private void SetSideIndex() {
        // set side index

        int start = 0;
        int end = 0;
        int size = 0;
        String previousLetter = null;
        Object[] tmpIndexItem = null;

        for (int i = 0; i < lst_category.size(); i++) {

            String category_name = lst_category.get(i).getName_en();
            String firstLetter = category_name.substring(0, 1);


            // If we've changed to a new letter, add the previous letter to the alphabet scroller
            if (previousLetter != null && !firstLetter.equals(previousLetter)) {

                tmpIndexItem = new Object[3];
                tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
                tmpIndexItem[1] = start;
                tmpIndexItem[2] = end;
                alphabet.add(tmpIndexItem);

                start = end + 1;
            }


            // Add the country to the list
            size++;
            previousLetter = firstLetter;
        }

        if (previousLetter != null) {
            // Save the last letter
            tmpIndexItem = new Object[3];
            tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
            tmpIndexItem[1] = start;
            tmpIndexItem[2] = size - 1;
            alphabet.add(tmpIndexItem);
        }

        setAlphabet();
    }

    private void SetCategories() {

        Category category = new Category();
        category.setName_en("ahmed");
        lst_category.add(category);

        category = new Category();
        category.setName_en("adel");
        lst_category.add(category);

        category = new Category();
        category.setName_en("nora");
        lst_category.add(category);

        category = new Category();
        category.setName_en("sara");
        lst_category.add(category);

        category = new Category();
        category.setName_en("farida");
        lst_category.add(category);

        category = new Category();
        category.setName_en("mariam");
        lst_category.add(category);

        category = new Category();
        category.setName_en("mohammed");
        lst_category.add(category);

        category = new Category();
        category.setName_en("mona");
        lst_category.add(category);

        category = new Category();
        category.setName_en("samar");
        lst_category.add(category);

        category = new Category();
        category.setName_en("heba");
        lst_category.add(category);

        category = new Category();
        category.setName_en("enas");
        lst_category.add(category);

        category = new Category();
        category.setName_en("mahmoud");
        lst_category.add(category);

        // sort category list
        Collections.sort(lst_category, new Comparator<Category>() {
            @Override
            public int compare(Category lhs, Category rhs) {
                return lhs.getName_en().compareToIgnoreCase(rhs.getName_en());
            }
        });
    }

    private void SearchCategory(String txt) {
        ArrayList<Category> searchList = new ArrayList<>();

        int searchListLength = searchList.size();
        for (int i = 0; i < lst_category.size(); i++) {
            if (lst_category.get(i).getName_en().contains(txt)) {
                searchList.add(lst_category.get(i));
            }

        }
        categoryAdapter = new SearchAdapter(this, searchList);
        mListView.setAdapter(categoryAdapter);

    }


    public void setAlphabet() {

        lnr_sideIndex.removeAllViews();
        indexListSize = alphabet.size();
        if (indexListSize < 1) {
            return;
        }

        int indexMaxSize = (int) Math.floor(lnr_sideIndex.getHeight() / 20);
        int tmpIndexListSize = indexListSize;
        while (tmpIndexListSize > indexMaxSize) {
            tmpIndexListSize = tmpIndexListSize / 2;
        }
        double delta;
        if (tmpIndexListSize > 0) {
            delta = indexListSize / tmpIndexListSize;
        } else {
            delta = 1;
        }

        TextView tmpTV;
        for (double i = 1; i <= indexListSize; i = i + delta) {
            Object[] tmpIndexItem = alphabet.get((int) i - 1);
            String tmpLetter = tmpIndexItem[0].toString();

            tmpTV = new TextView(this);
            tmpTV.setText(tmpLetter);
            tmpTV.setGravity(Gravity.CENTER);
            tmpTV.setTextSize(15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tmpTV.setLayoutParams(params);
            lnr_sideIndex.addView(tmpTV);
        }

        int sideIndexHeight = lnr_sideIndex.getHeight();

        lnr_sideIndex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // now you know coordinates of touch
                sideIndexX = event.getX();
                sideIndexY = event.getY();

                // and can display a proper item it country list
                displayListItem();

                return false;
            }
        });
    }

    public void displayListItem() {


        int sideIndexHeight = lnr_sideIndex.getHeight();
        // compute number of pixels for every side index item
        double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

        // compute the item index for given event position belongs to
        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

        // get the item (we can do it since we know item index)
        if (itemPosition < alphabet.size()) {
            Object[] indexItem = alphabet.get(itemPosition);
            // get selected char
            String txt = indexItem[0].toString();
            Log.e("touch txt", txt);


            // display sections of list according to selected char
            if (txt.equals("A"))
                mListView.setSelection(0);
            else {
                for (int i = 0; i < mListView.getCount(); i++) {
                    View v = mListView.getAdapter().getView(i, null, mListView);

                    FrameLayout frame_header = (FrameLayout) v.findViewById(R.id.frame_header);

                    TextView check_extra = (TextView) v.findViewById(R.id.header_text);

                    if (check_extra.getText().equals(txt)) {
                        Log.e("match found", txt);
                        mListView.setSelection(i);
                    }
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
