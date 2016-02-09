package media_sci.com.models;

public class UserFavourite {

    private String meal_id;
    private int is_custom;

    public String getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(String meal_id) {
        this.meal_id = meal_id;
    }

    public int getIs_custom() {
        return is_custom;
    }

    public void setIs_custom(int is_custom) {
        this.is_custom = is_custom;
    }
}
