/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyAcceptor;
/*     */ import ic2.api.energy.tile.IEnergyConductorColored;
/*     */ import ic2.api.energy.tile.IEnergyEmitter;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.network.INetworkTileEntityEventListener;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.ITickCallback;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.util.Util;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityCable
/*     */   extends TileEntityBlock
/*     */   implements IEnergyConductorColored, INetworkTileEntityEventListener
/*     */ {
/*  34 */   public short cableType = 0;
/*  35 */   public IEnergyConductorColored.WireColor color = IEnergyConductorColored.WireColor.Blank;
/*  36 */   public byte foamed = 0;
/*  37 */   public IEnergyConductorColored.WireColor foamColor = IEnergyConductorColored.WireColor.Blank;
/*     */   public boolean addedToEnergyNet = false;
/*  39 */   public byte[] connections = new byte[6];
/*     */   
/*  41 */   public Block[] textureBlock = new Block[6];
/*  42 */   public int[] textureMeta = new int[6];
/*  43 */   public int[] textureSide = new int[6];
/*     */   
/*     */   public boolean textureMimic = false;
/*     */   
/*     */   public TileEntityCable(short type) {
/*  48 */     this();
/*  49 */     this.cableType = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityCable() {
/*  54 */     addNetworkFields(new String[] { "cableType", "color", "foamed", "foamColor", "connections", "textureBlock", "textureMeta", "textureSide", "textureMimic" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  60 */     super.func_145839_a(nbttagcompound);
/*  61 */     this.textureMimic = nbttagcompound.func_74767_n("TextureCopy");
/*  62 */     this.cableType = nbttagcompound.func_74765_d("cableType");
/*  63 */     this.color = IEnergyConductorColored.WireColor.values()[nbttagcompound.func_74762_e("color")];
/*  64 */     this.foamColor = IEnergyConductorColored.WireColor.values()[nbttagcompound.func_74762_e("foamColor")];
/*  65 */     byte wasFoamed = nbttagcompound.func_74771_c("foamed");
/*  66 */     if (wasFoamed == 1) {
/*     */       
/*  68 */       changeFoam(wasFoamed, true);
/*     */     }
/*     */     else {
/*     */       
/*  72 */       this.foamed = wasFoamed;
/*     */     } 
/*  74 */     byte[] connect = nbttagcompound.func_74770_j("Connections");
/*  75 */     if (connect.length != 6)
/*     */     {
/*  77 */       connect = new byte[6];
/*     */     }
/*  79 */     this.connections = connect;
/*  80 */     this.textureBlock = new Block[6];
/*  81 */     this.textureMeta = new int[6];
/*  82 */     this.textureSide = new int[6];
/*  83 */     NBTTagList list = nbttagcompound.func_150295_c("BlockDeco", 10);
/*  84 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/*  86 */       NBTTagCompound nbt = list.func_150305_b(i);
/*  87 */       int slot = nbt.func_74762_e("Slot");
/*  88 */       this.textureBlock[slot] = Block.func_149684_b(nbt.func_74779_i("BlockID"));
/*  89 */       this.textureMeta[slot] = nbt.func_74762_e("BlockMeta");
/*  90 */       this.textureSide[slot] = nbt.func_74762_e("BlockSide");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  97 */     super.func_145841_b(nbttagcompound);
/*  98 */     nbttagcompound.func_74757_a("TextureCopy", this.textureMimic);
/*  99 */     nbttagcompound.func_74777_a("cableType", this.cableType);
/* 100 */     nbttagcompound.func_74777_a("color", (short)this.color.ordinal());
/* 101 */     nbttagcompound.func_74774_a("foamed", this.foamed);
/* 102 */     nbttagcompound.func_74774_a("foamColor", (byte)this.foamColor.ordinal());
/* 103 */     nbttagcompound.func_74773_a("Connections", this.connections);
/* 104 */     NBTTagList list = new NBTTagList();
/* 105 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 107 */       if (this.textureBlock[i] != null && this.textureBlock[i] != Blocks.field_150350_a) {
/*     */         
/* 109 */         NBTTagCompound nbt = new NBTTagCompound();
/* 110 */         nbt.func_74768_a("Slot", i);
/* 111 */         nbt.func_74778_a("BlockID", Block.field_149771_c.func_148750_c(this.textureBlock[i]));
/* 112 */         nbt.func_74768_a("BlockMeta", this.textureMeta[i]);
/* 113 */         nbt.func_74768_a("BlockSide", this.textureSide[i]);
/* 114 */         list.func_74742_a((NBTBase)nbt);
/*     */       } 
/*     */     } 
/* 117 */     nbttagcompound.func_74782_a("BlockDeco", (NBTBase)list);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 123 */     super.onLoaded();
/* 124 */     if (isSimulating() && !this.addedToEnergyNet) {
/*     */       
/* 126 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 127 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 134 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/* 136 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 137 */       this.addedToEnergyNet = false;
/*     */     } 
/* 139 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean changeColor(IEnergyConductorColored.WireColor newColor) {
/* 144 */     if ((this.foamed == 0 && (this.color == newColor || this.cableType == 1 || this.cableType == 2 || this.cableType == 5 || this.cableType == 10 || this.cableType == 11 || this.cableType == 12 || this.cableType == 13)) || (this.foamed > 0 && this.foamColor == newColor))
/*     */     {
/* 146 */       return false;
/*     */     }
/* 148 */     if (isSimulating())
/*     */     {
/* 150 */       if (this.foamed == 0) {
/*     */         
/* 152 */         if (this.addedToEnergyNet)
/*     */         {
/* 154 */           MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */         }
/* 156 */         this.addedToEnergyNet = false;
/* 157 */         this.color = newColor;
/* 158 */         MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 159 */         this.addedToEnergyNet = true;
/* 160 */         getNetwork().updateTileEntityField((TileEntity)this, "color");
/*     */       }
/*     */       else {
/*     */         
/* 164 */         this.foamColor = newColor;
/* 165 */         getNetwork().updateTileEntityField((TileEntity)this, "foamColor");
/*     */       } 
/*     */     }
/* 168 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean applyTexture(int slot, Block block, int meta, int side) {
/* 173 */     if (this.foamed <= 1)
/*     */     {
/* 175 */       return false;
/*     */     }
/* 177 */     if (this.textureBlock[slot] == block && this.textureMeta[slot] == meta && this.textureSide[slot] == side)
/*     */     {
/* 179 */       return false;
/*     */     }
/* 181 */     if (!Block.func_149680_a(this.textureBlock[slot], block)) {
/*     */       
/* 183 */       this.textureBlock[slot] = block;
/* 184 */       getNetwork().updateTileEntityField((TileEntity)this, "textureBlock");
/*     */     } 
/* 186 */     if (this.textureMeta[slot] != meta) {
/*     */       
/* 188 */       this.textureMeta[slot] = meta;
/* 189 */       getNetwork().updateTileEntityField((TileEntity)this, "textureMeta");
/*     */     } 
/* 191 */     if (this.textureSide[slot] != side) {
/*     */       
/* 193 */       this.textureSide[slot] = side;
/* 194 */       getNetwork().updateTileEntityField((TileEntity)this, "textureSide");
/*     */     } 
/* 196 */     if (!this.textureMimic) {
/*     */       
/* 198 */       this.textureMimic = true;
/* 199 */       getNetwork().updateTileEntityField((TileEntity)this, "textureMimic");
/*     */     } 
/* 201 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean changeFoam(byte foamed) {
/* 206 */     return changeFoam(foamed, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryAddInsulation() {
/* 211 */     short target = 0;
/* 212 */     switch (this.cableType) {
/*     */ 
/*     */       
/*     */       case 1:
/* 216 */         target = 0;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 221 */         target = 3;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 226 */         target = 4;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 231 */         target = 6;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 236 */         target = 7;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 241 */         target = 8;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 13:
/* 246 */         target = 14;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 14:
/* 251 */         target = 15;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 256 */         target = this.cableType;
/*     */         break;
/*     */     } 
/*     */     
/* 260 */     if (target != this.cableType) {
/*     */       
/* 262 */       if (isSimulating())
/*     */       {
/* 264 */         changeType(target);
/*     */       }
/* 266 */       return true;
/*     */     } 
/* 268 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryRemoveInsulation() {
/* 273 */     short target = 0;
/* 274 */     switch (this.cableType) {
/*     */ 
/*     */       
/*     */       case 0:
/* 278 */         target = 1;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 283 */         target = 2;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 288 */         target = 3;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 293 */         target = 5;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 298 */         target = 6;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 8:
/* 303 */         target = 7;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 14:
/* 308 */         target = 13;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 15:
/* 313 */         target = 14;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 318 */         target = this.cableType;
/*     */         break;
/*     */     } 
/*     */     
/* 322 */     if (target != this.cableType) {
/*     */       
/* 324 */       if (isSimulating())
/*     */       {
/* 326 */         changeType(target);
/*     */       }
/* 328 */       return true;
/*     */     } 
/* 330 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void changeType(short cableType) {
/* 335 */     this.field_145850_b.func_72921_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, cableType, 3);
/* 336 */     if (this.addedToEnergyNet)
/*     */     {
/* 338 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */     }
/* 340 */     this.addedToEnergyNet = false;
/* 341 */     this.cableType = cableType;
/* 342 */     MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 343 */     this.addedToEnergyNet = true;
/* 344 */     getNetwork().updateTileEntityField((TileEntity)this, "cableType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
/* 350 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
/* 356 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 361 */     return canConnect(direction.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/* 366 */     return canConnect(direction.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(TileEntity te, ForgeDirection dir) {
/* 371 */     if (te instanceof TileEntityCable)
/*     */     {
/* 373 */       return canInteractWithCable((TileEntityCable)te);
/*     */     }
/* 375 */     if (te instanceof IEnergyConductorColored)
/*     */     {
/* 377 */       return canInteractWithAPICable((IEnergyConductorColored)te);
/*     */     }
/* 379 */     if (te instanceof TileEntityLuminator)
/*     */     {
/* 381 */       return ((TileEntityLuminator)te).canCableConnectFrom(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/* 383 */     if (te instanceof IEnergyAcceptor && !(te instanceof IEnergyEmitter))
/*     */     {
/* 385 */       return ((IEnergyAcceptor)te).acceptsEnergyFrom((TileEntity)this, dir.getOpposite());
/*     */     }
/* 387 */     if (te instanceof IEnergyEmitter && !(te instanceof IEnergyAcceptor))
/*     */     {
/* 389 */       return ((IEnergyEmitter)te).emitsEnergyTo((TileEntity)this, dir.getOpposite());
/*     */     }
/* 391 */     if (te instanceof IEnergyAcceptor && te instanceof IEnergyEmitter)
/*     */     {
/* 393 */       return (((IEnergyEmitter)te).emitsEnergyTo((TileEntity)this, dir.getOpposite()) || ((IEnergyAcceptor)te).acceptsEnergyFrom((TileEntity)this, dir.getOpposite()));
/*     */     }
/* 395 */     return (te instanceof ic2.api.energy.tile.IMetaDelegate || te instanceof INetworkTileEntityEventListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canInteractWithAPICable(IEnergyConductorColored cable) {
/* 400 */     return (this.color == IEnergyConductorColored.WireColor.Blank || cable.getConductorColor() == IEnergyConductorColored.WireColor.Blank || this.color == cable.getConductorColor());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canInteractWithCable(TileEntityCable cable) {
/* 405 */     return (this.color == IEnergyConductorColored.WireColor.Blank || cable.color == IEnergyConductorColored.WireColor.Blank || this.color == cable.color);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCableThickness() {
/* 410 */     if (this.foamed == 2)
/*     */     {
/* 412 */       return 1.0F;
/*     */     }
/* 414 */     return getCableThickness(this.cableType);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getCableThickness(int cableType) {
/* 419 */     float p = 1.0F;
/* 420 */     switch (cableType) {
/*     */ 
/*     */       
/*     */       case 0:
/* 424 */         p = 6.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 429 */         p = 4.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 434 */         p = 3.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 439 */         p = 5.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 444 */         p = 6.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 449 */         p = 6.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 454 */         p = 8.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 459 */         p = 10.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 8:
/* 464 */         p = 12.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 9:
/* 469 */         p = 4.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 10:
/* 474 */         p = 5.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 11:
/* 479 */         p = 8.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 12:
/* 484 */         p = 8.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 13:
/* 489 */         p = 4.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 14:
/* 494 */         p = 6.0F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 15:
/* 499 */         p = 7.0F;
/*     */         break;
/*     */     } 
/*     */     
/* 503 */     return p / 16.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getConductionLoss() {
/* 509 */     switch (this.cableType) {
/*     */ 
/*     */       
/*     */       case 0:
/* 513 */         return 0.2D;
/*     */ 
/*     */       
/*     */       case 1:
/* 517 */         return 0.3D;
/*     */ 
/*     */       
/*     */       case 2:
/* 521 */         return 0.5D;
/*     */ 
/*     */       
/*     */       case 3:
/* 525 */         return 0.45D;
/*     */ 
/*     */       
/*     */       case 4:
/* 529 */         return 0.4D;
/*     */ 
/*     */       
/*     */       case 5:
/* 533 */         return 1.0D;
/*     */ 
/*     */       
/*     */       case 6:
/* 537 */         return 0.95D;
/*     */ 
/*     */       
/*     */       case 7:
/* 541 */         return 0.9D;
/*     */ 
/*     */       
/*     */       case 8:
/* 545 */         return 0.8D;
/*     */ 
/*     */       
/*     */       case 9:
/* 549 */         return 0.025D;
/*     */ 
/*     */       
/*     */       case 10:
/* 553 */         return 0.025D;
/*     */ 
/*     */       
/*     */       case 11:
/* 557 */         return 0.5D;
/*     */ 
/*     */       
/*     */       case 12:
/* 561 */         return 0.5D;
/*     */ 
/*     */       
/*     */       case 13:
/* 565 */         return 0.7D;
/*     */ 
/*     */       
/*     */       case 14:
/* 569 */         return 0.65D;
/*     */ 
/*     */       
/*     */       case 15:
/* 573 */         return 0.6D;
/*     */     } 
/*     */ 
/*     */     
/* 577 */     return 0.025D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInsulationEnergyAbsorption() {
/* 585 */     switch (this.cableType) {
/*     */ 
/*     */       
/*     */       case 0:
/* 589 */         return 32.0D;
/*     */ 
/*     */       
/*     */       case 1:
/* 593 */         return 8.0D;
/*     */ 
/*     */       
/*     */       case 2:
/* 597 */         return 8.0D;
/*     */ 
/*     */       
/*     */       case 3:
/* 601 */         return 32.0D;
/*     */ 
/*     */       
/*     */       case 4:
/* 605 */         return 128.0D;
/*     */ 
/*     */       
/*     */       case 5:
/* 609 */         return 0.0D;
/*     */ 
/*     */       
/*     */       case 6:
/* 613 */         return 128.0D;
/*     */ 
/*     */       
/*     */       case 7:
/* 617 */         return 512.0D;
/*     */ 
/*     */       
/*     */       case 8:
/* 621 */         return 9001.0D;
/*     */ 
/*     */       
/*     */       case 9:
/* 625 */         return 9001.0D;
/*     */ 
/*     */       
/*     */       case 10:
/* 629 */         return 3.0D;
/*     */ 
/*     */       
/*     */       case 11:
/* 633 */         return 9001.0D;
/*     */ 
/*     */       
/*     */       case 12:
/* 637 */         return 9001.0D;
/*     */ 
/*     */       
/*     */       case 13:
/* 641 */         return 8.0D;
/*     */ 
/*     */       
/*     */       case 14:
/* 645 */         return 32.0D;
/*     */ 
/*     */       
/*     */       case 15:
/* 649 */         return 128.0D;
/*     */     } 
/*     */ 
/*     */     
/* 653 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInsulationBreakdownEnergy() {
/* 661 */     return 9001.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getConductorBreakdownEnergy() {
/* 667 */     switch (this.cableType) {
/*     */ 
/*     */       
/*     */       case 0:
/* 671 */         return 33.0D;
/*     */ 
/*     */       
/*     */       case 1:
/* 675 */         return 33.0D;
/*     */ 
/*     */       
/*     */       case 2:
/* 679 */         return 129.0D;
/*     */ 
/*     */       
/*     */       case 3:
/* 683 */         return 129.0D;
/*     */ 
/*     */       
/*     */       case 4:
/* 687 */         return 129.0D;
/*     */ 
/*     */       
/*     */       case 5:
/* 691 */         return 2049.0D;
/*     */ 
/*     */       
/*     */       case 6:
/* 695 */         return 2049.0D;
/*     */ 
/*     */       
/*     */       case 7:
/* 699 */         return 2049.0D;
/*     */ 
/*     */       
/*     */       case 8:
/* 703 */         return 2049.0D;
/*     */ 
/*     */       
/*     */       case 9:
/* 707 */         return 513.0D;
/*     */ 
/*     */       
/*     */       case 10:
/* 711 */         return 6.0D;
/*     */ 
/*     */       
/*     */       case 11:
/* 715 */         return 2049.0D;
/*     */ 
/*     */       
/*     */       case 12:
/* 719 */         return 2049.0D;
/*     */ 
/*     */       
/*     */       case 13:
/* 723 */         return 129.0D;
/*     */ 
/*     */       
/*     */       case 14:
/* 727 */         return 129.0D;
/*     */ 
/*     */       
/*     */       case 15:
/* 731 */         return 129.0D;
/*     */     } 
/*     */ 
/*     */     
/* 735 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeInsulation() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeConductor() {
/* 748 */     this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 749 */     getNetwork().initiateTileEntityEvent((TileEntity)this, 0, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 755 */     if (field.equals("cableType") || field.equals("color") || field.equals("foamed") || field.equals("foamColor") || field.equals("connections") || field.equals("textureBlock") || field.equals("textureMeta") || field.equals("textureSide"))
/*     */     {
/* 757 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/* 759 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(int event) {
/*     */     int l;
/* 765 */     switch (event) {
/*     */ 
/*     */       
/*     */       case 0:
/* 769 */         this.field_145850_b.func_72908_a((this.field_145851_c + 0.5F), (this.field_145848_d + 0.5F), (this.field_145849_e + 0.5F), "random.fizz", 0.5F, 2.6F + (this.field_145850_b.field_73012_v.nextFloat() - this.field_145850_b.field_73012_v.nextFloat()) * 0.8F);
/* 770 */         for (l = 0; l < 8; l++)
/*     */         {
/* 772 */           this.field_145850_b.func_72869_a("largesmoke", this.field_145851_c + Math.random(), this.field_145848_d + 1.2D, this.field_145849_e + Math.random(), 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 778 */     IC2.platform.displayError("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID " + event + ", tile entity below)\nT: " + this + " (" + this.field_145851_c + "," + this.field_145848_d + "," + this.field_145849_e + ")");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 787 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean changeFoam(byte foamed, boolean duringLoad) {
/* 792 */     if (this.foamed == foamed)
/*     */     {
/* 794 */       return false;
/*     */     }
/* 796 */     if (isSimulating()) {
/*     */       
/* 798 */       if ((this.foamed = foamed) == 1) {
/*     */         
/* 800 */         this.foamColor = IEnergyConductorColored.WireColor.Silver;
/* 801 */         if (!duringLoad)
/*     */         {
/* 803 */           getNetwork().updateTileEntityField((TileEntity)this, "foamColor");
/*     */         }
/* 805 */         IC2.addContinuousTickCallback(this.field_145850_b, new ITickCallback()
/*     */             {
/*     */               
/*     */               public void tickCallback(World world)
/*     */               {
/* 810 */                 if (TileEntityCable.this.func_145837_r() || TileEntityCable.this.foamed != 1 || (TileEntityCable.this.field_145850_b.field_73012_v.nextInt(500) == 0 && TileEntityCable.this.field_145850_b.func_72957_l(TileEntityCable.this.field_145851_c, TileEntityCable.this.field_145848_d, TileEntityCable.this.field_145849_e) * 6 >= TileEntityCable.this.field_145850_b.field_73012_v.nextInt(1000))) {
/*     */                   
/* 812 */                   TileEntityCable.this.changeFoam((byte)2);
/* 813 */                   IC2.removeContinuousTickCallback(world, this);
/*     */                 } 
/*     */               }
/*     */             });
/*     */       } 
/* 818 */       if (!duringLoad)
/*     */       {
/* 820 */         getNetwork().updateTileEntityField((TileEntity)this, "foamed");
/*     */       }
/*     */     } 
/* 823 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockChange() {
/* 828 */     byte[] array = new byte[6];
/* 829 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*     */       
/* 831 */       TileEntity tile = EnergyNet.instance.getNeighbor((TileEntity)this, dir);
/* 832 */       if (tile != null)
/*     */       {
/* 834 */         array[dir.ordinal()] = (byte)(canInteractWith(tile, dir) ? 1 : 0);
/*     */       }
/*     */     } 
/* 837 */     if (!Util.match(this.connections, array)) {
/*     */       
/* 839 */       this.connections = array;
/* 840 */       getNetwork().updateTileEntityField((TileEntity)this, "connections");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnect(int side) {
/* 846 */     return (this.connections[side] == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IEnergyConductorColored.WireColor getConductorColor() {
/* 852 */     return this.color;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityCable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */