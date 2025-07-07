/*     */ package ic2.core.block.inventory.transporter;
/*     */ 
/*     */ import ic2.core.block.inventory.IInvSlot;
/*     */ import ic2.core.block.inventory.IItemTransporter;
/*     */ import ic2.core.block.inventory.TransporterManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicTransporter
/*     */   extends BaseTransporter
/*     */ {
/*     */   IInventory inv;
/*     */   
/*     */   public BasicTransporter(IInventory par1) {
/*  21 */     this.inv = par1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(IItemTransporter.IFilter filter, ForgeDirection dir, int amount, boolean doRemove) {
/*  28 */     ItemStack item = null;
/*  29 */     int realAmount = amount;
/*  30 */     for (IInvSlot slot : TransporterManager.manager.getIteratorForInventory(this.inv, dir)) {
/*     */       
/*  32 */       ItemStack stack = slot.getStack();
/*  33 */       if (stack != null && slot.canRemoveStack(stack) && filter.matches(stack) && (item == null || (canMerge(item, stack) && item.field_77994_a < amount))) {
/*     */         
/*  35 */         int max = Math.min(amount - getStackSize(item), stack.field_77994_a);
/*  36 */         if (doRemove) {
/*     */           
/*  38 */           if (item == null) {
/*     */             
/*  40 */             item = slot.decreaseStackInSlot(max).func_77946_l();
/*     */             
/*     */             continue;
/*     */           } 
/*  44 */           ItemStack itemAmount = slot.decreaseStackInSlot(max).func_77946_l();
/*  45 */           item.field_77994_a += itemAmount.field_77994_a;
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*  50 */         if (item == null) {
/*     */           
/*  52 */           item = stack.func_77946_l();
/*  53 */           item.field_77994_a = max;
/*     */           
/*     */           continue;
/*     */         } 
/*  57 */         item.field_77994_a += max;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  62 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getStackSize(ItemStack par1) {
/*  67 */     if (par1 == null)
/*     */     {
/*  69 */       return 0;
/*     */     }
/*  71 */     return par1.field_77994_a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int injectItem(ItemStack stack, ForgeDirection dir, boolean doAdd) {
/*  77 */     List<IInvSlot> filledSlots = new ArrayList<>();
/*  78 */     List<IInvSlot> emptySlots = new ArrayList<>();
/*  79 */     for (IInvSlot slot : TransporterManager.manager.getIteratorForInventory(this.inv, dir)) {
/*     */       
/*  81 */       if (slot.canInsertStack(stack)) {
/*     */         
/*  83 */         if (slot.getStack() == null) {
/*     */           
/*  85 */           emptySlots.add(slot);
/*     */           
/*     */           continue;
/*     */         } 
/*  89 */         filledSlots.add(slot);
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     int injected = 0;
/*  94 */     injected = tryPut(injected, stack, filledSlots, doAdd);
/*  95 */     injected = tryPut(injected, stack, emptySlots, doAdd);
/*  96 */     if (injected > 0 && doAdd)
/*     */     {
/*  98 */       this.inv.func_70296_d();
/*     */     }
/* 100 */     return injected;
/*     */   }
/*     */ 
/*     */   
/*     */   private int tryPut(int alreadyInjected, ItemStack stack, List<IInvSlot> slots, boolean doAdd) {
/* 105 */     if (alreadyInjected >= stack.field_77994_a)
/*     */     {
/* 107 */       return alreadyInjected;
/*     */     }
/* 109 */     for (IInvSlot slot : slots) {
/*     */       
/* 111 */       ItemStack item = slot.getStack();
/* 112 */       if (item == null || canMerge(stack, item)) {
/*     */         
/* 114 */         int used = addToSlot(slot, stack, alreadyInjected, doAdd);
/* 115 */         if (used > 0) {
/*     */           
/* 117 */           alreadyInjected += used;
/* 118 */           if (alreadyInjected >= stack.field_77994_a)
/*     */           {
/* 120 */             return alreadyInjected;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 125 */     return alreadyInjected;
/*     */   }
/*     */ 
/*     */   
/*     */   private int addToSlot(IInvSlot slot, ItemStack stack, int alreadyInjected, boolean doAdd) {
/* 130 */     int aviable = stack.field_77994_a - alreadyInjected;
/* 131 */     int max = Math.min(stack.func_77976_d(), this.inv.func_70297_j_());
/* 132 */     ItemStack item = slot.getStack();
/* 133 */     if (item == null) {
/*     */       
/* 135 */       int i = Math.min(aviable, max);
/* 136 */       if (doAdd) {
/*     */         
/* 138 */         item = stack.func_77946_l();
/* 139 */         item.field_77994_a = i;
/* 140 */         slot.setStack(item);
/*     */       } 
/* 142 */       return i;
/*     */     } 
/*     */     
/* 145 */     if (!canMerge(stack, item))
/*     */     {
/* 147 */       return 0;
/*     */     }
/* 149 */     int wanted = max - item.field_77994_a;
/* 150 */     if (wanted > aviable)
/*     */     {
/* 152 */       wanted = aviable;
/*     */     }
/* 154 */     if (doAdd) {
/*     */       
/* 156 */       item.field_77994_a += wanted;
/* 157 */       slot.setStack(item);
/*     */     } 
/* 159 */     return wanted;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\transporter\BasicTransporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */