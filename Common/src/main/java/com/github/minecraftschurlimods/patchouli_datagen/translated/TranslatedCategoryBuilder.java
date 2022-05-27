package com.github.minecraftschurlimods.patchouli_datagen.translated;

import com.github.minecraftschurlimods.patchouli_datagen.CategoryBuilder;
import com.github.minecraftschurlimods.patchouli_datagen.Util;
import net.minecraft.world.item.ItemStack;

public class TranslatedCategoryBuilder extends CategoryBuilder<TranslatedBookBuilder, TranslatedCategoryBuilder, TranslatedEntryBuilder> {
    protected TranslatedCategoryBuilder(String id, String name, String description, String icon, TranslatedBookBuilder bookBuilder) {
        super(id, name, description, icon, bookBuilder);
    }

    @Override
    public TranslatedCategoryBuilder addSubCategory(String id, String name, String description, ItemStack icon) {
        return addSubCategory(id, name, description, Util.serializeStack(icon));
    }

    @Override
    public TranslatedCategoryBuilder addSubCategory(String id, String name, String description, String icon) {
        String key = "item.%s.%s.%s.%s".formatted(getBookId().getNamespace(), getBookId().getPath(), getId().getPath().replaceAll("/", "."), id);
        return addSubCategory(new TranslatedCategoryBuilder(id, putLangKey(key + ".name", name), putLangKey(key + ".description", description), icon, bookBuilder));
    }

    @Override
    public TranslatedEntryBuilder addEntry(String id, String name, ItemStack icon) {
        return addEntry(id, name, Util.serializeStack(icon));
    }

    @Override
    public TranslatedEntryBuilder addEntry(String id, String name, String icon) {
        String key = "item.%s.%s.%s.%s.name".formatted(getBookId().getNamespace(), getBookId().getPath(), getId().getPath().replaceAll("/", "."), id);
        return addEntry(new TranslatedEntryBuilder(id, putLangKey(key, name), icon, this));
    }

    public String putLangKey(String key, String text) {
        return bookBuilder.putLangKey(key, text);
    }
}
