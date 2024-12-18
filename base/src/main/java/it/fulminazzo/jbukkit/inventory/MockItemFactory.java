package it.fulminazzo.jbukkit.inventory;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.jbukkit.Equable;
import it.fulminazzo.jbukkit.NotImplementedException;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a mock implementation for {@link ItemFactory}.
 */
public class MockItemFactory implements ItemFactory {

    @Override
    public @NotNull ItemMeta getItemMeta(@NotNull Material material) {
        @Nullable String className = getItemMetaName(material);
        if (className == null) throw new IllegalArgumentException("Unknown item meta type: " + material);
        Class<?> clazz = ReflectionUtils.getClass(getClass().getPackage().getName() + ".meta." + className);
        Refl<?> object;
        try {
            object = new Refl<>(clazz);
        } catch (IllegalArgumentException e) {
            object = new Refl<>(clazz, material);
        }
        return (ItemMeta) object.getObject();
    }

    @Override
    public boolean isApplicable(@Nullable ItemMeta itemMeta, @Nullable ItemStack itemStack) throws IllegalArgumentException {
        return itemStack != null && isApplicable(itemMeta, itemStack.getType());
    }

    @Override
    public boolean isApplicable(@Nullable ItemMeta itemMeta, @Nullable Material material) throws IllegalArgumentException {
        return material != null && itemMeta != null &&
                itemMeta.getClass().getSimpleName().equals(getItemMetaName(material));
    }

