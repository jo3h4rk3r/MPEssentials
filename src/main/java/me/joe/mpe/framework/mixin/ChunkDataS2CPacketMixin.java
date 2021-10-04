package me.joe.mpe.framework.mixin;

import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkDataS2CPacket.class)
public class ChunkDataS2CPacketMixin {

   /* @Shadow @Final private int[] biomeArray;
    @Redirect(method = "<init>(Lnet/minecraft/world/chunk/WorldChunk;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/source/BiomeArray;toIntArray()[I"))
    public int[] fixInvalidBiomeId(BiomeArray biomeArray) {
        int[] result = biomeArray.toIntArray();
        for (int i = 0; i < result.length; i++) {
            result[i] = result[i] == -1 ? 0 : result[i];
        }
        return result;

    }

    */


}
