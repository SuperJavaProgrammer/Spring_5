package configurationAndBootstrap.profile.kindergarten;

import configurationAndBootstrap.profile.Food;
import configurationAndBootstrap.profile.FoodProviderService;

import java.util.ArrayList;
import java.util.List;

public class FoodProviderServiceImpl implements FoodProviderService { //еда для детского сада
    @Override
    public List<Food> provideLunchSet() {
        List<Food> lunchSet = new ArrayList<>();
            lunchSet.add(new Food("Milk"));
            lunchSet.add(new Food("Biscuits"));
        return lunchSet;
    }
}
