package com.github.minecraftschurlimods.patchouli_datagen.page;

import com.github.minecraftschurlimods.patchouli_datagen.AbstractPageBuilder;
import com.github.minecraftschurlimods.patchouli_datagen.EntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public class TextPageBuilder extends AbstractPageBuilder<TextPageBuilder> {
    private final String text;
    private final String title;

    public TextPageBuilder(String text, String title, EntryBuilder<?,?,?> entryBuilder) {
        super(new ResourceLocation("patchouli", "text"), entryBuilder);
        this.text = text;
        this.title = title;
    }

    public TextPageBuilder(String text, EntryBuilder<?,?,?> entryBuilder) {
        super(new ResourceLocation("patchouli", "text"), entryBuilder);
        this.text = text;
        this.title = null;
    }

    @Override
    protected void serialize(JsonObject json) {
        json.addProperty("text", text);
        if (title != null) {
            json.addProperty("title", title);
        }
    }
}
