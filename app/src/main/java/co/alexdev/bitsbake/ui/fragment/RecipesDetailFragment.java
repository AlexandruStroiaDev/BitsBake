package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.StepsAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipeDetailBinding;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import co.alexdev.bitsbake.viewmodel.MainViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecipesDetailFragment extends BaseFragment {

    private StepsAdapter mStepsAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentRecipeDetailBinding mBinding;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initView(container);
        initRecycler();
        return rootView;
    }

    private void initView(ViewGroup container) {

        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.fragment_recipe_detail, container,
                false);
        rootView = mBinding.getRoot();
        vm = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
    }

    private void initRecycler() {
        Bundle args = getArguments();
        String recipeKey = getString(R.string.recipe_id);
        if (args != null && args.containsKey(recipeKey)) {
            configureStepsAdapter();
            int id = args.getInt(recipeKey);
            configureIngredientsTextView(id);
        }
    }

    private void configureStepsAdapter() {
        mStepsAdapter = new StepsAdapter(new ArrayList<>());
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mBinding.rvDetails.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.rvDetails.getContext(), mLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getActivity().getDrawable(R.color._black));
        mBinding.rvDetails.addItemDecoration(dividerItemDecoration);
        mBinding.rvDetails.setAdapter(mStepsAdapter);
    }

    private void configureIngredientsTextView(int id){
        vm.setId(id);
        vm.getIngredientById().observe(this,
                ingredients -> mBinding.tvIngredients.setText(
                        BitsBakeUtils.buildIngredientsTextView(ingredients)
                ));
        vm.getStepsById().observe(this, steps -> mStepsAdapter.setList(steps));
    }
}
