package zenovra.swordfight.net

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.server.level.ServerLevel

class eisenportteleport(properties: Item.Properties) : Item(properties) {

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        if (level !is ServerLevel || player !is ServerPlayer) return InteractionResult.SUCCESS
            player.cooldowns.addCooldown(player.getItemInHand(hand), 240)
        return EisenportTeleportLogic.EisenportTeleportExecute(player, level)
    }

}
