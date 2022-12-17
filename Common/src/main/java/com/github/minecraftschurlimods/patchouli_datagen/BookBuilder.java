package com.github.minecraftschurlimods.patchouli_datagen;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class BookBuilder<B extends BookBuilder<B, C, E>, C extends CategoryBuilder<B, C, E>, E extends EntryBuilder<B, C, E>> {
    private final List<C> categories = new ArrayList<>();
    protected final PatchouliBookProvider provider;
    private final ResourceLocation id;
    protected String name;
    protected String landingText;
    private ResourceLocation bookTexture;
    private String fillerTexture;
    private String craftingTexture;
    private String model;
    private String textColor;
    private String headerColor;
    private String nameplateColor;
    private String linkColor;
    private String linkHoverColor;
    private Boolean useBlockyFont;
    private String progressBarColor;
    private String progressBarBackground;
    private ResourceLocation openSound;
    private ResourceLocation flipSound;
    private Boolean showProgress;
    private String indexIcon;
    private String version;
    private String subtitle;
    private ResourceLocation creativeTab;
    private ResourceLocation advancementsTab;
    private Boolean dontGenerateBook;
    private String customBookItem;
    private Boolean showToasts;
    private Boolean useResourcepack;
    private Boolean i18n;

    protected BookBuilder(ResourceLocation id, String name, String landingText, PatchouliBookProvider provider) {
        this.id = id;
        this.provider = provider;
        this.name = name;
        this.landingText = landingText;
    }

    public String getLocale() {
        return "en_us";
    }

    JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("landing_text", this.landingText);
        if (this.bookTexture != null) {
            json.addProperty("book_texture", this.bookTexture.toString());
        }

        if (this.fillerTexture != null) {
            json.addProperty("filler_texture", this.fillerTexture);
        }

        if (this.craftingTexture != null) {
            json.addProperty("crafting_texture", this.craftingTexture);
        }

        if (this.model != null) {
            json.addProperty("model", this.model);
        }

        if (this.textColor != null) {
            json.addProperty("text_color", this.textColor);
        }

        if (this.headerColor != null) {
            json.addProperty("header_color", this.headerColor);
        }

        if (this.nameplateColor != null) {
            json.addProperty("nameplate_color", this.nameplateColor);
        }

        if (this.linkColor != null) {
            json.addProperty("link_color", this.linkColor);
        }

        if (this.linkHoverColor != null) {
            json.addProperty("link_hover_color", this.linkHoverColor);
        }

        if (this.progressBarColor != null) {
            json.addProperty("progress_bar_color", this.progressBarColor);
        }

        if (this.progressBarBackground != null) {
            json.addProperty("progress_bar_background", this.progressBarBackground);
        }

        if (this.openSound != null) {
            json.addProperty("open_sound", this.openSound.toString());
        }

        if (this.flipSound != null) {
            json.addProperty("flip_sound", this.flipSound.toString());
        }

        if (this.indexIcon != null) {
            json.addProperty("index_icon", this.indexIcon);
        }

        if (this.showProgress != null) {
            json.addProperty("show_progress", this.showProgress);
        }

        if (this.subtitle != null) {
            json.addProperty("subtitle", this.subtitle);
        } else if (this.version != null) {
            json.addProperty("version", this.version);
        } else {
            json.addProperty("subtitle", "item.%s.%s.subtitle".formatted(this.id.getNamespace(), this.id.getPath()));
        }

        if (this.creativeTab != null) {
            json.addProperty("creative_tab", this.creativeTab.toString());
        }

        if (this.advancementsTab != null) {
            json.addProperty("advancements_tab", this.advancementsTab.toString());
        }

        if (this.dontGenerateBook != null) {
            json.addProperty("dont_generate_book", this.dontGenerateBook);
        }

        if (this.customBookItem != null) {
            json.addProperty("custom_book_item", this.customBookItem);
        }

        if (this.showToasts != null) {
            json.addProperty("show_toasts", this.showToasts);
        }

        if (this.useBlockyFont != null) {
            json.addProperty("use_blocky_font", this.useBlockyFont);
        }

        if (this.i18n != null) {
            json.addProperty("i18n", this.i18n);
        }

        if (this.useResourcepack != null) {
            json.addProperty("use_resource_pack", this.useResourcepack);
        }

        this.serialize(json);
        return json;
    }

    protected void serialize(JsonObject json) {
    }

    protected List<C> getCategories() {
        return Collections.unmodifiableList(this.categories);
    }

    public void build(Consumer<BookBuilder<?, ?, ?>> consumer) {
        consumer.accept(this.self());
    }

    public abstract C addCategory(String var1, String var2, String var3, ItemStack var4);

    public abstract C addCategory(String var1, String var2, String var3, String var4);

    protected <T extends C> T addCategory(T builder) {
        this.categories.add(builder);
        return builder;
    }

    public B setBookTexture(ResourceLocation bookTexture) {
        this.bookTexture = bookTexture;
        return this.self();
    }

    public B setFillerTexture(String fillerTexture) {
        this.fillerTexture = fillerTexture;
        return this.self();
    }

    public B setCraftingTexture(String craftingTexture) {
        this.craftingTexture = craftingTexture;
        return this.self();
    }

    public B setModel(ResourceLocation model) {
        return this.setModel(model.toString());
    }

    public B setModel(String model) {
        this.model = model;
        return this.self();
    }

    public B setTextColor(String textColor) {
        this.textColor = textColor;
        return this.self();
    }

    public B setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
        return this.self();
    }

    public B setNameplateColor(String nameplateColor) {
        this.nameplateColor = nameplateColor;
        return this.self();
    }

    public B setLinkColor(String linkColor) {
        this.linkColor = linkColor;
        return this.self();
    }

    public B setLinkHoverColor(String linkHoverColor) {
        this.linkHoverColor = linkHoverColor;
        return this.self();
    }

    public B setProgressBarColor(String progressBarColor) {
        this.progressBarColor = progressBarColor;
        return this.self();
    }

    public B setProgressBarBackground(String progressBarBackground) {
        this.progressBarBackground = progressBarBackground;
        return this.self();
    }

    public B setOpenSound(SoundEvent openSound) {
        this.openSound = BuiltInRegistries.SOUND_EVENT.getKey(openSound);
        return this.self();
    }

    public B setOpenSound(ResourceLocation openSound) {
        this.openSound = openSound;
        return this.self();
    }

    public B setFlipSound(SoundEvent flipSound) {
        this.flipSound = BuiltInRegistries.SOUND_EVENT.getKey(flipSound);
        return this.self();
    }

    public B setFlipSound(ResourceLocation flipSound) {
        this.flipSound = flipSound;
        return this.self();
    }

    public B setIndexIcon(String indexIcon) {
        this.indexIcon = indexIcon;
        return this.self();
    }

    public B setIndexIcon(ItemStack indexIcon) {
        this.indexIcon = Util.serializeStack(indexIcon);
        return this.self();
    }

    public B setVersion(String version) {
        this.version = version;
        return this.self();
    }

    public B setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this.self();
    }

    public B setCreativeTab(ResourceLocation creativeTab) {
        this.creativeTab = creativeTab;
        return this.self();
    }

    public B setAdvancementsTab(ResourceLocation advancementsTab) {
        this.advancementsTab = advancementsTab;
        return this.self();
    }

    public B setCustomBookItem(ItemStack customBookItem) {
        this.customBookItem = Util.serializeStack(customBookItem);
        return this.self();
    }

    public B setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
        return this.self();
    }

    public B setDontGenerateBook(boolean dontGenerateBook) {
        this.dontGenerateBook = dontGenerateBook;
        return this.self();
    }

    public B setShowToasts(boolean showToasts) {
        this.showToasts = showToasts;
        return this.self();
    }

    public B setUseBlockyFont(boolean useBlockyFont) {
        this.useBlockyFont = useBlockyFont;
        return this.self();
    }

    public B setUseI18n() {
        this.i18n = true;
        return this.self();
    }

    public B setUseResourcepack() {
        this.useResourcepack = true;
        return this.self();
    }

    public boolean useResourcepack() {
        return this.useResourcepack;
    }

    public boolean useI18n() {
        return this.i18n;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @SuppressWarnings("unchecked")
    protected B self() {
        return (B) this;
    }

    public boolean equals(Object obj) {
        return obj instanceof BookBuilder && Objects.equals(((BookBuilder)obj).getId(), this.getId());
    }
}
