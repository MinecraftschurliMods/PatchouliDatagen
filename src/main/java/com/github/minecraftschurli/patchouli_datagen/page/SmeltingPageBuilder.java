package com.github.minecraftschurli.patchouli_datagen.page;

import com.github.minecraftschurli.patchouli_datagen.EntryBuilder;
import net.minecraft.resources.ResourceLocation;

public class SmeltingPageBuilder extends RecipePageBuilder<SmeltingPageBuilder> {
	public SmeltingPageBuilder(ResourceLocation recipe, EntryBuilder entryBuilder) {
		super("smelting", recipe, entryBuilder);
	}
}
