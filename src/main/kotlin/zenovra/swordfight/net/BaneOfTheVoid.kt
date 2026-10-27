package zenovra.swordfight.net

import net.minecraft.world.item.Item
import net.minecraft.world.level.ClipContext
import net.minecraft.world.phys.HitResult
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3


class BaneOfTheVoid(properties: Item.Properties) : Item(properties) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        if (level !is ServerLevel || player !is ServerPlayer) return InteractionResult.SUCCESS

        val maxDistance = 25.0
        val dir = player.lookAngle
        val start = player.eyePosition


        val hit = level.clip(
            ClipContext(
                start,
                start.add(dir.scale(maxDistance)),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
            )
        )

        var dist = if (hit.type == HitResult.Type.MISS) maxDistance else start.distanceTo(hit.location)


        while (dist > 0.0 && !level.noCollision(player, player.boundingBox.move(dir.scale(dist)))) {
            dist -= 0.25
        }

        val from = player.eyePosition
        val dest = from.add(dir.scale(dist))


        level.sendParticles(ParticleTypes.ASH, from.x, from.y + 1.0, from.z, 800, 0.3, 0.6, 0.3, 3.0)
        player.teleportTo(dest.x, dest.y - player.eyeHeight, dest.z)
        player.fallDistance = 0.0
        level.sendParticles(ParticleTypes.ASH, dest.x, dest.y + 1.0, dest.z, 400, 0.3, 0.6, 0.3, 0.2)
        level.playSound(null, dest.x, dest.y, dest.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 1f)

        player.cooldowns.addCooldown(player.getItemInHand(hand), 20)
        return InteractionResult.SUCCESS
    }
}