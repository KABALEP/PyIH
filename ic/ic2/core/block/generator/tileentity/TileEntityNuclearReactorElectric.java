/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.energy.tile.IMetaDelegate;
/*     */ import ic2.core.IC2;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityNuclearReactorElectric
/*     */   extends TileEntityNuclearReactor
/*     */   implements IEnergySource, IMetaDelegate
/*     */ {
/*     */   public boolean addedToEnergyNet = false;
/*     */   public List<TileEntity> subTiles;
/*     */   
/*     */   public void onLoaded() {
/*  32 */     super.onLoaded();
/*  33 */     if (isSimulating()) {
/*     */       
/*  35 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*  36 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  43 */     if (isSimulating() && this.addedToEnergyNet) {
/*     */       
/*  45 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*  46 */       this.addedToEnergyNet = false;
/*     */     } 
/*  48 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshChambers() {
/*  54 */     super.refreshChambers();
/*  55 */     if (this.addedToEnergyNet)
/*     */     {
/*  57 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */     }
/*  59 */     this.subTiles = null;
/*  60 */     if (this.addedToEnergyNet)
/*     */     {
/*  62 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TileEntity> getSubTiles() {
/*  69 */     if (this.subTiles == null) {
/*     */       
/*  71 */       this.subTiles = new ArrayList<>();
/*     */     }
/*     */     else {
/*     */       
/*  75 */       return this.subTiles;
/*     */     } 
/*  77 */     this.subTiles.add(this);
/*  78 */     for (Direction dir : Direction.directions) {
/*     */       
/*  80 */       TileEntity te = dir.applyToTileEntity((TileEntity)this);
/*  81 */       if (te instanceof TileEntityReactorChamberElectric)
/*     */       {
/*  83 */         this.subTiles.add(te);
/*     */       }
/*     */     } 
/*  86 */     return this.subTiles;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/*  98 */     return (this.output * IC2.energyGeneratorNuclear);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/* 109 */     return EnergyNet.instance.getTierFromPower(this.output);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getReactorEUEnergyOutput() {
/* 115 */     return (this.output * IC2.energyGeneratorNuclear);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityNuclearReactorElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */