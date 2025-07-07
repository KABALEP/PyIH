/*     */ package ic2.core.item.upgrades;
/*     */ 
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.block.inventory.IItemTransporter;
/*     */ import ic2.core.block.inventory.filter.BackupItemFilter;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
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
/*     */ public class ExportUpgrade
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
/*  33 */           int aviableItems = Math.min(64, (int)(machine.getEnergy() * 3.0D));
/*  34 */           BackupItemFilter backupItemFilter = new BackupItemFilter(null);
/*  35 */           ItemStack stack = StackUtil.getFromInventory(machine, dir, getOwner(upgrade), (IItemTransporter.IFilter)backupItemFilter, aviableItems, false);
/*  36 */           if (stack != null) {
/*     */             
/*  38 */             int added = StackUtil.putInInventory(tile, dir.getInverse(), getOwner(upgrade), stack);
/*  39 */             if (added > 0) {
/*     */               
/*  41 */               StackUtil.getFromInventory(machine, dir, getOwner(upgrade), (IItemTransporter.IFilter)backupItemFilter, added, true);
/*  42 */               machine.useEnergy((added / 3), false);
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
/*  65 */       if (tile != null && (tile instanceof net.minecraft.inventory.IInventory || tile instanceof ic2.core.block.personal.IPersonalBlock)) {
/*     */         
/*  67 */         int aviableItems = Math.min(64, (int)(machine.getEnergy() * 3.0D));
/*  68 */         int itemsAdded = 0;
/*  69 */         List<ItemStack> left = new ArrayList<>();
/*  70 */         while (!results.isEmpty() && aviableItems > 0) {
/*     */           
/*  72 */           ItemStack item = results.remove(0);
/*  73 */           if (item != null) {
/*     */             
/*  75 */             ItemStack copy = item.func_77946_l();
/*  76 */             copy.field_77994_a = Math.min(aviableItems, item.field_77994_a);
/*  77 */             int added = StackUtil.putInInventory(tile, dir.getInverse(), getOwner(upgrade), copy);
/*  78 */             if (added > 0) {
/*     */               
/*  80 */               item.field_77994_a -= added;
/*  81 */               aviableItems -= added;
/*  82 */               itemsAdded += added;
/*  83 */               if (item.field_77994_a <= 0) {
/*     */                 continue;
/*     */               }
/*     */             } 
/*     */             
/*  88 */             left.add(item);
/*     */           } 
/*     */         } 
/*  91 */         machine.useEnergy(Math.max(1, itemsAdded / 3), false);
/*  92 */         if (!left.isEmpty())
/*     */         {
/*  94 */           results.addAll(left);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachine.UpgradeType getType() {
/* 103 */     return IMachine.UpgradeType.ImportExport;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 109 */     return "itemExportUpgrade";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getTexture() {
/* 115 */     return Ic2Icons.getTexture("i0")[136];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesOwner() {
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesDirection() {
/* 127 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\ExportUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */