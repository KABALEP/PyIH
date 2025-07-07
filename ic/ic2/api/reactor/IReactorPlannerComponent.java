/*     */ package ic2.api.reactor;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagFloat;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IReactorPlannerComponent
/*     */   extends IReactorComponent
/*     */ {
/*  16 */   public static final NBTBase.NBTPrimitive nulltag = (NBTBase.NBTPrimitive)new NBTNull();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ItemStack[] getSubParts();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasSubParts();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ItemStack getReactorPart();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   short getID(ItemStack paramItemStack);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ReactorType getReactorInfo(ItemStack paramItemStack);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ReactorComponentType getType(ItemStack paramItemStack);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<ReactorComponentStat> getExtraStats(ItemStack paramItemStack);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NBTBase.NBTPrimitive getReactorStat(ReactorComponentStat paramReactorComponentStat, ItemStack paramItemStack);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isAdvancedStat(ReactorComponentStat paramReactorComponentStat, ItemStack paramItemStack);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NBTBase.NBTPrimitive getReactorStat(IReactor paramIReactor, int paramInt1, int paramInt2, ItemStack paramItemStack, ReactorComponentStat paramReactorComponentStat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum ReactorComponentType
/*     */   {
/* 109 */     FuelRod((String)new IReactorPlannerComponent.ReactorComponentStat[] { IReactorPlannerComponent.ReactorComponentStat.RodAmount, IReactorPlannerComponent.ReactorComponentStat.PulseAmount, IReactorPlannerComponent.ReactorComponentStat.HeatProduction, IReactorPlannerComponent.ReactorComponentStat.EnergyProduction, IReactorPlannerComponent.ReactorComponentStat.MaxDurability, IReactorPlannerComponent.ReactorComponentStat.ReactorEEM }),
/* 110 */     CoolantCell((String)new IReactorPlannerComponent.ReactorComponentStat[] { IReactorPlannerComponent.ReactorComponentStat.HeatStorage }),
/* 111 */     Conensator((String)new IReactorPlannerComponent.ReactorComponentStat[] { IReactorPlannerComponent.ReactorComponentStat.HeatStorage }),
/* 112 */     HeatPack((String)new IReactorPlannerComponent.ReactorComponentStat[] { IReactorPlannerComponent.ReactorComponentStat.HeatProduction }),
/* 113 */     Vent((String)new IReactorPlannerComponent.ReactorComponentStat[] { IReactorPlannerComponent.ReactorComponentStat.SelfCooling, IReactorPlannerComponent.ReactorComponentStat.ReactorCooling }),
/* 114 */     VentSpread((String)new IReactorPlannerComponent.ReactorComponentStat[] { IReactorPlannerComponent.ReactorComponentStat.PartCooling, IReactorPlannerComponent.ReactorComponentStat.ReactorCooling }),
/* 115 */     HeatSwitch((String)new IReactorPlannerComponent.ReactorComponentStat[] { IReactorPlannerComponent.ReactorComponentStat.ReactorChange, IReactorPlannerComponent.ReactorComponentStat.PartChange }),
/* 116 */     Plating((String)new IReactorPlannerComponent.ReactorComponentStat[] { IReactorPlannerComponent.ReactorComponentStat.ReactorMaxHeat, IReactorPlannerComponent.ReactorComponentStat.ReactorEEM }),
/* 117 */     Reflection((String)new IReactorPlannerComponent.ReactorComponentStat[] { IReactorPlannerComponent.ReactorComponentStat.EnergyProduction, IReactorPlannerComponent.ReactorComponentStat.MaxDurability }),
/* 118 */     IsotopeCell((String)new IReactorPlannerComponent.ReactorComponentStat[] { IReactorPlannerComponent.ReactorComponentStat.MaxDurability, IReactorPlannerComponent.ReactorComponentStat.ReactorEEM });
/*     */     
/*     */     IReactorPlannerComponent.ReactorComponentStat[] parts;
/*     */ 
/*     */     
/*     */     ReactorComponentType(IReactorPlannerComponent.ReactorComponentStat... par1) {
/* 124 */       this.parts = par1;
/*     */     }
/*     */ 
/*     */     
/*     */     public IReactorPlannerComponent.ReactorComponentStat[] getStats() {
/* 129 */       return this.parts;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ReactorComponentStat
/*     */   {
/* 135 */     HeatProduction(false),
/* 136 */     EnergyProduction(true),
/* 137 */     RodAmount(false),
/* 138 */     PulseAmount(false),
/* 139 */     SelfCooling(false),
/* 140 */     PartCooling(false),
/* 141 */     ReactorCooling(false),
/* 142 */     PartChange(false),
/* 143 */     ReactorChange(false),
/* 144 */     HeatStorage(false),
/* 145 */     ReactorMaxHeat(false),
/* 146 */     ReactorEEM(true),
/* 147 */     MaxDurability(false),
/* 148 */     EnergyUsage(true),
/* 149 */     WaterConsumtion(true),
/* 150 */     SteamProduction(true),
/* 151 */     WaterStorage(true);
/*     */ 
/*     */     
/*     */     final boolean isFloat;
/*     */ 
/*     */     
/*     */     ReactorComponentStat(boolean isFloat) {
/* 158 */       this.isFloat = isFloat;
/*     */     }
/*     */ 
/*     */     
/*     */     public NBTBase.NBTPrimitive createStat(Number nr) {
/* 163 */       if (this.isFloat)
/*     */       {
/* 165 */         return (NBTBase.NBTPrimitive)new NBTTagFloat(nr.floatValue());
/*     */       }
/* 167 */       return (NBTBase.NBTPrimitive)new NBTTagInt(nr.intValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ReactorType
/*     */   {
/* 173 */     Reactor,
/* 174 */     SteamReactor,
/* 175 */     Both;
/*     */ 
/*     */     
/*     */     public boolean isReactor() {
/* 179 */       return (this == Reactor || this == Both);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSteamReactor() {
/* 184 */       return (this == SteamReactor || this == Both);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NBTNull
/*     */     extends NBTTagInt
/*     */   {
/*     */     public NBTNull() {
/* 192 */       super(0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\reactor\IReactorPlannerComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */