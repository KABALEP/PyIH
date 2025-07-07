/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.item.ItemCropSeed;
/*    */ import ic2.core.slot.SlotCustom;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class ContainerCropnalyzer
/*    */   extends ContainerIC2 {
/*    */   public HandHeldCropnalyzer cropnalyzer;
/*    */   
/*    */   public ContainerCropnalyzer(EntityPlayer entityPlayer, HandHeldCropnalyzer cropnalyzer) {
/* 20 */     this.cropnalyzer = cropnalyzer;
/* 21 */     func_75146_a((Slot)new SlotCustom(cropnalyzer, new Object[] { ItemCropSeed.class }, 0, 8, 7));
/* 22 */     func_75146_a((Slot)new SlotCustom(cropnalyzer, new Object[0], 1, 41, 7));
/* 23 */     func_75146_a((Slot)new SlotDischarge(cropnalyzer, 2, 152, 7));
/* 24 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 26 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 33 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 39 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_75137_b(int index, int value) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 50 */     return this.cropnalyzer.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_75144_a(int slot, int button, int par3, EntityPlayer entityPlayer) {
/* 55 */     if (IC2.platform.isSimulating() && slot == -999)
/*    */     {
/* 57 */       if (par3 == 4 && slot != -999) {
/*    */         
/* 59 */         ItemStack stack = this.field_75153_a.get(slot);
/* 60 */         if (stack != null) {
/*    */           
/* 62 */           NBTTagCompound nbtTagCompoundSlot = StackUtil.getOrCreateNbtData(stack);
/* 63 */           if (this.cropnalyzer.matchesUid(nbtTagCompoundSlot.func_74762_e("uid")))
/*    */           {
/* 65 */             this.cropnalyzer.onGuiClosed(entityPlayer);
/* 66 */             entityPlayer.func_71053_j();
/*    */           }
/*    */         
/*    */         } 
/* 70 */       } else if (slot == -999 && (button == 0 || button == 1)) {
/*    */         
/* 72 */         ItemStack itemStackSlot = entityPlayer.field_71071_by.func_70445_o();
/* 73 */         if (itemStackSlot != null) {
/*    */           
/* 75 */           NBTTagCompound nbtTagCompoundSlot = StackUtil.getOrCreateNbtData(itemStackSlot);
/* 76 */           if (this.cropnalyzer.matchesUid(nbtTagCompoundSlot.func_74762_e("uid"))) {
/*    */             
/* 78 */             this.cropnalyzer.onGuiClosed(entityPlayer);
/* 79 */             entityPlayer.func_71053_j();
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     }
/* 84 */     return super.func_75144_a(slot, button, par3, entityPlayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75134_a(EntityPlayer entityPlayer) {
/* 89 */     this.cropnalyzer.onGuiClosed(entityPlayer);
/* 90 */     super.func_75134_a(entityPlayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ContainerCropnalyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */