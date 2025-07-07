/*     */ package ic2.core.item.upgrades;
/*     */ 
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.block.inventory.IItemTransporter;
/*     */ import ic2.core.block.inventory.filter.BackupItemFilter;
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
/*     */ 
/*     */ 
/*     */ public class BasicExportUpgrade
/*     */   extends BaseMetaUpgrade
/*     */ {
/*     */   public boolean onTick(ItemStack upgrade, IMachine machine) {
/*  27 */     if (!machine.isProcessing() && getWorldTime(machine) % 20L == 0L) {
/*     */       
/*  29 */       Direction dir = getDirection(upgrade);
/*  30 */       if (dir != null) {
/*     */         
/*  32 */         TileEntity tile = dir.applyToTileEntity((TileEntity)machine);
/*  33 */         if (tile != null && (tile instanceof net.minecraft.inventory.IInventory || tile instanceof ic2.core.block.personal.IPersonalBlock)) {
/*     */           
/*  35 */           BackupItemFilter backupItemFilter = new BackupItemFilter(null);
/*  36 */           ItemStack stack = StackUtil.getFromInventory(machine, dir.getInverse(), getOwner(upgrade), (IItemTransporter.IFilter)backupItemFilter, 1, false);
/*  37 */           if (stack != null) {
/*     */             
/*  39 */             int added = StackUtil.putInInventory(tile, dir, getOwner(upgrade), stack);
/*  40 */             if (added > 0) {
/*     */               
/*  42 */               StackUtil.getFromInventory(machine, dir.getInverse(), getOwner(upgrade), (IItemTransporter.IFilter)backupItemFilter, added, true);
/*  43 */               return true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsTick() {
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProcessEnd(ItemStack upgrade, IMachine machine, List<ItemStack> results) {
/*  61 */     Direction dir = getDirection(upgrade);
/*  62 */     if (dir != null) {
/*     */       
/*  64 */       TileEntity tile = dir.applyToTileEntity((TileEntity)machine);
/*  65 */       if (tile != null && (tile instanceof net.minecraft.inventory.IInventory || tile instanceof ic2.core.block.personal.IPersonalBlock))
/*     */       {
/*  67 */         for (int i = 0; i < results.size(); i++) {
/*     */           
/*  69 */           ItemStack item = results.get(i);
/*  70 */           if (item != null) {
/*     */             
/*  72 */             ItemStack copy = item.func_77946_l();
/*  73 */             copy.field_77994_a = 1;
/*  74 */             int added = StackUtil.putInInventory(tile, dir, getOwner(upgrade), copy);
/*  75 */             if (added > 0) {
/*     */               
/*  77 */               item.field_77994_a -= added;
/*  78 */               if (item.field_77994_a <= 0) {
/*     */                 
/*  80 */                 results.remove(i);
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachine.UpgradeType getType() {
/*  94 */     return IMachine.UpgradeType.ImportExport;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 100 */     return "basicItemExportUpgrade";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getTexture() {
/* 106 */     return Ic2Icons.getTexture("i0")[135];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesOwner() {
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesDirection() {
/* 118 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\BasicExportUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */