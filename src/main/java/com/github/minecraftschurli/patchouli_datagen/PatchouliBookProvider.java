package com.github.minecraftschurli.patchouli_datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;

import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

public abstract class PatchouliBookProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    private final DataGenerator generator;
    private final String locale;
    private final String modid;
    private final boolean includeClient;
    private final boolean includeServer;

    public PatchouliBookProvider(DataGenerator gen, String modid, String locale, boolean includeClient, boolean includeServer) {
        this.generator = gen;
        this.modid = modid;
        this.locale = locale;
        this.includeClient = includeClient;
        this.includeServer = includeServer;
    }

    /**
     * Performs this provider's action.
     *
     * @param cache the cache
     */
    @Override
    public void run(@Nonnull HashCache cache) {
        addBooks(book -> {
            if (includeServer) {
                saveBook(cache, book.toJson(), book.getId());
            }
            if ((book.useResourcepack() && includeClient) || (!book.useResourcepack() && includeServer)) {
                for (CategoryBuilder category : book.getCategories()) {
                    saveCategory(cache, category.toJson(), book.getId(), category.getId(), book.useResourcepack());
                    for (EntryBuilder entry : category.getEntries()) {
                        saveEntry(cache, entry.toJson(), book.getId(), entry.getId(), book.useResourcepack());
                    }
                }
            }
        });
    }

    protected abstract void addBooks(Consumer<BookBuilder> consumer);

    private void saveEntry(HashCache cache, JsonObject json, ResourceLocation bookId, ResourceLocation id, boolean rp) {
        Path mainOutput = generator.getOutputFolder();
        String pathSuffix = makeBookPath(bookId, rp) + "/" + locale + "/entries/" + id.getPath() + ".json";
        Path outputPath = mainOutput.resolve(pathSuffix);
        try {
            DataProvider.save(GSON, cache, json, outputPath);
        } catch (IOException e) {
            LOGGER.error("Couldn't save entry to {}", outputPath, e);
        }
    }

    private void saveCategory(HashCache cache, JsonObject json, ResourceLocation bookId, ResourceLocation id, boolean rp) {
        Path mainOutput = generator.getOutputFolder();
        String pathSuffix = makeBookPath(bookId, rp) + "/" + locale + "/categories/" + id.getPath() + ".json";
        Path outputPath = mainOutput.resolve(pathSuffix);
        try {
            DataProvider.save(GSON, cache, json, outputPath);
        } catch (IOException e) {
            LOGGER.error("Couldn't save category to {}", outputPath, e);
        }
    }

    private void saveBook(HashCache cache, JsonObject json, ResourceLocation bookId) {
        Path mainOutput = generator.getOutputFolder();
        String pathSuffix = makeBookPath(bookId, false) + "/book.json";
        Path outputPath = mainOutput.resolve(pathSuffix);
        try {
            DataProvider.save(GSON, cache, json, outputPath);
        } catch (IOException e) {
            LOGGER.error("Couldn't save book to {}", outputPath, e);
        }
    }

    public BookBuilder createBookBuilder(String id) {
        return new BookBuilder(new ResourceLocation(modid, id));
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
