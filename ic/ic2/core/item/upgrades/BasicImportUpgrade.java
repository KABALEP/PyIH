/*     */ package ic2.core.item.upgrades;
/*     */ 
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.block.inventory.IItemTransporter;
/*     */ import ic2.core.block.inventory.filter.MachineFilter;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicImportUpgrade
/*     */   extends BaseMetaUpgrade
/*     */ {
/*     */   public boolean onTick(ItemStack upgrade, IMachine machine) {
/*  25 */     if (!machine.isProcessing() && getWorldTime(machine) % 20L == 0L) {
/*     */       
/*  27 */       Direction dir = getDirection(upgrade);
/*  28 */       if (dir != null) {
/*     */         
/*  30 */         TileEntity tile = dir.applyToTileEntity((TileEntity)machine);
/*  31 */         if (tile != null && (tile instanceof net.minecraft.inventory.IInventory || tile instanceof ic2.core.block.personal.IPersonalBlock)) {
/*     */           
/*  33 */           MachineFilter machineFilter = new MachineFilter(machine);
/*  34 */           ItemStack item = StackUtil.getFromInventory(tile, dir, getOwner(upgrade), (IItemTransporter.IFilter)machineFilter, 1, false);
/*  35 */           if (item != null) {
/*     */             
/*  37 */             int added = StackUtil.putInInventory(machine, dir.getInverse(), getOwner(upgrade), item);
/*  38 */             if (added > 0) {
/*     */               
/*  40 */               StackUtil.getFromInventory(tile, dir, getOwner(upgrade), (IItemTransporter.IFilter)machineFilter, added, true);
/*  41 */               return true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  47 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsTick() {
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProcessEnd(ItemStack upgrade, IMachine machine, List<ItemStack> results) {
/*  59 */     Direction dir = getDirection(upgrade);
/*  60 */     if (dir != null) {
/*     */       
/*  62 */       TileEntity tile = dir.applyToTileEntity((TileEntity)machine);
/*  63 */       if (tile != null && (tile instanceof net.minecraft.inventory.IInventory || tile instanceof ic2.core.block.personal.IPersonalBlock)) {
/*     */         
/*  65 */         MachineFilter machineFilter = new MachineFilter(machine);
/*  66 */         ItemStack item = StackUtil.getFromInventory(tile, dir, getOwner(upgrade), (IItemTransporter.IFilter)machineFilter, 1, false);
/*  67 */         if (item != null) {
/*     */           
/*  69 */           int added = StackUtil.putInInventory(machine, dir.getInverse(), getOwner(upgrade), item);
/*  70 */           if (added > 0)
/*     */           {
/*  72 */             StackUtil.getFromInventory(tile, dir, getOwner(upgrade), (IItemTransporter.IFilter)machineFilter, added, true);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachine.UpgradeType getType() {
/*  82 */     return IMachine.UpgradeType.ImportExport;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getTexture() {
/*  88 */     return Ic2Icons.getTexture("i0")[133];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  94 */     return "basicItemImportUpgrade";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesOwner() {
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesDirection() {
/* 106 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\BasicImportUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */