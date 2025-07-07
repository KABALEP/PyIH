/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.machine.container.ContainerCropScanner;
/*     */ import ic2.core.item.ItemCropSeed;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ 
/*     */ public class TileEntityCropScanner
/*     */   extends TileEntityElecMachine
/*     */   implements IHasGui
/*     */ {
/*  16 */   public int[] progresses = new int[] { 1, 9, 90, 900 };
/*     */   
/*     */   public int progress;
/*     */   
/*     */   public TileEntityCropScanner() {
/*  21 */     super(3, 0, 10000, 32, 1);
/*  22 */     addGuiFields(new String[] { "progress" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int p_94128_1_) {
/*  28 */     return new int[] { 1, 2 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  34 */     return "Crop Scanner";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxProgress() {
/*  39 */     if (this.inventory[1] == null)
/*     */     {
/*  41 */       return 0;
/*     */     }
/*  43 */     int scanLevel = ItemCropSeed.getScannedFromStack(this.inventory[1]);
/*  44 */     if (scanLevel <= -1 || scanLevel >= 4)
/*     */     {
/*  46 */       return 0;
/*     */     }
/*  48 */     return this.progresses[scanLevel];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/*  54 */     return (i == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/*  60 */     return (i == 1 && itemstack != null && itemstack.func_77973_b() instanceof ItemCropSeed);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  66 */     super.func_145845_h();
/*  67 */     if (this.inventory[1] == null || !(this.inventory[1].func_77973_b() instanceof ItemCropSeed) || this.inventory[2] != null) {
/*     */       
/*  69 */       if (this.progress > 0) {
/*     */         
/*  71 */         this.progress = 0;
/*  72 */         getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */       } 
/*     */       return;
/*     */     } 
/*  76 */     int scanLevel = ItemCropSeed.getScannedFromStack(this.inventory[1]);
/*  77 */     if (scanLevel >= 4 || scanLevel == -1) {
/*     */       
/*  79 */       if (this.progress > 0) {
/*     */         
/*  81 */         this.progress = 0;
/*  82 */         getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */       } 
/*     */       return;
/*     */     } 
/*  86 */     int maxProgress = this.progresses[scanLevel];
/*  87 */     this.progress++;
/*  88 */     if (this.progress >= maxProgress) {
/*     */       
/*  90 */       this.progress = 0;
/*  91 */       ItemStack item = this.inventory[1].func_77946_l();
/*  92 */       this.inventory[1] = null;
/*  93 */       ItemCropSeed.incrementScannedOfStack(item);
/*  94 */       scanLevel = ItemCropSeed.getScannedFromStack(item);
/*  95 */       int newSlot = (scanLevel >= 4 || scanLevel == -1) ? 2 : 1;
/*  96 */       this.inventory[newSlot] = item.func_77946_l();
/*     */     } 
/*  98 */     getNetwork().updateTileGuiField((TileEntity)this, "progress");
/*     */   }
/*     */ 
/*     */   
/*     */   public float getChargeLevel() {
/* 103 */     return this.energy / this.maxEnergy;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getProgress() {
/* 108 */     return this.progress / getMaxProgress();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 114 */     return (ContainerIC2)new ContainerCropScanner(p0.field_71071_by, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 120 */     return "block.machine.gui.GuiCropScanner";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer p0) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 132 */     return 10;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityCropScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */