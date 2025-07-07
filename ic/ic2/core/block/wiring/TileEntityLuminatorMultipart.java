/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyAcceptor;
/*     */ import ic2.api.energy.tile.IEnergyConductorColored;
/*     */ import ic2.api.energy.tile.IEnergyEmitter;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.network.INetworkFieldData;
/*     */ import ic2.api.tile.ISpecialWrenchable;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityLuminatorMultipart
/*     */   extends TileEntityBlock
/*     */   implements IEnergySink, ISpecialWrenchable, IEnergyConductorColored
/*     */ {
/*  39 */   public boolean[] lamps = new boolean[6];
/*  40 */   public byte[] connections = new byte[6];
/*  41 */   public byte[] emit = new byte[6];
/*     */   
/*  43 */   public int[] energy = new int[6];
/*  44 */   public int[] colors = new int[] { -1, -1, -1, -1, -1, -1 };
/*     */ 
/*     */   
/*  47 */   public int maxEnergy = 10000;
/*  48 */   public int current = 0;
/*  49 */   public int ticker = 0;
/*     */   
/*     */   public ItemStack cable;
/*  52 */   public IEnergyConductorColored.WireColor cableColor = IEnergyConductorColored.WireColor.Blank;
/*     */   
/*     */   public boolean addedToEnergyNet;
/*  55 */   public CableData data = new CableData();
/*     */ 
/*     */   
/*     */   public TileEntityLuminatorMultipart() {
/*  59 */     addNetworkFields(new String[] { "connections", "lamps", "cable", "cableColor", "colors", "data", "emit" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  65 */     if (this.ticker++ % 4 == 0) {
/*     */       
/*  67 */       for (int i = 0; i < 6; i++) {
/*     */         
/*  69 */         if (this.lamps[i])
/*     */         {
/*  71 */           if ((this.emit[i] == 1 && this.energy[i] <= 0) || (this.emit[i] == 0 && this.energy[i] <= 0)) {
/*     */             
/*  73 */             this.emit[i] = 0;
/*     */           } else {
/*     */             
/*  76 */             if (this.emit[i] == 0 && this.energy[i] > 0)
/*     */             {
/*  78 */               this.emit[i] = 1;
/*     */             }
/*  80 */             this.energy[i] = this.energy[i] - 1;
/*     */           }  } 
/*     */       } 
/*  83 */       boolean newActive = false;
/*  84 */       for (int j = 0; j < 6; j++) {
/*     */         
/*  86 */         if (this.emit[j] == 1) {
/*     */           
/*  88 */           newActive = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*  92 */       setActive(newActive);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockChange() {
/*  98 */     byte[] array = new byte[6];
/*  99 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*     */       
/* 101 */       TileEntity tile = EnergyNet.instance.getNeighbor((TileEntity)this, dir);
/* 102 */       if (tile != null)
/*     */       {
/* 104 */         array[dir.ordinal()] = (byte)(canInteractWith(tile, dir) ? 1 : 0);
/*     */       }
/*     */     } 
/* 107 */     if (!Util.match(this.connections, array)) {
/*     */       
/* 109 */       this.connections = array;
/* 110 */       getNetwork().updateTileEntityField((TileEntity)this, "connections");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnect(int side) {
/* 116 */     return (this.connections[side] == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSide(int side) {
/* 121 */     return this.lamps[side];
/*     */   }
/*     */ 
/*     */   
/*     */   public void addLamp(int side) {
/* 126 */     removeFromEnet();
/* 127 */     this.lamps[side] = true;
/* 128 */     addToEnet();
/* 129 */     getNetwork().updateTileEntityField((TileEntity)this, "lamps");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addColor(int side, int color) {
/* 134 */     if (hasSide(side)) {
/*     */       
/* 136 */       this.colors[side] = color;
/* 137 */       getNetwork().updateTileEntityField((TileEntity)this, "colors");
/* 138 */       return true;
/*     */     } 
/* 140 */     if (canBeColored()) {
/*     */       
/* 142 */       removeFromEnet();
/* 143 */       this.cableColor = IEnergyConductorColored.WireColor.values()[color + 1];
/* 144 */       addToEnet();
/* 145 */       getNetwork().updateTileEntityField((TileEntity)this, "cableColor");
/* 146 */       return true;
/*     */     } 
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeColored() {
/* 153 */     int meta = (this.cable != null) ? this.cable.func_77960_j() : 0;
/* 154 */     return (meta == 0 || meta == 3 || meta == 4 || meta == 6 || meta == 7 || meta == 8 || meta == 14 || meta == 15);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 160 */     super.onNetworkUpdate(field);
/* 161 */     if (field.equals("cable") || field.equals("lamps") || field.equals("colors") || field.equals("cableColor"))
/*     */     {
/* 163 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/* 165 */     if (field.equals("active"))
/*     */     {
/* 167 */       this.field_145850_b.func_147463_c(EnumSkyBlock.Block, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCable(ItemStack item, IEnergyConductorColored.WireColor color, int side) {
/* 173 */     this.cable = item;
/* 174 */     this.lamps[side] = true;
/* 175 */     this.cableColor = color;
/* 176 */     getNetwork().updateTileEntityField((TileEntity)this, "lamps");
/* 177 */     getNetwork().updateTileEntityField((TileEntity)this, "cable");
/* 178 */     getNetwork().updateTileEntityField((TileEntity)this, "cableColor");
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTarget() {
/* 183 */     boolean found = false;
/* 184 */     while (!found) {
/*     */       
/* 186 */       this.current++;
/* 187 */       if (this.current >= this.energy.length)
/*     */       {
/* 189 */         this.current = 0;
/*     */       }
/* 191 */       found = this.lamps[this.current];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateCableData() {
/* 197 */     if (this.cable != null) {
/*     */       
/* 199 */       TileEntityCable tileEntityCable = new TileEntityCable((short)this.cable.func_77960_j());
/* 200 */       this.data.loss = tileEntityCable.getConductionLoss();
/* 201 */       this.data.insolation = tileEntityCable.getInsulationEnergyAbsorption();
/* 202 */       this.data.isoBreaking = tileEntityCable.getInsulationBreakdownEnergy();
/* 203 */       this.data.conductorBreaking = tileEntityCable.getConductorBreakdownEnergy();
/*     */     }
/*     */     else {
/*     */       
/* 207 */       this.data.loss = 0.0D;
/* 208 */       this.data.insolation = 0.0D;
/* 209 */       this.data.isoBreaking = 0.0D;
/* 210 */       this.data.conductorBreaking = 0.0D;
/*     */     } 
/* 212 */     getNetwork().updateTileEntityField((TileEntity)this, "data");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 218 */     super.onLoaded();
/* 219 */     validateCableData();
/* 220 */     addToEnet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 226 */     removeFromEnet();
/* 227 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 233 */     if (this.lamps[direction.ordinal()])
/*     */     {
/* 235 */       return false;
/*     */     }
/* 237 */     return canConnect(direction.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addToEnet() {
/* 242 */     if (isSimulating() && !this.addedToEnergyNet) {
/*     */       
/* 244 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 245 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFromEnet() {
/* 251 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/* 253 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 254 */       this.addedToEnergyNet = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/* 261 */     if (this.lamps[direction.ordinal()])
/*     */     {
/* 263 */       return false;
/*     */     }
/* 265 */     return canConnect(direction.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(TileEntity te, ForgeDirection dir) {
/* 270 */     if (te instanceof TileEntityCable)
/*     */     {
/* 272 */       return canInteractWithCable((TileEntityCable)te);
/*     */     }
/* 274 */     if (te instanceof TileEntityLuminatorMultipart)
/*     */     {
/* 276 */       return (!((TileEntityLuminatorMultipart)te).hasSide(dir.getOpposite().ordinal()) && canInteractWithAPICable((IEnergyConductorColored)te));
/*     */     }
/* 278 */     if (te instanceof IEnergyConductorColored)
/*     */     {
/* 280 */       return canInteractWithAPICable((IEnergyConductorColored)te);
/*     */     }
/* 282 */     if (te instanceof TileEntityLuminator)
/*     */     {
/* 284 */       return ((TileEntityLuminator)te).canCableConnectFrom(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/* 286 */     if (te instanceof IEnergyAcceptor && !(te instanceof IEnergyEmitter))
/*     */     {
/* 288 */       return ((IEnergyAcceptor)te).acceptsEnergyFrom((TileEntity)this, dir.getOpposite());
/*     */     }
/* 290 */     if (te instanceof IEnergyEmitter && !(te instanceof IEnergyAcceptor))
/*     */     {
/* 292 */       return ((IEnergyEmitter)te).emitsEnergyTo((TileEntity)this, dir.getOpposite());
/*     */     }
/* 294 */     if (te instanceof IEnergyAcceptor && te instanceof IEnergyEmitter)
/*     */     {
/* 296 */       return (((IEnergyEmitter)te).emitsEnergyTo((TileEntity)this, dir.getOpposite()) || ((IEnergyAcceptor)te).acceptsEnergyFrom((TileEntity)this, dir.getOpposite()));
/*     */     }
/* 298 */     return (te instanceof ic2.api.energy.tile.IMetaDelegate || te instanceof ic2.api.network.INetworkTileEntityEventListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canInteractWithAPICable(IEnergyConductorColored cable) {
/* 303 */     return (this.cableColor == IEnergyConductorColored.WireColor.Blank || cable.getConductorColor() == IEnergyConductorColored.WireColor.Blank || this.cableColor == cable.getConductorColor());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canInteractWithCable(TileEntityCable cable) {
/* 308 */     return (this.cableColor == IEnergyConductorColored.WireColor.Blank || cable.color == IEnergyConductorColored.WireColor.Blank || this.cableColor == cable.color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 316 */     return (this.maxEnergy - this.energy[this.current]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 322 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
/* 328 */     if (amount > 32.0D)
/*     */     {
/* 330 */       return 0.0D;
/*     */     }
/* 332 */     if (amount <= 0.0D)
/*     */     {
/* 334 */       return 0.0D;
/*     */     }
/* 336 */     int energy = this.energy[this.current];
/* 337 */     energy = (int)(energy + amount);
/* 338 */     int re = 0;
/* 339 */     if (energy > this.maxEnergy) {
/*     */       
/* 341 */       re = energy - this.maxEnergy;
/* 342 */       energy = this.maxEnergy;
/*     */     } 
/* 344 */     this.energy[this.current] = energy;
/* 345 */     updateTarget();
/* 346 */     return re;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getConductionLoss() {
/* 352 */     return this.data.loss;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInsulationEnergyAbsorption() {
/* 358 */     return this.data.insolation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInsulationBreakdownEnergy() {
/* 364 */     return this.data.isoBreaking;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getConductorBreakdownEnergy() {
/* 370 */     return this.data.conductorBreaking;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeInsulation() {
/* 376 */     TileEntityCable tile = new TileEntityCable();
/* 377 */     int newType = 0;
/* 378 */     int type = this.cable.func_77973_b().func_77647_b(this.cable.func_77960_j());
/* 379 */     switch (type) {
/*     */ 
/*     */       
/*     */       case 0:
/* 383 */         newType = 1;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 388 */         newType = 2;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 393 */         newType = 3;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 398 */         newType = 5;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 403 */         newType = 6;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 8:
/* 408 */         newType = 7;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 14:
/* 413 */         newType = 13;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 15:
/* 418 */         newType = 14;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 423 */         newType = type;
/*     */         break;
/*     */     } 
/*     */     
/* 427 */     removeFromEnet();
/* 428 */     this.cable.func_77964_b(newType);
/* 429 */     if (!canBeColored()) {
/*     */       
/* 431 */       this.cableColor = IEnergyConductorColored.WireColor.Blank;
/* 432 */       getNetwork().updateTileEntityField((TileEntity)this, "cableColor");
/*     */     } 
/* 434 */     addToEnet();
/* 435 */     getNetwork().updateTileEntityField((TileEntity)this, "cable");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeConductor() {
/* 441 */     this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryRemoveIsolation() {
/* 446 */     int meta = (this.cable != null) ? this.cable.func_77960_j() : 0;
/* 447 */     int target = 0;
/* 448 */     switch (meta) {
/*     */ 
/*     */       
/*     */       case 0:
/* 452 */         target = 1;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 457 */         target = 2;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 462 */         target = 3;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 467 */         target = 5;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 472 */         target = 6;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 8:
/* 477 */         target = 7;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 14:
/* 482 */         target = 13;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 15:
/* 487 */         target = 14;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 492 */         target = meta;
/*     */         break;
/*     */     } 
/*     */     
/* 496 */     if (meta != target) {
/*     */       
/* 498 */       if (isSimulating()) {
/*     */         
/* 500 */         removeFromEnet();
/* 501 */         this.cable.func_77964_b(target);
/* 502 */         if (!canBeColored()) {
/*     */           
/* 504 */           this.cableColor = IEnergyConductorColored.WireColor.Blank;
/* 505 */           getNetwork().updateTileEntityField((TileEntity)this, "cableColor");
/*     */         } 
/* 507 */         addToEnet();
/* 508 */         getNetwork().updateTileEntityField((TileEntity)this, "cable");
/*     */       } 
/* 510 */       return true;
/*     */     } 
/* 512 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryAddIsolation() {
/* 517 */     int meta = (this.cable != null) ? this.cable.func_77960_j() : 0;
/* 518 */     int target = 0;
/* 519 */     switch (meta) {
/*     */ 
/*     */       
/*     */       case 1:
/* 523 */         target = 0;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 528 */         target = 3;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 533 */         target = 4;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 538 */         target = 6;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 543 */         target = 7;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 548 */         target = 8;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 13:
/* 553 */         target = 14;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 14:
/* 558 */         target = 15;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 563 */         target = meta;
/*     */         break;
/*     */     } 
/*     */     
/* 567 */     if (meta != target) {
/*     */       
/* 569 */       if (isSimulating()) {
/*     */         
/* 571 */         removeFromEnet();
/* 572 */         this.cable.func_77964_b(target);
/* 573 */         addToEnet();
/* 574 */         getNetwork().updateTileEntityField((TileEntity)this, "cable");
/*     */       } 
/* 576 */       return true;
/*     */     } 
/* 578 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 585 */     super.func_145839_a(nbt);
/* 586 */     this.cable = ItemStack.func_77949_a(nbt.func_74775_l("Cable"));
/* 587 */     this.cableColor = IEnergyConductorColored.WireColor.values()[nbt.func_74762_e("CableColor")];
/* 588 */     this.current = nbt.func_74762_e("Current");
/* 589 */     this.connections = nbt.func_74770_j("Connect");
/* 590 */     this.emit = nbt.func_74770_j("Light");
/* 591 */     if (this.connections.length != 6)
/*     */     {
/* 593 */       this.connections = new byte[6];
/*     */     }
/* 595 */     if (this.emit.length != 6)
/*     */     {
/* 597 */       this.emit = new byte[6];
/*     */     }
/* 599 */     NBTTagList list = nbt.func_150295_c("Data", 10);
/* 600 */     this.energy = new int[6];
/* 601 */     this.colors = new int[6];
/* 602 */     this.lamps = new boolean[6];
/* 603 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/* 605 */       NBTTagCompound data = list.func_150305_b(i);
/* 606 */       byte slot = data.func_74771_c("Slot");
/* 607 */       this.lamps[slot] = true;
/* 608 */       this.energy[slot] = data.func_74762_e("EnergyStored");
/* 609 */       this.colors[slot] = data.func_74762_e("Color");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 616 */     super.func_145841_b(nbt);
/* 617 */     NBTTagCompound itemTag = new NBTTagCompound();
/* 618 */     if (this.cable != null) this.cable.func_77955_b(itemTag); 
/* 619 */     nbt.func_74782_a("Cable", (NBTBase)itemTag);
/* 620 */     nbt.func_74768_a("CableColor", this.cableColor.ordinal());
/* 621 */     nbt.func_74768_a("Current", this.current);
/* 622 */     nbt.func_74773_a("Connect", this.connections);
/* 623 */     nbt.func_74773_a("Light", this.emit);
/*     */     
/* 625 */     NBTTagList list = new NBTTagList(); byte i;
/* 626 */     for (i = 0; i < 6; i = (byte)(i + 1)) {
/*     */       
/* 628 */       if (this.lamps[i]) {
/*     */         
/* 630 */         NBTTagCompound data = new NBTTagCompound();
/* 631 */         data.func_74774_a("Slot", i);
/* 632 */         data.func_74768_a("EnergyStored", this.energy[i]);
/* 633 */         data.func_74768_a("Color", this.colors[i]);
/* 634 */         list.func_74742_a((NBTBase)data);
/*     */       } 
/*     */     } 
/* 637 */     nbt.func_74782_a("Data", (NBTBase)list);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/* 643 */     return isSimulating();
/*     */   }
/*     */ 
/*     */   
/*     */   public class CableData
/*     */     implements INetworkFieldData
/*     */   {
/*     */     double loss;
/*     */     double insolation;
/*     */     double isoBreaking;
/*     */     double conductorBreaking;
/*     */     
/*     */     public void read(DataInput stream) {
/*     */       try {
/* 657 */         this.loss = stream.readDouble();
/* 658 */         this.insolation = stream.readDouble();
/* 659 */         this.isoBreaking = stream.readDouble();
/* 660 */         this.conductorBreaking = stream.readDouble();
/*     */       }
/* 662 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(DataOutput stream) {
/*     */       try {
/* 671 */         stream.writeDouble(this.loss);
/* 672 */         stream.writeDouble(this.insolation);
/* 673 */         stream.writeDouble(this.isoBreaking);
/* 674 */         stream.writeDouble(this.conductorBreaking);
/*     */       }
/* 676 */       catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
/* 685 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDoSpecial(EntityPlayer player, int side) {
/* 691 */     return this.lamps[side];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doSpecial(EntityPlayer player, int side) {
/* 699 */     if (this.lamps[side]) {
/*     */       
/* 701 */       this.lamps[side] = false;
/* 702 */       this.colors[side] = -1;
/* 703 */       if (!player.field_71075_bZ.field_75098_d)
/*     */       {
/* 705 */         this.field_145850_b.func_72838_d((Entity)new EntityItem(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, Ic2Items.luminatorMultipart.func_77946_l()));
/*     */       }
/*     */     } 
/*     */     
/* 709 */     boolean found = false;
/* 710 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 712 */       if (this.lamps[i]) {
/*     */         
/* 714 */         found = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 718 */     if (!found) {
/*     */       
/* 720 */       if (this.field_145850_b.func_147465_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, Block.func_149634_a(Ic2Items.copperCableBlock.func_77973_b()), this.cable.func_77960_j(), 3))
/*     */       {
/* 722 */         TileEntity tile = this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 723 */         if (tile instanceof TileEntityCable)
/*     */         {
/* 725 */           ((TileEntityCable)tile).changeColor(this.cableColor);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 731 */       getNetwork().updateTileEntityField((TileEntity)this, "lamps");
/* 732 */       getNetwork().updateTileEntityField((TileEntity)this, "colors");
/*     */     } 
/* 734 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IEnergyConductorColored.WireColor getConductorColor() {
/* 740 */     return this.cableColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getDrop() {
/* 745 */     if (this.cable != null)
/*     */     {
/* 747 */       return this.cable.func_77946_l();
/*     */     }
/* 749 */     return new ItemStack(Ic2Items.copperCableItem.func_77973_b());
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityLuminatorMultipart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */