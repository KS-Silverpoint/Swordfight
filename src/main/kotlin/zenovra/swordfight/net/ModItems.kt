package zenovra.swordfight.net

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
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