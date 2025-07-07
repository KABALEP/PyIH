/*    */ package ic2.api.item;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public interface IKineticRotor {
/*    */   int getDiameter(ItemStack paramItemStack);
/*    */   
/*    */   ResourceLocation getRotorRenderTexture(ItemStack paramItemStack);
/*    */   
/*    */   float getEfficiency(ItemStack paramItemStack);
/*    */   
/*    */   int getMinWindStrength(ItemStack paramItemStack);
/*    */   
/*    */   int getMaxWindStrength(ItemStack paramItemStack);
/*    */   
/*    */   boolean isAcceptedType(ItemStack paramItemStack, GearboxType paramGearboxType);
/*    */   
/*    */   public enum GearboxType {
/* 20 */     WATER,
/* 21 */     WIND;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\IKineticRotor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */