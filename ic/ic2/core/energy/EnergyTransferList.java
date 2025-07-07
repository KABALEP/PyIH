/*     */ package ic2.core.energy;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.energy.tile.IEnergySourceInfo;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.core.IC2;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnergyTransferList
/*     */ {
/*  19 */   public static Map<String, Integer> values = new HashMap<>();
/*  20 */   public static Map<String, Integer> acceptingOverride = new HashMap<>();
/*     */ 
/*     */   
/*     */   public EnergyTransferList() {
/*  24 */     MinecraftForge.EVENT_BUS.register(this);
/*  25 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getMaxEnergy(IEnergySource par1, int currentMax) {
/*  30 */     if (par1 instanceof IEnergySourceInfo) {
/*     */       
/*  32 */       IEnergySourceInfo info = (IEnergySourceInfo)par1;
/*  33 */       return info.getMaxEnergyAmount();
/*     */     } 
/*  35 */     if (!values.containsKey(par1.getClass().getSimpleName()))
/*     */     {
/*  37 */       values.put(par1.getClass().getSimpleName(), Integer.valueOf(currentMax));
/*     */     }
/*  39 */     if (values.containsKey(par1.getClass().getSimpleName())) {
/*     */       
/*  41 */       int newValue = ((Integer)values.get(par1.getClass().getSimpleName())).intValue();
/*  42 */       if (newValue < currentMax)
/*     */       {
/*  44 */         values.put(par1.getClass().getSimpleName(), Integer.valueOf(currentMax));
/*     */       }
/*  46 */       currentMax = ((Integer)values.get(par1.getClass().getSimpleName())).intValue();
/*     */     } 
/*  48 */     return currentMax;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initIEnergySource(IEnergySource par1) {
/*  53 */     if (!values.containsKey(par1.getClass().getSimpleName()))
/*     */     {
/*  55 */       values.put(par1.getClass().getSimpleName(), Integer.valueOf(10));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasOverrideInput(IEnergySink par1) {
/*  61 */     if (par1 == null)
/*     */     {
/*  63 */       return false;
/*     */     }
/*  65 */     Class<?> clz = par1.getClass();
/*  66 */     return acceptingOverride.containsKey(clz.getSimpleName());
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getOverrideInput(IEnergySink par1) {
/*  71 */     if (par1 == null || !hasOverrideInput(par1))
/*     */     {
/*  73 */       return 0;
/*     */     }
/*  75 */     Class<?> clz = par1.getClass();
/*  76 */     return ((Integer)acceptingOverride.get(clz.getSimpleName())).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  81 */     Map<String, Integer> list = new HashMap<>();
/*  82 */     list.put("TileEntityEnergyOMat", Integer.valueOf(32));
/*  83 */     list.put("TileEntityNuclearReactorElectric", Integer.valueOf(1512 * IC2.energyGeneratorNuclear));
/*  84 */     list.put("TileEntityReactorChamberElectric", Integer.valueOf(240 * IC2.energyGeneratorNuclear));
/*  85 */     list.put("TileEntityWindGenerator", Integer.valueOf(10));
/*  86 */     list.put("TileEntityGenerator", Integer.valueOf(IC2.energyGeneratorBase));
/*  87 */     list.put("TileEntityGeoGenerator", Integer.valueOf(IC2.energyGeneratorGeo));
/*  88 */     list.put("TileEntitySolarGenerator", Integer.valueOf(1));
/*  89 */     list.put("TileEntityWaterGenerator", Integer.valueOf(2));
/*  90 */     list.put("TileEntityElectricBatBox", Integer.valueOf(32));
/*  91 */     list.put("TileEntityTransformerMV", Integer.valueOf(512));
/*  92 */     list.put("TileEntityTransformerLV", Integer.valueOf(128));
/*  93 */     list.put("TileEntityTransformerHV", Integer.valueOf(2048));
/*  94 */     list.put("TileEntityElectricMFSU", Integer.valueOf(512));
/*  95 */     list.put("TileEntityElectricMFE", Integer.valueOf(128));
/*  96 */     list.put("TileIC2MultiEmitterDelegate", Integer.valueOf(200));
/*  97 */     values.putAll(list);
/*  98 */     acceptingOverride.put("TileEntityMolecularTransformer", Integer.valueOf(4096));
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEnergyInit(EnergyTileLoadEvent event) {
/* 104 */     IEnergyTile tile = event.energyTile;
/* 105 */     if (tile != null && tile instanceof IEnergySource && !(tile instanceof IEnergySourceInfo))
/*     */     {
/* 107 */       initIEnergySource((IEnergySource)event.energyTile);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\energy\EnergyTransferList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */