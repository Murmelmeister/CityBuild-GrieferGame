package de.murmelmeister.citybuild.utils;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexColor {

    /*public static String format(String message) {
        Matcher matcher = Pattern.compile("#[a-fA-F0-9]{6}").matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, TextColor.fromHexString(color) + "");
            matcher = Pattern.compile("#[a-fA-F0-9]{6}").matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }*/

    public static String format(String message) {
        Matcher matcher = Pattern.compile("#[a-fA-F0-9]{6}").matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
            matcher = Pattern.compile("#[a-fA-F0-9]{6}").matcher(message);
        }
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
    }

    /*public static String format(String message) {
        Matcher matcher = Pattern.compile("#[a-fA-F0-9]{6}").matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, Objects.requireNonNull(TextColor.fromHexString(color)).toString());
            matcher = Pattern.compile("#[a-fA-F0-9]{6}").matcher(message);
        }
        return LegacyComponentSerializer.legacy('&').deserialize(message).toString();
    }*/

}
