package com.github.minecraftschurlimods.patchouli_datagen.page;

import com.github.minecraftschurlimods.patchouli_datagen.AbstractPageBuilder;
import com.github.minecraftschurlimods.patchouli_datagen.EntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public class LinkPageBuilder extends AbstractPageBuilder<LinkPageBuilder> {
    private final String url;
    private final String linkText;
    private String text;
    private String title;

    public LinkPageBuilder(String url, String linkText, EntryBuilder<?,?,?> entryBuilder) {
        super(new ResourceLocation("patchouli", "link"), entryBuilder);
        this.url = url;
        this.linkText = linkText;
    }

    protected void serialize(JsonObject json) {
        json.addProperty("url", this.url);
        json.addProperty("link_text", this.linkText);
        if (this.text != null) {
            json.addProperty("text", this.text);
        }

        if (this.title != null) {
            json.addProperty("title", this.title);
        }

    }

    public LinkPageBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public LinkPageBuilder setText(String text) {
        this.text = text;
        return this;
    }
}
