package me.grantswim4.namechecke;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

public class CheckCommand implements CommandExecutor {

	String uuidWOH;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("NameChecker.Check")) {
			sender.sendMessage("Sorry, you don't have access to this. Permission: NameChecker.Check");
		} else {

			Player ply = Bukkit.getPlayer(args[0]);

			uuidWOH = ply.getUniqueId().toString();

			uuidWOH.replaceAll("[\\s\\-()]", "");

			if (ply != null) {
				sender.sendMessage("Target: " + ply.getDisplayName() + "(" + ply.getUniqueId() + ")");
				sender.sendMessage("Website: ");
				sender.sendMessage("https://api.mojang.com/user/profiles/" + uuidWOH + "/names");

				sender.sendMessage("Attempting InGame Results...");
				try {

					String tar = mojangapi.readJsonFromUrl("https://api.mojang.com/user/profiles/" + uuidWOH + "/names").toString();
					for (String line : "},{".split(tar)) {
						sender.sendMessage(line);
					}
				} catch (JsonSyntaxException e) {
					sender.sendMessage("Failed to grab ingame results!");
					e.printStackTrace();
				} catch (IOException e) {
					sender.sendMessage("Failed to grab ingame results!");
					e.printStackTrace();
				}

			}

		}
		return false;
	}

}
