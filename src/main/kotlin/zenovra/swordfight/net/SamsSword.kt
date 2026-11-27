package zenovra.swordfight.net


import kotlinx.coroutines.time.delay
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item.TooltipContext
import net.minecraft.world.item.component.TooltipDisplay
import net.minecraft.world.level.Level
import java.util.function.Consumer
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.ChatFormatting
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket
import kotlinx.coroutines.delay
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket



class SamsSword(properties: Properties) : Item(properties) {
    override fun hurtEnemy(itemStack: ItemStack, mob: LivingEntity, attacker: LivingEntity) {
        mob.addEffect(MobEffectInstance(MobEffects.SLOWNESS,100 ,1), attacker)
        mob.addEffect(MobEffectInstance(MobEffects.WITHER,100 ,1), attacker)
        mob.addEffect(MobEffectInstance(MobEffects.WEAKNESS,100 ,1), attacker)
        super.hurtEnemy(itemStack, mob, attacker)
    }
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        if (level !is ServerLevel || player !is ServerPlayer) return InteractionResult.SUCCESS
        val radius = 15.0

        val targets = level.getEntitiesOfClass(
            LivingEntity::class.java,
            player.boundingBox.inflate(radius)
        ) { it !== player && it.distanceToSqr(player) <= radius * radius }

        for (target in targets) {
            target.hurtServer(level, level.damageSources().genericKill(), Float.MAX_VALUE)
        }

        val black = DustParticleOptions(0x000000, 4.0f)
        repeat(1500) {
            // random point inside a sphere
            var dx: Double;
            var dy: Double;
            var dz: Double
            do {
                dx = level.random.nextDouble() * 2 - 1
                dy = level.random.nextDouble() * 2 - 1
                dz = level.random.nextDouble() * 2 - 1
            } while (dx * dx + dy * dy + dz * dz > 1.0)

            level.sendParticles(
                black,
                player.x + dx * radius,
                player.y + 1.0 + dy * radius,
                player.z + dz * radius,
                1, 0.0, 0.0, 0.0, 0.0
            )
        }
        level.sendParticles(
            ParticleTypes.LARGE_SMOKE,
            player.x,
            player.y + 1.0,
            player.z,
            900,
            radius / 2,
            radius / 3,
            radius / 2,
            0.005
        )

        level.sendParticles(
            DustParticleOptions(0xFF0000, 1.0f),
            player.x,
            player.y + 1.0,
            player.z,
            450,
            radius / 2,
            radius / 3,
            radius / 2,
            0.005
        )

        val title = Component.literal("Reality bends to the will of one amongst you.")
            .withStyle(ChatFormatting.DARK_RED,)

        val title2 = Component.literal("The wielder of MALICE Has Activated ABSOLUTE ANNIHILATION")
            .withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD)

        val title3 = Component.literal(".")
            .withStyle(ChatFormatting.DARK_RED)

        for (p in level.server.playerList.players) {
            p.connection.send(ClientboundSetTitlesAnimationPacket(10, 60, 20)) // fade in, stay, fade out (ticks)
            p.connection.send(ClientboundSetSubtitleTextPacket(title2))
            p.connection.send(ClientboundSetTitleTextPacket(title))

        }

        level.playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.0f, 1.0f)
        player.cooldowns.addCooldown(player.getItemInHand(hand), 100)

        return InteractionResult.SUCCESS
    }
    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        display: TooltipDisplay,
        builder: Consumer<Component>,
        flag: TooltipFlag
    ) {
        builder.accept(Component.translatable("item.swordfight.sams_sword.desc"))
    }
}
