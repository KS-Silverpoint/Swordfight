package zenovra.swordfight.net

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item.TooltipContext
import net.minecraft.world.item.component.TooltipDisplay
import java.util.function.Consumer

class ShardOfOblivion(properties: Properties) : Item(properties) {
    override fun hurtEnemy(itemStack: ItemStack, mob: LivingEntity, attacker: LivingEntity) {
        mob.addEffect(MobEffectInstance(MobEffects.SLOWNESS,100 ,1), attacker)
        super.hurtEnemy(itemStack, mob, attacker)
    }
    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        display: TooltipDisplay,
        builder: Consumer<Component>,
        flag: TooltipFlag
    ) {
        builder.accept(Component.translatable("item.swordfight.shard_of_oblivion.desc"))
    }
}