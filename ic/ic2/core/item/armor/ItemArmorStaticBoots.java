/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IMetalArmor;
/*    */ import ic2.core.IItemTickListener;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemArmorStaticBoots
/*    */   extends ItemArmorUtility
/*    */   implements IMetalArmor, IItemTickListener
/*    */ {
/*    */   public ItemArmorStaticBoots(int index, int renderIndex) {
/* 19 */     super(index, renderIndex, 3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onTick(EntityPlayer player, ItemStack itemStack) {
/* 31 */     if (player.field_71071_by.field_70460_b[2] == null || !(player.field_71071_by.field_70460_b[2].func_77973_b() instanceof ic2.api.item.IElectricItem))
/*    */     {
/* 33 */       return false;
/*    */     }
/* 35 */     NBTTagCompound compound = StackUtil.getOrCreateNbtData(itemStack);
/* 36 */     boolean isNotWalking = (player.field_70154_o != null || player.func_70090_H());
/* 37 */     if (!compound.func_74764_b("x") || isNotWalking)
/*    */     {
/* 39 */       compound.func_74768_a("x", (int)((Entity)player).field_70165_t);
/*    */     }
/* 41 */     if (!compound.func_74764_b("z") || isNotWalking)
/*    */     {
/* 43 */       compound.func_74768_a("z", (int)((Entity)player).field_70161_v);
/*    */     }
/* 45 */     double distance = Math.sqrt(((compound.func_74762_e("x") - (int)player.field_70165_t) * (compound.func_74762_e("x") - (int)player.field_70165_t) + (compound.func_74762_e("z") - (int)player.field_70161_v) * (compound.func_74762_e("z") - (int)player.field_70161_v)));
/* 46 */     if (distance >= 5.0D) {
/*    */       
/* 48 */       compound.func_74768_a("x", (int)player.field_70165_t);
/* 49 */       compound.func_74768_a("z", (int)player.field_70161_v);
/* 50 */       return (ElectricItem.manager.charge(player.field_71071_by.field_70460_b[2], Math.min(3, (int)distance / 5), 2147483647, true, false) > 0.0D);
/*    */     } 
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTextureName() {
/* 58 */     return "rubber";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorStaticBoots.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */