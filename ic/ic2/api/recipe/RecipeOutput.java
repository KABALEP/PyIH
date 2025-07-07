/*    */ package ic2.api.recipe;
/*    */ import java.util.Arrays;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public final class RecipeOutput {
/*    */   public final List<ItemStack> items;
/*    */   
/*    */   public RecipeOutput(NBTTagCompound metadata1, List<ItemStack> items1) {
/* 12 */     assert !items1.contains(null);
/*    */     
/* 14 */     this.metadata = metadata1;
/* 15 */     this.items = items1;
/*    */   }
/*    */   public final NBTTagCompound metadata;
/*    */   public RecipeOutput(NBTTagCompound metadata1, ItemStack... items1) {
/* 19 */     this(metadata1, Arrays.asList(items1));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 24 */     if (obj instanceof RecipeOutput) {
/* 25 */       RecipeOutput ro = (RecipeOutput)obj;
/*    */       
/* 27 */       if (this.items.size() == ro.items.size() && ((this.metadata == null && ro.metadata == null) || (this.metadata != null && ro.metadata != null && this.metadata
/* 28 */         .equals(ro.metadata)))) {
/* 29 */         Iterator<ItemStack> itA = this.items.iterator();
/* 30 */         Iterator<ItemStack> itB = ro.items.iterator();
/*    */         
/* 32 */         while (itA.hasNext() && itB.hasNext()) {
/* 33 */           ItemStack stackA = itA.next();
/* 34 */           ItemStack stackB = itB.next();
/*    */           
/* 36 */           if (ItemStack.func_77989_b(stackA, stackB)) return false;
/*    */         
/*    */         } 
/* 39 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 48 */     return "ROutput<" + this.items + "," + this.metadata + ">";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\RecipeOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */