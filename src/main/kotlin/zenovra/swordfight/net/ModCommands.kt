package zenovra.swordfight.net

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import com.mojang.brigadier.Command
import net.minecraft.commands.Commands
object ModCommands {
    fun init() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                Commands.literal("swordfight")
                    .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(
                    Commands.literal("save").executes { context ->
                        val player = context.source.playerOrException
                        ColosseumReturn.SaveLoc(player, player.level())
                        Command.SINGLE_SUCCESS
                    }
                )
                    .then(
                        Commands.literal("back").executes { context ->
                            val player = context.source.playerOrException
                            ColosseumReturn.BackLoc(player, player.level())
                            Command.SINGLE_SUCCESS
                        }
                    )
            )
        }
    }
}