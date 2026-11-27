package zenovra.swordfight.net

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Item
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.MinecartItem
import net.minecraft.world.item.ToolMaterial
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.component.CustomData

object ModItems {
    //quest & admin weapons
    val Heavens_Wrath: Item = register ("heavens_wrath") { props ->
        HeavensWrath(props
            .sword(ToolMaterial.DIAMOND, 5.0f, -1.75f)
            .enchantable(10)
            .rarity(Rarity.EPIC))
    }
    val Bad_Omens_Wrath: Item = register ("bad_omens_wrath") { props ->
        BadOmensWrath(props
            .sword(ToolMaterial.NETHERITE, 6.6f, -2.6f)
            .enchantable(10)
            .rarity(Rarity.EPIC))
    }
    val Bane_Of_The_Void: Item = register ("bane_of_the_void") { props ->
        BaneOfTheVoid(props
            .sword(ToolMaterial.DIAMOND, 6.6f, -2.6f)
            .enchantable(10)
            .rarity(Rarity.EPIC))
    }
    val Prismatic_Blade: Item = register ("prismatic_blade") { props ->
        PrismaticBlade(props
            .sword(ToolMaterial.DIAMOND, 6.5f, -2.3f)
            .enchantable(10)
            .rarity(Rarity.EPIC))
    }
    val Sams_Sword: Item = register ("sams_sword") { props ->
        SamsSword(props
            .sword(ToolMaterial.DIAMOND, 6.6f, -2.6f)
            .enchantable(10)
            .rarity(Rarity.EPIC))
    }
    val Broken_Stick: Item = register ("broken_stick") { props ->
        BrokenStick(props
            .sword(ToolMaterial.DIAMOND, 60.9f, -0.1f)
            .enchantable(10)
            .rarity(Rarity.EPIC))
    }
    val Shard_of_Oblivion: Item = register("shard_of_oblivion") { props ->
        ShardOfOblivion(props
            .sword(ToolMaterial.NETHERITE, 7.5f, -3.25f)
            .enchantable(10)
            .rarity(Rarity.EPIC))

    }
    //weakened weapons
    val Echo_Of_The_Void: Item = register("echo_of_the_void") { props ->
        EchoOfTheVoid(props
            .sword(ToolMaterial.DIAMOND, 4.0f, -3f)
            .enchantable(10)
            .rarity(Rarity.RARE))
    }
    val Enhanced_Diamond_Sword: Item = register("enhanced_diamond_sword") { props ->
        EmptyAbility(props
            .sword(ToolMaterial.DIAMOND, 4.0f, -2.3f)
            .enchantable(10)
            .rarity(Rarity.RARE))
    }
    val Enhanced_Netherite_Sword: Item = register("enhanced_netherite_sword") { props ->
        EmptyAbility(props
            .sword(ToolMaterial.NETHERITE, 4.0f, -2.5f)
            .enchantable(10)
            .rarity(Rarity.RARE))
    }
    //items
    val Zorn: Item = register("zorn")
    val Vom: Item = register("vom")
    val Ochs: Item = register("ochs")
    val Spacer: Item = register("spacer")
    val Ice_Shard: Item = register("ice_shard")
    val Orb_Of_Eisenport: Item = register("orb_of_eisenport") { props ->
        eisenportteleport(props.stacksTo(1))
    }
    private fun register(name: String, factory: (Item.Properties) -> Item = ::Item): Item {
        val key = ResourceKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath("swordfight", name)
        )
        return Registry.register(BuiltInRegistries.ITEM, key, factory(Item.Properties().setId(key)))
    }

    fun init() {}
}
private fun spacer(index: Int): ItemStack {
    val stack = ItemStack(ModItems.Spacer)
    val tag = CompoundTag()
    tag.putInt("spacer_id", index)
    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag))
    return stack
}
object ModCreativeTabs {
    val SWORDFIGHT_TAB_KEY: ResourceKey<CreativeModeTab> = ResourceKey.create(
        BuiltInRegistries.CREATIVE_MODE_TAB.key(),
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    Identifier.fromNamespaceAndPath("swordfight", "swordfight_tab")
    )
    val SWORDFIGHTITEMS_TAB_KEY: ResourceKey<CreativeModeTab> = ResourceKey.create(
        BuiltInRegistries.CREATIVE_MODE_TAB.key(),
        Identifier.fromNamespaceAndPath("swordfight", "swordfightitems_tab")
    )

    val SWORDFIGHT_TAB: CreativeModeTab = FabricCreativeModeTab.builder()
        .icon { ItemStack(ModItems.Heavens_Wrath) }
        .title(Component.translatable("creativeTab.warforgedswords"))
        .displayItems { _, output ->
            var n = 0
            fun gap(count: Int) = repeat(count) { output.accept(spacer(n++)) }
            output.accept(ModItems.Heavens_Wrath)
            output.accept(ModItems.Shard_of_Oblivion)
            output.accept(ModItems.Prismatic_Blade)
            output.accept(ModItems.Bad_Omens_Wrath)
            output.accept(ModItems.Bane_Of_The_Void)
            gap(4)
            output.accept(ModItems.Sams_Sword)
            output.accept(ModItems.Broken_Stick)
            gap(7)
            output.accept(ModItems.Echo_Of_The_Void)
            output.accept(ModItems.Enhanced_Diamond_Sword)
            output.accept(ModItems.Enhanced_Netherite_Sword)
        }
        .build()
    val SWORDFIGHTITEMS_TAB: CreativeModeTab = FabricCreativeModeTab.builder()
        .icon { ItemStack(ModItems.Orb_Of_Eisenport) }
        .title(Component.translatable("creativeTab.warforgeditems"))
        .displayItems { _, output ->
            var n = 0
            fun gap(count: Int) = repeat(count) { output.accept(spacer(n++)) }
            output.accept(ModItems.Zorn)
            output.accept(ModItems.Vom)
            output.accept(ModItems.Ochs)
            gap(7)
            output.accept(ModItems.Ice_Shard)
            output.accept(ModItems.Orb_Of_Eisenport)
        }
        .build()

    fun init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SWORDFIGHT_TAB_KEY, SWORDFIGHT_TAB)
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SWORDFIGHTITEMS_TAB_KEY, SWORDFIGHTITEMS_TAB)
    }
}
