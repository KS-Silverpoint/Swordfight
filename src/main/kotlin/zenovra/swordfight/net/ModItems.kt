package zenovra.swordfight.net

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.core.Registry

object ModItems {
    val Zorn: Item = register("zorn")
    val Vom: Item = register("vom")
    val Ochs: Item = register("ochs")
    val Ice_Shard: Item = register("ice_shard")
    val Orb_Of_Eisenport: Item = register("orb_of_eisenport")
    private fun register(name: String): Item {
        val key = ResourceKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath("swordfight", name)
        )
        return Registry.register(BuiltInRegistries.ITEM, key, Item(Item.Properties().setId(key)))
    }

    fun init() {}
}