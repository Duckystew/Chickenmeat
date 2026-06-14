package com.ankpudding.duckystew.chickenmeat.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class SoundItem extends Item {
    public final SoundEvent useSound;

    public SoundItem(Properties properties, SoundEvent sound) {
        super(properties);
        useSound = sound;
    }

    @Override
    public @NonNull InteractionResult use(final Level level, final @NonNull Player player, final @NonNull InteractionHand hand) {
        if (level.isClientSide()) {
            player.playSound(SoundEvents.COPPER_BREAK);
        }
        else {
            level.playSound(
                    player,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    useSound,
                    SoundSource.PLAYERS,
                    1.0f,
                    1.0f
            );
        }

        return InteractionResult.PASS;
    }
}