    /**
     * Gives the appropriate mock class from the given {@link Material}.
     *
     * @param material the material
     * @return the class name
     */
    protected @Nullable String getItemMetaName(final @NotNull Material material) {
        switch (material.name()) {
            case "AIR":
                return null;
            case "WRITTEN_BOOK":
                return "MockBookMeta";
            case "WRITABLE_BOOK":
                return "MockWritableBookMeta";
            case "CREEPER_HEAD":
            case "CREEPER_WALL_HEAD":
            case "DRAGON_HEAD":
            case "DRAGON_WALL_HEAD":
            case "PIGLIN_HEAD":
            case "PIGLIN_WALL_HEAD":
            case "PLAYER_HEAD":
            case "PLAYER_WALL_HEAD":
            case "SKELETON_SKULL":
            case "SKELETON_WALL_SKULL":
            case "WITHER_SKELETON_SKULL":
            case "WITHER_SKELETON_WALL_SKULL":
            case "ZOMBIE_HEAD":
            case "ZOMBIE_WALL_HEAD":
                return "MockSkullMeta";
            case "CHAINMAIL_HELMET":
            case "CHAINMAIL_CHESTPLATE":
            case "CHAINMAIL_LEGGINGS":
            case "CHAINMAIL_BOOTS":
            case "DIAMOND_HELMET":
            case "DIAMOND_CHESTPLATE":
            case "DIAMOND_LEGGINGS":
            case "DIAMOND_BOOTS":
            case "GOLDEN_HELMET":
            case "GOLDEN_CHESTPLATE":
            case "GOLDEN_LEGGINGS":
            case "GOLDEN_BOOTS":
            case "IRON_HELMET":
            case "IRON_CHESTPLATE":
            case "IRON_LEGGINGS":
            case "IRON_BOOTS":
            case "NETHERITE_HELMET":
            case "NETHERITE_CHESTPLATE":
            case "NETHERITE_LEGGINGS":
            case "NETHERITE_BOOTS":
            case "TURTLE_HELMET":
                return "MockArmorMeta";
            case "LEATHER_HELMET":
            case "LEATHER_CHESTPLATE":
            case "LEATHER_LEGGINGS":
            case "LEATHER_BOOTS":
                return "MockColorableArmorMeta";
            case "LEATHER_HORSE_ARMOR":
                return "MockLeatherArmorMeta";
            case "POTION":
            case "SPLASH_POTION":
            case "LINGERING_POTION":
            case "TIPPED_ARROW":
                return "MockPotionMeta";
            case "FILLED_MAP":
                return "MockMapMeta";
            case "FIREWORK_ROCKET":
                return "MockFireworkMeta";
            case "FIREWORK_STAR":
                return "MockFireworkEffectMeta";
            case "ENCHANTED_BOOK":
                return "MockEnchantmentStorageMeta";
            case "BLACK_BANNER":
            case "BLACK_WALL_BANNER":
            case "BLUE_BANNER":
            case "BLUE_WALL_BANNER":
            case "BROWN_BANNER":
            case "BROWN_WALL_BANNER":
            case "CYAN_BANNER":
            case "CYAN_WALL_BANNER":
            case "GRAY_BANNER":
            case "GRAY_WALL_BANNER":
            case "GREEN_BANNER":
            case "GREEN_WALL_BANNER":
            case "LIGHT_BLUE_BANNER":
            case "LIGHT_BLUE_WALL_BANNER":
            case "LIGHT_GRAY_BANNER":
            case "LIGHT_GRAY_WALL_BANNER":
            case "LIME_BANNER":
            case "LIME_WALL_BANNER":
            case "MAGENTA_BANNER":
            case "MAGENTA_WALL_BANNER":
            case "ORANGE_BANNER":
            case "ORANGE_WALL_BANNER":
            case "PINK_BANNER":
            case "PINK_WALL_BANNER":
            case "PURPLE_BANNER":
            case "PURPLE_WALL_BANNER":
            case "RED_BANNER":
            case "RED_WALL_BANNER":
            case "WHITE_BANNER":
            case "WHITE_WALL_BANNER":
            case "YELLOW_BANNER":
            case "YELLOW_WALL_BANNER":
                return "MockBannerMeta";
            case "ALLAY_SPAWN_EGG":
            case "AXOLOTL_SPAWN_EGG":
            case "BAT_SPAWN_EGG":
            case "BEE_SPAWN_EGG":
            case "BLAZE_SPAWN_EGG":
            case "CAT_SPAWN_EGG":
            case "CAMEL_SPAWN_EGG":
            case "CAVE_SPIDER_SPAWN_EGG":
            case "CHICKEN_SPAWN_EGG":
            case "COD_SPAWN_EGG":
            case "COW_SPAWN_EGG":
            case "CREEPER_SPAWN_EGG":
            case "DOLPHIN_SPAWN_EGG":
            case "DONKEY_SPAWN_EGG":
            case "DROWNED_SPAWN_EGG":
            case "ELDER_GUARDIAN_SPAWN_EGG":
            case "ENDER_DRAGON_SPAWN_EGG":
            case "ENDERMAN_SPAWN_EGG":
            case "ENDERMITE_SPAWN_EGG":
            case "EVOKER_SPAWN_EGG":
            case "FOX_SPAWN_EGG":
            case "FROG_SPAWN_EGG":
            case "GHAST_SPAWN_EGG":
            case "GLOW_SQUID_SPAWN_EGG":
            case "GOAT_SPAWN_EGG":
            case "GUARDIAN_SPAWN_EGG":
            case "HOGLIN_SPAWN_EGG":
            case "HORSE_SPAWN_EGG":
            case "HUSK_SPAWN_EGG":
            case "IRON_GOLEM_SPAWN_EGG":
            case "LLAMA_SPAWN_EGG":
            case "MAGMA_CUBE_SPAWN_EGG":
            case "MOOSHROOM_SPAWN_EGG":
            case "MULE_SPAWN_EGG":
            case "OCELOT_SPAWN_EGG":
            case "PANDA_SPAWN_EGG":
            case "PARROT_SPAWN_EGG":
            case "PHANTOM_SPAWN_EGG":
            case "PIGLIN_BRUTE_SPAWN_EGG":
            case "PIGLIN_SPAWN_EGG":
            case "PIG_SPAWN_EGG":
            case "PILLAGER_SPAWN_EGG":
            case "POLAR_BEAR_SPAWN_EGG":
            case "PUFFERFISH_SPAWN_EGG":
            case "RABBIT_SPAWN_EGG":
            case "RAVAGER_SPAWN_EGG":
            case "SALMON_SPAWN_EGG":
            case "SHEEP_SPAWN_EGG":
            case "SHULKER_SPAWN_EGG":
            case "SILVERFISH_SPAWN_EGG":
            case "SKELETON_HORSE_SPAWN_EGG":
            case "SKELETON_SPAWN_EGG":
            case "SLIME_SPAWN_EGG":
            case "SNIFFER_SPAWN_EGG":
            case "SNOW_GOLEM_SPAWN_EGG":
            case "SPIDER_SPAWN_EGG":
            case "SQUID_SPAWN_EGG":
            case "STRAY_SPAWN_EGG":
            case "STRIDER_SPAWN_EGG":
            case "TADPOLE_SPAWN_EGG":
            case "TRADER_LLAMA_SPAWN_EGG":
            case "TROPICAL_FISH_SPAWN_EGG":
            case "TURTLE_SPAWN_EGG":
            case "VEX_SPAWN_EGG":
            case "VILLAGER_SPAWN_EGG":
            case "VINDICATOR_SPAWN_EGG":
            case "WANDERING_TRADER_SPAWN_EGG":
            case "WARDEN_SPAWN_EGG":
            case "WITCH_SPAWN_EGG":
            case "WITHER_SKELETON_SPAWN_EGG":
            case "WITHER_SPAWN_EGG":
            case "WOLF_SPAWN_EGG":
            case "ZOGLIN_SPAWN_EGG":
            case "ZOMBIE_HORSE_SPAWN_EGG":
            case "ZOMBIE_SPAWN_EGG":
            case "ZOMBIE_VILLAGER_SPAWN_EGG":
            case "ZOMBIFIED_PIGLIN_SPAWN_EGG":
                return "MockSpawnEggMeta";
            case "KNOWLEDGE_BOOK":
                return "MockKnowledgeBookMeta";
            case "FURNACE":
            case "CHEST":
            case "TRAPPED_CHEST":
            case "JUKEBOX":
            case "DISPENSER":
            case "DROPPER":
            case "ACACIA_HANGING_SIGN":
            case "ACACIA_SIGN":
            case "ACACIA_WALL_HANGING_SIGN":
            case "ACACIA_WALL_SIGN":
            case "BAMBOO_HANGING_SIGN":
            case "BAMBOO_SIGN":
            case "BAMBOO_WALL_HANGING_SIGN":
            case "BAMBOO_WALL_SIGN":
            case "BIRCH_HANGING_SIGN":
            case "BIRCH_SIGN":
            case "BIRCH_WALL_HANGING_SIGN":
            case "BIRCH_WALL_SIGN":
            case "CHERRY_HANGING_SIGN":
            case "CHERRY_SIGN":
            case "CHERRY_WALL_HANGING_SIGN":
            case "CHERRY_WALL_SIGN":
            case "CRIMSON_HANGING_SIGN":
            case "CRIMSON_SIGN":
            case "CRIMSON_WALL_HANGING_SIGN":
            case "CRIMSON_WALL_SIGN":
            case "DARK_OAK_HANGING_SIGN":
            case "DARK_OAK_SIGN":
            case "DARK_OAK_WALL_HANGING_SIGN":
            case "DARK_OAK_WALL_SIGN":
            case "JUNGLE_HANGING_SIGN":
            case "JUNGLE_SIGN":
            case "JUNGLE_WALL_HANGING_SIGN":
            case "JUNGLE_WALL_SIGN":
            case "MANGROVE_HANGING_SIGN":
            case "MANGROVE_SIGN":
            case "MANGROVE_WALL_HANGING_SIGN":
            case "MANGROVE_WALL_SIGN":
            case "OAK_HANGING_SIGN":
            case "OAK_SIGN":
            case "OAK_WALL_HANGING_SIGN":
            case "OAK_WALL_SIGN":
            case "SPRUCE_HANGING_SIGN":
            case "SPRUCE_SIGN":
            case "SPRUCE_WALL_HANGING_SIGN":
            case "SPRUCE_WALL_SIGN":
            case "WARPED_HANGING_SIGN":
            case "WARPED_SIGN":
            case "WARPED_WALL_HANGING_SIGN":
            case "WARPED_WALL_SIGN":
            case "SPAWNER":
            case "BREWING_STAND":
            case "ENCHANTING_TABLE":
            case "COMMAND_BLOCK":
            case "REPEATING_COMMAND_BLOCK":
            case "CHAIN_COMMAND_BLOCK":
            case "BEACON":
            case "DAYLIGHT_DETECTOR":
            case "HOPPER":
            case "COMPARATOR":
            case "SHIELD":
            case "STRUCTURE_BLOCK":
            case "SHULKER_BOX":
            case "WHITE_SHULKER_BOX":
            case "ORANGE_SHULKER_BOX":
            case "MAGENTA_SHULKER_BOX":
            case "LIGHT_BLUE_SHULKER_BOX":
            case "YELLOW_SHULKER_BOX":
            case "LIME_SHULKER_BOX":
            case "PINK_SHULKER_BOX":
            case "GRAY_SHULKER_BOX":
            case "LIGHT_GRAY_SHULKER_BOX":
            case "CYAN_SHULKER_BOX":
            case "PURPLE_SHULKER_BOX":
            case "BLUE_SHULKER_BOX":
            case "BROWN_SHULKER_BOX":
            case "GREEN_SHULKER_BOX":
            case "RED_SHULKER_BOX":
            case "BLACK_SHULKER_BOX":
            case "ENDER_CHEST":
            case "BARREL":
            case "BELL":
            case "BLAST_FURNACE":
            case "CAMPFIRE":
            case "SOUL_CAMPFIRE":
            case "JIGSAW":
            case "LECTERN":
            case "SMOKER":
            case "BEEHIVE":
            case "BEE_NEST":
            case "SCULK_CATALYST":
            case "SCULK_SHRIEKER":
            case "SCULK_SENSOR":
            case "CALIBRATED_SCULK_SENSOR":
            case "CHISELED_BOOKSHELF":
            case "DECORATED_POT":
            case "SUSPICIOUS_SAND":
            case "SUSPICIOUS_GRAVEL":
                return "MockBlockStateMeta";
            case "TROPICAL_FISH_BUCKET":
                return "MockTropicalFishBucketMeta";
            case "AXOLOTL_BUCKET":
                return "MockAxolotlBucketMeta";
            case "CROSSBOW":
                return "MockCrossbowMeta";
            case "SUSPICIOUS_STEW":
                return "MockSuspiciousStewMeta";
            case "COMPASS":
                return "MockCompassMeta";
            case "BUNDLE":
                return "MockBundleMeta";
            case "GOAT_HORN":
                return "MockMusicInstrumentMeta";
            default:
                return "MockItemMeta";
        }
    }

    @Override
    public boolean equals(@Nullable ItemMeta meta1, @Nullable ItemMeta meta2) throws IllegalArgumentException {
        return Equable.equals(meta1, meta2);
    }

    @Override
    public ItemMeta asMetaFor(@NotNull ItemMeta itemMeta, @NotNull ItemStack itemStack) throws IllegalArgumentException {
        return asMetaFor(itemMeta, itemStack.getType());
    }

    @Override
    public @Nullable ItemMeta asMetaFor(@NotNull ItemMeta itemMeta, @NotNull Material material) throws IllegalArgumentException {
        String metaName = getItemMetaName(material);
        if (metaName == null) return null;
        ItemMeta actualMeta = getItemMeta(material);
        ReflectionUtils.getFields(actualMeta).stream()
                .map(ReflectionUtils::setAccessible)
                .map(f -> f.orElseGet(null))
                .filter(Objects::nonNull)
                .map(f -> {
                    try {
                        return new Tuple<>(f, f.get(itemMeta));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(t -> {
                    try {
                        t.getKey().set(actualMeta, t.getValue());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        return actualMeta;
    }

    @Override
    public @NotNull Color getDefaultLeatherColor() {
        return Color.MAROON;
    }

    @Override
    public @NotNull Material updateMaterial(@NotNull ItemMeta meta, @NotNull Material material) throws IllegalArgumentException {
        throw new NotImplementedException();
    }

    public @NotNull ItemStack createItemStack(@NotNull String input) throws IllegalArgumentException {
        throw new NotImplementedException();
    }

    public @Nullable Material getSpawnEgg(@NotNull EntityType type) {
        try {
            return Material.valueOf(type.name() + "_SPAWN_EGG");
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public @NotNull ItemStack enchantItem(@NotNull Entity entity, @NotNull ItemStack item, int level, boolean allowTreasures) {
        throw new NotImplementedException();
    }

    public @NotNull ItemStack enchantItem(@NotNull World world, @NotNull ItemStack item, int level, boolean allowTreasures) {
        throw new NotImplementedException();
    }

    public @NotNull ItemStack enchantItem(@NotNull ItemStack item, int level, boolean allowTreasures) {
        throw new NotImplementedException();
    }

}
