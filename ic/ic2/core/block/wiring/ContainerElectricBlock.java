/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.slot.SlotCharge;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ public class ContainerElectricBlock
/*    */   extends ContainerIC2 {
/*    */   public TileEntityElectricBlock tileEntity;
/*    */   public int energy;
/*    */   
/*    */   public ContainerElectricBlock(InventoryPlayer player, TileEntityElectricBlock tileEntity) {
/* 23 */     this.energy = -1;
/* 24 */     this.tileEntity = tileEntity;
/* 25 */     func_75146_a((Slot)new SlotCharge((IInventory)tileEntity, tileEntity.tier, 0, 56, 17));
/* 26 */     func_75146_a((Slot)new SlotDischarge((IInventory)tileEntity, tileEntity.tier, 1, 56, 53)); int i;
/* 27 */     for (i = 0; i < 4; i++)
/*    */     {
/* 29 */       func_75146_a(new SlotArmor(i, player, player.func_70302_i_() - 1 - i, 8, 8 + i * 18));
/*    */     }
/* 31 */     for (i = 0; i < 3; i++) {
/*    */       
/* 33 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 35 */         func_75146_a(new Slot((IInventory)player, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 38 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 40 */       func_75146_a(new Slot((IInventory)player, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 47 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 53 */     return 6;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 59 */     return 0;
/*    */   }
/*    */   
/*    */   public static class SlotArmor
/*    */     extends Slot {
/*    */     int slot;
/*    */     InventoryPlayer inv;
/*    */     
/*    */     public SlotArmor(int iter, InventoryPlayer p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
/* 68 */       super((IInventory)p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
/* 69 */       this.slot = iter;
/* 70 */       this.inv = p_i1824_1_;
/*    */     }
/*    */     
/*    */     public int func_75219_a() {
/* 74 */       return 1;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean func_75214_a(ItemStack p_75214_1_) {
/* 79 */       if (p_75214_1_ == null) return false; 
/* 80 */       return p_75214_1_.func_77973_b().isValidArmor(p_75214_1_, this.slot, (Entity)this.inv.field_70458_d);
/*    */     }
/*    */ 
/*    */     
/*    */     @SideOnly(Side.CLIENT)
/*    */     public IIcon func_75212_b() {
/* 86 */       return ItemArmor.func_94602_b(this.slot);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\ContainerElectricBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */