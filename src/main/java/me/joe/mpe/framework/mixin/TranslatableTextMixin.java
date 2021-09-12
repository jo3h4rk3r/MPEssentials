package me.joe.mpe.framework.mixin;

import net.minecraft.text.*;
import org.spongepowered.asm.mixin.*;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TranslatableText.class)
public abstract class TranslatableTextMixin {

    @Mutable
    @Final
    @Shadow
    private Object[] args;

    // Without "(Ljava/lang/String;[Ljava/lang/Object;)V" the void would not be able to use all the variables
    @Inject(method = "<init>(Ljava/lang/String;[Ljava/lang/Object;)V", at = @At("TAIL"))

    public void TranslatableText(String key, Object[] args, CallbackInfo ci) {
        for (int i = 0; i < args.length; ++i) {
          Object object = args[i];

            if (object == null)
                object = "null";
            if (object instanceof String)
                object = new LiteralText((String) object);
            if (object instanceof Text) {
               // MutableText text = ((Text) object).copy();
          //      text.setStyle(text.getStyle().withParent(((Text) this).getStyle()));
              //  object = text;
            }

             this.args[i] = object;
        }
     }

}
