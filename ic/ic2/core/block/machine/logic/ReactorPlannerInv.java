/*     */ package ic2.core.block.machine.logic;
/*     */ 
/*     */ import ic2.api.network.INetworkFieldData;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ public class ReactorPlannerInv
/*     */   implements IInventory, INetworkFieldData
/*     */ {
/*     */   TileEntityReactorPlanner planner;
/*  19 */   ItemStack[] currentSet = new ItemStack[0];
/*  20 */   ItemStack[] items = new ItemStack[15];
/*  21 */   public int offset = 0;
/*  22 */   public byte type = -1;
/*     */ 
/*     */   
/*     */   public ReactorPlannerInv(TileEntityReactorPlanner tile) {
/*  26 */     this.planner = tile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void create() {
/*  31 */     this.currentSet = getItemsFromType();
/*  32 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   void init() {
/*  37 */     int start = this.offset * 3;
/*  38 */     for (int i = 0; i < 15; i++) {
/*     */       
/*  40 */       int slot = start + i;
/*  41 */       if (slot >= this.currentSet.length) {
/*     */         
/*  43 */         this.items[i] = null;
/*     */       } else {
/*     */         
/*  46 */         ItemStack stack = this.currentSet[slot];
/*  47 */         if (stack == null) {
/*     */           
/*  49 */           this.items[i] = null;
/*     */         } else {
/*     */           
/*  52 */           this.items[i] = StackUtil.copyWithSize(stack, 1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public boolean canDecrease() {
/*  58 */     return (this.offset > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canIncrease() {
/*  63 */     return (this.offset * 3 + 15 < ((this.currentSet == null) ? 0 : this.currentSet.length));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFilter(IReactorPlannerComponent.ReactorComponentType newType) {
/*  68 */     this.offset = 0;
/*  69 */     this.type = (byte)((newType == null) ? -1 : newType.ordinal());
/*  70 */     this.currentSet = getItemsFromType();
/*  71 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNextFilter() {
/*  76 */     IReactorPlannerComponent.ReactorComponentType[] array = IReactorPlannerComponent.ReactorComponentType.values();
/*  77 */     if (this.type + 1 >= array.length) {
/*     */       
/*  79 */       setFilter(null);
/*     */       return;
/*     */     } 
/*  82 */     setFilter(array[this.type + 1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyOffset(int extra) {
/*  87 */     if (extra > 0) {
/*     */       
/*  89 */       int realOffset = this.offset * 3 + 15;
/*  90 */       if (realOffset >= this.currentSet.length) {
/*     */         return;
/*     */       }
/*     */       
/*  94 */       this.offset += extra;
/*  95 */       int max = (this.currentSet.length - 15) / 3 + 1;
/*  96 */       if (this.offset > max)
/*     */       {
/*  98 */         this.offset = max;
/*     */       }
/* 100 */       init();
/*     */     }
/* 102 */     else if (extra < 0) {
/*     */       
/* 104 */       this.offset += extra;
/* 105 */       if (this.offset < 0)
/*     */       {
/* 107 */         this.offset = 0;
/*     */       }
/* 109 */       init();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 116 */     return 15;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slot) {
/* 122 */     return this.items[slot];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int p_70304_1_) {
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_) {
/* 140 */     this.items[p_70299_1_] = p_70299_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 146 */     return "Planner Inv";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 158 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70296_d() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer p_70300_1_) {
/* 169 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70305_f() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] getItemsFromType() {
/* 190 */     return PlannerRegistry.getItemsByType(this.type, this.planner.isSteamReactor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 195 */     this.type = nbt.func_74771_c("Type");
/* 196 */     this.offset = nbt.func_74762_e("Offset");
/* 197 */     this.currentSet = getItemsFromType();
/* 198 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 203 */     nbt.func_74774_a("Type", this.type);
/* 204 */     nbt.func_74768_a("Offset", this.offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(DataInput stream) {
/*     */     try {
/* 212 */       this.type = stream.readByte();
/* 213 */       this.offset = stream.readInt();
/* 214 */       this.currentSet = new ItemStack[stream.readInt()];
/*     */     }
/* 216 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(DataOutput stream) {
/*     */     try {
/* 226 */       stream.writeByte(this.type);
/* 227 */       stream.writeInt(this.offset);
/* 228 */       stream.writeInt((this.currentSet == null) ? 0 : this.currentSet.length);
/*     */     }
/* 230 */     catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\ReactorPlannerInv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */