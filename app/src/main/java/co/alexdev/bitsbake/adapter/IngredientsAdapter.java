package co.alexdev.bitsbake.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.databinding.ItemRecipeIngredientLayoutBinding;
import co.alexdev.bitsbake.model.model.Ingredient;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    List<Ingredient> mIngredients;

    public IngredientsAdapter(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecipeIngredientLayoutBinding binding = ItemRecipeIngredientLayoutBinding.inflate(inflater, parent, false);
        return new IngredientsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.bind(mIngredients.get(position));
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null) return 0;
        return mIngredients.size();
    }

    static class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private ItemRecipeIngredientLayoutBinding binding;

        public IngredientsViewHolder(ItemRecipeIngredientLayoutBinding itemRecipeIngredientLayoutBinding) {
            super(itemRecipeIngredientLayoutBinding.getRoot());
            this.binding = itemRecipeIngredientLayoutBinding;
            binding.executePendingBindings();
        }

        public void bind(Ingredient ingredient) {
            binding.tvIngredient = ingredient.getIngredient();
            binding.tvMeasure = ingredient.getMeasure();
            binding.tvQuantity = ingredient.getQuantity();
        }
    }
}
