package com.uraneptus.sullysmod.core.events;

import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.core.other.SMBlockTags;
import com.uraneptus.sullysmod.core.registry.SMParticleTypes;
import com.uraneptus.sullysmod.core.registry.SMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SullysMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SMEntityEvents {

    @SubscribeEvent //Here's still some stuff to fix
    public static void onProjectileHitsBlock(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();
        Level level = event.getEntity().getLevel();
        HitResult hitResult = event.getRayTraceResult();
        Vec3 vec3 = projectile.getDeltaMovement();

        if (!(projectile instanceof ShulkerBullet || projectile instanceof Fireball)) {
            if (hitResult instanceof BlockHitResult blockHitResult) {
                BlockPos pos = blockHitResult.getBlockPos();
                BlockState block = level.getBlockState(pos);
                if (block.is(SMBlockTags.PROJECTILES_BOUNCE_ON)) {
                    event.setCanceled(true);

                    Direction direction = ((BlockHitResult) hitResult).getDirection();
                    if (direction.getAxis().isHorizontal()) {
                        projectile.shoot(vec3.reverse().x, vec3.reverse().y, vec3.reverse().z, 0.4F, 1.0F); // TODO: Calculate the incoming velocity and use it as velocity here!
                    } else {
                        projectile.shoot(vec3.scale(1.0D).x + vec3.scale(1.5D).x, vec3.scale(-2.5D).y, vec3.scale(1.0D).z + vec3.scale(1.5D).z, 0.4F, 1.0F); // TODO: Calculate the incoming velocity and use it as velocity here!
                    }
                    level.addParticle(SMParticleTypes.RICOCHET.get(), projectile.getX(), projectile.getY(), projectile.getZ(), 0, 0, 0);
                    level.playLocalSound(projectile.getX(), projectile.getY(), projectile.getZ(), SMSounds.JADE_RICOCHET.get(), SoundSource.BLOCKS, 1.0F, 0.0F, false);
                }
            }
        }
    }
}
