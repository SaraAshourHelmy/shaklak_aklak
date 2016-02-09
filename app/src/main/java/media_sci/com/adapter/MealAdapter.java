package media_sci.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.models.Ingredients;
import media_sci.com.models.UserMeal;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 1/12/2016.
 */
public class MealAdapter extends ArrayAdapter<UserMeal> {

    private Context context;
    private ArrayList<UserMeal> lst_userMeal;

    public MealAdapter(Context context, int layout, ArrayList<UserMeal> lst_userMeal) {
        super(context, layout, lst_userMeal);
        this.context = context;
        this.lst_userMeal = lst_userMeal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_meal, parent, false);
        }

        TextView tv_mealName = (TextView) convertView.findViewById(R.id.tv_mealName);
        TextView tv_meal_calories = (TextView) convertView.findViewById(R.id.tv_meal_calories);

        tv_mealName.setText(lst_userMeal.get(position).getItemName());
        tv_mealName.setTypeface(Utility.GetFont(context));
        tv_meal_calories.setTypeface(Utility.GetFont(context));

        Ingredients ingredients;
        if (lst_userMeal.get(position).getIs_custom() == 0) {

            ingredients = Ingredients.GetIngredient(context,
                    Integer.parseInt(lst_userMeal.get(position).getIngredient_id()));
        } else {

            ingredients = Ingredients.GetCustomIngredient(context,
                    lst_userMeal.get(position).getIngredient_id());
        }

        double calories = lst_userMeal.get(position).getServing_no() * lst_userMeal
                .get(position).getServing_size() * ingredients.getEnergy();

        tv_meal_calories.setText(
                Utility.GetDecimalFormat(calories) + " " + StaticVarClass.kcal);

        return convertView;
    }
}
