package com.github.minecraftschurlimods.patchouli_datagen;

import com.github.minecraftschurlimods.patchouli_datagen.regular.RegularBookBuilder;
import com.github.minecraftschurlimods.patchouli_datagen.translated.TranslatedBookBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class PatchouliBookProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();

    private final DataGenerator generator;
    private final String modid;
    private final boolean includeClient;
    private final boolean includeServer;

    public PatchouliBookProvider(DataGenerator gen, String modid, boolean includeClient, boolean includeServer) {
        this.generator = gen;
        this.modid = modid;
        this.includeClient = includeClient;
        this.includeServer = includeServer;
    }

    /**
     * Performs this provider's action.
     *
     * @param cache the cache
     */
    @Override
    public void run(@Nonnull CachedOutput cache) {
        addBooks((BookBuilder<?,?,?> book) -> {
            if (includeServer) {
                saveBook(cache, book.toJson(), book.getId());
            }
            if ((book.useResourcepack() && includeClient) || (!book.useResourcepack() && includeServer)) {
                for (CategoryBuilder<?,?,?> category : book.getCategories()) {
                    saveCategory(cache, category.toJson(), book.getId(), category.getId(), category.getLocale(), book.useResourcepack());
                    for (var entry : category.getEntries()) {
                        saveEntry(cache, entry.toJson(), book.getId(), entry.getId(), entry.getLocale(), book.useResourcepack());
                    }
                }
            }
        });
    }

    protected abstract void addBooks(Consumer<BookBuilder<?,?,?>> consumer);

    private void saveEntry(CachedOutput cache, JsonObject json, ResourceLocation bookId, ResourceLocation id, String locale, boolean rp) {
        Path mainOutput = generator.getOutputFolder();
        String pathSuffix = makeBookPath(bookId, rp) + "/" + locale + "/entries/" + id.getPath() + ".json";
        Path outputPath = mainOutput.resolve(pathSuffix);
        try {
            DataProvider.saveStable(cache, json, outputPath);
        } catch (IOException e) {
            LOGGER.error("Couldn't save entry to {}", outputPath, e);
        }
    }

    private void saveCategory(CachedOutput cache, JsonObject json, ResourceLocation bookId, ResourceLocation id, String locale, boolean rp) {
        Path mainOutput = generator.getOutputFolder();
        String pathSuffix = makeBookPath(bookId, rp) + "/" + locale + "/categories/" + id.getPath() + ".json";
        Path outputPath = mainOutput.resolve(pathSuffix);
        try {
            DataProvider.saveStable(cache, json, outputPath);
        } catch (IOException e) {
            LOGGER.error("Couldn't save category to {}", outputPath, e);
        }
    }

    private void saveBook(CachedOutput cache, JsonObject json, ResourceLocation bookId) {
        Path mainOutput = generator.getOutputFolder();
        String pathSuffix = makeBookPath(bookId, false) + "/book.json";
        Path outputPath = mainOutput.resolve(pathSuffix);
        try {
            DataProvider.saveStable(cache, json, outputPath);
        } catch (IOException e) {
            LOGGER.error("Couldn't save book to {}", outputPath, e);
        }
    }

    public RegularBookBuilder createBookBuilder(String id, String name, String landingText) {
        return new RegularBookBuilder(new ResourceLocation(modid, id), name, landingText, this);
    }

    public TranslatedBookBuilder createBookBuilder(String id, String name, String landingText, BiConsumer<String, String> langProvider) {
        return new TranslatedBookBuilder(new ResourceLocation(modid, id), name, landingText, langProvider, this);
    }

    private String makeBookPath(ResourceLocation bookId, boolean rp) {
        return (rp ? "assets/" : "data/") + bookId.getNamespace() + "/patchouli_books/" + bookId.getPath();
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    @Nonnull
    @Override
    public String getName() {
        return "Patchouli Book Provider";
    }
}
