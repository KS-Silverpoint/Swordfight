package zenovra.swordfight.net

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResult
import net.minecraft.world.level.portal.TeleportTransition
import net.minecraft.world.phys.Vec3

object EisenportTeleportLogic {

    private const val SCALE = 16.0

    private val dimKey = ResourceKey.create(
        Registries.DIMENSION,
        Identifier.fromNamespaceAndPath("swordfight", "eisenport")
    )

    fun EisenportTeleportExecute(
        player: ServerPlayer,
        level: ServerLevel
    ): InteractionResult {

        if (level.dimension() == dimKey) {

            val overworld = level.server.overworld()

            val landing = Vec3(
                player.x * SCALE,
                196.0,
                player.z * SCALE
            )

            player.teleport(
                TeleportTransition(
                    overworld,
                    landing,
                    Vec3.ZERO,
                    player.yRot,
                    player.xRot,
                    TeleportTransition.DO_NOTHING
                )
            )
            player.addEffect(MobEffectInstance(MobEffects.SLOW_FALLING, 360, 0, false, false, false))

        } else {

            val eisen = level.server.getLevel(dimKey)
                ?: return InteractionResult.FAIL

            ColosseumReturn.SaveLoc(player, level)

            val landing = Vec3(
                player.x / SCALE,
                -60.0,
                player.z / SCALE
            )

            player.teleport(
                TeleportTransition(
                    eisen,
                    landing,
                    Vec3.ZERO,
                    player.yRot,
                    player.xRot,
                    TeleportTransition.DO_NOTHING
                )
            )
        }

        return InteractionResult.SUCCESS
    }
}