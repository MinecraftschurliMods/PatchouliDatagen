package com.github.minecraftschurli.patchouli_datagen;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractPageBuilder<T extends AbstractPageBuilder<T>> {
    protected final EntryBuilder<?,?,?> parent;
    private final   ResourceLocation    type;
    private         String              advancement;
    private         String              flag;
    private         String              anchor;

    protected AbstractPageBuilder(ResourceLocation type, EntryBuilder<?,?,?> parent) {
        this.parent = parent;
        this.type = type;
    }

    JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", this.type.toString());
        if (this.advancement != null) {
            json.addProperty("advancement", this.advancement);
        }
        if (this.flag != null) {
            json.addProperty("flag", this.flag);
        }
        if (this.anchor != null) {
            json.addProperty("anchor", this.anchor);
        }
        this.serialize(json);
        return json;
    }

    protected abstract void serialize(JsonObject var1);

    @SuppressWarnings("unchecked")
    public <E extends EntryBuilder<?,?,?>> E build() {
        return (E) this.parent;
    }

    public T setAdvancement(String advancement) {
        this.advancement = advancement;
        return self();
    }

    public T setFlag(String flag) {
        this.flag = flag;
        return self();
    }

    public T setAnchor(String anchor) {
        this.anchor = anchor;
        return self();
    }

    @SuppressWarnings("unchecked")
    protected <S extends T> S self() {
        return (S) this;
    }
}
