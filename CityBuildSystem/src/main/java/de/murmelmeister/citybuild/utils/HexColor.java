package de.murmelmeister.citybuild.utils;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexColor {

    public static String format(String message) {
        Matcher matcher = Pattern.compile("#[a-fA-F0-9]{6}").matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, TextColor.fromHexString(color) + "");
            matcher = Pattern.compile("#[a-fA-F0-9]{6}").matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
