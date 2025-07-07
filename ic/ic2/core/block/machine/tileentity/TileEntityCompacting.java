/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.slot.SlotDischarge;
/*     */ import ic2.core.slot.SlotOutput;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityCompacting
/*     */   extends TileEntityAdvancedMachine
/*     */ {
/*     */   public TileEntityCompacting() {
/*  22 */     super(3, 10, 300);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/*  28 */     return "block.machine.gui.GuiCompacting";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int p_94128_1_) {
/*  34 */     return new int[] { 1, 2 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getResultFor(ItemStack input, boolean adjusted) {
/*  40 */     if (input.func_77969_a(Ic2Items.scrap)) {
/*     */       
/*  42 */       if (input.field_77994_a < 9)
/*     */       {
/*  44 */         return null;
/*     */       }
/*  46 */       if (adjusted)
/*     */       {
/*  48 */         input.field_77994_a -= 9;
/*     */       }
/*  50 */       return new RecipeOutput(null, new ItemStack[] { Ic2Items.scrapBox.func_77946_l() });
/*     */     } 
/*  52 */     return Recipes.recycler.getOutputFor(input, adjusted);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Slot[] getInvSlots(InventoryPlayer par2) {
/*  58 */     Slot[] slot = new Slot[3];
/*  59 */     slot[0] = (Slot)new SlotDischarge(this, 2147483647, 0, 56, 53);
/*  60 */     slot[1] = new Slot(this, 1, 56, 17);
/*  61 */     slot[2] = (Slot)new SlotOutput(par2.field_70458_d, this, 2, 116, 35);
/*  62 */     return slot;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getOutputSlots() {
/*  68 */     return new int[] { 2 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  74 */     return "Compacting Recycler";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void operateOnce(RecipeOutput result, int slot, List<ItemStack> items) {
/*  80 */     ItemStack item = this.inventory[slot];
/*  81 */     if (item.func_77969_a(Ic2Items.scrapBox)) {
/*     */       
/*  83 */       super.operateOnce(result, slot, items);
/*     */       return;
/*     */     } 
/*  86 */     if (this.field_145850_b.field_73012_v.nextInt(TileEntityRecycler.recycleChance(item)) == 0) {
/*     */       
/*  88 */       super.operateOnce(result, slot, items);
/*     */       return;
/*     */     } 
/*  91 */     getResultFor(this.inventory[slot], true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInputSlot(int slotID) {
/*  97 */     return (slotID == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOutputSlot(int slotID) {
/* 103 */     return (slotID == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachineRecipeManager getRecipeList() {
/* 109 */     return Recipes.recycler;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/* 115 */     return "Machines/RecyclerOp.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/* 121 */     return "Machines/InterruptOne.ogg";
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityCompacting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */