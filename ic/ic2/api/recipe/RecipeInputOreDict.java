/*    */ package ic2.api.recipe;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.oredict.OreDictionary;
/*    */ 
/*    */ public class RecipeInputOreDict implements IRecipeInput {
/*    */   public final String input;
/*    */   public final int amount;
/*    */   
/*    */   public RecipeInputOreDict(String input1) {
/* 14 */     this(input1, 1);
/*    */   }
/*    */   public final Integer meta; private List<ItemStack> ores;
/*    */   public RecipeInputOreDict(String input1, int amount1) {
/* 18 */     this(input1, amount1, null);
/*    */   }
/*    */   
/*    */   public RecipeInputOreDict(String input1, int amount1, Integer meta) {
/* 22 */     this.input = input1;
/* 23 */     this.amount = amount1;
/* 24 */     this.meta = meta;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack subject) {
/* 29 */     List<ItemStack> inputs = getOres();
/* 30 */     boolean useOreStackMeta = (this.meta == null);
/* 31 */     Item subjectItem = subject.func_77973_b();
/* 32 */     int subjectMeta = subject.func_77960_j();
/*    */     
/* 34 */     for (ItemStack oreStack : inputs) {
/* 35 */       Item oreItem = oreStack.func_77973_b();
/* 36 */       if (oreItem == null)
/*    */         continue; 
/* 38 */       int metaRequired = useOreStackMeta ? oreStack.func_77960_j() : this.meta.intValue();
/*    */       
/* 40 */       if (subjectItem == oreItem && (subjectMeta == metaRequired || metaRequired == 32767))
/*    */       {
/* 42 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 51 */     return this.amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getInputs() {
/* 56 */     List<ItemStack> ores = getOres();
/*    */ 
/*    */     
/* 59 */     boolean hasInvalidEntries = false;
/*    */     
/* 61 */     for (ItemStack stack : ores) {
/* 62 */       if (stack.func_77973_b() == null) {
/* 63 */         hasInvalidEntries = true;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 68 */     if (!hasInvalidEntries) return ores;
/*    */     
/* 70 */     List<ItemStack> ret = new ArrayList<>(ores.size());
/*    */     
/* 72 */     for (ItemStack stack : ores) {
/* 73 */       if (stack.func_77973_b() != null) ret.add(stack);
/*    */     
/*    */     } 
/* 76 */     return Collections.unmodifiableList(ret);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 81 */     if (this.meta == null) {
/* 82 */       return "RInputOreDict<" + this.amount + "x" + this.input + ">";
/*    */     }
/* 84 */     return "RInputOreDict<" + this.amount + "x" + this.input + "@" + this.meta + ">";
/*    */   }
/*    */ 
/*    */   
/*    */   private List<ItemStack> getOres() {
/* 89 */     if (this.ores != null) return this.ores;
/*    */ 
/*    */ 
/*    */     
/* 93 */     List<ItemStack> ret = OreDictionary.getOres(this.input);
/*    */     
/* 95 */     if (ret != OreDictionary.EMPTY_LIST) this.ores = ret;
/*    */     
/* 97 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\RecipeInputOreDict.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */