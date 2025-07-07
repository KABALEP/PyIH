/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IBoxable;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ItemBatterySU
/*    */   extends ItemIC2
/*    */   implements IBoxable
/*    */ {
/*    */   public int capacity;
/*    */   public int tier;
/*    */   
/*    */   public ItemBatterySU(int sprite, int capacity, int tier) {
/* 18 */     super(sprite);
/* 19 */     this.capacity = capacity;
/* 20 */     this.tier = tier;
/* 21 */     setSpriteID("i1");
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/* 26 */     if (itemstack.func_77973_b() != this)
/*    */     {
/* 28 */       return itemstack;
/*    */     }
/* 30 */     int energy = this.capacity;
/* 31 */     for (int i = 0; i < 9 && energy > 0; i++) {
/*    */       
/* 33 */       ItemStack stack = entityplayer.field_71071_by.field_70462_a[i];
/* 34 */       if (stack != null && stack.func_77973_b() instanceof ic2.api.item.IElectricItem && stack != itemstack)
/*    */       {
/* 36 */         energy = (int)(energy - ElectricItem.manager.charge(stack, energy, this.tier, true, false));
/*    */       }
/*    */     } 
/* 39 */     if (energy != this.capacity)
/*    */     {
/* 41 */       itemstack.field_77994_a--;
/*    */     }
/* 43 */     return itemstack;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 49 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemBatterySU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */