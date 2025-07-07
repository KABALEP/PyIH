/*    */ package ic2.nerIntigration;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Items;
/*    */ import neresources.api.distributions.DistributionBase;
/*    */ import neresources.api.distributions.DistributionSquare;
/*    */ import neresources.api.messages.RegisterOreMessage;
/*    */ import neresources.registry.OreRegistry;
/*    */ 
/*    */ public class NerPlugin
/*    */ {
/*    */   public static void load() {
/* 13 */     float density = IC2.oreDensityFactor;
/* 14 */     int baseScale = Math.round(64.0F * density);
/* 15 */     if (IC2.enableWorldGenOreCopper && Ic2Items.copperOre != null) {
/*    */       
/* 17 */       float chance = (15 * baseScale / 64 * 10) / 15104.0F;
/* 18 */       RegisterOreMessage mes = new RegisterOreMessage(Ic2Items.copperOre.func_77946_l(), (DistributionBase)new DistributionSquare(10, 60, chance), 9127187, new net.minecraft.item.ItemStack[0]);
/* 19 */       OreRegistry.registerOre(mes);
/*    */     } 
/* 21 */     if (IC2.enableWorldGenOreTin && Ic2Items.tinOre != null) {
/*    */       
/* 23 */       float chance = (25 * baseScale / 64 * 6) / 10240.0F;
/* 24 */       int maxY = 40 * baseScale / 64;
/* 25 */       RegisterOreMessage mes = new RegisterOreMessage(Ic2Items.tinOre.func_77946_l(), (DistributionBase)new DistributionSquare(0, maxY, chance), 13882323, new net.minecraft.item.ItemStack[0]);
/* 26 */       OreRegistry.registerOre(mes);
/*    */     } 
/* 28 */     if (IC2.enableWorldGenOreCopper && Ic2Items.copperOre != null) {
/*    */       
/* 30 */       float chance = (20 * baseScale / 64 * 3) / 16384.0F;
/* 31 */       int maxY = 64 * baseScale / 64;
/* 32 */       RegisterOreMessage mes = new RegisterOreMessage(Ic2Items.uraniumOre.func_77946_l(), (DistributionBase)new DistributionSquare(10, maxY, chance), 25600, new net.minecraft.item.ItemStack[0]);
/* 33 */       OreRegistry.registerOre(mes);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\nerIntigration\NerPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */