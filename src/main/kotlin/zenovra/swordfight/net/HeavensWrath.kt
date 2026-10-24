package zenovra.swordfight.net

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.minecraft.core.particles.ParticleTypes


class HeavensWrath(properties: Item.Properties) : Item(properties) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        if (level !is ServerLevel || player !is ServerPlayer) return InteractionResult.SUCCESS

        val look = player.lookAngle
        val flat = Vec3(look.x, look.y, look.z).normalize()

        player.deltaMovement = Vec3(flat.x * 2, flat.y * 1.4, flat.z * 2)
        player.connection.send(ClientboundSetEntityMotionPacket(player))
        level.sendParticles(ParticleTypes.CLOUD, player.x, player.y + 0.1, player.z, 128, 0.8, 0.1, 0.8, 0.02)

        player.cooldowns.addCooldown(player.getItemInHand(hand), 40)
        return InteractionResult.SUCCESS
    }
}