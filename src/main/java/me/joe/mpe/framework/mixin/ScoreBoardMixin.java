package me.joe.mpe.framework.mixin;

import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.utilities.TextFormatter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScoreboardDisplayS2CPacket.class)
public abstract class ScoreBoardMixin {

    private LiteralText slot;

    @Mutable
    @Final
    @Shadow private String name;


    @Inject(at= @At("RETURN"), method = "<init>")
    public void PlayerScoreBoard(CallbackInfo ci) {


            this.name = "aaaaaa";


        this.slot = new LiteralText(TextFormatter.tablistChars(mpe.settings.footer));
       // this.name = new String(TextFormatter.tablistChars(mpe.settings.header));
    }

}

