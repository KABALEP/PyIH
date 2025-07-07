/*    */ package ic2.core.slot;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotCustom
/*    */   extends Slot
/*    */ {
/*    */   private Object[] items;
/*    */   
/*    */   public SlotCustom(IInventory iinventory, Object[] items, int i, int j, int k) {
/* 15 */     super(iinventory, i, j, k);
/* 16 */     this.items = items;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack itemstack) {
/* 21 */     if (itemstack == null)
/*    */     {
/* 23 */       return false;
/*    */     }
/* 25 */     for (Object o : this.items) {
/*    */       
/* 27 */       if (o != null)
/*    */       {
/* 29 */         if (o instanceof Class) {
/*    */           
/* 31 */           if (Block.func_149634_a(itemstack.func_77973_b()) != null && ((Class)o).isAssignableFrom(Block.func_149634_a(itemstack.func_77973_b()).getClass()))
/*    */           {
/* 33 */             return true;
/*    */           }
/* 35 */           if (((Class)o).isAssignableFrom(itemstack.func_77973_b().getClass()))
/*    */           {
/* 37 */             return true;
/*    */           }
/*    */         }
/* 40 */         else if (o instanceof ItemStack) {
/*    */           
/* 42 */           ItemStack stack = (ItemStack)o;
/* 43 */           if (stack.func_77960_j() == 32767 && itemstack.func_77973_b() == stack.func_77973_b())
/*    */           {
/* 45 */             return true;
/*    */           }
/* 47 */           if (itemstack.func_77969_a(stack))
/*    */           {
/* 49 */             return true;
/*    */           }
/*    */         }
/*    */         else {
/*    */           
/* 54 */           if (o instanceof Block && Block.func_149634_a(itemstack.func_77973_b()) == (Block)o)
/*    */           {
/* 56 */             return true;
/*    */           }
/* 58 */           if (o instanceof Item && itemstack.func_77973_b() == (Item)o)
/*    */           {
/* 60 */             return true;
/*    */           }
/* 62 */           if (o instanceof Integer && Item.func_150891_b(itemstack.func_77973_b()) == ((Integer)o).intValue())
/*    */           {
/* 64 */             return true;
/*    */           }
/*    */         } 
/*    */       }
/*    */     } 
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\slot\SlotCustom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */