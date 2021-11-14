package com.github.minecraftschurli.patchouli_datagen.page;

import com.github.minecraftschurli.patchouli_datagen.AbstractPageBuilder;
import com.github.minecraftschurli.patchouli_datagen.EntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public class EmptyPageBuilder extends AbstractPageBuilder<EmptyPageBuilder> {
    private final boolean drawFiller;

    public EmptyPageBuilder(boolean drawFiller, EntryBuilder<?,?,?> entryBuilder) {
        super(new ResourceLocation("patchouli", "empty"), entryBuilder);
        this.drawFiller = drawFiller;
    }

    @Override
    protected void serialize(JsonObject json) {
        json.addProperty("draw_filler", drawFiller);
    }
}
