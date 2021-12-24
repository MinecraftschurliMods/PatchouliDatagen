package com.github.minecraftschurlimods.patchouli_datagen.page;

import com.github.minecraftschurlimods.patchouli_datagen.AbstractPageBuilder;
import com.github.minecraftschurlimods.patchouli_datagen.EntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public class MultiblockPageBuilder extends AbstractPageBuilder<MultiblockPageBuilder> {
    private final String name;
    private final ResourceLocation multiblock;
    private       String text;

    public MultiblockPageBuilder(String name, ResourceLocation multiblock, EntryBuilder<?, ?, ?> parent) {
        super(new ResourceLocation("patchouli", "multiblock"), parent);
        this.name = name;
        this.multiblock = multiblock;
    }

    @Override
    protected void serialize(JsonObject var1) {
        var1.addProperty("name", this.name);
        var1.addProperty("multiblock_id", this.multiblock.toString());
        if (this.text != null) {
            var1.addProperty("text", this.text);
        }
    }

    public MultiblockPageBuilder setText(String text) {
        this.text = text;
        return this;
    }
}
