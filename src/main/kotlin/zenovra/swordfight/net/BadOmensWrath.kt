package zenovra.swordfight.net

import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult

class BadOmensWrath(properties: Item.Properties) : Item(properties) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        if (level !is ServerLevel || player !is ServerPlayer) return InteractionResult.SUCCESS

        val hit = player.pick(32.0, 1.0f, false)
        if (hit is BlockHitResult) {
            level.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, hit.blockPos.x.toDouble(), hit.blockPos.y.toDouble(), hit.blockPos.z.toDouble(), 16384, 2.4, 3.2, 2.4, 0.0)
            player.cooldowns.addCooldown(player.getItemInHand(hand), 240)
        }
        return InteractionResult.SUCCESS
    }
}