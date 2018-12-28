package co.alexdev.bitsbake.viewmodel;

import android.app.Application;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import co.alexdev.bitsbake.model.model.Ingredient;
import co.alexdev.bitsbake.model.model.RecipeWithIngredientsAndSteps;
import co.alexdev.bitsbake.model.model.Step;
import co.alexdev.bitsbake.model.model.Recipe;
import co.alexdev.bitsbake.networking.NetworkResponse;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainViewModel extends AndroidViewModel {

    private BitsBakeRepository mRepository;
    private final MutableLiveData<NetworkResponse> mNetworkResponse = new MutableLiveData<>();
    private static final int TIMEOUT = 5000;
    public Recipe recipe;
    public Ingredient ingredient;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = BitsBakeRepository.getInstance(this.getApplication());

    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRepository.getRecipes();
    }

    public LiveData<List<Ingredient>> getIngredient() {
        return mRepository.getIngredients();
    }

    public MutableLiveData<NetworkResponse> getNetworkResponse() {
        return mNetworkResponse;
    }

    public void loadData() {
        mRepository.fetchNetworkingData().
                subscribeOn(Schedulers.io())
                .timeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Recipe>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mNetworkResponse.setValue(NetworkResponse.loading());
                    }

                    @Override
                    public void onSuccess(List<Recipe> recipes) {
                        mNetworkResponse.setValue(NetworkResponse.success(recipes));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mNetworkResponse.setValue(NetworkResponse.error(e));
                    }
                });
    }

    public LiveData<RecipeWithIngredientsAndSteps> getRecipe(int id) {
        return mRepository.getRecipe(id);
    }

    public LiveData<List<RecipeWithIngredientsAndSteps>> getRecipeList() {
        return mRepository.getRecipesList();
    }

    public void insertToDatabase(List<Recipe> recipes) {
        Timber.d("Recipes: " + recipes.toString());
        List<Recipe> formatedRecipes = BitsBakeUtils.formatRecipes(recipes);

        wipeAll();

        for (Recipe recipe : formatedRecipes) {
            List<Step> steps = recipe.getSteps();
            List<Ingredient> ingredients = recipe.getIngredients();

            mRepository.insertIngredientsToDatabase(ingredients);
            mRepository.insertStepsToDatabase(steps);

            recipe.setIngredients(ingredients);
            recipe.setSteps(steps);
        }

        mRepository.insertRecipesToDatabase(formatedRecipes);
    }

    private void wipeAll() {
        mRepository.deleteIngredients();
        mRepository.deleteSteps();
        mRepository.deleteIngredients();
    }
}
