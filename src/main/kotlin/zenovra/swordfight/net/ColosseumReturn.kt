package zenovra.swordfight.net

import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.minecraft.world.level.portal.TeleportTransition
import net.minecraft.world.phys.Vec3
import java.util.UUID

object ColosseumReturn {
    private val savedPos = HashMap<UUID, Vec3>()
    private val savedDim = HashMap<UUID, ResourceKey<Level>>()

    fun SaveLoc(player: ServerPlayer, level: ServerLevel) {
        savedPos[player.uuid] = player.position()
        savedDim[player.uuid] = level.dimension()
    }

    fun BackLoc(player: ServerPlayer, level: ServerLevel) {
        val pos = savedPos[player.uuid] ?: return
        val dim = savedDim[player.uuid] ?: return
        val target = level.server.getLevel(dim) ?: return
        player.teleport(
            TeleportTransition(target, pos, Vec3.ZERO, player.yRot, player.xRot, TeleportTransition.DO_NOTHING )
        )
        savedPos.remove(player.uuid)
        savedDim.remove(player.uuid)
    }
}