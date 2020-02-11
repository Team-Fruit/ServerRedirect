package net.kaikk.mc.serverredirect.forge.command;

import net.kaikk.mc.serverredirect.forge.ServerRedirect;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.UUID;

public class RedirectCommand extends CommandBase {
	@Override
	public String getName() {
		return "redirect";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Usage: /redirect <address> <PlayerName|PlayerUUID|\"*\">";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 2) {
			sender.sendMessage(new TextComponentString(this.getUsage(sender)));
			return;
		}

		if (args[1].equals("*")) {
			ServerRedirect.sendAllTo(args[0]);
		} else {
			try {
				try {
					ServerRedirect.sendTo(args[0], UUID.fromString(args[1]));
				} catch (IllegalArgumentException e) {
					ServerRedirect.sendTo(args[0], getPlayer(server, sender, args[1]));
				}
			} catch (IllegalArgumentException e) {
				sender.sendMessage(new TextComponentString("Error: " + e.getMessage()));
			}
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(2, this.getName());
	}
}
