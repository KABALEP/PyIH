/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.api.network.INetworkDataProvider;
/*     */ import ic2.api.network.INetworkUpdateListener;
/*     */ import ic2.api.tile.IWrenchable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.ITickCallback;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.network.internal.INetworkGuiDataProvider;
/*     */ import ic2.core.util.FilteredList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntityBlock
/*     */   extends TileEntity
/*     */   implements INetworkDataProvider, INetworkUpdateListener, IWrenchable, INetworkGuiDataProvider {
/*     */   private boolean active = false;
/*  26 */   private short facing = 0;
/*     */   public boolean prevActive = false;
/*  28 */   public short prevFacing = 0;
/*     */   
/*     */   public boolean loaded = false;
/*     */   private final boolean isServer;
/*     */   private final NetworkManager manager;
/*  33 */   private final List<String> networkFields = (List<String>)new FilteredList();
/*  34 */   private final List<String> guiFields = (List<String>)new FilteredList();
/*     */ 
/*     */   
/*     */   public TileEntityBlock() {
/*  38 */     this.isServer = IC2.platform.isSimulating();
/*  39 */     this.manager = (NetworkManager)IC2.network.get();
/*  40 */     addNetworkFields(new String[] { "active", "facing" });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addNetworkFields(String... fields) {
/*  45 */     for (String field : fields)
/*     */     {
/*  47 */       this.networkFields.add(field);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addGuiFields(String... fields) {
/*  53 */     for (String field : fields)
/*     */     {
/*  55 */       this.guiFields.add(field);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getGuiFields() {
/*  62 */     return this.guiFields;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/*  68 */     return this.networkFields;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145829_t() {
/*  73 */     super.func_145829_t();
/*  74 */     if (!this.loaded)
/*     */     {
/*  76 */       if (!func_145837_r() && this.field_145850_b != null) {
/*     */         
/*  78 */         if (isSimulating())
/*     */         {
/*  80 */           IC2.addSingleTickCallback(this.field_145850_b, new ITickCallback()
/*     */               {
/*     */                 
/*     */                 public void tickCallback(World world)
/*     */                 {
/*  85 */                   TileEntityBlock.this.onLoaded();
/*     */                 }
/*     */               });
/*     */         }
/*     */         else
/*     */         {
/*  91 */           onLoaded();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  96 */         IC2.log.warn(this + " (" + this.field_145851_c + "," + this.field_145848_d + "," + this.field_145849_e + ") was not added, isInvalid=" + func_145837_r() + ", worldObj=" + this.field_145850_b);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145843_s() {
/* 103 */     if (this.loaded)
/*     */     {
/* 105 */       onUnloaded();
/*     */     }
/* 107 */     super.func_145843_s();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {
/* 112 */     if (this.loaded)
/*     */     {
/* 114 */       onUnloaded();
/*     */     }
/* 116 */     super.onChunkUnload();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 121 */     if (isRendering())
/*     */     {
/* 123 */       ((NetworkManager)IC2.network.get()).requestInitialData(this);
/*     */     }
/* 125 */     this.loaded = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 130 */     this.loaded = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 135 */     super.func_145839_a(nbttagcompound);
/* 136 */     short short1 = nbttagcompound.func_74765_d("facing");
/* 137 */     this.facing = short1;
/* 138 */     this.prevFacing = short1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 143 */     super.func_145841_b(nbttagcompound);
/* 144 */     nbttagcompound.func_74777_a("facing", this.facing);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getActive() {
/* 154 */     return this.active;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActive(boolean active) {
/* 159 */     this.active = active;
/* 160 */     if (this.prevActive != active)
/*     */     {
/* 162 */       getNetwork().updateTileEntityField(this, "active");
/*     */     }
/* 164 */     this.prevActive = active;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActiveWithoutNotify(boolean active) {
/* 169 */     this.active = active;
/* 170 */     this.prevActive = active;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getFacing() {
/* 175 */     return this.facing;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 180 */     if ((field.equals("active") && this.prevActive != this.active) || (field.equals("facing") && this.prevFacing != this.facing)) {
/*     */       
/* 182 */       Block block = this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 183 */       if (block != null) {
/*     */         
/* 185 */         boolean newActive = this.active;
/* 186 */         short newFacing = this.facing;
/* 187 */         this.active = this.prevActive;
/* 188 */         this.facing = this.prevFacing;
/* 189 */         IIcon[] textureIndex = new IIcon[6]; int side;
/* 190 */         for (side = 0; side < 6; side++)
/*     */         {
/* 192 */           textureIndex[side] = IC2.platform.getBlockTexture(block, (IBlockAccess)this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, side);
/*     */         }
/* 194 */         this.active = newActive;
/* 195 */         this.facing = newFacing;
/* 196 */         for (side = 0; side < 6; side++) {
/*     */           
/* 198 */           IIcon newTextureIndex = IC2.platform.getBlockTexture(block, (IBlockAccess)this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, side);
/* 199 */           if (textureIndex[side] != newTextureIndex) {
/*     */             
/* 201 */             this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 208 */         System.out.println("[IC2] Invalid TE at " + this.field_145851_c + "/" + this.field_145848_d + "/" + this.field_145849_e + ", no corresponding block");
/*     */       } 
/* 210 */       this.prevActive = this.active;
/* 211 */       this.prevFacing = this.facing;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFacing(short facing) {
/* 222 */     this.facing = facing;
/* 223 */     if (this.prevFacing != facing)
/*     */     {
/* 225 */       getNetwork().updateTileEntityField(this, "facing");
/*     */     }
/* 227 */     this.prevFacing = facing;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
/* 232 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 237 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
/* 242 */     return new ItemStack(func_145838_q(), 1, func_145832_p());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockBreak(Block a, int b) {}
/*     */ 
/*     */   
/*     */   public boolean hasTileMeta() {
/* 251 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTileMeta() {
/* 256 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSimulating() {
/* 261 */     return this.isServer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRendering() {
/* 266 */     return !this.isServer;
/*     */   }
/*     */ 
/*     */   
/*     */   public NetworkManager getNetwork() {
/* 271 */     return this.manager;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NBTTagCompound getNBT(NBTTagCompound data, String tag) {
/* 276 */     if (!data.func_74764_b(tag))
/*     */     {
/* 278 */       data.func_74782_a(tag, (NBTBase)new NBTTagCompound());
/*     */     }
/* 280 */     return data.func_74775_l(tag);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\TileEntityBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */