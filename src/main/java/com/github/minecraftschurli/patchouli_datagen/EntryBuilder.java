package com.github.minecraftschurli.patchouli_datagen;

import com.github.minecraftschurli.patchouli_datagen.page.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("unused")
public abstract class EntryBuilder<B extends BookBuilder<B, C, E>, C extends CategoryBuilder<B, C, E>, E extends EntryBuilder<B, C, E>> {
    protected final C parent;
    private final ResourceLocation id;
    private final ResourceLocation category;
    private final String icon;
    private final List<AbstractPageBuilder<?>> pages = new ArrayList<>();
    private final String name;
    private ResourceLocation advancement;
    private String flag;
    private Boolean priority;
    private Boolean secret;
    private Boolean readByDefault;
    private Integer sortnum;
    private String turnin;
    private Map<ItemStack, Integer> extraRecipeMappings;

    protected EntryBuilder(String id, String name, String icon, C parent) {
        this.id = new ResourceLocation(parent.getId().getNamespace(), parent.getId().getPath() + "/" + id);
        this.name = name;
        this.category = parent.getId();
        this.icon = icon;
        this.parent = parent;
    }

    public String getLocale() {
        return this.parent.getLocale();
    }

    JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("category", this.category.toString());
        json.addProperty("icon", this.icon);
        JsonArray pages = new JsonArray();

        for (final AbstractPageBuilder<?> page : this.pages) {
            pages.add(page.toJson());
        }

        json.add("pages", pages);
        if (this.advancement != null) {
            json.addProperty("advancement", this.advancement.toString());
        }

        if (this.flag != null) {
            json.addProperty("flag", this.flag);
        }

        if (this.priority != null) {
            json.addProperty("priority", this.priority);
        }

        if (this.secret != null) {
            json.addProperty("secret", this.secret);
        }

        if (this.readByDefault != null) {
            json.addProperty("read_by_default", this.readByDefault);
        }

        if (this.sortnum != null) {
            json.addProperty("sortnum", this.sortnum);
        }

        if (this.turnin != null) {
            json.addProperty("turnin", this.turnin);
        }

        if (this.extraRecipeMappings != null) {
            JsonObject mappings = new JsonObject();

            for (final Entry<ItemStack, Integer> entry : this.extraRecipeMappings.entrySet()) {
                mappings.addProperty(Util.serializeStack(entry.getKey()), entry.getValue());
            }

            json.add("extra_recipe_mappings", mappings);
        }

        this.serialize(json);
        return json;
    }

    protected void serialize(JsonObject json) {
    }

    public C build() {
        return this.parent;
    }

    public E addSimpleTextPage(String text) {
        return this.addTextPage(text).build();
    }

    public E addSimpleTextPage(String text, String title) {
        return this.addTextPage(text, title).build();
    }

    public TextPageBuilder addTextPage(String text) {
        return this.addTextPage(text, null);
    }

    public TextPageBuilder addTextPage(String text, String title) {
        return this.addPage(new TextPageBuilder(text, title, this));
    }

    public E addSimpleImagePage(ResourceLocation image, String text, String title) {
        ImagePageBuilder page = this.addImagePage(image);
        if (text != null) {
            page.setText(text);
        }

        if (title != null) {
            page.setTitle(title);
        }

        return page.build();
    }

    public ImagePageBuilder addImagePage(ResourceLocation image) {
        return this.addPage(new ImagePageBuilder(image, this));
    }

    public E addSimpleRecipePage(String type, ResourceLocation recipe) {
        return this.addSimpleRecipePage(type, recipe, null, null);
    }

    public E addSimpleRecipePage(String type, ResourceLocation recipe, String text) {
        return this.addSimpleRecipePage(type, recipe, text, null);
    }

    public E addSimpleRecipePage(String type, ResourceLocation recipe, String text, String title) {
        return this.addRecipePage(type, recipe, text, title).build();
    }

    public E addSimpleDoubleRecipePage(String type, ResourceLocation recipe1, ResourceLocation recipe2) {
        return this.addSimpleDoubleRecipePage(type, recipe1, recipe2, null, null);
    }

    public E addSimpleDoubleRecipePage(String type, ResourceLocation recipe1, ResourceLocation recipe2, String text) {
        return this.addSimpleDoubleRecipePage(type, recipe1, recipe2, text, null);
    }

    public E addSimpleDoubleRecipePage(String type, ResourceLocation recipe1, ResourceLocation recipe2, String text, String title) {
        return this.addRecipePage(type, recipe1, text, title).setRecipe2(recipe2).build();
    }

    public RecipePageBuilder addRecipePage(String type, ResourceLocation recipe, String text, String title) {
        RecipePageBuilder page = this.addRecipePage(type, recipe);
        if (text != null) {
            page.setText(text);
        }

        if (title != null) {
            page.setTitle(title);
        }

        return page;
    }

    public RecipePageBuilder addRecipePage(String type, ResourceLocation recipe) {
        return this.addPage(new RecipePageBuilder(new ResourceLocation("patchouli", type), recipe, this));
    }

    public EntityPageBuilder addEntityPage(String entity) {
        return this.addPage(new EntityPageBuilder(entity, this));
    }

    public EntityPageBuilder addEntityPage(ResourceLocation entity) {
        return this.addEntityPage(entity.toString());
    }

    public E addSimpleSpotlightPage(ItemStack stack) {
        return this.addSimpleSpotlightPage(stack, null, null);
    }

    public E addSimpleSpotlightPage(ItemStack stack, String text) {
        return this.addSimpleSpotlightPage(stack, text, null);
    }

    public E addSimpleSpotlightPage(ItemStack stack, String text, String title) {
        SpotlightPageBuilder page = this.addSpotlightPage(stack);
        if (text != null) {
            page.setText(text);
        }

        if (title != null) {
            page.setTitle(title);
        }

        return page.build();
    }

    public SpotlightPageBuilder addSpotlightPage(ItemStack stack) {
        return this.addPage(new SpotlightPageBuilder(stack, this));
    }

    public E addSimpleLinkPage(String url, String linkText, String title, String text) {
        LinkPageBuilder page = this.addLinkPage(url, linkText);
        if (text != null) {
            page.setText(text);
        }

        if (title != null) {
            page.setTitle(title);
        }

        return page.build();
    }

    public LinkPageBuilder addLinkPage(String url, String linkText) {
        return this.addPage(new LinkPageBuilder(url, linkText, this));
    }

    public E addSimpleEmptyPage() {
        return this.addEmptyPage().build();
    }

    public E addSimpleEmptyPage(boolean drawFiller) {
        return this.addEmptyPage(drawFiller).build();
    }

    public EmptyPageBuilder addEmptyPage() {
        return this.addPage(new EmptyPageBuilder(true, this));
    }

    public EmptyPageBuilder addEmptyPage(boolean drawFiller) {
        return this.addPage(new EmptyPageBuilder(drawFiller, this));
    }

    public E addSimpleMultiblockPage(String name, ResourceLocation multiblock) {
        return this.addMultiblockPage(name, multiblock).build();
    }

    public E addSimpleMultiblockPage(String name, String text, ResourceLocation multiblock) {
        return this.addMultiblockPage(name, multiblock).setText(text).build();
    }

    public MultiblockPageBuilder addMultiblockPage(String name, ResourceLocation multiblock) {
        return this.addPage(new MultiblockPageBuilder(name, multiblock, this));
    }

    public <T extends AbstractPageBuilder<T>> T addPage(T builder) {
        this.pages.add(builder);
        return builder;
    }

    public E setAdvancement(Advancement advancement) {
        return this.setAdvancement(advancement.getId());
    }

    public E setAdvancement(ResourceLocation advancement) {
        this.advancement = advancement;
        return this.self();
    }

    public E setFlag(String flag) {
        this.flag = flag;
        return this.self();
    }

    public E setPriority(boolean priority) {
        this.priority = priority;
        return this.self();
    }

    public E setSecret(boolean secret) {
        this.secret = secret;
        return this.self();
    }

    public E setReadByDefault(boolean readByDefault) {
        this.readByDefault = readByDefault;
        return this.self();
    }

    public E setSortnum(int sortnum) {
        this.sortnum = sortnum;
        return this.self();
    }

    public E setTurnin(String turnin) {
        this.turnin = turnin;
        return this.self();
    }

    public E addExtraRecipeMapping(ItemStack stack, int index) {
        if (this.extraRecipeMappings == null) {
            this.extraRecipeMappings = new HashMap<>();
        }

        this.extraRecipeMappings.put(stack, index);
        return this.self();
    }

    @SuppressWarnings("unchecked")
    protected E self() {
        return (E)this;
    }

    protected int pageCount() {
        return this.pages.size();
    }

    public ResourceLocation getId() {
        return this.id;
    }
}
