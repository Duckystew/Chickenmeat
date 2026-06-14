package com.ankpudding.duckystew.chickenmeat;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final SoundEvent RUBBER_DUCK_SQUEAK = registerSound("rubber_duck_squeak");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.fromNamespaceAndPath(Chickenmeat.MOD_ID, id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
    }

    public static void initialize(){
        Chickenmeat.LOGGER.info("Registering " + Chickenmeat.MOD_ID + " Sounds");
    }
}
