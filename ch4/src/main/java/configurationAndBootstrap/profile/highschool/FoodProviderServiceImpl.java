package configurationAndBootstrap.profile.highschool;

import configurationAndBootstrap.profile.Food;
import configurationAndBootstrap.profile.FoodProviderService;

import java.util.ArrayList;
import java.util.List;

public class FoodProviderServiceImpl implements FoodProviderService { //еда для школы
    @Override
    public List<Food> provideLunchSet() {
        List<Food> lunchSet = new ArrayList<>();
            lunchSet.add(new Food("Coke"));
            lunchSet.add(new Food("Hamburger"));
            lunchSet.add(new Food("French Fries"));
        return lunchSet;
    }
}
