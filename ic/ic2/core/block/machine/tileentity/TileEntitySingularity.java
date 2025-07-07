/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.slot.SlotDischarge;
/*     */ import ic2.core.slot.SlotOutput;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntitySingularity
/*     */   extends TileEntityAdvancedMachine
/*     */ {
/*     */   public TileEntityPump validPump;
/*     */   
/*     */   public TileEntitySingularity() {
/*  26 */     super(3, 15, 4000);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/*  32 */     return "block.machine.gui.GuiSingularity";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/*  38 */     return new int[] { 1, 2 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecipeOutput getResultFor(ItemStack input, boolean adjusted) {
/*  44 */     RecipeOutput output = (input == null) ? null : Recipes.compressor.getOutputFor(input, adjusted);
/*  45 */     if (output == null) {
/*     */       
/*  47 */       if (getValidPump() != null) {
/*     */         
/*  49 */         NBTTagCompound nbt = new NBTTagCompound();
/*  50 */         nbt.func_74757_a("Pump", true);
/*  51 */         return new RecipeOutput(nbt, new ItemStack[] { new ItemStack(Items.field_151126_ay) });
/*     */       } 
/*  53 */       return null;
/*     */     } 
/*  55 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canWorkWithoutItems() {
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void operateOnce(RecipeOutput result, int slot, List<ItemStack> items) {
/*  67 */     super.operateOnce(result, slot, items);
/*  68 */     ItemStack item = result.items.get(0);
/*  69 */     if (item != null && item.func_77973_b() == Items.field_151126_ay && result.metadata != null)
/*     */     {
/*  71 */       if (result.metadata.func_74767_n("Pump")) {
/*     */         
/*  73 */         TileEntityPump pump = getValidPump();
/*  74 */         if (pump == null) {
/*     */           return;
/*     */         }
/*     */         
/*  78 */         pump.pumpCharge = 0;
/*  79 */         getNetwork().updateTileGuiField((TileEntity)pump, "pumpCharge");
/*  80 */         this.field_145850_b.func_147468_f(pump.field_145851_c, pump.field_145848_d - 1, pump.field_145849_e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityPump getValidPump() {
/*  87 */     if (this.validPump != null && !this.validPump.func_145837_r() && this.validPump.isPumpReady() && this.validPump.isWaterBelow())
/*     */     {
/*  89 */       return this.validPump;
/*     */     }
/*  91 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) instanceof TileEntityPump) {
/*     */       
/*  93 */       TileEntityPump pump = (TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e);
/*  94 */       if (pump.isPumpReady() && pump.isWaterBelow())
/*     */       {
/*  96 */         return this.validPump = pump;
/*     */       }
/*     */     } 
/*  99 */     if (this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityPump) {
/*     */       
/* 101 */       TileEntityPump pump = (TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e);
/* 102 */       if (pump.isPumpReady() && pump.isWaterBelow())
/*     */       {
/* 104 */         return this.validPump = pump;
/*     */       }
/*     */     } 
/* 107 */     if (this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityPump) {
/*     */       
/* 109 */       TileEntityPump pump = (TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e);
/* 110 */       if (pump.isPumpReady() && pump.isWaterBelow())
/*     */       {
/* 112 */         return this.validPump = pump;
/*     */       }
/*     */     } 
/* 115 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1) instanceof TileEntityPump) {
/*     */       
/* 117 */       TileEntityPump pump = (TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1);
/* 118 */       if (pump.isPumpReady() && pump.isWaterBelow())
/*     */       {
/* 120 */         return this.validPump = pump;
/*     */       }
/*     */     } 
/* 123 */     if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1) instanceof TileEntityPump) {
/*     */       
/* 125 */       TileEntityPump pump = (TileEntityPump)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1);
/* 126 */       if (pump.isPumpReady() && pump.isWaterBelow())
/*     */       {
/* 128 */         return this.validPump = pump;
/*     */       }
/*     */     } 
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Slot[] getInvSlots(InventoryPlayer par2) {
/* 137 */     Slot[] slots = new Slot[3];
/* 138 */     slots[0] = (Slot)new SlotDischarge(this, 2147483647, 0, 56, 53);
/* 139 */     slots[1] = new Slot(this, 1, 56, 17);
/* 140 */     slots[2] = (Slot)new SlotOutput(par2.field_70458_d, this, 2, 116, 35);
/* 141 */     return slots;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getOutputSlots() {
/* 147 */     return new int[] { 2 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 153 */     return "Singularity Compressor";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidInput(ItemStack par1) {
/* 159 */     if (par1 == null)
/*     */     {
/* 161 */       return false;
/*     */     }
/* 163 */     for (IRecipeInput input : Recipes.compressor.getRecipes().keySet()) {
/*     */       
/* 165 */       if (input.matches(par1))
/*     */       {
/* 167 */         return super.isValidInput(par1);
/*     */       }
/*     */     } 
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStartSoundFile() {
/* 176 */     return "Machines/CompressorOp.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInterruptSoundFile() {
/* 182 */     return "Machines/InterruptOne.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInputSlot(int slotID) {
/* 188 */     return (slotID == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOutputSlot(int slotID) {
/* 194 */     return (slotID == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachineRecipeManager getRecipeList() {
/* 200 */     return Recipes.compressor;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntitySingularity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */