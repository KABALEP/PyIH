/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.slot.SlotReactor;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ public class ContainerNuclearJetpack
/*     */   extends ContainerIC2 {
/*     */   public NuclearJetpackInventory inv;
/*     */   
/*     */   public ContainerNuclearJetpack(NuclearJetpackInventory par1, InventoryPlayer par2) {
/*  19 */     this.inv = par1;
/*  20 */     for (int y = 0; y < 5; y++) {
/*     */       
/*  22 */       for (int x = 0; x < 5; x++)
/*     */       {
/*  24 */         func_75146_a((Slot)new SlotReactor(par1, x + y * 5, 44 + x * 18, 18 + y * 18));
/*     */       }
/*     */     } 
/*  27 */     for (int i = 0; i < 3; i++) {
/*     */       
/*  29 */       for (int k = 0; k < 9; k++)
/*     */       {
/*  31 */         func_75146_a(new Slot((IInventory)par2, k + i * 9 + 9, 8 + k * 18, 140 + i * 18));
/*     */       }
/*     */     } 
/*  34 */     for (int j = 0; j < 9; j++)
/*     */     {
/*  36 */       func_75146_a(new Slot((IInventory)par2, j, 8 + j * 18, 198));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int guiInventorySize() {
/*  43 */     return 25;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInput() {
/*  49 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75137_b(int p0, int p1) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer p0) {
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75134_a(EntityPlayer par1EntityPlayer) {
/*  67 */     this.inv.onGuiClosed(par1EntityPlayer);
/*  68 */     super.func_75134_a(par1EntityPlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_75144_a(int slot, int button, int par3, EntityPlayer entityPlayer) {
/*  74 */     if (IC2.platform.isSimulating())
/*     */     {
/*  76 */       if (par3 == 4 && slot != -999) {
/*     */         
/*  78 */         ItemStack stack = this.field_75153_a.get(slot);
/*  79 */         if (stack != null) {
/*     */           
/*  81 */           NBTTagCompound nbtTagCompoundSlot = StackUtil.getOrCreateNbtData(stack);
/*  82 */           if (this.inv.id == nbtTagCompoundSlot.func_74762_e("Uuid"))
/*     */           {
/*  84 */             this.inv.onGuiClosed(entityPlayer);
/*  85 */             entityPlayer.func_71053_j();
/*     */           }
/*     */         
/*     */         } 
/*  89 */       } else if (slot == -999 && (button == 0 || button == 1)) {
/*     */         
/*  91 */         ItemStack itemStackSlot = entityPlayer.field_71071_by.func_70445_o();
/*  92 */         if (itemStackSlot != null) {
/*     */           
/*  94 */           NBTTagCompound nbtTagCompoundSlot = StackUtil.getOrCreateNbtData(itemStackSlot);
/*  95 */           if (this.inv.id == nbtTagCompoundSlot.func_74762_e("Uuid")) {
/*     */             
/*  97 */             this.inv.onGuiClosed(entityPlayer);
/*  98 */             entityPlayer.func_71053_j();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 103 */     return super.func_75144_a(slot, button, par3, entityPlayer);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ContainerNuclearJetpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */