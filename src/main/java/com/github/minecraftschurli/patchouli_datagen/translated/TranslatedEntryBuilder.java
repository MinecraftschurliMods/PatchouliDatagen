package com.github.minecraftschurli.patchouli_datagen.translated;

import com.github.minecraftschurli.patchouli_datagen.EntryBuilder;
import com.github.minecraftschurli.patchouli_datagen.page.LinkPageBuilder;
import com.github.minecraftschurli.patchouli_datagen.page.MultiblockPageBuilder;
import com.github.minecraftschurli.patchouli_datagen.page.RecipePageBuilder;
import com.github.minecraftschurli.patchouli_datagen.page.TextPageBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class TranslatedEntryBuilder extends EntryBuilder<TranslatedBookBuilder, TranslatedCategoryBuilder, TranslatedEntryBuilder> {
    protected TranslatedEntryBuilder(String id, String name, String icon, TranslatedCategoryBuilder parent) {
        super(id, name, icon, parent);
    }

    @Override
    public TranslatedEntryBuilder addSimpleImagePage(ResourceLocation image, String text, String title) {
        var key = getLangKey();
        if (text != null) {
            putLangKey(key+".text", text);
            text = key+".text";
        }
        if (title != null) {
            putLangKey(key+".title", title);
            title = key+".title";
        }
        return super.addSimpleImagePage(image, text, title);
    }

    @Override
    public RecipePageBuilder addRecipePage(String type, ResourceLocation recipe, String text, String title) {
        var key = getLangKey();
        if (text != null) {
            putLangKey(key+".text", text);
            text = key+".text";
        }
        if (title != null) {
            putLangKey(key+".title", title);
            title = key+".title";
        }
        return super.addRecipePage(type, recipe, text, title);
    }

    @Override
    public TranslatedEntryBuilder addSimpleSpotlightPage(ItemStack stack, String text, String title) {
        var key = getLangKey();
        if (text != null) {
            putLangKey(key+".text", text);
            text = key+".text";
        }
        if (title != null) {
            putLangKey(key+".title", title);
            title = key+".title";
        }
        return super.addSimpleSpotlightPage(stack, text, title);
    }

    @Override
    public TranslatedEntryBuilder addSimpleLinkPage(String url, String linkText, String title, String text) {
        var key = getLangKey();
        if (text != null) {
            putLangKey(key+".text", text);
            text = key+".text";
        }
        if (title != null) {
            putLangKey(key+".title", title);
            title = key+".title";
        }
        if (linkText != null) {
            putLangKey(key+".linkText", linkText);
            linkText = key+".linkText";
        }
        return super.addSimpleLinkPage(url, linkText, title, text);
    }

    @Override
    public LinkPageBuilder addLinkPage(String url, String linkText) {
        var key = getLangKey();
        if (linkText != null) {
            putLangKey(key+".linkText", linkText);
            linkText = key+".linkText";
        }
        return super.addLinkPage(url, linkText);
    }

    @Override
    public TranslatedEntryBuilder addSimpleMultiblockPage(String name, String text, ResourceLocation multiblock) {
        var key = getLangKey();
        if (text != null) {
            putLangKey(key+".text", text);
            text = key+".text";
        }
        return super.addSimpleMultiblockPage(name, text, multiblock);
    }

    @Override
    public MultiblockPageBuilder addMultiblockPage(String name, ResourceLocation multiblock) {
        var key = getLangKey();
        if (name != null) {
            putLangKey(key+".title", name);
            name = key+".title";
        }
        return super.addMultiblockPage(name, multiblock);
    }

    @Override
    public TextPageBuilder addTextPage(String text, String title) {
        var key = getLangKey();
        if (text != null) {
            putLangKey(key+".text", text);
            text = key+".text";
        }
        if (title != null) {
            putLangKey(key+".title", title);
            title = key+".title";
        }
        return super.addTextPage(text, title);
    }

    private String getLangKey() {
        return "item.%s.%s.%s.%s".formatted(
                parent.getBookId().getNamespace(),
                parent.getBookId().getPath(),
                getId().getPath().replaceAll("/", "."),
                "page" + pageCount());
    }

    protected void putLangKey(String key, String text) {
        parent.putLangKey(key, text);
    }
}
