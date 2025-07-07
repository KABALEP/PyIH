/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.item.IMachineUpgradeItem;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.slot.SlotCustom;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ public class ContainerUpgradeContainer
/*     */   extends ContainerIC2
/*     */ {
/*     */   HandHeldUpgradeContainer inv;
/*     */   
/*     */   public ContainerUpgradeContainer(HandHeldUpgradeContainer par1, InventoryPlayer par2) {
/*  21 */     this.inv = par1; int i;
/*  22 */     for (i = 0; i < 3; i++)
/*     */     {
/*  24 */       func_75146_a((Slot)new UpgradeSlot((IInventory)par1, i, 62 + i * 18, 17));
/*     */     }
/*     */     
/*  27 */     for (i = 0; i < 3; i++) {
/*     */       
/*  29 */       for (int k = 0; k < 9; k++)
/*     */       {
/*  31 */         func_75146_a(new Slot((IInventory)par2, k + i * 9 + 9, 8 + k * 18, 52 + i * 18));
/*     */       }
/*     */     } 
/*  34 */     for (int j = 0; j < 9; j++)
/*     */     {
/*  36 */       func_75146_a(new Slot((IInventory)par2, j, 8 + j * 18, 110));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int guiInventorySize() {
/*  43 */     return 3;
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
/*     */   public boolean func_75145_c(EntityPlayer p0) {
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75134_a(EntityPlayer p_75134_1_) {
/*  61 */     this.inv.onGuiClosed(p_75134_1_);
/*  62 */     super.func_75134_a(p_75134_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_75144_a(int slot, int button, int par3, EntityPlayer entityPlayer) {
/*  67 */     if (IC2.platform.isSimulating())
/*     */     {
/*  69 */       if (par3 == 4 && slot != -999) {
/*     */         
/*  71 */         ItemStack stack = this.field_75153_a.get(slot);
/*  72 */         if (stack != null) {
/*     */           
/*  74 */           NBTTagCompound nbtTagCompoundSlot = StackUtil.getOrCreateNbtData(stack);
/*  75 */           if (this.inv.matchesUid(nbtTagCompoundSlot.func_74762_e("uid")))
/*     */           {
/*  77 */             this.inv.onGuiClosed(entityPlayer);
/*  78 */             entityPlayer.func_71053_j();
/*     */           }
/*     */         
/*     */         } 
/*  82 */       } else if (slot == -999 && (button == 0 || button == 1)) {
/*     */         
/*  84 */         ItemStack itemStackSlot = entityPlayer.field_71071_by.func_70445_o();
/*  85 */         if (itemStackSlot != null) {
/*     */           
/*  87 */           NBTTagCompound nbtTagCompoundSlot = StackUtil.getOrCreateNbtData(itemStackSlot);
/*  88 */           if (this.inv.matchesUid(nbtTagCompoundSlot.func_74762_e("uid"))) {
/*     */             
/*  90 */             this.inv.onGuiClosed(entityPlayer);
/*  91 */             entityPlayer.func_71053_j();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*  96 */     return super.func_75144_a(slot, button, par3, entityPlayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class UpgradeSlot
/*     */     extends SlotCustom
/*     */   {
/*     */     public UpgradeSlot(IInventory iinventory, int i, int j, int k) {
/* 104 */       super(iinventory, new Object[] { IMachineUpgradeItem.class }, i, j, k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean func_75214_a(ItemStack itemstack) {
/* 111 */       if (itemstack != null && itemstack.func_77973_b() instanceof ItemUpgradeContainer)
/*     */       {
/* 113 */         return false;
/*     */       }
/* 115 */       return super.func_75214_a(itemstack);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ContainerUpgradeContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */