/*    */ package ic2.tcIntigration.core;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import tconstruct.library.ActiveToolMod;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActiveEUModifier
/*    */   extends ActiveToolMod
/*    */ {
/*    */   public boolean damageTool(ItemStack stack, int damage, EntityLivingBase entity) {
/* 17 */     NBTTagCompound nbt = stack.func_77978_p().func_74775_l("InfiTool");
/* 18 */     if (!nbt.func_74764_b("EUEnergy"))
/*    */     {
/* 20 */       return false;
/*    */     }
/* 22 */     if (ElectricItem.manager.canUse(stack, (damage * 50))) {
/*    */       
/* 24 */       ElectricItem.manager.use(stack, (damage * 50), entity);
/* 25 */       return true;
/*    */     } 
/* 27 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\tcIntigration\core\ActiveEUModifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */