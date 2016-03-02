package media_sci.com.utility;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import media_sci.com.models.Ingredients;
import media_sci.com.models.UserData;

/**
 * Created by Bassem on 2/1/2016.
 */
public class Sorting {

    public static ArrayList<Ingredients> SortIngredients
            (Context context, ArrayList<Ingredients> lst_ingredients) {
        final UserData userData = new UserData(context);
        Collections.sort(lst_ingredients, new Comparator<Ingredients>() {

            @Override
            public int compare(Ingredients meal1, Ingredients meal2) {


                if (userData.GetLanguage() == StaticVarClass.Arabic) {
                    return meal1.getItem_name_ar().compareTo(
                            meal2.getItem_name_ar()
                    );
                } else {
                    return meal1.getItem_name_en().compareToIgnoreCase(
                            meal2.getItem_name_en()
                    );
                }

            }
        });

        return lst_ingredients;
    }
}
