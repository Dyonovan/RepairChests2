package com.dyonovan.repairchests2.providers;

import com.dyonovan.repairchests2.RepairChests2;
import com.dyonovan.repairchests2.blocks.RCBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class RCRecipeProvider extends RecipeProvider {

    public RCRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> itemRegistryLookup = this.registries.lookupOrThrow(Registries.ITEM);
        this.addChestRecipes(itemRegistryLookup);
    }

    private void addChestRecipes(HolderGetter<Item> itemRegistryLookup) {
        String folder = "chests/";

        ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, RCBlocks.BASIC_CHEST.get())
                .define('L', ItemTags.PLANKS)
                .define('A', Blocks.ANVIL)
                .pattern("LLL")
                .pattern("LAL")
                .pattern("LLL")
                .unlockedBy("has_anvil", has(Blocks.ANVIL))
                .save(this.output, createKey(folder + "basic_chest"));

        ShapelessRecipeBuilder.shapeless(itemRegistryLookup, RecipeCategory.DECORATIONS, RCBlocks.ADVANCED_CHEST.get())
                .requires(RCBlocks.BASIC_CHEST)
                .requires(Items.ENDER_PEARL)
                .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
                .save(this.output, createKey(folder + "advanced_chest"));

        ShapelessRecipeBuilder.shapeless(itemRegistryLookup, RecipeCategory.DECORATIONS, RCBlocks.ULTIMATE_CHEST.get())
                .requires(RCBlocks.ADVANCED_CHEST)
                .requires(Items.NETHER_STAR)
                .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
                .save(this.output, createKey(folder + "ultimate_chest"));
    }

    protected ResourceKey<Recipe<?>> createKey(String name) {
        return ResourceKey.create(Registries.RECIPE, RepairChests2.prefix(name));
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new RCRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "Repair Chests Recipes";
        }
    }
}
