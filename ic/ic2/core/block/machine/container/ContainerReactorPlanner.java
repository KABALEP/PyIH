/*     */ package ic2.core.block.machine.container;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.block.machine.logic.ReactorInventory;
/*     */ import ic2.core.block.machine.logic.ReactorLogicBase;
/*     */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerReactorPlanner
/*     */   extends ContainerIC2
/*     */ {
/*     */   public TileEntityReactorPlanner planner;
/*     */   
/*     */   public ContainerReactorPlanner(InventoryPlayer player, TileEntityReactorPlanner tile) {
/*  23 */     this.planner = tile;
/*  24 */     addReactor();
/*  25 */     addComponents();
/*  26 */     addPlayerInv(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  31 */     this.field_75153_a.clear();
/*  32 */     this.field_75151_b.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset(InventoryPlayer player) {
/*  37 */     clear();
/*  38 */     addReactor();
/*  39 */     addComponents();
/*  40 */     addPlayerInv(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addReactor() {
/*  45 */     ReactorInventory inv = new ReactorInventory(this.planner);
/*  46 */     int size = this.planner.reactorSize + 3;
/*  47 */     int remove = this.planner.reactorSize * 9;
/*  48 */     for (int i = 0; i < 54; i++) {
/*     */       
/*  50 */       int x = i % 9;
/*  51 */       int y = i / 9;
/*  52 */       if (x < size)
/*     */       {
/*  54 */         func_75146_a(new SlotPlanner((IInventory)inv, i, 61 + x * 18 - remove, 21 + y * 18));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addComponents() {
/*  61 */     for (int y = 0; y < 5; y++) {
/*     */       
/*  63 */       for (int x = 0; x < 3; x++)
/*     */       {
/*  65 */         func_75146_a(new SlotComponent((IInventory)this.planner.components, x + y * 3, 216 + 18 * x, 18 + 18 * y));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPlayerInv(InventoryPlayer player) {
/*  72 */     for (int j = 0; j < 9; j++)
/*     */     {
/*  74 */       func_75146_a(new Slot((IInventory)player, j, 8 + j * 18, 188));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int guiInventorySize() {
/*  81 */     return 15 + (this.planner.reactorSize + 3) * 9;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInput() {
/*  87 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75141_a(int slotID, ItemStack stack) {
/*  93 */     if (slotID >= this.field_75151_b.size()) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     Slot slot = func_75139_a(slotID);
/*  98 */     if (slot == null) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     slot.func_75215_d(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/* 108 */     return this.planner.canInteractWith(player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ItemStack func_75144_a(int slotId, int dragType, int clickType, EntityPlayer player) {
/* 115 */     if (slotId != -999) {
/*     */       
/* 117 */       if (slotId >= guiInventorySize())
/*     */       {
/* 119 */         return null;
/*     */       }
/* 121 */       boolean client = this.planner.isRendering();
/* 122 */       Slot slot = func_75139_a(slotId);
/* 123 */       if (slot instanceof SlotComponent) {
/*     */         
/* 125 */         if (client)
/*     */         {
/* 127 */           return null;
/*     */         }
/* 129 */         this.planner.selectedSlot = slot.getSlotIndex();
/* 130 */         this.planner.getNetwork().updateTileEntityField((TileEntity)this.planner, "selectedSlot");
/* 131 */         return null;
/*     */       } 
/* 133 */       if (slot instanceof SlotPlanner) {
/*     */         
/* 135 */         if (client)
/*     */         {
/* 137 */           return null;
/*     */         }
/* 139 */         ReactorLogicBase logic = this.planner.getReactorLogic();
/* 140 */         ReactorLogicBase.IReactorPrediction predict = this.planner.getPrediction();
/* 141 */         if (dragType == 0) {
/*     */           
/* 143 */           if (logic.isValid()) {
/*     */             
/* 145 */             predict.clear();
/* 146 */             logic.reset();
/* 147 */             this.planner.updatePrediction();
/* 148 */             this.planner.updateReactor();
/*     */           } 
/* 150 */           ItemStack stack = ItemStack.func_77944_b(this.planner.components.func_70301_a(this.planner.selectedSlot));
/* 151 */           if (stack != null)
/*     */           {
/* 153 */             stack.field_77994_a = Math.min(stack.func_77976_d(), this.planner.stackSize);
/*     */           }
/* 155 */           slot.func_75215_d(stack);
/* 156 */           func_75142_b();
/*     */         }
/* 158 */         else if (dragType == 1) {
/*     */           
/* 160 */           if (logic.isValid()) {
/*     */             
/* 162 */             predict.clear();
/* 163 */             logic.reset();
/* 164 */             this.planner.updatePrediction();
/* 165 */             this.planner.updateReactor();
/*     */           } 
/* 167 */           slot.func_75215_d(null);
/* 168 */           func_75142_b();
/*     */         } 
/* 170 */         return null;
/*     */       } 
/*     */     } 
/* 173 */     return super.func_75144_a(slotId, dragType, clickType, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SlotComponent
/*     */     extends SlotGhoest
/*     */   {
/*     */     public SlotComponent(IInventory inv, int index, int xPosition, int yPosition) {
/* 181 */       super(inv, index, xPosition, yPosition);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SlotPlanner
/*     */     extends SlotGhoest
/*     */   {
/*     */     public SlotPlanner(IInventory inv, int index, int xPosition, int yPosition) {
/* 189 */       super(inv, index, xPosition, yPosition);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SlotGhoest
/*     */     extends Slot
/*     */   {
/*     */     public SlotGhoest(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
/* 198 */       super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean func_75214_a(ItemStack p_75214_1_) {
/* 204 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean func_82869_a(EntityPlayer p_82869_1_) {
/* 210 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerReactorPlanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */