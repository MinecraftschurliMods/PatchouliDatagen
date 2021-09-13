package com.github.minecraftschurli.patchouli_datagen.page;

import com.github.minecraftschurli.patchouli_datagen.AbstractPageBuilder;
import com.github.minecraftschurli.patchouli_datagen.EntryBuilder;
import com.google.gson.JsonObject;

public class LinkPageBuilder extends AbstractPageBuilder<LinkPageBuilder> {
    private final String url;
    private final String linkText;

    public LinkPageBuilder(String url, String linkText, EntryBuilder entryBuilder) {
        super("link", entryBuilder);
        this.url = url;
        this.linkText = linkText;
    }

    @Override
    protected void serialize(JsonObject json) {
        json.addProperty("url", url);
        json.addProperty("link_text", linkText);
    }
}
