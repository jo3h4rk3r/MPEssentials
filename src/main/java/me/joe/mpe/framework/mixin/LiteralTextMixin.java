package me.joe.mpe.framework.mixin;

import me.joe.mpe.impl.mpe;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(LiteralText.class)
public class LiteralTextMixin { //removed BaseText - again this causes a crash

    @Mutable
    @Final
    @Shadow
    public String string;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void LiteralText(String string, CallbackInfo ci) {

        if (mpe.settings.enableColor) {
            this.string = mpe.TF.ampersandFormatting(string);//call the static reference in Main, instead of creating one in the mixin - prevents a crash



        } else {
            this.string = string;
        }
    }



}