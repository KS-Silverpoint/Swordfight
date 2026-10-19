package zenovra.swordfight.net

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.core.Registry
import net.minecraft.world.item.ToolMaterial
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.item.component.ItemAttributeModifiers
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTabs

object ModItems {
    val Zorn: Item = register("zorn")
    val Vom: Item = register("vom")
    val Ochs: Item = register("ochs")
    val Ice_Shard: Item = register("ice_shard")
    val Orb_Of_Eisenport: Item = register("orb_of_eisenport")

    val Heavens_wrath: Item = register(
        "heavens_wrath",
        Item.Properties()
            .sword(ToolMaterial.IRON, 3f, -2.4f)
            .attributes(
                ItemAttributeModifiers.builder()
                    .add(
                        Attributes.ATTACK_DAMAGE,
                        AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 5.0, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                    )
                    .add(
                        Attributes.ATTACK_SPEED,
                        AttributeModifier(Item.BASE_ATTACK_SPEED_ID, -2.4, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                    )
                    .add(
                        Attributes.ENTITY_INTERACTION_RANGE,
                        AttributeModifier(
                            Identifier.fromNamespaceAndPath("swordfight", "sword_reach"),
                            3.0,
                            AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                    )
                    .build(),
            )
        )


    private fun register(name: String, properties: Item.Properties = Item.Properties()): Item {
        val key = ResourceKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath("swordfight", name)
        )
        return Registry.register(BuiltInRegistries.ITEM, key, Item(properties.setId(key)))
    }

    fun init() {
    }
}