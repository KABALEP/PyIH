/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.api.item.ICustomDamageItem;
/*    */ import ic2.core.item.ItemGradual;
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.CraftingManager;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class RecipeGradual
/*    */   implements IRecipe
/*    */ {
/*    */   public ICustomDamageItem item;
/*    */   public ItemStack chargeMaterial;
/*    */   public int amount;
/*    */   
/*    */   public RecipeGradual(ItemGradual item, ItemStack chargeMaterial, int amount) {
/* 21 */     this.item = (ICustomDamageItem)item;
/* 22 */     this.chargeMaterial = chargeMaterial;
/* 23 */     this.amount = amount;
/* 24 */     CraftingManager.func_77594_a().func_77592_b().add(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_77569_a(InventoryCrafting var1, World world) {
/* 29 */     return (func_77572_b(var1) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_77572_b(InventoryCrafting var1) {
/* 34 */     ItemStack gridItem = null;
/* 35 */     int chargeMats = 0;
/* 36 */     for (int i = 0; i < var1.func_70302_i_(); i++) {
/*    */       
/* 38 */       ItemStack stack = var1.func_70301_a(i);
/* 39 */       if (stack != null)
/*    */       {
/* 41 */         if (gridItem == null && stack.func_77973_b() == this.item) {
/*    */           
/* 43 */           gridItem = stack;
/*    */         }
/*    */         else {
/*    */           
/* 47 */           if (stack.func_77973_b() != this.chargeMaterial.func_77973_b() || (stack.func_77960_j() != this.chargeMaterial.func_77960_j() && this.chargeMaterial.func_77960_j() != -1))
/*    */           {
/* 49 */             return null;
/*    */           }
/* 51 */           chargeMats++;
/*    */         } 
/*    */       }
/*    */     } 
/* 55 */     if (gridItem != null && chargeMats > 0) {
/*    */       
/* 57 */       ItemStack stack2 = gridItem.func_77946_l();
/* 58 */       int damage = this.item.getCustomDamage(stack2) - this.amount * chargeMats;
/* 59 */       if (damage > this.item.getMaxCustomDamage(stack2)) {
/*    */         
/* 61 */         damage = this.item.getMaxCustomDamage(stack2);
/*    */       }
/* 63 */       else if (damage < 0) {
/*    */         
/* 65 */         damage = 0;
/*    */       } 
/* 67 */       this.item.setCustomDamage(stack2, damage);
/* 68 */       return stack2;
/*    */     } 
/* 70 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77570_a() {
/* 75 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_77571_b() {
/* 80 */     return new ItemStack((Item)this.item);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\RecipeGradual.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */