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
import net.minecraft.world.item.ToolMaterial

object ModItems {
    val Heavens_Wrath: Item = register ("heavens_wrath") { props ->
        HeavensWrath(props.sword(ToolMaterial.DIAMOND, 2.0f, -2.0f))
    }
    val Bad_Omens_Wrath: Item = register ("bad_omens_wrath") { props ->
        BadOmensWrath(props.sword(ToolMaterial.NETHERITE, 4.6f, -2.6f))
    }
    val Bane_Of_The_Void: Item = register ("bane_of_the_void") { props ->
        BaneOfTheVoid(props.sword(ToolMaterial.DIAMOND, 4.6f, -2.6f))
    }
    val Prismatic_Blade: Item = register ("prismatic_blade") { props ->
        PrismaticBlade(props.sword(ToolMaterial.DIAMOND, 4.5f, -2.3f))
    }
    val Zorn: Item = register("zorn")
    val Vom: Item = register("vom")
    val Ochs: Item = register("ochs")
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
        .title(Component.translatable("creativeTab.swordfight"))
        .displayItems { _, output ->
            output.accept(ModItems.Heavens_Wrath)
            output.accept(ModItems.Bad_Omens_Wrath)
            output.accept(ModItems.Bane_Of_The_Void)
            output.accept(ModItems.Prismatic_Blade)
        }
        .build()
    val SWORDFIGHTITEMS_TAB: CreativeModeTab = FabricCreativeModeTab.builder()
        .icon { ItemStack(ModItems.Orb_Of_Eisenport) }
        .title(Component.translatable("creativeTab.swordfightitems"))
        .displayItems { _, output ->
            output.accept(ModItems.Zorn)
            output.accept(ModItems.Vom)
            output.accept(ModItems.Ochs)
            output.accept(ModItems.Ice_Shard)
            output.accept(ModItems.Orb_Of_Eisenport)
        }
        .build()

    fun init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SWORDFIGHT_TAB_KEY, SWORDFIGHT_TAB)
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SWORDFIGHTITEMS_TAB_KEY, SWORDFIGHTITEMS_TAB)
    }
}
