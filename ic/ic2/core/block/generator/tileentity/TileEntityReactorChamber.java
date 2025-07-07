/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorChamber;
/*     */ import ic2.api.tile.IWrenchable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.ITickCallback;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class TileEntityReactorChamber
/*     */   extends TileEntity
/*     */   implements IWrenchable, IInventory, IReactorChamber, ISidedInventory
/*     */ {
/*  21 */   private static Direction[] directions = Direction.values();
/*     */   public TileEntityNuclearReactor reactorReference;
/*     */   private boolean loaded;
/*  24 */   private int ticker = 15;
/*     */   
/*     */   private boolean isPowered = false;
/*     */   
/*     */   public TileEntityReactorChamber() {
/*  29 */     this.loaded = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145829_t() {
/*  34 */     super.func_145829_t();
/*  35 */     if (!this.loaded)
/*     */     {
/*  37 */       if (!func_145837_r() && this.field_145850_b != null) {
/*     */         
/*  39 */         if (IC2.platform.isSimulating())
/*     */         {
/*  41 */           IC2.addSingleTickCallback(this.field_145850_b, new ITickCallback()
/*     */               {
/*     */                 
/*     */                 public void tickCallback(World world)
/*     */                 {
/*  46 */                   TileEntityReactorChamber.this.onLoaded();
/*     */                 }
/*     */               });
/*     */         }
/*     */         else
/*     */         {
/*  52 */           onLoaded();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  57 */         IC2.log.warn(this + " (" + this.field_145851_c + "," + this.field_145848_d + "," + this.field_145849_e + ") was not added, isInvalid=" + func_145837_r() + ", worldObj=" + this.field_145850_b);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/*  64 */     super.func_145843_s();
/*  65 */     if (this.loaded)
/*     */     {
/*  67 */       onUnloaded();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {
/*  73 */     super.onChunkUnload();
/*  74 */     if (this.loaded)
/*     */     {
/*  76 */       onUnloaded();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  82 */     this.loaded = true;
/*  83 */     if (IC2.platform.isSimulating()) {
/*     */       
/*  85 */       TileEntityNuclearReactor reactor = getReactor();
/*  86 */       if (reactor == null) {
/*     */         return;
/*     */       }
/*     */       
/*  90 */       reactor.refreshChambers();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  96 */     this.loaded = false;
/*  97 */     if (IC2.platform.isSimulating()) {
/*     */       
/*  99 */       TileEntityNuclearReactor reactor = getReactor();
/* 100 */       if (reactor == null) {
/*     */         return;
/*     */       }
/*     */       
/* 104 */       reactor.refreshChambers();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getFacing() {
/* 120 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacing(short facing) {}
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 134 */     return 0.8F;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
/* 139 */     return new ItemStack(this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e), 1, this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 144 */     TileEntityNuclearReactor reactor = getReactor();
/* 145 */     if (reactor == null)
/*     */     {
/* 147 */       return 0;
/*     */     }
/* 149 */     return reactor.func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int i) {
/* 154 */     TileEntityNuclearReactor reactor = getReactor();
/* 155 */     if (reactor == null)
/*     */     {
/* 157 */       return null;
/*     */     }
/* 159 */     return reactor.func_70301_a(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int i, int j) {
/* 164 */     TileEntityNuclearReactor reactor = getReactor();
/* 165 */     if (reactor == null)
/*     */     {
/* 167 */       return null;
/*     */     }
/* 169 */     return reactor.func_70298_a(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int i, ItemStack itemstack) {
/* 174 */     TileEntityNuclearReactor reactor = getReactor();
/* 175 */     if (reactor == null) {
/*     */       return;
/*     */     }
/*     */     
/* 179 */     reactor.func_70299_a(i, itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 184 */     TileEntityNuclearReactor reactor = getReactor();
/* 185 */     if (reactor == null)
/*     */     {
/* 187 */       return "Nuclear Reactor";
/*     */     }
/* 189 */     return reactor.func_145825_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 194 */     TileEntityNuclearReactor reactor = getReactor();
/* 195 */     if (reactor == null)
/*     */     {
/* 197 */       return 64;
/*     */     }
/* 199 */     return reactor.func_70297_j_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityplayer) {
/* 204 */     TileEntityNuclearReactor reactor = getReactor();
/* 205 */     return (reactor != null && reactor.func_70300_a(entityplayer));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {
/* 210 */     TileEntityNuclearReactor reactor = getReactor();
/* 211 */     if (reactor == null) {
/*     */       return;
/*     */     }
/*     */     
/* 215 */     reactor.func_70295_k_();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70305_f() {
/* 220 */     TileEntityNuclearReactor reactor = getReactor();
/* 221 */     if (reactor == null) {
/*     */       return;
/*     */     }
/*     */     
/* 225 */     reactor.func_70305_f();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int var1) {
/* 230 */     TileEntityNuclearReactor reactor = getReactor();
/* 231 */     if (reactor == null)
/*     */     {
/* 233 */       return null;
/*     */     }
/* 235 */     return reactor.func_70304_b(var1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/* 240 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 245 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 250 */     TileEntityNuclearReactor reactor = getReactor();
/* 251 */     if (reactor == null)
/*     */     {
/* 253 */       return null;
/*     */     }
/* 255 */     return reactor.func_94128_d(var1);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityNuclearReactor getReactor() {
/* 260 */     if (this.reactorReference != null && this.reactorReference.func_145837_r())
/*     */     {
/* 262 */       this.reactorReference = null;
/*     */     }
/* 264 */     if (this.reactorReference == null) {
/*     */       
/* 266 */       for (Direction value : directions) {
/*     */         
/* 268 */         TileEntity te = value.applyToTileEntity(this);
/* 269 */         if (te instanceof TileEntityNuclearReactor) {
/*     */           
/* 271 */           this.reactorReference = (TileEntityNuclearReactor)te;
/*     */           break;
/*     */         } 
/*     */       } 
/* 275 */       if (this.reactorReference == null) {
/*     */         
/* 277 */         Block blk = this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 278 */         if (blk != null)
/*     */         {
/* 280 */           blk.func_149695_a(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, blk);
/*     */         }
/*     */       } 
/*     */     } 
/* 284 */     return this.reactorReference;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 289 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRedstoneSignal(boolean redstone) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 300 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 306 */     super.func_145845_h();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityReactorChamber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */