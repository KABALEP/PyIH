/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.IPacketEnergyNet;
/*     */ import ic2.api.energy.NodeStats;
/*     */ import ic2.api.energy.PacketStat;
/*     */ import ic2.api.energy.tile.IEnergyConductor;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTeleporter;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.util.KeyboardClient;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemToolMeter
/*     */   extends ItemIC2
/*     */ {
/*     */   public ItemToolMeter(int index) {
/*  35 */     super(index);
/*  36 */     this.field_77777_bU = 1;
/*  37 */     func_77656_e(0);
/*  38 */     setSpriteID("i1");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack par1, World par2, EntityPlayer par3) {
/*  44 */     if (!par2.field_72995_K)
/*     */     {
/*  46 */       if (IC2.keyboard.isModeSwitchKeyDown(par3)) {
/*     */         
/*  48 */         int meta = par1.func_77960_j() + 1;
/*  49 */         if (meta >= (MeasuringMode.values()).length)
/*     */         {
/*  51 */           meta = 0;
/*     */         }
/*  53 */         par1.func_77964_b(meta);
/*  54 */         IC2.platform.messagePlayer(par3, StatCollector.func_74837_a("itemInfo.energyMeterModeChanged.name", new Object[] { MeasuringMode.values()[meta].getName() }));
/*  55 */         return par1;
/*     */       } 
/*     */     }
/*     */     
/*  59 */     return par1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> p_77624_3_, boolean p_77624_4_) {
/*  66 */     super.func_77624_a(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
/*  67 */     p_77624_3_.add(StatCollector.func_74837_a("itemInfo.energyMeterModeSwitchHelp.name", new Object[] { ((KeyboardClient)IC2.keyboard).getKey(2) }));
/*  68 */     p_77624_3_.add(StatCollector.func_74837_a("itemInfo.energyMeterMode.name", new Object[] { MeasuringMode.values()[p_77624_1_.func_77960_j()].getName() }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float hitX, float hitY, float hitZ) {
/*  74 */     MeasuringMode mode = MeasuringMode.values()[itemstack.func_77960_j()];
/*  75 */     if (!IC2.keyboard.isModeSwitchKeyDown(entityplayer))
/*     */     {
/*  77 */       if (IC2.platform.isSimulating()) {
/*     */         
/*  79 */         TileEntity tileEntity = EnergyNet.instance.getTileEntity(world, i, j, k);
/*  80 */         if (tileEntity instanceof IEnergySource || tileEntity instanceof IEnergyConductor || tileEntity instanceof IEnergySink) {
/*     */           
/*  82 */           if (mode == MeasuringMode.EUAverageReading) {
/*     */             
/*  84 */             NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
/*  85 */             NodeStats node = EnergyNet.instance.getNodeStats(tileEntity);
/*  86 */             double currentTotalEnergyEmitted = node.getEnergyOut();
/*  87 */             double currentTotalEnergySunken = node.getEnergyIn();
/*  88 */             long currentMeasureTime = world.func_82737_E();
/*  89 */             if (nbtData.func_74762_e("lastMeasuredTileEntityX") != i || nbtData.func_74762_e("lastMeasuredTileEntityY") != j || nbtData.func_74762_e("lastMeasuredTileEntityZ") != k) {
/*     */               
/*  91 */               nbtData.func_74768_a("lastMeasuredTileEntityX", i);
/*  92 */               nbtData.func_74768_a("lastMeasuredTileEntityY", j);
/*  93 */               nbtData.func_74768_a("lastMeasuredTileEntityZ", k);
/*  94 */               IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.energyMeterNewMeasurment.name"));
/*     */             }
/*     */             else {
/*     */               
/*  98 */               long measurePeriod = currentMeasureTime - nbtData.func_74763_f("lastMeasureTime");
/*  99 */               if (measurePeriod < 1L)
/*     */               {
/* 101 */                 measurePeriod = 1L;
/*     */               }
/* 103 */               double deltaEmitted = (currentTotalEnergyEmitted - nbtData.func_74769_h("lastTotalEnergyEmitted")) / measurePeriod;
/* 104 */               double deltaSunken = (currentTotalEnergySunken - nbtData.func_74769_h("lastTotalEnergySunken")) / measurePeriod;
/* 105 */               DecimalFormat powerFormat = new DecimalFormat("0.##");
/* 106 */               IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.energyMeterAverageEU.name", new Object[] { powerFormat.format(deltaSunken), powerFormat.format(deltaEmitted), powerFormat.format(deltaSunken - deltaEmitted), Long.valueOf(measurePeriod) }));
/*     */             } 
/* 108 */             nbtData.func_74780_a("lastTotalEnergyEmitted", currentTotalEnergyEmitted);
/* 109 */             nbtData.func_74780_a("lastTotalEnergySunken", currentTotalEnergySunken);
/* 110 */             nbtData.func_74772_a("lastMeasureTime", currentMeasureTime);
/* 111 */             return true;
/*     */           } 
/* 113 */           if (mode == MeasuringMode.TileInfo) {
/*     */             
/* 115 */             if (tileEntity instanceof IEnergySource) {
/*     */               
/* 117 */               int offer = (int)((IEnergySource)tileEntity).getOfferedEnergy();
/* 118 */               IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.energyMeterSourceInfo.name", new Object[] { Integer.valueOf(offer) }));
/*     */             } 
/* 120 */             if (tileEntity instanceof IEnergySink) {
/*     */               
/* 122 */               int safeInput = (int)EnergyNet.instance.getPowerFromTier(((IEnergySink)tileEntity).getSinkTier());
/* 123 */               IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.energyMeterSinkInfo.name", new Object[] { Integer.valueOf(safeInput) }));
/*     */             } 
/* 125 */             if (tileEntity instanceof IEnergyConductor) {
/*     */               
/* 127 */               double Powerloss = ((IEnergyConductor)tileEntity).getConductionLoss();
/* 128 */               int conductorBreakdown = (int)((IEnergyConductor)tileEntity).getConductorBreakdownEnergy() - 1;
/* 129 */               IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.energyMeterConductorLoss.name", new Object[] { Double.valueOf(Powerloss) }));
/* 130 */               IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.energyMeterConductorLimit.name", new Object[] { Integer.valueOf(conductorBreakdown) }));
/*     */             } 
/*     */             
/* 133 */             return true;
/*     */           } 
/* 135 */           if (mode == MeasuringMode.TotalEmitted) {
/*     */             
/* 137 */             NodeStats stats = EnergyNet.instance.getNodeStats(tileEntity);
/* 138 */             DecimalFormat powerFormat = new DecimalFormat("0.##");
/* 139 */             IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.energyMeterTotalEmitted.name", new Object[] { powerFormat.format(stats.getEnergyOut()) }));
/* 140 */             return true;
/*     */           } 
/* 142 */           if (mode == MeasuringMode.PacketReading) {
/*     */             
/* 144 */             NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
/* 145 */             List<PacketStat> packets = ((IPacketEnergyNet)EnergyNet.instance).getSendedPackets(tileEntity);
/* 146 */             long currentMeasureTime = world.func_82737_E();
/* 147 */             if (nbtData.func_74762_e("lastMeasuredTileEntityX") != i || nbtData.func_74762_e("lastMeasuredTileEntityY") != j || nbtData.func_74762_e("lastMeasuredTileEntityZ") != k) {
/*     */               
/* 149 */               nbtData.func_74768_a("lastMeasuredTileEntityX", i);
/* 150 */               nbtData.func_74768_a("lastMeasuredTileEntityY", j);
/* 151 */               nbtData.func_74768_a("lastMeasuredTileEntityZ", k);
/* 152 */               IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.energyMeterNewMeasurment.name"));
/*     */             }
/*     */             else {
/*     */               
/* 156 */               long measurePeriod = currentMeasureTime - nbtData.func_74763_f("lastMeasureTime");
/* 157 */               if (measurePeriod < 1L)
/*     */               {
/* 159 */                 measurePeriod = 1L;
/*     */               }
/* 161 */               DecimalFormat powerFormat = new DecimalFormat("0.##");
/* 162 */               IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.energyMeterAveragePacket.name", new Object[] { Long.valueOf(measurePeriod) }));
/* 163 */               if (packets.isEmpty()) {
/*     */                 
/* 165 */                 IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.energyMeterNone.name"));
/*     */               }
/*     */               else {
/*     */                 
/* 169 */                 for (PacketStat stat : packets) {
/*     */                   
/* 171 */                   IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.energyMeterEUPacket.name", new Object[] { powerFormat.format(stat.getPacketEnergy()), Long.valueOf(stat.getPacketCount()) }));
/*     */                 } 
/*     */               } 
/*     */             } 
/* 175 */             nbtData.func_74772_a("lastMeasureTime", currentMeasureTime);
/* 176 */             return true;
/*     */           } 
/* 178 */           if (mode == MeasuringMode.TotalPackets) {
/*     */             
/* 180 */             List<PacketStat> packets = ((IPacketEnergyNet)EnergyNet.instance).getTotalSendedPackets(tileEntity);
/* 181 */             DecimalFormat powerFormat = new DecimalFormat("0.##");
/* 182 */             IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.energyMeterTotalPacket.name"));
/* 183 */             if (packets.isEmpty()) {
/*     */               
/* 185 */               IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.energyMeterNone.name"));
/*     */             }
/*     */             else {
/*     */               
/* 189 */               for (PacketStat stat : packets) {
/*     */                 
/* 191 */                 IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.energyMeterEUPacket.name", new Object[] { powerFormat.format(stat.getPacketEnergy()), Long.valueOf(stat.getPacketCount()) }));
/*     */               } 
/*     */             } 
/* 194 */             return true;
/*     */           } 
/*     */         } 
/* 197 */         if (mode == MeasuringMode.TileInfo) {
/*     */           
/* 199 */           tileEntity = world.func_147438_o(i, j, k);
/* 200 */           if (tileEntity instanceof TileEntityTeleporter) {
/*     */             Entity entity;
/* 202 */             TileEntityTeleporter tele = (TileEntityTeleporter)tileEntity;
/* 203 */             List<Entity> list = world.func_72872_a(Entity.class, AxisAlignedBB.func_72330_a(i, (j + 1), k, (i + 1), (j + 2), (k + 1)));
/* 204 */             EntityPlayer entityPlayer = entityplayer;
/* 205 */             if (list.size() == 1)
/*     */             {
/* 207 */               entity = list.get(0);
/*     */             }
/* 209 */             if (tele.targetSet) {
/*     */               
/* 211 */               int weight = tele.getWeightOf(entity);
/* 212 */               double distance = tele.getDistance();
/* 213 */               int energyCost = (int)(weight * Math.pow(distance + 10.0D, 0.7D) * 5.0D);
/* 214 */               String s = "energyMeterTeleporterInfoCost";
/* 215 */               if (tele.isDimSwtich()) {
/*     */                 
/* 217 */                 energyCost *= 10;
/* 218 */                 s = s + "Dim";
/*     */               } 
/* 220 */               DecimalFormat powerFormat = new DecimalFormat("0.##");
/* 221 */               IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.energyMeterTeleporterInfoEntity.name", new Object[] { (entity instanceof EntityPlayer) ? "Player" : EntityList.func_75621_b(entity), Integer.valueOf(weight) }));
/* 222 */               IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo." + s + ".name", new Object[] { powerFormat.format(distance), Integer.valueOf(energyCost) }));
/*     */             } 
/* 224 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 230 */     return false;
/*     */   }
/*     */   
/*     */   public enum MeasuringMode
/*     */   {
/* 235 */     EUAverageReading("measureMode.averageEU.name"),
/* 236 */     TileInfo("measureMode.tileInfo.name"),
/* 237 */     TotalEmitted("measureMode.totalEmitted.name"),
/* 238 */     PacketReading("measureMode.packetReading.name"),
/* 239 */     TotalPackets("measureMode.totalPackets.name");
/*     */     
/*     */     String name;
/*     */ 
/*     */     
/*     */     MeasuringMode(String par1) {
/* 245 */       this.name = par1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 250 */       return StatCollector.func_74838_a(this.name);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemToolMeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */