/*     */ package ic2.aeIntigration.core;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyConductor;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.energy.tile.IMetaDelegate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnergyNetwork
/*     */   extends TileEntity
/*     */   implements IEnergyConductor, IMetaDelegate
/*     */ {
/*     */   long frequency;
/*  23 */   ClassicEUP2PTunnel.P2PEntry passiveInput = null;
/*  24 */   ClassicEUP2PTunnel.P2PEntry input = null;
/*  25 */   List<ClassicEUP2PTunnel.P2PEntry> passiveOutputs = new ArrayList<>();
/*  26 */   List<ClassicEUP2PTunnel.P2PEntry> outputs = new ArrayList<>();
/*  27 */   List<TileEntity> tiles = null;
/*  28 */   ClassicEUP2PTunnel.CableType type = ClassicEUP2PTunnel.CableType.None;
/*     */   
/*     */   boolean addedToNetwork = false;
/*     */   boolean changed = false;
/*     */   boolean burned = false;
/*     */   boolean removeing = false;
/*     */   
/*     */   public EnergyNetwork(long freq) {
/*  36 */     this.frequency = freq;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addTunnel(ClassicEUP2PTunnel par1) {
/*  41 */     if (par1.isOutput()) {
/*     */       
/*  43 */       this.passiveOutputs.add(new ClassicEUP2PTunnel.P2PEntry(par1));
/*     */     }
/*     */     else {
/*     */       
/*  47 */       this.passiveInput = new ClassicEUP2PTunnel.P2PEntry(par1);
/*  48 */       TileEntity tile = par1.getTile();
/*  49 */       this.field_145850_b = tile.func_145831_w();
/*  50 */       this.field_145851_c = tile.field_145851_c;
/*  51 */       this.field_145848_d = tile.field_145848_d;
/*  52 */       this.field_145849_e = tile.field_145849_e;
/*  53 */       this.type = par1.type;
/*  54 */       this.burned = par1.burned;
/*  55 */       ENetHelper.updateNetwork(this);
/*  56 */       par1.onAddedToNetwork();
/*     */     } 
/*  58 */     this.changed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeTunnel(ClassicEUP2PTunnel par1) {
/*  63 */     if (par1.isOutput()) {
/*     */       
/*  65 */       for (int i = 0; i < this.passiveOutputs.size(); i++) {
/*     */         
/*  67 */         ClassicEUP2PTunnel.P2PEntry entry = this.passiveOutputs.get(i);
/*  68 */         if (tunnelsMatch(entry.tunnel, par1)) {
/*     */           
/*  70 */           this.passiveOutputs.remove(i--);
/*  71 */           this.changed = true;
/*  72 */           ENetHelper.updateNetwork(this);
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*  79 */     } else if (this.passiveInput != null && tunnelsMatch(this.passiveInput.tunnel, par1)) {
/*     */       
/*  81 */       this.passiveInput = null;
/*  82 */       this.changed = true;
/*  83 */       ENetHelper.updateNetwork(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tunnelsMatch(ClassicEUP2PTunnel par1, ClassicEUP2PTunnel par2) {
/*  90 */     if (par1 == null || par2 == null || par1.getTile() == null || par2.getTile() == null)
/*     */     {
/*  92 */       return false;
/*     */     }
/*  94 */     TileEntity key = par1.getTile();
/*  95 */     TileEntity value = par2.getTile();
/*  96 */     if ((key.func_145831_w()).field_73011_w.field_76574_g != (value.func_145831_w()).field_73011_w.field_76574_g)
/*     */     {
/*  98 */       return false;
/*     */     }
/* 100 */     if (key.field_145851_c != value.field_145851_c || key.field_145848_d != value.field_145848_d || key.field_145849_e != value.field_145849_e)
/*     */     {
/* 102 */       return false;
/*     */     }
/* 104 */     return (par1.getFacing().ordinal() == par2.getFacing().ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canAdd(ClassicEUP2PTunnel par1) {
/* 109 */     if (!hasValidID(par1) || (par1.isOutput() && this.burned))
/*     */     {
/* 111 */       return false;
/*     */     }
/* 113 */     if (par1.isOutput()) {
/*     */       
/* 115 */       ClassicEUP2PTunnel.P2PEntry entry = new ClassicEUP2PTunnel.P2PEntry(par1);
/* 116 */       if (this.passiveOutputs.contains(entry))
/*     */       {
/* 118 */         return false;
/*     */       }
/* 120 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 124 */     if (this.passiveInput == null)
/*     */     {
/* 126 */       return true;
/*     */     }
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNetwork() {
/* 134 */     if (this.input == null) {
/*     */       
/* 136 */       if (this.passiveInput == null) {
/*     */         return;
/*     */       }
/*     */       
/* 140 */       if (!hasValidID(this.passiveInput.tunnel)) {
/*     */         
/* 142 */         this.passiveInput = null;
/* 143 */         this.changed = true;
/*     */         return;
/*     */       } 
/* 146 */       if (isP2PValid(this.passiveInput.tunnel))
/*     */       {
/* 148 */         this.input = this.passiveInput;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 153 */       if (this.passiveInput == null) {
/*     */         
/* 155 */         this.input = null;
/* 156 */         this.changed = true;
/*     */         return;
/*     */       } 
/* 159 */       if (hasValidID(this.input.tunnel)) {
/*     */         
/* 161 */         if (!isP2PValid(this.input.tunnel))
/*     */         {
/* 163 */           this.input = null;
/* 164 */           this.changed = true;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 169 */         this.changed = true;
/* 170 */         this.input = null;
/* 171 */         if (hasValidID(this.passiveInput.tunnel) && isP2PValid(this.passiveInput.tunnel))
/*     */         {
/* 173 */           this.input = this.passiveInput; } 
/*     */       } 
/*     */     } 
/*     */     int i;
/* 177 */     for (i = 0; i < this.outputs.size(); i++) {
/*     */       
/* 179 */       ClassicEUP2PTunnel.P2PEntry entry = this.outputs.get(i);
/* 180 */       if (!hasValidID(entry.tunnel) || !isP2PValid(entry.tunnel) || !this.passiveOutputs.contains(entry)) {
/*     */ 
/*     */ 
/*     */         
/* 184 */         this.outputs.remove(i--);
/* 185 */         this.changed = true;
/*     */       } 
/* 187 */     }  for (i = 0; i < this.passiveOutputs.size(); i++) {
/*     */       
/* 189 */       ClassicEUP2PTunnel.P2PEntry entry = this.passiveOutputs.get(i);
/* 190 */       if (!hasValidID(entry.tunnel)) {
/*     */         
/* 192 */         this.passiveOutputs.remove(i--);
/* 193 */         this.changed = true;
/*     */       
/*     */       }
/* 196 */       else if (isP2PValid(entry.tunnel)) {
/*     */ 
/*     */ 
/*     */         
/* 200 */         if (!this.outputs.contains(entry)) {
/*     */ 
/*     */ 
/*     */           
/* 204 */           this.outputs.add(entry);
/* 205 */           this.changed = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public void validateNetwork() {
/* 211 */     if (this.passiveInput == null) {
/*     */       
/* 213 */       this.passiveOutputs.clear();
/* 214 */       this.outputs.clear();
/* 215 */       if (this.input != null)
/*     */       {
/* 217 */         this.input = null;
/*     */       }
/* 219 */       this.removeing = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEnergyNet() {
/* 225 */     if (!this.changed && !this.removeing) {
/*     */       return;
/*     */     }
/*     */     
/* 229 */     this.changed = false;
/*     */     
/* 231 */     if (this.addedToNetwork) {
/*     */       
/* 233 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 234 */       this.addedToNetwork = false;
/*     */     } 
/* 236 */     this.tiles = null;
/* 237 */     if (!this.removeing && this.burned) {
/*     */       return;
/*     */     }
/*     */     
/* 241 */     if (this.removeing) {
/*     */       
/* 243 */       ENetHelper.removeNetwork(this.frequency);
/*     */       return;
/*     */     } 
/* 246 */     MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 247 */     this.addedToNetwork = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(boolean input, TileEntity tile, ForgeDirection dir) {
/* 252 */     if (input) {
/*     */       
/* 254 */       if (this.input == null)
/*     */       {
/* 256 */         return false;
/*     */       }
/* 258 */       TileEntity tileEntity = this.input.tile;
/* 259 */       ForgeDirection face = this.input.dir;
/*     */       
/* 261 */       if (tileEntity == tile && dir == face)
/*     */       {
/* 263 */         return true;
/*     */       }
/* 265 */       return false;
/*     */     } 
/* 267 */     for (ClassicEUP2PTunnel.P2PEntry entry : this.outputs) {
/*     */       
/* 269 */       TileEntity tileEntity = entry.tile;
/* 270 */       ForgeDirection face = entry.dir;
/* 271 */       if (tileEntity == tile && dir == face)
/*     */       {
/* 273 */         return true;
/*     */       }
/*     */     } 
/* 276 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isP2PValid(ClassicEUP2PTunnel par1) {
/* 281 */     if (par1.isPowered() && par1.isActive() && !par1.getTile().func_145837_r() && !par1.burned && par1.type != ClassicEUP2PTunnel.CableType.None)
/*     */     {
/* 283 */       return true;
/*     */     }
/* 285 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasValidID(ClassicEUP2PTunnel par1) {
/* 290 */     return (par1.getFrequency() == this.frequency && (this.type == ClassicEUP2PTunnel.CableType.None || this.type == par1.type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 297 */     TileEntity tile = Direction.fromForgeDirection(direction.getOpposite()).applyToTileEntity(emitter);
/* 298 */     if (tile != null && contains(true, tile, direction) && !this.burned)
/*     */     {
/* 300 */       return true;
/*     */     }
/* 302 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/* 308 */     TileEntity tile = Direction.fromForgeDirection(direction.getOpposite()).applyToTileEntity(receiver);
/* 309 */     if (tile != null && contains(false, tile, direction) && !this.burned)
/*     */     {
/* 311 */       return true;
/*     */     }
/* 313 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TileEntity> getSubTiles() {
/* 319 */     if (this.tiles != null)
/*     */     {
/* 321 */       return this.tiles;
/*     */     }
/* 323 */     this.tiles = new ArrayList<>();
/* 324 */     if (this.input != null)
/*     */     {
/* 326 */       this.tiles.add(this.input.tile);
/*     */     }
/* 328 */     for (ClassicEUP2PTunnel.P2PEntry entry : this.outputs)
/*     */     {
/* 330 */       this.tiles.add(entry.tile);
/*     */     }
/* 332 */     return this.tiles;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getConductionLoss() {
/* 338 */     return this.type.getLoss();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInsulationEnergyAbsorption() {
/* 344 */     return 2.147483647E9D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInsulationBreakdownEnergy() {
/* 350 */     return 2.147483647E9D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getConductorBreakdownEnergy() {
/* 356 */     return this.type.getMaxEnergy();
/*     */   }
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
/* 368 */     this.burned = true;
/* 369 */     this.type = ClassicEUP2PTunnel.CableType.None;
/* 370 */     if (this.passiveInput != null)
/*     */     {
/* 372 */       this.passiveInput.tunnel.setBurned();
/*     */     }
/* 374 */     for (int i = 0; i < this.passiveOutputs.size(); i++)
/*     */     {
/* 376 */       ((ClassicEUP2PTunnel.P2PEntry)this.passiveOutputs.get(i)).tunnel.setBurned();
/*     */     }
/* 378 */     ENetHelper.updateNetwork(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\aeIntigration\core\EnergyNetwork.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */