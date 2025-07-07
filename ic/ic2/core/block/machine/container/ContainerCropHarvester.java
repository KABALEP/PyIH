/*     */ package ic2.core.block.machine.container;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCropHarvester;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerCropHarvester
/*     */   extends ContainerIC2 {
/*     */   public TileEntityCropHarvester machine;
/*     */   
/*     */   public ContainerCropHarvester(InventoryPlayer player, TileEntityCropHarvester crop) {
/*  18 */     this.machine = crop;
/*  19 */     for (int x = 0; x < 3; x++) {
/*     */       
/*  21 */       for (int y = 0; y < 3; y++)
/*     */       {
/*  23 */         func_75146_a(new SlotExtract((IInventory)crop, x + y * 3, 63 + 18 * x, 14 + y * 18)); } 
/*     */     } 
/*     */     int i;
/*  26 */     for (i = 0; i < 4; i++)
/*     */     {
/*  28 */       func_75146_a(new UpgradeSlot((IInventory)crop.inv, i, 152, 8 + 18 * i, crop));
/*     */     }
/*  30 */     func_75146_a(new UpgradeSlot((IInventory)crop.inv, 4, 134, 62, crop));
/*  31 */     for (i = 0; i < 3; i++) {
/*     */       
/*  33 */       for (int k = 0; k < 9; k++)
/*     */       {
/*  35 */         func_75146_a(new Slot((IInventory)player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*  38 */     for (int j = 0; j < 9; j++)
/*     */     {
/*  40 */       func_75146_a(new Slot((IInventory)player, j, 8 + j * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInput() {
/*  47 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer p0) {
/*  54 */     return this.machine.func_70300_a(p0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int guiInventorySize() {
/*  60 */     return 14;
/*     */   }
/*     */   
/*     */   public static class UpgradeSlot
/*     */     extends Slot {
/*     */     TileEntityCropHarvester harvester;
/*     */     
/*     */     public UpgradeSlot(IInventory inv, int index, int xPosition, int yPosition, TileEntityCropHarvester tile) {
/*  68 */       super(inv, index, xPosition, yPosition);
/*  69 */       this.harvester = tile;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean func_75214_a(ItemStack stack) {
/*  75 */       if (stack == null)
/*     */       {
/*  77 */         return false;
/*     */       }
/*  79 */       if (StackUtil.isStackEqual(stack, Ic2Items.overclockerUpgrade))
/*     */       {
/*  81 */         return true;
/*     */       }
/*  83 */       if (StackUtil.isStackEqual(stack, Ic2Items.padUpgradeBasicFieldUpgrade))
/*     */       {
/*  85 */         return true;
/*     */       }
/*  87 */       if (StackUtil.isStackEqual(stack, Ic2Items.padUpgradeFieldUpgrade))
/*     */       {
/*  89 */         return true;
/*     */       }
/*  91 */       if (StackUtil.isStackEqual(stack, Ic2Items.padUpgradeAdvFieldUpgrade))
/*     */       {
/*  93 */         return true;
/*     */       }
/*  95 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_75218_e() {
/* 101 */       this.harvester.setOverclockerUpgrade();
/* 102 */       super.func_75218_e();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SlotExtract
/*     */     extends Slot
/*     */   {
/*     */     public SlotExtract(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
/* 111 */       super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean func_75214_a(ItemStack p_75214_1_) {
/* 117 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerCropHarvester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */