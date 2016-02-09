package media_sci.com.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import media_sci.com.models.Ingredients;

/**
 * Created by Bassem on 2/1/2016.
 */
public class Sorting {

    public static ArrayList<Ingredients> SortIngredients
            (ArrayList<Ingredients> lst_ingredients) {
        Collections.sort(lst_ingredients, new Comparator<Ingredients>() {

            @Override
            public int compare(Ingredients meal1, Ingredients meal2) {

                return meal1.getItem_name_en().compareToIgnoreCase(
                        meal2.getItem_name_en()
                );

            }
        });

        return lst_ingredients;
    }
}
