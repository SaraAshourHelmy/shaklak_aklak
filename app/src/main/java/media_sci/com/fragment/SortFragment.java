package media_sci.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import media_sci.com.adapter.SortAdapter;
import media_sci.com.models.Ingredients;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;


public class SortFragment extends Fragment implements View.OnTouchListener {

    String actionbar_title = "";
    private View actionbar;
    private ListView lst_sort;
    private int sort_no, sort_type;
    private ArrayList<Ingredients> lst_ingredients;
    private SortAdapter sortAdapter;
    private EditText et_search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        SetupTools(view);
        view.setOnTouchListener(this);

        return view;
    }

    private void SetupTools(View view) {

        lst_ingredients = Ingredients.GetAllIngredients(getActivity());
        actionbar = (View) view.findViewById(R.id.actionbar_sort);
        GetSortData();
        Utility.ActionBarSetting(actionbar, actionbar_title, 0, "", getActivity());

        lst_sort = (ListView) view.findViewById(R.id.lst_sort);
        et_search = (EditText) view.findViewById(R.id.et_sort_search);

        sortAdapter = new SortAdapter(getActivity(), R.layout.adapter_sort,
                lst_ingredients, sort_no);
        lst_sort.setAdapter(sortAdapter);
        lst_sort.setOnTouchListener(this);

        // touch search edit text
        et_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_search.setFocusableInTouchMode(true);
                return false;
            }
        });

        // search
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                SearchItems(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void GetSortData() {
        Bundle b = getArguments();
        if (b != null && b.containsKey("sort_no") && b.containsKey("sort_type")) {

            sort_no = b.getInt("sort_no");
            sort_type = b.getInt("sort_type");
        }


        if (sort_type == 0) {
            actionbar_title = getString(R.string.lowest);
        } else if (sort_type == 1) {
            actionbar_title = getString(R.string.highest);
        }

        /////

        if (sort_no == 1) {
            actionbar_title += " " + getString(R.string.calories);
            SortByCalories();
        } else if (sort_no == 2) {
            actionbar_title += " " + getString(R.string.total_fat);
            SortByTotalFat();
        } else if (sort_no == 3) {
            actionbar_title += " " + getString(R.string.cholest);
            SortByCholest();
        } else if (sort_no == 4) {
            actionbar_title += " " + getString(R.string.sodium);
            SortBySodium();
        } else if (sort_no == 5) {
            actionbar_title += " " + getString(R.string.protein);
            SortByProtein();
        } else if (sort_no == 6) {
            actionbar_title += " " + getString(R.string.carbo);
            SortByCarbo();
        }
    }

    private void SearchItems(String txt) {

        ArrayList<Ingredients> searchList = new ArrayList<>();

        int searchListLength = searchList.size();
        for (int i = 0; i < lst_ingredients.size(); i++) {

            String item_txt = lst_ingredients.get(i).getItem_name_en().toLowerCase();
            if (item_txt.contains(txt.toLowerCase())) {
                searchList.add(lst_ingredients.get(i));
            }

        }

        sortAdapter = new SortAdapter(getActivity(), R.layout.adapter_sort,
                searchList, sort_no);
        lst_sort.setAdapter(sortAdapter);

    }

    private void SortByCalories() {

        // this sort from low to high
        if (sort_type == 0) {
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getEnergy() ==
                            ingre2.getEnergy()) {
                        return 0;
                    } else if (ingre1.getEnergy() <
                            ingre2.getEnergy()) {
                        return -1;
                    }
                    return 1;

                }
            });
        } else {
            // sort from hight to low
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getEnergy() ==
                            ingre2.getEnergy()) {
                        return 0;
                    } else if (ingre1.getEnergy() >
                            ingre2.getEnergy()) {
                        return -1;
                    }
                    return 1;

                }
            });
        }
    }

    private void SortByTotalFat() {

        // this sort from low to high
        if (sort_type == 0) {
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getFat() ==
                            ingre2.getFat()) {
                        return 0;
                    } else if (ingre1.getFat() <
                            ingre2.getFat()) {
                        return -1;
                    }
                    return 1;

                }
            });
        } else {
            // sort from high to low
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getFat() ==
                            ingre2.getFat()) {
                        return 0;
                    } else if (ingre1.getFat() >
                            ingre2.getFat()) {
                        return -1;
                    }
                    return 1;

                }
            });
        }
    }

    private void SortByCholest() {
        // this sort from low to high
        if (sort_type == 0) {
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getCholest() ==
                            ingre2.getCholest()) {
                        return 0;
                    } else if (ingre1.getCholest() <
                            ingre2.getCholest()) {
                        return -1;
                    }
                    return 1;

                }
            });
        } else {
            // sort from high to low
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getCholest() ==
                            ingre2.getCholest()) {
                        return 0;
                    } else if (ingre1.getCholest() >
                            ingre2.getCholest()) {
                        return -1;
                    }
                    return 1;

                }
            });
        }
    }

    private void SortBySodium() {
        // this sort from low to high
        if (sort_type == 0) {
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getSodium() ==
                            ingre2.getSodium()) {
                        return 0;
                    } else if (ingre1.getSodium() <
                            ingre2.getSodium()) {
                        return -1;
                    }
                    return 1;

                }
            });
        } else {
            // sort from high to low
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getSodium() ==
                            ingre2.getSodium()) {
                        return 0;
                    } else if (ingre1.getSodium() >
                            ingre2.getSodium()) {
                        return -1;
                    }
                    return 1;

                }
            });
        }
    }

    private void SortByProtein() {
        // this sort from low to high
        if (sort_type == 0) {
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getProtein() ==
                            ingre2.getProtein()) {
                        return 0;
                    } else if (ingre1.getProtein() <
                            ingre2.getProtein()) {
                        return -1;
                    }
                    return 1;

                }
            });
        } else {
            // sort from high to low
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getProtein() ==
                            ingre2.getProtein()) {
                        return 0;
                    } else if (ingre1.getProtein() >
                            ingre2.getProtein()) {
                        return -1;
                    }
                    return 1;

                }
            });
        }
    }

    private void SortByCarbo() {
        // this sort from low to high
        if (sort_type == 0) {
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getCarbo_tot() ==
                            ingre2.getCarbo_tot()) {
                        return 0;
                    } else if (ingre1.getCarbo_tot() <
                            ingre2.getCarbo_tot()) {
                        return -1;
                    }
                    return 1;

                }
            });
        } else {
            // sort from high to low
            Collections.sort(lst_ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients ingre1, Ingredients ingre2) {
                    if (ingre1.getCarbo_tot() ==
                            ingre2.getCarbo_tot()) {
                        return 0;
                    } else if (ingre1.getCarbo_tot() >
                            ingre2.getCarbo_tot()) {
                        return -1;
                    }
                    return 1;

                }
            });
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v != et_search)
            Utility.HideKeyboard(getActivity(), et_search);
        return false;
    }

}
