package com.ankpudding.duckystew.chickenmeat;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.function.Function;


public class ModItems{
    public static final Consumable CHICKEN_NUGGET_COMPONENT = Consumables.defaultFood()
            //Add slow falling effect and change consume time to 0.8 seconds
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20, 1), 1.0f))
            .build();

    public static final Item CHICKEN_NUGGET = register(
            "chicken_nugget",
            Item::new,
            new Item.Properties()
                    //Item max stack size
                    .stacksTo(16)

                    //Add food nutrition
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationModifier(12.8f)
                    .build()
            ,CHICKEN_NUGGET_COMPONENT)
    );

    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Chickenmeat.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register(fabricItemGroupEntries -> fabricItemGroupEntries.accept(ModItems.CHICKEN_NUGGET));
    }
}
