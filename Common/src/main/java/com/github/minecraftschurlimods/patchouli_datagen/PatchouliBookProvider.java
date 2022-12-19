package com.github.minecraftschurlimods.patchouli_datagen;

import com.github.minecraftschurlimods.patchouli_datagen.regular.RegularBookBuilder;
import com.github.minecraftschurlimods.patchouli_datagen.translated.TranslatedBookBuilder;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class PatchouliBookProvider implements DataProvider {
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final String modid;
    private final boolean includeClient;
    private final boolean includeServer;

    public PatchouliBookProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modid, boolean includeClient, boolean includeServer) {
        this.output = output;
        this.lookupProvider = lookupProvider;
        this.modid = modid;
        this.includeClient = includeClient;
        this.includeServer = includeServer;
    }

    /**
     * Performs this provider's action.
     *
     * @param cache the cache
     * @return
     */
    @Override
    public CompletableFuture<?> run(@Nonnull CachedOutput cache) {
        return lookupProvider.thenCompose(provider -> {
            List<CompletableFuture<?>> futures = new ArrayList<>();
            addBooks(provider, book -> {
                if (includeServer) {
                    futures.add(saveBook(cache, book.toJson(), book.getId()));
                }
                if ((book.useResourcepack() && includeClient) || (!book.useResourcepack() && includeServer)) {
                    for (CategoryBuilder<?,?,?> category : book.getCategories()) {
                        futures.add(saveCategory(cache, category.toJson(), book.getId(), category.getId(), category.getLocale(), book.useResourcepack()));
                        for (var entry : category.getEntries()) {
                            futures.add(saveEntry(cache, entry.toJson(), book.getId(), entry.getId(), entry.getLocale(), book.useResourcepack()));
                        }
                    }
                }
            });
            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        });
    }

    protected abstract void addBooks(HolderLookup.Provider lookupProvider, Consumer<BookBuilder<?,?,?>> consumer);

    private CompletableFuture<?> saveEntry(CachedOutput cache, JsonObject json, ResourceLocation bookId, ResourceLocation id, String locale, boolean rp) {
        Path mainOutput = output.getOutputFolder();
        String pathSuffix = makeBookPath(bookId, rp) + "/" + locale + "/entries/" + id.getPath() + ".json";
        Path outputPath = mainOutput.resolve(pathSuffix);
        return DataProvider.saveStable(cache, json, outputPath);
    }

    private CompletableFuture<?> saveCategory(CachedOutput cache, JsonObject json, ResourceLocation bookId, ResourceLocation id, String locale, boolean rp) {
        Path mainOutput = output.getOutputFolder();
        String pathSuffix = makeBookPath(bookId, rp) + "/" + locale + "/categories/" + id.getPath() + ".json";
        Path outputPath = mainOutput.resolve(pathSuffix);
        return DataProvider.saveStable(cache, json, outputPath);
    }

    private CompletableFuture<?> saveBook(CachedOutput cache, JsonObject json, ResourceLocation bookId) {
        Path mainOutput = output.getOutputFolder();
        String pathSuffix = makeBookPath(bookId, false) + "/book.json";
        Path outputPath = mainOutput.resolve(pathSuffix);
        return DataProvider.saveStable(cache, json, outputPath);
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
