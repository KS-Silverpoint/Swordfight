package zenovra.swordfight.net

import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.util.Mth
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.ProjectileUtil
import net.minecraft.world.item.Item
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult

class PrismaticBlade(properties: Properties) : Item(properties) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        if (level !is ServerLevel) return InteractionResult.SUCCESS

        val maxDistance = 30.0
        val start = player.eyePosition
        val dir = player.lookAngle
        val end = start.add(dir.scale(maxDistance))

        val blockHit = level.clip(
            ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player)
        )
        var beamEnd = if (blockHit.type == HitResult.Type.MISS) end else blockHit.location

        val searchBox = player.boundingBox.expandTowards(dir.scale(start.distanceTo(beamEnd))).inflate(1.0)
        val entityHit = ProjectileUtil.getEntityHitResult(
            player,
            start,
            beamEnd,
            searchBox,
            { entity -> entity is LivingEntity && entity != player },
            start.distanceToSqr(beamEnd)
        )
        if (entityHit != null) {
            beamEnd = entityHit.location
            entityHit.entity.hurtServer(level, level.damageSources().playerAttack(player), 8f)
        }

        // make the LAZERBEAM
        val length = start.distanceTo(beamEnd)
        var d = 1.0
        while (d < length) {
            val p = start.add(dir.scale(d))

            val hue = ((d * 0.04) + (level.gameTime * 0.02)) % 1.0
            val color = Mth.hsvToRgb(hue.toFloat(), 1f, 1f)

            level.sendParticles(DustParticleOptions(color, 1.2f), p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0)
            d += 0.4
        }

        level.playSound(null, player.x, player.y, player.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1f, 2f)
        player.cooldowns.addCooldown(player.getItemInHand(hand), 20)
        return InteractionResult.SUCCESS
    }

}