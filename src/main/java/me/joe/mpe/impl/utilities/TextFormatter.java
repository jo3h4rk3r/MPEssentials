package me.joe.mpe.impl.utilities;

import me.joe.mpe.impl.utilities.TickCounter;
import me.joe.mpe.impl.utilities.files.PlayerCounter;
import me.joe.mpe.impl.utilities.math.MathUtility;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class TextFormatter {
    public String ampersandFormatting(String text){
        StringBuilder colorBuilder = new StringBuilder(text);
        String output;
        for (int d = 0; d < colorBuilder.length(); d++) {

            if (colorBuilder.charAt(d) == '&' && matches(colorBuilder.charAt(d + 1))) {
                colorBuilder.setCharAt(d, '§');
            }
        }
        output = colorBuilder.toString();





        return output;
    }
    private String getFormattedPart(String text, int firstIndex, int lastIndex){
        String outputString = text.substring(firstIndex, lastIndex);
        return setParagraphs(outputString);
    }
    private String setParagraphs(String string){
        StringBuilder builder = new StringBuilder(string);
        for(int i = 0; i < builder.length(); i++){
            if(builder.charAt(i) == '&'){
                if(i == 0 || builder.charAt(i-1) != '\\'){
                    if(i != builder.length() - 1 && matches(builder.charAt(i+1))){
                        builder.setCharAt(i, '§');
                    }
                }
            }
        }
        return builder.toString();
    }
    private boolean matches(Character c){
        return "b0931825467adcfeklmnor".contains(c.toString());
    }
    public static String tablistChars(String string){
        String output;
        /*
        double tps = MathUtility.round(Globals.TPS1.getAverage(), 1);
        Formatting color;
        if (tps >= 18.0D) {
            color = Formatting.GREEN;
        } else if (tps >= 15.0D) {
            color = Formatting.RED;
        } else {
            color = Formatting.DARK_RED;
        }
         */
      //  TranslatableText ticks = (TranslatableText) (new LiteralText(String.valueOf(tps))).setStyle((Style.EMPTY).withColor(color));
       // TranslatableText ticks = (new TranslatableText("%s", (new LiteralText(String.valueOf(tps))).setStyle((Style.EMPTY).withColor(color))));
        //output = string.replaceAll("#TPS", String.valueOf(tps));
        output = string.replaceAll("#TPS", Integer.toString(TickCounter.getTps()));
        output = output.replaceAll("#MSPT", Integer.toString(TickCounter.getMspt()));
        output = output.replaceAll("#PLAYERSONLINE", Integer.toString(PlayerCounter.getPlayersOnline()));
        //output = output.replaceAll("#PING", Integer.toString(PlayerCounter.getPlayersPing()));
        return output;
    }
    public static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }
    public static String after(String value, String a) {
        // Returns a substring containing all characters after a string.
        int posA = value.lastIndexOf(a);
        if (posA == -1) {
            return "";
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= value.length()) {
            return "";
        }
        return value.substring(adjustedPosA);
    }
    public static String between(String value, String a, String b) {
        // Return a substring between the two strings.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        int posB = value.lastIndexOf(b);
        if (posB == -1) {
            return "";
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= posB) {
            return "";
        }
        return value.substring(adjustedPosA, posB);
    }
}
