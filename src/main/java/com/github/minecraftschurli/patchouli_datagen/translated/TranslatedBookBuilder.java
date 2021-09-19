package com.github.minecraftschurli.patchouli_datagen.translated;

import com.github.minecraftschurli.patchouli_datagen.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.LanguageProvider;

public class TranslatedBookBuilder extends BookBuilder<TranslatedBookBuilder, TranslatedCategoryBuilder, TranslatedEntryBuilder> {
    private final LanguageProvider languageProvider;

    public TranslatedBookBuilder(ResourceLocation id, String name, String landingText, LanguageProvider languageProvider, PatchouliBookProvider provider) {
        super(id, name, landingText, provider);
        this.languageProvider = languageProvider;
        setUseI18n();

    }

    @Override
    public TranslatedCategoryBuilder addCategory(String id, String name, String description, ItemStack icon) {
        return this.addCategory(id, name, description, Util.serializeStack(icon));
    }

    @Override
    public TranslatedCategoryBuilder addCategory(String id, String name, String description, String icon) {
        var key = "item.%s.%s.%s".formatted(getId().getNamespace(), getId().getPath(), id);
        putLangKey(key+".name", name);
        putLangKey(key+".description", description);
        return this.addCategory(new TranslatedCategoryBuilder(id, key+".name", key+".description", icon, this));
    }

    public void putLangKey(final String key, final String text) {
        languageProvider.add(key, text);
    }
}
