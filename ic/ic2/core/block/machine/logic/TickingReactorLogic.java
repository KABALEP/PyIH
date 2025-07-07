/*     */ package ic2.core.block.machine.logic;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorComponent;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*     */ import ic2.core.util.MathUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.util.EnumMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TickingReactorLogic
/*     */   extends ReactorLogicBase
/*     */ {
/*  28 */   EnumMap<IReactorPlannerComponent.ReactorComponentType, Set<Integer>> typeToSlots = new EnumMap<>(IReactorPlannerComponent.ReactorComponentType.class);
/*  29 */   TileEntityReactorPlanner.ReactorBackup backup = new TileEntityReactorPlanner.ReactorBackup();
/*  30 */   int[] replacedItems = new int[54];
/*  31 */   ReactorPrediction predict = new ReactorPrediction();
/*     */   
/*  33 */   public long totalProducedEnergy = 0L;
/*     */ 
/*     */   
/*     */   public TickingReactorLogic(TileEntityReactorPlanner tile) {
/*  37 */     super(tile);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  43 */     NBTTagCompound nbt = new NBTTagCompound();
/*  44 */     writeStateToNBT(nbt);
/*  45 */     nbt = (NBTTagCompound)nbt.func_74737_b();
/*  46 */     doReactorTick();
/*  47 */     Set<Integer> resets = requireReset();
/*  48 */     if (resets.size() > 0) {
/*     */       
/*  50 */       readStateFromNBT(nbt);
/*  51 */       resetSlots(resets);
/*  52 */       doReactorTick();
/*     */     } 
/*  54 */     if (this.currentHeat >= this.maxHeat)
/*     */     {
/*  56 */       this.isExploded = true;
/*     */     }
/*  58 */     if (isComponentBroken())
/*     */     {
/*  60 */       this.didComponentBreak = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFinished() {
/*  67 */     for (int i = 0; i < 54; i++) {
/*     */       
/*  69 */       if (this.replacedItems[i] > 0)
/*     */       {
/*  71 */         StackUtil.addToolTip(this.inv[i], EnumChatFormatting.AQUA + StatCollector.func_74837_a("container.reactorplannerInfo.replacedTimes.name", new Object[] { Integer.valueOf(this.replacedItems[i]) }));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSizeUpdate() {
/*  79 */     super.onSizeUpdate();
/*  80 */     int size = getReactorSize();
/*  81 */     for (int x = size; x < 9; x++) {
/*     */       
/*  83 */       for (int y = 0; y < 6; y++)
/*     */       {
/*  85 */         this.backup.items[y * 9 + x] = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ReactorLogicBase.IReactorPrediction createPrediction() {
/*  93 */     return this.predict;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/*  99 */     this.predict = new ReactorPrediction();
/* 100 */     this.typeToSlots.clear();
/* 101 */     this.backup = new TileEntityReactorPlanner.ReactorBackup();
/* 102 */     this.backup.reactorSize = this.planner.reactorSize;
/* 103 */     this.backup.hasBackup = true;
/* 104 */     this.replacedItems = new int[54];
/* 105 */     this.ticksDone = 0;
/* 106 */     this.ticksLeft = 0;
/* 107 */     this.maxTick = 0;
/* 108 */     this.currentHeat = 0;
/* 109 */     this.maxHeat = 10000;
/* 110 */     this.explosionEffect = 1.0F;
/* 111 */     this.producing = true;
/* 112 */     this.production = 0.0F;
/* 113 */     this.isValid = true;
/* 114 */     this.isExploded = false;
/* 115 */     this.collecting = false;
/* 116 */     this.didComponentBreak = false;
/* 117 */     this.custom = false;
/* 118 */     this.fuelPulses = 0;
/* 119 */     this.reEnrichPulses = 0; int i;
/* 120 */     for (i = 0; i < 54; i++) {
/*     */       
/* 122 */       this.backup.items[i] = ItemStack.func_77944_b(this.inv[i]);
/* 123 */       addToType(PlannerRegistry.getTypeByItem(this.inv[i]), i);
/*     */     } 
/* 125 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.FuelRod)) {
/*     */       
/* 127 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.FuelRod)) {
/*     */         
/* 129 */         IReactorPlannerComponent comp = getComponent(slot.intValue());
/* 130 */         if (comp != null) {
/*     */           
/* 132 */           this.maxTick = Math.max(this.maxTick, comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.MaxDurability, this.inv[slot.intValue()]) ? comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.MaxDurability).func_150287_d() : comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.MaxDurability, this.inv[slot.intValue()]).func_150287_d());
/*     */           
/* 134 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.HeatProduction, this.inv[slot.intValue()])) { this.predict.heatPerTick += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.HeatProduction).func_150287_d(); }
/* 135 */           else { this.predict.heatPerTick += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.HeatProduction, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 137 */           this.production = 0.0F;
/* 138 */           this.fuelPulses = 0;
/* 139 */           this.reEnrichPulses = 0;
/* 140 */           this.collecting = true;
/* 141 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.EnergyProduction, this.inv[slot.intValue()])) { this.predict.energyPerTick += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.EnergyProduction).func_150288_h(); }
/* 142 */           else { this.predict.energyPerTick += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.EnergyProduction, this.inv[slot.intValue()]).func_150288_h(); }
/* 143 */            this.predict.totalFuelRodPulses += this.fuelPulses;
/* 144 */           this.predict.totalReEnrichtingPulses += this.reEnrichPulses;
/* 145 */           this.collecting = false;
/* 146 */           this.fuelPulses = 0;
/* 147 */           this.reEnrichPulses = 0;
/* 148 */           this.production = 0.0F;
/*     */           
/* 150 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.RodAmount, this.inv[slot.intValue()])) { this.predict.totalCellCount += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.RodAmount).func_150287_d(); }
/* 151 */           else { this.predict.totalCellCount += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.RodAmount, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 153 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.PulseAmount, this.inv[slot.intValue()])) { this.predict.totalInternalFuelPulses += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.PulseAmount).func_150287_d(); }
/* 154 */           else { this.predict.totalInternalFuelPulses += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.PulseAmount, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 156 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.EnergyProduction, this.inv[slot.intValue()])) { addExplosionEffect(comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.EnergyProduction).func_150288_h()); continue; }
/* 157 */            addExplosionEffect(comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.EnergyProduction, this.inv[slot.intValue()]).func_150288_h());
/*     */         } 
/*     */       } 
/*     */       
/* 161 */       this.fuelPulses = 0;
/* 162 */       this.reEnrichPulses = 0;
/* 163 */       this.production = 0.0F;
/* 164 */       this.collecting = false;
/*     */     } 
/* 166 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.HeatPack))
/*     */     {
/* 168 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.HeatPack)) {
/*     */         
/* 170 */         IReactorPlannerComponent comp = getComponent(slot.intValue());
/* 171 */         if (comp != null) {
/*     */           
/* 173 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.HeatProduction, this.inv[slot.intValue()])) { addHeatPackHeat(comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.HeatProduction).func_150287_d()); continue; }
/* 174 */            addHeatPackHeat(comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.HeatProduction, this.inv[slot.intValue()]).func_150287_d());
/*     */         } 
/*     */       } 
/*     */     }
/* 178 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.Vent))
/*     */     {
/* 180 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.Vent)) {
/*     */         
/* 182 */         IReactorPlannerComponent comp = getComponent(slot.intValue());
/* 183 */         if (comp != null) {
/*     */           
/* 185 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.SelfCooling, this.inv[slot.intValue()])) { this.predict.coolingPerTick += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.SelfCooling).func_150287_d(); }
/* 186 */           else { this.predict.coolingPerTick += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.SelfCooling, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 188 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.ReactorCooling, this.inv[slot.intValue()])) { this.predict.reactorCoolingPerTick += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.ReactorCooling).func_150287_d(); continue; }
/* 189 */            this.predict.reactorCoolingPerTick += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.ReactorCooling, this.inv[slot.intValue()]).func_150287_d();
/*     */         } 
/*     */       } 
/*     */     }
/* 193 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.VentSpread))
/*     */     {
/* 195 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.VentSpread)) {
/*     */         
/* 197 */         IReactorPlannerComponent comp = getComponent(slot.intValue());
/* 198 */         if (comp != null) {
/*     */           
/* 200 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.PartCooling, this.inv[slot.intValue()])) { this.predict.coolingPerTick += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.PartCooling).func_150287_d(); }
/* 201 */           else { this.predict.coolingPerTick += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.PartCooling, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 203 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.ReactorCooling, this.inv[slot.intValue()])) { this.predict.reactorCoolingPerTick += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.ReactorCooling).func_150287_d(); continue; }
/* 204 */            this.predict.reactorCoolingPerTick += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.ReactorCooling, this.inv[slot.intValue()]).func_150287_d();
/*     */         } 
/*     */       } 
/*     */     }
/* 208 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.Plating))
/*     */     {
/* 210 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.Plating)) {
/*     */         
/* 212 */         IReactorPlannerComponent comp = getComponent(slot.intValue());
/* 213 */         if (comp != null) {
/*     */           
/* 215 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.ReactorMaxHeat, this.inv[slot.intValue()])) { this.maxHeat += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.ReactorMaxHeat).func_150287_d(); }
/* 216 */           else { this.maxHeat += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.ReactorMaxHeat, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 218 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.ReactorEEM, this.inv[slot.intValue()])) { addExplosionEffect(comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.ReactorEEM).func_150287_d()); continue; }
/* 219 */            addExplosionEffect(comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.ReactorEEM, this.inv[slot.intValue()]).func_150287_d());
/*     */         } 
/*     */       } 
/*     */     }
/* 223 */     for (i = 0; i < 54; i++)
/*     */     {
/* 225 */       this.inv[i] = ItemStack.func_77944_b(this.backup.items[i]);
/*     */     }
/* 227 */     this.ticksLeft = this.maxTick;
/* 228 */     TileEntityReactorPlanner.UserSettings settings = this.planner.getUserSettings();
/* 229 */     if (settings.maxTicks != 0) {
/*     */       
/* 231 */       this.ticksLeft = settings.maxTicks;
/* 232 */       this.backup.customTicks = this.ticksLeft;
/* 233 */       this.custom = true;
/* 234 */       this.maxTick = this.ticksLeft;
/*     */     } 
/* 236 */     finishCalculations();
/* 237 */     this.production = this.predict.energyPerTick;
/* 238 */     this.predict.energyPerTick = (float)getReactorEUEnergyOutput();
/* 239 */     this.production = 0.0F;
/* 240 */     this.predict.totalHeatProduced += (this.predict.heatPerTick + this.predict.heatPackHeatPerTick) * this.maxTick;
/* 241 */     this.predict.totalEnergyProduced += (long)this.predict.energyPerTick * this.maxTick * getTickRate();
/* 242 */     this.currentHeat = Math.min(this.maxHeat - 100, settings.startingHeat);
/* 243 */     this.backup.customHeat = this.currentHeat;
/* 244 */     this.backup.customTicks = this.maxTick;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityReactorPlanner.ReactorBackup createBackup() {
/* 250 */     if (this.ticksDone > 0)
/*     */     {
/* 252 */       reset();
/*     */     }
/* 254 */     TileEntityReactorPlanner.ReactorBackup toSave = new TileEntityReactorPlanner.ReactorBackup();
/* 255 */     toSave.hasBackup = true;
/* 256 */     toSave.isSteam = isSteamLogic();
/* 257 */     for (int i = 0; i < 54; i++)
/*     */     {
/* 259 */       toSave.items[i] = ItemStack.func_77944_b(this.inv[i]);
/*     */     }
/* 261 */     TileEntityReactorPlanner.UserSettings settings = this.planner.getUserSettings();
/* 262 */     if (settings.maxTicks != 0)
/*     */     {
/* 264 */       toSave.customTicks = settings.maxTicks;
/*     */     }
/* 266 */     toSave.reactorSize = this.planner.reactorSize;
/* 267 */     toSave.customHeat = settings.startingHeat;
/* 268 */     return toSave;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void restoreFromBackup(TileEntityReactorPlanner.ReactorBackup toLoad) {
/* 274 */     NBTTagCompound nbt = new NBTTagCompound();
/* 275 */     toLoad.writeToNBT(nbt);
/* 276 */     this.backup.readFromNBT((NBTTagCompound)nbt.func_74737_b());
/* 277 */     this.isValid = false;
/* 278 */     this.isExploded = false;
/* 279 */     this.inv = new ItemStack[54];
/* 280 */     for (int i = 0; i < 54; i++)
/*     */     {
/* 282 */       this.inv[i] = ItemStack.func_77944_b(toLoad.items[i]);
/*     */     }
/* 284 */     TileEntityReactorPlanner.UserSettings settings = this.planner.getUserSettings();
/* 285 */     settings.maxTicks = toLoad.customTicks;
/* 286 */     settings.startingHeat = toLoad.customHeat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 292 */     this.isValid = false;
/* 293 */     this.isExploded = false;
/* 294 */     this.didComponentBreak = false;
/* 295 */     this.inv = new ItemStack[54];
/* 296 */     this.planner.reactorSize = this.backup.reactorSize;
/* 297 */     this.ticksDone = 0;
/* 298 */     this.ticksLeft = 0;
/* 299 */     this.maxTick = 0;
/* 300 */     this.currentHeat = 0;
/* 301 */     this.maxHeat = 10000;
/* 302 */     this.explosionEffect = 1.0F;
/* 303 */     this.production = 0.0F;
/* 304 */     this.producing = false;
/* 305 */     this.totalProducedEnergy = 0L;
/* 306 */     for (int i = 0; i < 54; i++)
/*     */     {
/* 308 */       this.inv[i] = ItemStack.func_77944_b(this.backup.items[i]);
/*     */     }
/* 310 */     if (this.custom) {
/*     */       
/* 312 */       TileEntityReactorPlanner.UserSettings settings = this.planner.getUserSettings();
/* 313 */       settings.maxTicks = this.backup.customTicks;
/* 314 */       settings.startingHeat = this.backup.customHeat;
/*     */     } 
/* 316 */     this.custom = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 322 */     super.clear();
/* 323 */     this.backup.items = new ItemStack[54];
/* 324 */     this.backup.customHeat = 0;
/* 325 */     this.backup.customTicks = 0;
/* 326 */     this.backup.hasBackup = false;
/* 327 */     this.totalProducedEnergy = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSteamLogic() {
/* 333 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTickRate() {
/* 339 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readStateFromNBT(NBTTagCompound nbt) {
/* 345 */     super.readStateFromNBT(nbt);
/* 346 */     this.totalProducedEnergy = nbt.func_74763_f("TotalEUProduced");
/* 347 */     this.replacedItems = nbt.func_74759_k("Replacements");
/* 348 */     if (this.replacedItems.length != 54)
/*     */     {
/* 350 */       this.replacedItems = new int[54];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStateToNBT(NBTTagCompound nbt) {
/* 357 */     super.writeStateToNBT(nbt);
/* 358 */     nbt.func_74772_a("TotalEUProduced", this.totalProducedEnergy);
/* 359 */     nbt.func_74783_a("Replacements", this.replacedItems);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 365 */     super.writeToNBT(nbt);
/* 366 */     this.backup.writeToNBT(getTag(nbt, "Backup"));
/* 367 */     nbt.func_74772_a("TotalEUProduced", this.totalProducedEnergy);
/* 368 */     nbt.func_74783_a("Replacements", this.replacedItems);
/* 369 */     NBTTagList list = new NBTTagList();
/* 370 */     for (Map.Entry<IReactorPlannerComponent.ReactorComponentType, Set<Integer>> entry : this.typeToSlots.entrySet()) {
/*     */       
/* 372 */       NBTTagCompound data = new NBTTagCompound();
/* 373 */       data.func_74774_a("Key", (byte)((IReactorPlannerComponent.ReactorComponentType)entry.getKey()).ordinal());
/* 374 */       data.func_74783_a("Value", MathUtil.fromIntegerToInt(entry.getValue()));
/* 375 */       list.func_74742_a((NBTBase)data);
/*     */     } 
/* 377 */     nbt.func_74782_a("TypeSorting", (NBTBase)list);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 383 */     super.readFromNBT(nbt);
/* 384 */     this.backup.readFromNBT(nbt.func_74775_l("Backup"));
/* 385 */     this.totalProducedEnergy = nbt.func_74763_f("TotalEUProduced");
/* 386 */     this.replacedItems = nbt.func_74759_k("Replacements");
/* 387 */     if (this.replacedItems.length != 54)
/*     */     {
/* 389 */       this.replacedItems = new int[54];
/*     */     }
/* 391 */     NBTTagList list = nbt.func_150295_c("TypeSorting", 10);
/* 392 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/* 394 */       NBTTagCompound entry = list.func_150305_b(i);
/* 395 */       addMass(entry.func_74762_e("Key"), entry.func_74759_k("Value"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(DataInput stream) {
/* 402 */     super.read(stream);
/*     */     
/*     */     try {
/* 405 */       this.totalProducedEnergy = stream.readLong();
/*     */     }
/* 407 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(DataOutput stream) {
/* 415 */     super.write(stream);
/*     */     
/*     */     try {
/* 418 */       stream.writeLong(this.totalProducedEnergy);
/*     */     }
/* 420 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToType(IReactorPlannerComponent.ReactorComponentType type, int slot) {
/* 427 */     if (type == null) {
/*     */       return;
/*     */     }
/*     */     
/* 431 */     Set<Integer> slots = this.typeToSlots.get(type);
/* 432 */     if (slots == null) {
/*     */       
/* 434 */       slots = new LinkedHashSet<>();
/* 435 */       this.typeToSlots.put(type, slots);
/*     */     } 
/* 437 */     slots.add(Integer.valueOf(slot));
/*     */   }
/*     */ 
/*     */   
/*     */   private void addMass(int key, int[] value) {
/* 442 */     if (value.length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 446 */     IReactorPlannerComponent.ReactorComponentType[] array = IReactorPlannerComponent.ReactorComponentType.values();
/* 447 */     if (array.length > key)
/*     */     {
/* 449 */       this.typeToSlots.put(array[key], new LinkedHashSet<>(MathUtil.fromIntToInteger(value)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addHeatPackHeat(int amount) {
/* 455 */     this.predict.heatPerTick += amount;
/* 456 */     this.predict.heatPackHeatPerTick += amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addExplosionEffect(float value) {
/* 461 */     if (value > 0.0F && value < 1.0F) {
/*     */       
/* 463 */       this.predict.boomMod *= value;
/*     */     }
/*     */     else {
/*     */       
/* 467 */       this.predict.boomPower += value;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishCalculations() {
/* 473 */     this.predict.totalExplosionPower = this.predict.boomPower * this.explosionEffect * this.predict.boomMod;
/* 474 */     this.predict.efficency = (this.predict.energyPerTick != 0.0F) ? (this.predict.totalFuelRodPulses / this.predict.totalCellCount) : 0.0F;
/* 475 */     this.predict.totalEfficency = (this.predict.energyPerTick != 0.0F) ? ((this.predict.totalFuelRodPulses + this.predict.totalReEnrichtingPulses) / this.predict.totalCellCount) : 0.0F;
/* 476 */     this.predict.breeder = (this.predict.totalReEnrichtingPulses > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doReactorTick() {
/* 481 */     this.ticksDone++;
/* 482 */     this.ticksLeft -= (this.producing || !this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.FuelRod)) ? 1 : 0;
/* 483 */     boolean finished = isFinished();
/* 484 */     if (finished)
/*     */     {
/* 486 */       this.totalProducedEnergy = (long)(this.totalProducedEnergy + getReactorEUEnergyOutput() * getTickRate());
/*     */     }
/* 488 */     this.production = 0.0F;
/* 489 */     this.maxHeat = 10000;
/* 490 */     this.explosionEffect = 1.0F;
/* 491 */     for (int pass = 0; pass < 2; pass++) {
/*     */       
/* 493 */       for (int y = 0; y < 6; y++) {
/*     */         
/* 495 */         for (int x = 0; x < 9; x++) {
/*     */           
/* 497 */           ItemStack thing = getItemAt(x, y);
/* 498 */           if (thing != null && thing.func_77973_b() instanceof IReactorComponent) {
/*     */             
/* 500 */             IReactorComponent comp = (IReactorComponent)thing.func_77973_b();
/* 501 */             comp.processChamber((IReactor)this, thing, x, y, (pass == 0));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 506 */     for (int i = 0; i < 54; i++) {
/*     */       
/* 508 */       if (func_70301_a(i) != null && !(func_70301_a(i).func_77973_b() instanceof IReactorComponent))
/*     */       {
/* 510 */         func_70299_a(i, null);
/*     */       }
/*     */     } 
/* 513 */     if (!finished)
/*     */     {
/* 515 */       this.totalProducedEnergy = (long)(this.totalProducedEnergy + getReactorEUEnergyOutput() * getTickRate());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Integer> requireReset() {
/* 521 */     Set<Integer> result = new LinkedHashSet<>(54);
/* 522 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.Conensator))
/*     */     {
/* 524 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.Conensator)) {
/*     */         
/* 526 */         IReactorPlannerComponent iReactorPlannerComponent = getComponent(slot.intValue());
/* 527 */         if (iReactorPlannerComponent == null || !iReactorPlannerComponent.canStoreHeat((IReactor)this, this.inv[slot.intValue()], slot.intValue() % 9, slot.intValue() / 9))
/*     */         {
/* 529 */           result.add(slot);
/*     */         }
/*     */       } 
/*     */     }
/* 533 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.Reflection))
/*     */     {
/* 535 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.Reflection)) {
/*     */         
/* 537 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 539 */           result.add(slot);
/*     */         }
/*     */       } 
/*     */     }
/* 543 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.IsotopeCell))
/*     */     {
/* 545 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.IsotopeCell)) {
/*     */         
/* 547 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 549 */           result.add(slot);
/*     */         }
/*     */       } 
/*     */     }
/* 553 */     if (this.ticksLeft > 0 && this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.FuelRod))
/*     */     {
/* 555 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.FuelRod)) {
/*     */         
/* 557 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 559 */           result.add(slot);
/*     */         }
/*     */       } 
/*     */     }
/* 563 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetSlots(Set<Integer> slots) {
/* 568 */     for (Integer slot : slots) {
/*     */       
/* 570 */       this.inv[slot.intValue()] = ItemStack.func_77944_b(this.backup.items[slot.intValue()]);
/* 571 */       this.replacedItems[slot.intValue()] = this.replacedItems[slot.intValue()] + 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComponentBroken() {
/* 577 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.CoolantCell))
/*     */     {
/* 579 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.CoolantCell)) {
/*     */         
/* 581 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 583 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 587 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.Vent))
/*     */     {
/* 589 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.Vent)) {
/*     */         
/* 591 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 593 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 597 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.HeatSwitch))
/*     */     {
/* 599 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.HeatSwitch)) {
/*     */         
/* 601 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 603 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 607 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ReactorPrediction
/*     */     implements ReactorLogicBase.IReactorPrediction
/*     */   {
/*     */     public long totalEnergyProduced;
/*     */     
/*     */     public long totalHeatProduced;
/*     */     
/*     */     public float energyPerTick;
/*     */     
/*     */     public int heatPerTick;
/*     */     
/*     */     public int heatPackHeatPerTick;
/*     */     public int coolingPerTick;
/*     */     public int reactorCoolingPerTick;
/*     */     public float efficency;
/*     */     public float totalEfficency;
/*     */     public boolean breeder;
/*     */     public int totalFuelRodPulses;
/*     */     public int totalInternalFuelPulses;
/*     */     public int totalCellCount;
/*     */     public int totalReEnrichtingPulses;
/*     */     public float totalExplosionPower;
/* 633 */     float boomPower = 10.0F;
/* 634 */     float boomMod = 1.0F;
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {
/* 639 */       this.totalEnergyProduced = 0L;
/* 640 */       this.totalHeatProduced = 0L;
/* 641 */       this.energyPerTick = 0.0F;
/* 642 */       this.heatPerTick = 0;
/* 643 */       this.heatPackHeatPerTick = 0;
/* 644 */       this.coolingPerTick = 0;
/* 645 */       this.reactorCoolingPerTick = 0;
/* 646 */       this.efficency = 0.0F;
/* 647 */       this.totalEfficency = 0.0F;
/* 648 */       this.breeder = false;
/* 649 */       this.totalFuelRodPulses = 0;
/* 650 */       this.totalInternalFuelPulses = 0;
/* 651 */       this.totalCellCount = 0;
/* 652 */       this.totalReEnrichtingPulses = 0;
/* 653 */       this.totalExplosionPower = 0.0F;
/* 654 */       this.boomPower = 10.0F;
/* 655 */       this.boomMod = 1.0F;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void read(DataInput stream) {
/*     */       try {
/* 663 */         this.totalEnergyProduced = stream.readLong();
/* 664 */         this.totalHeatProduced = stream.readLong();
/* 665 */         this.energyPerTick = stream.readFloat();
/* 666 */         this.heatPerTick = stream.readInt();
/* 667 */         this.heatPackHeatPerTick = stream.readInt();
/* 668 */         this.coolingPerTick = stream.readInt();
/* 669 */         this.reactorCoolingPerTick = stream.readInt();
/* 670 */         this.totalExplosionPower = stream.readFloat();
/* 671 */         this.efficency = stream.readFloat();
/* 672 */         this.totalEfficency = stream.readFloat();
/* 673 */         this.breeder = stream.readBoolean();
/*     */       }
/* 675 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(DataOutput stream) {
/*     */       try {
/* 685 */         stream.writeLong(this.totalEnergyProduced);
/* 686 */         stream.writeLong(this.totalHeatProduced);
/* 687 */         stream.writeFloat(this.energyPerTick);
/* 688 */         stream.writeInt(this.heatPerTick);
/* 689 */         stream.writeInt(this.heatPackHeatPerTick);
/* 690 */         stream.writeInt(this.coolingPerTick);
/* 691 */         stream.writeInt(this.reactorCoolingPerTick);
/* 692 */         stream.writeFloat(this.totalExplosionPower);
/* 693 */         stream.writeFloat(this.efficency);
/* 694 */         stream.writeFloat(this.totalEfficency);
/* 695 */         stream.writeBoolean(this.breeder);
/*     */       }
/* 697 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void readFromNBT(NBTTagCompound nbt) {
/* 705 */       this.totalEnergyProduced = nbt.func_74763_f("TotalEnergy");
/* 706 */       this.totalHeatProduced = nbt.func_74763_f("TotalHeat");
/* 707 */       this.energyPerTick = nbt.func_74760_g("EnergyProduction");
/* 708 */       this.heatPerTick = nbt.func_74762_e("HeatProduction");
/* 709 */       this.heatPackHeatPerTick = nbt.func_74762_e("HeatPackPerTick");
/* 710 */       this.coolingPerTick = nbt.func_74762_e("Coolant");
/* 711 */       this.reactorCoolingPerTick = nbt.func_74762_e("ReactorCoolant");
/* 712 */       this.totalExplosionPower = nbt.func_74760_g("TotalExplosionEffect");
/* 713 */       this.efficency = nbt.func_74760_g("Efficency");
/* 714 */       this.totalEfficency = nbt.func_74760_g("TotalEfficency");
/* 715 */       this.breeder = nbt.func_74767_n("Breeder");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeToNBT(NBTTagCompound nbt) {
/* 721 */       nbt.func_74772_a("TotalEnergy", this.totalEnergyProduced);
/* 722 */       nbt.func_74772_a("TotalHeat", this.totalHeatProduced);
/* 723 */       nbt.func_74776_a("EnergyProduction", this.energyPerTick);
/* 724 */       nbt.func_74768_a("HeatProduction", this.heatPerTick);
/* 725 */       nbt.func_74768_a("HeatPackPerTick", this.heatPackHeatPerTick);
/* 726 */       nbt.func_74768_a("Coolant", this.coolingPerTick);
/* 727 */       nbt.func_74768_a("ReactorCoolant", this.reactorCoolingPerTick);
/* 728 */       nbt.func_74776_a("TotalExplosionEffect", this.totalExplosionPower);
/* 729 */       nbt.func_74776_a("Efficency", this.efficency);
/* 730 */       nbt.func_74776_a("TotalEfficency", this.totalEfficency);
/* 731 */       nbt.func_74757_a("Breeder", this.breeder);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSteam() {
/* 737 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\TickingReactorLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */