package zenovra.swordfight.net

import net.fabricmc.api.ModInitializer
import net.minecraft.resources.Identifier
import org.slf4j.LoggerFactory

object SwordFight : ModInitializer {
	const val MOD_ID: String = "swordfight"

	private val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ModItems.init()
		ModCreativeTabs.init()
		ModCommands.init()

		LOGGER.info("Hello Fabric world!")
	}

	fun id(path: String): Identifier
		= Identifier.fromNamespaceAndPath(MOD_ID, path)
}
