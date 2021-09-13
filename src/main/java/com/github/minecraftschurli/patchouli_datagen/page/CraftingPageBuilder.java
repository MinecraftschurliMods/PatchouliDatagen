package com.github.minecraftschurli.patchouli_datagen.page;

import com.github.minecraftschurli.patchouli_datagen.EntryBuilder;
import net.minecraft.resources.ResourceLocation;

public class CraftingPageBuilder extends RecipePageBuilder<CraftingPageBuilder> {
	public CraftingPageBuilder(ResourceLocation recipe, EntryBuilder entryBuilder) {
		super("crafting", recipe, entryBuilder);
	}
}
