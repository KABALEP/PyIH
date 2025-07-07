/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.generator.container.ContainerBaseGenerator;
/*     */ import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ public class TileEntityGenerator
/*     */   extends TileEntityBaseGenerator
/*     */   implements ISidedInventory
/*     */ {
/*     */   public int itemFuelTime;
/*     */   
/*     */   public TileEntityGenerator() {
/*  21 */     super(2, IC2.energyGeneratorBase, 4000);
/*  22 */     this.itemFuelTime = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int gaugeFuelScaled(int i) {
/*  28 */     if (this.fuel <= 0)
/*     */     {
/*  30 */       return 0;
/*     */     }
/*  32 */     if (this.itemFuelTime <= 0)
/*     */     {
/*  34 */       this.itemFuelTime = this.fuel;
/*     */     }
/*  36 */     int r = (int)(this.fuel / this.itemFuelTime * i);
/*  37 */     if (r > i)
/*     */     {
/*  39 */       r = i;
/*     */     }
/*  41 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean gainFuel() {
/*  47 */     if (this.inventory[1] == null)
/*     */     {
/*  49 */       return false;
/*     */     }
/*  51 */     if (this.inventory[1].func_77973_b() == Items.field_151129_at)
/*     */     {
/*  53 */       return false;
/*     */     }
/*  55 */     int value = TileEntityIronFurnace.getFuelValueFor(this.inventory[1]) / 4;
/*  56 */     if (this.inventory[1].func_77969_a(Ic2Items.scrap) && !IC2.enableBurningScrap)
/*     */     {
/*  58 */       value = 0;
/*     */     }
/*  60 */     if (value <= 0)
/*     */     {
/*  62 */       return false;
/*     */     }
/*  64 */     this.fuel += value;
/*  65 */     this.itemFuelTime = value;
/*  66 */     if (this.inventory[1].func_77973_b().hasContainerItem(this.inventory[1])) {
/*     */       
/*  68 */       this.inventory[1] = this.inventory[1].func_77973_b().getContainerItem(this.inventory[1]);
/*     */     }
/*     */     else {
/*     */       
/*  72 */       ItemStack itemStack = this.inventory[1];
/*  73 */       itemStack.field_77994_a--;
/*     */     } 
/*  75 */     if ((this.inventory[1]).field_77994_a == 0)
/*     */     {
/*  77 */       this.inventory[1] = null;
/*     */     }
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  85 */     return "Generator";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/*  90 */     return "block.generator.gui.GuiGenerator";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConverting() {
/*  96 */     return (this.fuel > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOperationSoundFile() {
/* 102 */     return "Generators/GeneratorLoop.ogg";
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 107 */     return (ContainerIC2)new ContainerBaseGenerator(entityPlayer, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 122 */     if (var1 > 1)
/*     */     {
/* 124 */       return new int[] { 1 };
/*     */     }
/* 126 */     return new int[] { 0 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyAmount() {
/* 132 */     return IC2.energyGeneratorBase;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */