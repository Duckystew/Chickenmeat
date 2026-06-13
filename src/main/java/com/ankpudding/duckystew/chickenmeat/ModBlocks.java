package com.ankpudding.duckystew.chickenmeat;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

public class ModBlocks {
    public static final BlockBehaviour.Properties CHICKEN_BLOCK_PROPERTIES = BlockBehaviour.Properties.of()
            .sound(SoundType.SLIME_BLOCK)
            .strength(1.0f);

    public static final Block RAW_CHICKEN_BLOCK = register(
            "raw_chicken_block",
            Block::new,
            CHICKEN_BLOCK_PROPERTIES
            ,
            true
    );

    public static final Block COOKED_CHICKEN_BLOCK = register(
            "cooked_chicken_block",
            Block::new,
            CHICKEN_BLOCK_PROPERTIES
            ,
            true
    );

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        ResourceKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.setId(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            ResourceKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }
        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Chickenmeat.MOD_ID, name));
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Chickenmeat.MOD_ID, name));
    }

    public static void initialize(){
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register((itemGroup) -> {
            itemGroup.accept(ModBlocks.RAW_CHICKEN_BLOCK.asItem());
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register((itemGroup) -> {
            itemGroup.accept(ModBlocks.COOKED_CHICKEN_BLOCK.asItem());
        });
    }
}
