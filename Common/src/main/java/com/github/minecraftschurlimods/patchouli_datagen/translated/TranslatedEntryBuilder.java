package com.github.minecraftschurlimods.patchouli_datagen.translated;

import com.github.minecraftschurlimods.patchouli_datagen.EntryBuilder;
import com.github.minecraftschurlimods.patchouli_datagen.page.LinkPageBuilder;
import com.github.minecraftschurlimods.patchouli_datagen.page.MultiblockPageBuilder;
import com.github.minecraftschurlimods.patchouli_datagen.page.RecipePageBuilder;
import com.github.minecraftschurlimods.patchouli_datagen.page.TextPageBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class TranslatedEntryBuilder extends EntryBuilder<TranslatedBookBuilder, TranslatedCategoryBuilder, TranslatedEntryBuilder> {
    protected TranslatedEntryBuilder(String id, String name, String icon, TranslatedCategoryBuilder parent) {
        super(id, name, icon, parent);
    }

    @Override
    public TranslatedEntryBuilder addSimpleImagePage(ResourceLocation image, String text, String title) {
        String key = getLangKey(pageCount());
        return super.addSimpleImagePage(image, putLangKey(key+".text", text), putLangKey(key+".title", title));
    }

    @Override
    public RecipePageBuilder addRecipePage(String type, ResourceLocation recipe, String text, String title) {
        String key = getLangKey(pageCount());
        return super.addRecipePage(type, recipe, putLangKey(key+".text", text), putLangKey(key+".title", title));
    }

    @Override
    public TranslatedEntryBuilder addSimpleSpotlightPage(ItemStack stack, String text, String title) {
        String key = getLangKey(pageCount());
        return super.addSimpleSpotlightPage(stack, putLangKey(key+".text", text), putLangKey(key+".title", title));
    }

    @Override
    public TranslatedEntryBuilder addSimpleLinkPage(String url, String linkText, String title, String text) {
        String key = getLangKey(pageCount());
        return super.addSimpleLinkPage(url, putLangKey(key+".linkText", linkText), putLangKey(key+".title", title), putLangKey(key+".text", text));
    }

    @Override
    public LinkPageBuilder addLinkPage(String url, String linkText) {
        String key = getLangKey(pageCount());
        return super.addLinkPage(url, putLangKey(key+".linkText", linkText));
    }

    @Override
    public TranslatedEntryBuilder addSimpleMultiblockPage(String name, String text, ResourceLocation multiblock) {
        String key = getLangKey(pageCount());
        return super.addSimpleMultiblockPage(name, putLangKey(key+".text", text), multiblock);
    }

    @Override
    public MultiblockPageBuilder addMultiblockPage(String name, ResourceLocation multiblock) {
        String key = getLangKey(pageCount());
        return super.addMultiblockPage(putLangKey(key+".title", name), multiblock);
    }

    @Override
    public TextPageBuilder addTextPage(String text, String title) {
        String key = getLangKey(pageCount());
        return super.addTextPage(putLangKey(key+".text", text), putLangKey(key+".title", title));
    }

    public String getLangKey(int page) {
        return "item.%s.%s.%s.%s".formatted(
                parent.getBookId().getNamespace(),
                parent.getBookId().getPath(),
                getId().getPath().replaceAll("/", "."),
                "page" + page);
    }

    protected String putLangKey(String key, String text) {
        if (text == null) {
            return null;
        }
        return parent.putLangKey(key, text);
    }
}
