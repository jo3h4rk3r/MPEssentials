package me.joe.mpe.api.world.impl;

import net.minecraft.block.Blocks;

import java.util.Optional;

class InternalBlockConverter {
   static Optional<net.minecraft.block.Block> convertBlock(Blocks block) {
      try {
         return Optional.ofNullable((net.minecraft.block.Block)Blocks.class.getDeclaredField(block.toString()).get((Object)null));
      } catch (NoSuchFieldException | IllegalAccessException var2) {
         var2.printStackTrace();
         return Optional.empty();
      }
   }
}
