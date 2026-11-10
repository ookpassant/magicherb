package net.magicherb.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;

// Smelting that keeps the herb's strain, indoor-grown flag etc. on the dried result.
public class HerbDryingRecipe extends SmeltingRecipe {

    public static final RecipeSerializer<HerbDryingRecipe> SERIALIZER = new RecipeSerializer<>(
        AbstractCookingRecipe.cookingMapCodec(HerbDryingRecipe::new),
        AbstractCookingRecipe.cookingStreamCodec(HerbDryingRecipe::new));

    public HerbDryingRecipe(Recipe.CommonInfo info, AbstractCookingRecipe.CookingBookInfo book,
                            Ingredient input, ItemStackTemplate result, float experience, int cookingTime) {
        super(info, book, input, result, experience, cookingTime);
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input) {
        ItemStack out = super.assemble(input);
        out.applyComponents(input.item().getComponentsPatch());
        return out;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public RecipeSerializer<SmeltingRecipe> getSerializer() {
        return (RecipeSerializer) SERIALIZER;
    }
}
