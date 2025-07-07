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
/*     */ public class ImportUpgrade
/*     */   extends BaseMetaUpgrade
/*     */ {
/*     */   public boolean onTick(ItemStack upgrade, IMachine machine) {
/*  24 */     if (!machine.isProcessing() && getWorldTime(machine) % 20L == 0L) {
/*     */       
/*  26 */       Direction dir = getDirection(upgrade);
/*  27 */       if (dir != null) {
/*     */         
/*  29 */         TileEntity tile = dir.applyToTileEntity((TileEntity)machine);
/*  30 */         if (tile != null && (tile instanceof net.minecraft.inventory.IInventory || tile instanceof ic2.core.block.personal.IPersonalBlock)) {
/*     */           
/*  32 */           int amount = Math.min(64, (int)(machine.getEnergy() * 3.0D));
/*  33 */           MachineFilter machineFilter = new MachineFilter(machine);
/*  34 */           ItemStack item = StackUtil.getFromInventory(tile, dir, getOwner(upgrade), (IItemTransporter.IFilter)machineFilter, amount, false);
/*  35 */           if (item != null) {
/*     */             
/*  37 */             int added = StackUtil.putInInventory(machine, dir.getInverse(), getOwner(upgrade), item);
/*  38 */             if (added > 0) {
/*     */               
/*  40 */               StackUtil.getFromInventory(tile, dir, getOwner(upgrade), (IItemTransporter.IFilter)machineFilter, added, true);
/*  41 */               machine.useEnergy(Math.max(1, added / 3), false);
/*  42 */               return true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsTick() {
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProcessEnd(ItemStack upgrade, IMachine machine, List<ItemStack> results) {
/*  60 */     Direction dir = getDirection(upgrade);
/*  61 */     if (dir != null) {
/*     */       
/*  63 */       TileEntity tile = dir.applyToTileEntity((TileEntity)machine);
/*  64 */       if (tile != null && (tile instanceof net.minecraft.inventory.IInventory || tile instanceof ic2.core.block.personal.IPersonalBlock)) {
/*     */         
/*  66 */         int amount = Math.min(64, (int)(machine.getEnergy() * 3.0D));
/*  67 */         MachineFilter machineFilter = new MachineFilter(machine);
/*  68 */         ItemStack item = StackUtil.getFromInventory(tile, dir, getOwner(upgrade), (IItemTransporter.IFilter)machineFilter, amount, false);
/*  69 */         if (item != null) {
/*     */           
/*  71 */           int added = StackUtil.putInInventory(machine, dir.getInverse(), getOwner(upgrade), item);
/*  72 */           if (added > 0) {
/*     */             
/*  74 */             StackUtil.getFromInventory(tile, dir, getOwner(upgrade), (IItemTransporter.IFilter)machineFilter, added, true);
/*  75 */             machine.useEnergy(Math.max(1, added / 3), false);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachine.UpgradeType getType() {
/*  85 */     return IMachine.UpgradeType.ImportExport;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getTexture() {
/*  91 */     return Ic2Icons.getTexture("i0")[134];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  97 */     return "itemImportUpgrade";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesOwner() {
/* 103 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesDirection() {
/* 109 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\ImportUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */