package com.github.minecraftschurli.patchouli_datagen;

import com.github.minecraftschurli.patchouli_datagen.translated.TranslatedCategoryBuilder;
import com.github.minecraftschurli.patchouli_datagen.translated.TranslatedEntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryBuilder {

    private final BookBuilder bookBuilder;
    private final ResourceLocation id;
    private final String name;
    private final String description;
    private final String icon;
    private final List<E> entries = new ArrayList<>();
    private String parent;
    private String flag;
    private Integer sortnum;
    private Boolean secret;

    protected CategoryBuilder(String id, String name, String description, ItemStack icon, BookBuilder bookBuilder) {
        this(id, name, description, Util.serializeStack(icon), bookBuilder);
    }

    protected CategoryBuilder(String id, String name, String description, String icon, BookBuilder bookBuilder) {
        this.bookBuilder = bookBuilder;
        this.id = new ResourceLocation(bookBuilder.getId().getNamespace(), id);
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("description", description);
        json.addProperty("icon", icon);
        if (parent != null) {
            json.addProperty("parent", parent);
        }
        if (flag != null) {
            json.addProperty("flag", flag);
        }
        if (sortnum != null) {
            json.addProperty("sortnum", sortnum);
        }
        if (secret != null) {
            json.addProperty("secret", secret);
        }
        this.serialize(json);
        return json;
    }

    protected void serialize(JsonObject json) {
    }

    protected List<E> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public B build() {
        return bookBuilder;
    }

    public abstract C addSubCategory(String id, String name, String description, ItemStack icon);

    public abstract C addSubCategory(String id, String name, String description, String icon);

    protected C addSubCategory(C builder) {
        return this.bookBuilder.addCategory(builder).setParent(self());
    }

    public abstract E addEntry(String id, String name, ItemStack icon);

    public abstract E addEntry(String id, String name, String icon);

    protected E addEntry(E builder) {
        this.entries.add(builder);
        return builder;
    }

    public C setParent(String parent) {
        this.parent = parent;
        return self();
    }

    public C setParent(C parent) {
        this.parent = parent.getId().toString();
        return self();
    }

    public C setFlag(String flag) {
        this.flag = flag;
        return self();
    }

    public C setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
        return self();
    }

    public C setSecret(Boolean secret) {
        this.secret = secret;
        return self();
    }

    private <T extends CategoryBuilder<B, C, E>> T self() {
        return (T) this;
    }

    public ResourceLocation getId() {
        return id;
    }
}
