/*     */ package ic2.core.block.machine.logic;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.api.reactor.ISteamReactorComponent;
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
/*     */ 
/*     */ public class TickingSteamReactorLogic
/*     */   extends SteamReactorLogicBase
/*     */ {
/*  29 */   EnumMap<IReactorPlannerComponent.ReactorComponentType, Set<Integer>> typeToSlots = new EnumMap<>(IReactorPlannerComponent.ReactorComponentType.class);
/*  30 */   TileEntityReactorPlanner.ReactorBackup backup = new TileEntityReactorPlanner.ReactorBackup();
/*  31 */   int[] replacedItems = new int[54];
/*  32 */   SteamReactorPrediction prediction = new SteamReactorPrediction();
/*     */ 
/*     */ 
/*     */   
/*     */   public TickingSteamReactorLogic(TileEntityReactorPlanner tile) {
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
/*     */   public void doReactorTick() {
/*  66 */     this.ticksDone++;
/*  67 */     this.ticksLeft -= (this.producing || !this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.FuelRod)) ? 1 : 0;
/*  68 */     boolean finished = isFinished();
/*  69 */     if (finished)
/*     */     {
/*  71 */       updateTanks();
/*     */     }
/*  73 */     this.production = 0.0F;
/*  74 */     this.maxHeat = 10000;
/*  75 */     this.explosionEffect = 1.0F;
/*  76 */     for (int pass = 0; pass < 2; pass++) {
/*     */       
/*  78 */       for (int y = 0; y < 6; y++) {
/*     */         
/*  80 */         for (int x = 0; x < 9; x++) {
/*     */           
/*  82 */           ItemStack thing = getItemAt(x, y);
/*  83 */           if (thing != null && thing.func_77973_b() instanceof ISteamReactorComponent) {
/*     */             
/*  85 */             ISteamReactorComponent comp = (ISteamReactorComponent)thing.func_77973_b();
/*  86 */             comp.processTick(this, thing, x, y, (pass == 0), (this.ticksDone % 20 == 0 && pass == 1));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  91 */     for (int i = 0; i < 54; i++) {
/*     */       
/*  93 */       if (func_70301_a(i) != null && !(func_70301_a(i).func_77973_b() instanceof ic2.api.reactor.IReactorComponent))
/*     */       {
/*  95 */         func_70299_a(i, null);
/*     */       }
/*     */     } 
/*  98 */     if (!finished)
/*     */     {
/* 100 */       updateTanks();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFinished() {
/* 107 */     for (int i = 0; i < 54; i++) {
/*     */       
/* 109 */       if (this.replacedItems[i] > 0)
/*     */       {
/* 111 */         StackUtil.addToolTip(this.inv[i], EnumChatFormatting.AQUA + StatCollector.func_74837_a("container.reactorplannerInfo.replacedTimes.name", new Object[] { Integer.valueOf(this.replacedItems[i]) }));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/* 119 */     super.validate();
/* 120 */     this.prediction = new SteamReactorPrediction();
/* 121 */     this.typeToSlots.clear();
/* 122 */     this.backup = new TileEntityReactorPlanner.ReactorBackup();
/* 123 */     this.backup.reactorSize = this.planner.reactorSize;
/* 124 */     this.backup.hasBackup = true;
/* 125 */     this.replacedItems = new int[54];
/* 126 */     this.ticksDone = 0;
/* 127 */     this.ticksLeft = 0;
/* 128 */     this.maxTick = 0;
/* 129 */     this.currentHeat = 0;
/* 130 */     this.maxHeat = 10000;
/* 131 */     this.explosionEffect = 1.0F;
/* 132 */     this.producing = true;
/* 133 */     this.production = 0.0F;
/* 134 */     this.isValid = true;
/* 135 */     this.isExploded = false;
/* 136 */     this.collecting = false;
/* 137 */     this.didComponentBreak = false;
/* 138 */     this.custom = false;
/* 139 */     this.fuelPulses = 0;
/* 140 */     this.reEnrichPulses = 0; int i;
/* 141 */     for (i = 0; i < 54; i++) {
/*     */       
/* 143 */       this.backup.items[i] = ItemStack.func_77944_b(this.inv[i]);
/* 144 */       addToType(PlannerRegistry.getTypeByItem(this.inv[i]), i);
/*     */     } 
/* 146 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.FuelRod)) {
/*     */       
/* 148 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.FuelRod)) {
/*     */         
/* 150 */         IReactorPlannerComponent comp = getComponent(slot.intValue());
/* 151 */         if (comp != null) {
/*     */           
/* 153 */           this.maxTick = Math.max(this.maxTick, (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.MaxDurability, this.inv[slot.intValue()]) ? comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.MaxDurability).func_150287_d() : comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.MaxDurability, this.inv[slot.intValue()]).func_150287_d()) * 20);
/*     */           
/* 155 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.HeatProduction, this.inv[slot.intValue()])) { this.prediction.heatPerTick += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.HeatProduction).func_150287_d(); }
/* 156 */           else { this.prediction.heatPerTick += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.HeatProduction, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 158 */           this.production = 0.0F;
/* 159 */           this.fuelPulses = 0;
/* 160 */           this.reEnrichPulses = 0;
/* 161 */           this.collecting = true;
/* 162 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.EnergyProduction, this.inv[slot.intValue()])) { comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.EnergyProduction).func_150288_h(); }
/* 163 */           else { comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.EnergyProduction, this.inv[slot.intValue()]).func_150288_h(); }
/* 164 */            this.prediction.totalFuelRodPulses += this.fuelPulses;
/* 165 */           this.prediction.totalReEnrichtingPulses += this.reEnrichPulses;
/* 166 */           this.collecting = false;
/* 167 */           this.fuelPulses = 0;
/* 168 */           this.reEnrichPulses = 0;
/* 169 */           this.production = 0.0F;
/*     */           
/* 171 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.RodAmount, this.inv[slot.intValue()])) { this.prediction.totalCellCount += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.RodAmount).func_150287_d(); }
/* 172 */           else { this.prediction.totalCellCount += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.RodAmount, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 174 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.PulseAmount, this.inv[slot.intValue()])) { this.prediction.totalInternalFuelPulses += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.PulseAmount).func_150287_d(); }
/* 175 */           else { this.prediction.totalInternalFuelPulses += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.PulseAmount, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 177 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.EnergyProduction, this.inv[slot.intValue()])) { addExplosionEffect(comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.EnergyProduction).func_150288_h()); continue; }
/* 178 */            addExplosionEffect(comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.EnergyProduction, this.inv[slot.intValue()]).func_150288_h());
/*     */         } 
/*     */       } 
/*     */       
/* 182 */       this.fuelPulses = 0;
/* 183 */       this.reEnrichPulses = 0;
/* 184 */       this.production = 0.0F;
/* 185 */       this.collecting = false;
/*     */     } 
/* 187 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.Vent))
/*     */     {
/* 189 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.Vent)) {
/*     */         
/* 191 */         IReactorPlannerComponent comp = getComponent(slot.intValue());
/* 192 */         if (comp != null) {
/*     */           
/* 194 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.SelfCooling, this.inv[slot.intValue()])) { this.prediction.coolingPerTick += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.SelfCooling).func_150287_d(); }
/* 195 */           else { this.prediction.coolingPerTick += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.SelfCooling, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 197 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.ReactorCooling, this.inv[slot.intValue()])) { this.prediction.reactorCoolingPerTick += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.ReactorCooling).func_150287_d(); continue; }
/* 198 */            this.prediction.reactorCoolingPerTick += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.ReactorCooling, this.inv[slot.intValue()]).func_150287_d();
/*     */         } 
/*     */       } 
/*     */     }
/* 202 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.VentSpread))
/*     */     {
/* 204 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.VentSpread)) {
/*     */         
/* 206 */         IReactorPlannerComponent comp = getComponent(slot.intValue());
/* 207 */         if (comp != null) {
/*     */           
/* 209 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.PartCooling, this.inv[slot.intValue()])) { this.prediction.coolingPerTick += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.PartCooling).func_150287_d(); }
/* 210 */           else { this.prediction.coolingPerTick += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.PartCooling, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 212 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.ReactorCooling, this.inv[slot.intValue()])) { this.prediction.reactorCoolingPerTick += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.ReactorCooling).func_150287_d(); continue; }
/* 213 */            this.prediction.reactorCoolingPerTick += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.ReactorCooling, this.inv[slot.intValue()]).func_150287_d();
/*     */         } 
/*     */       } 
/*     */     }
/* 217 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.Plating))
/*     */     {
/* 219 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.Plating)) {
/*     */         
/* 221 */         IReactorPlannerComponent comp = getComponent(slot.intValue());
/* 222 */         if (comp != null) {
/*     */           
/* 224 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.ReactorMaxHeat, this.inv[slot.intValue()])) { this.maxHeat += comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.ReactorMaxHeat).func_150287_d(); }
/* 225 */           else { this.maxHeat += comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.ReactorMaxHeat, this.inv[slot.intValue()]).func_150287_d(); }
/*     */           
/* 227 */           if (comp.isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat.ReactorEEM, this.inv[slot.intValue()])) { addExplosionEffect(comp.getReactorStat((IReactor)this, slot.intValue() % 9, slot.intValue() / 9, this.inv[slot.intValue()], IReactorPlannerComponent.ReactorComponentStat.ReactorEEM).func_150287_d()); continue; }
/* 228 */            addExplosionEffect(comp.getReactorStat(IReactorPlannerComponent.ReactorComponentStat.ReactorEEM, this.inv[slot.intValue()]).func_150287_d());
/*     */         } 
/*     */       } 
/*     */     }
/* 232 */     for (i = 0; i < 54; i++)
/*     */     {
/* 234 */       this.inv[i] = ItemStack.func_77944_b(this.backup.items[i]);
/*     */     }
/* 236 */     this.ticksLeft = this.maxTick;
/* 237 */     TileEntityReactorPlanner.UserSettings settings = this.planner.getUserSettings();
/* 238 */     if (settings.maxTicks != 0) {
/*     */       
/* 240 */       this.ticksLeft = settings.maxTicks;
/* 241 */       this.backup.customTicks = this.ticksLeft;
/* 242 */       this.custom = true;
/* 243 */       this.maxTick = this.ticksLeft;
/*     */     } 
/* 245 */     finishCalculations();
/* 246 */     if (this.maxTick != 0) {
/*     */       
/* 248 */       this.prediction.totalHeatProduced = this.prediction.heatPerTick * this.maxTick;
/* 249 */       this.prediction.totalWaterConsumed = Math.min(this.prediction.totalHeatProduced / 40L, (this.prediction.coolingPerTick * this.maxTick));
/* 250 */       this.prediction.totalSteamProduced = this.prediction.totalWaterConsumed * 160L;
/* 251 */       this.prediction.steamPerTick = (float)(this.prediction.totalSteamProduced / this.maxTick);
/*     */     } 
/* 253 */     this.currentHeat = Math.min(this.maxHeat - 100, settings.startingHeat);
/* 254 */     this.backup.customHeat = this.currentHeat;
/* 255 */     this.backup.customTicks = this.maxTick;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ReactorLogicBase.IReactorPrediction createPrediction() {
/* 261 */     return this.prediction;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityReactorPlanner.ReactorBackup createBackup() {
/* 267 */     if (this.ticksDone > 0)
/*     */     {
/* 269 */       reset();
/*     */     }
/* 271 */     TileEntityReactorPlanner.ReactorBackup toSave = new TileEntityReactorPlanner.ReactorBackup();
/* 272 */     toSave.hasBackup = true;
/* 273 */     toSave.isSteam = isSteamLogic();
/* 274 */     for (int i = 0; i < 54; i++)
/*     */     {
/* 276 */       toSave.items[i] = ItemStack.func_77944_b(this.inv[i]);
/*     */     }
/* 278 */     TileEntityReactorPlanner.UserSettings settings = this.planner.getUserSettings();
/* 279 */     if (settings.maxTicks != 0)
/*     */     {
/* 281 */       toSave.customTicks = settings.maxTicks;
/*     */     }
/* 283 */     toSave.reactorSize = this.planner.reactorSize;
/* 284 */     toSave.customHeat = settings.startingHeat;
/* 285 */     return toSave;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void restoreFromBackup(TileEntityReactorPlanner.ReactorBackup toLoad) {
/* 291 */     NBTTagCompound nbt = new NBTTagCompound();
/* 292 */     toLoad.writeToNBT(nbt);
/* 293 */     this.backup.readFromNBT((NBTTagCompound)nbt.func_74737_b());
/* 294 */     this.isValid = false;
/* 295 */     this.isExploded = false;
/* 296 */     this.inv = new ItemStack[54];
/* 297 */     for (int i = 0; i < 54; i++)
/*     */     {
/* 299 */       this.inv[i] = ItemStack.func_77944_b(toLoad.items[i]);
/*     */     }
/* 301 */     TileEntityReactorPlanner.UserSettings settings = this.planner.getUserSettings();
/* 302 */     settings.maxTicks = toLoad.customTicks;
/* 303 */     settings.startingHeat = toLoad.customHeat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 309 */     super.clear();
/* 310 */     this.backup.items = new ItemStack[54];
/* 311 */     this.backup.customHeat = 0;
/* 312 */     this.backup.customTicks = 0;
/* 313 */     this.backup.hasBackup = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 319 */     super.reset();
/* 320 */     this.isValid = false;
/* 321 */     this.isExploded = false;
/* 322 */     this.didComponentBreak = false;
/* 323 */     this.inv = new ItemStack[54];
/* 324 */     this.planner.reactorSize = this.backup.reactorSize;
/* 325 */     this.ticksDone = 0;
/* 326 */     this.ticksLeft = 0;
/* 327 */     this.maxTick = 0;
/* 328 */     this.currentHeat = 0;
/* 329 */     this.maxHeat = 10000;
/* 330 */     this.explosionEffect = 1.0F;
/* 331 */     this.production = 0.0F;
/* 332 */     this.producing = false;
/* 333 */     for (int i = 0; i < 54; i++)
/*     */     {
/* 335 */       this.inv[i] = ItemStack.func_77944_b(this.backup.items[i]);
/*     */     }
/* 337 */     if (this.custom) {
/*     */       
/* 339 */       TileEntityReactorPlanner.UserSettings settings = this.planner.getUserSettings();
/* 340 */       settings.maxTicks = this.backup.customTicks;
/* 341 */       settings.startingHeat = this.backup.customHeat;
/*     */     } 
/* 343 */     this.custom = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTickRate() {
/* 349 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSizeUpdate() {
/* 355 */     super.onSizeUpdate();
/* 356 */     int size = getReactorSize();
/* 357 */     for (int x = size; x < 9; x++) {
/*     */       
/* 359 */       for (int y = 0; y < 6; y++)
/*     */       {
/* 361 */         this.backup.items[y * 9 + x] = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 369 */     super.writeToNBT(nbt);
/* 370 */     this.backup.writeToNBT(getTag(nbt, "Backup"));
/* 371 */     nbt.func_74783_a("Replacements", this.replacedItems);
/* 372 */     NBTTagList list = new NBTTagList();
/* 373 */     for (Map.Entry<IReactorPlannerComponent.ReactorComponentType, Set<Integer>> entry : this.typeToSlots.entrySet()) {
/*     */       
/* 375 */       NBTTagCompound data = new NBTTagCompound();
/* 376 */       data.func_74774_a("Key", (byte)((IReactorPlannerComponent.ReactorComponentType)entry.getKey()).ordinal());
/* 377 */       data.func_74783_a("Value", MathUtil.fromIntegerToInt(entry.getValue()));
/* 378 */       list.func_74742_a((NBTBase)data);
/*     */     } 
/* 380 */     nbt.func_74782_a("TypeSorting", (NBTBase)list);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 386 */     super.readFromNBT(nbt);
/* 387 */     this.backup.readFromNBT(nbt.func_74775_l("Backup"));
/* 388 */     this.replacedItems = nbt.func_74759_k("Replacements");
/* 389 */     if (this.replacedItems.length != 54)
/*     */     {
/* 391 */       this.replacedItems = new int[54];
/*     */     }
/* 393 */     NBTTagList list = nbt.func_150295_c("TypeSorting", 10);
/* 394 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/* 396 */       NBTTagCompound entry = list.func_150305_b(i);
/* 397 */       addMass(entry.func_74762_e("Key"), entry.func_74759_k("Value"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readStateFromNBT(NBTTagCompound nbt) {
/* 404 */     super.readStateFromNBT(nbt);
/* 405 */     this.replacedItems = nbt.func_74759_k("Replacements");
/* 406 */     if (this.replacedItems.length != 54)
/*     */     {
/* 408 */       this.replacedItems = new int[54];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStateToNBT(NBTTagCompound nbt) {
/* 415 */     super.writeStateToNBT(nbt);
/* 416 */     nbt.func_74783_a("Replacements", this.replacedItems);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToType(IReactorPlannerComponent.ReactorComponentType type, int slot) {
/* 421 */     if (type == null) {
/*     */       return;
/*     */     }
/*     */     
/* 425 */     Set<Integer> slots = this.typeToSlots.get(type);
/* 426 */     if (slots == null) {
/*     */       
/* 428 */       slots = new LinkedHashSet<>();
/* 429 */       this.typeToSlots.put(type, slots);
/*     */     } 
/* 431 */     slots.add(Integer.valueOf(slot));
/*     */   }
/*     */ 
/*     */   
/*     */   private void addMass(int key, int[] value) {
/* 436 */     if (value.length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 440 */     IReactorPlannerComponent.ReactorComponentType[] array = IReactorPlannerComponent.ReactorComponentType.values();
/* 441 */     if (array.length > key)
/*     */     {
/* 443 */       this.typeToSlots.put(array[key], new LinkedHashSet<>(MathUtil.fromIntToInteger(value)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Integer> requireReset() {
/* 449 */     Set<Integer> result = new LinkedHashSet<>(54);
/* 450 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.Conensator))
/*     */     {
/* 452 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.Conensator)) {
/*     */         
/* 454 */         IReactorPlannerComponent iReactorPlannerComponent = getComponent(slot.intValue());
/* 455 */         if (iReactorPlannerComponent == null || !iReactorPlannerComponent.canStoreHeat((IReactor)this, this.inv[slot.intValue()], slot.intValue() % 9, slot.intValue() / 9))
/*     */         {
/* 457 */           result.add(slot);
/*     */         }
/*     */       } 
/*     */     }
/* 461 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.Reflection))
/*     */     {
/* 463 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.Reflection)) {
/*     */         
/* 465 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 467 */           result.add(slot);
/*     */         }
/*     */       } 
/*     */     }
/* 471 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.IsotopeCell))
/*     */     {
/* 473 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.IsotopeCell)) {
/*     */         
/* 475 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 477 */           result.add(slot);
/*     */         }
/*     */       } 
/*     */     }
/* 481 */     if (this.ticksLeft > 0 && this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.FuelRod))
/*     */     {
/* 483 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.FuelRod)) {
/*     */         
/* 485 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 487 */           result.add(slot);
/*     */         }
/*     */       } 
/*     */     }
/* 491 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetSlots(Set<Integer> slots) {
/* 496 */     for (Integer slot : slots) {
/*     */       
/* 498 */       this.inv[slot.intValue()] = ItemStack.func_77944_b(this.backup.items[slot.intValue()]);
/* 499 */       this.replacedItems[slot.intValue()] = this.replacedItems[slot.intValue()] + 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComponentBroken() {
/* 505 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.CoolantCell))
/*     */     {
/* 507 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.CoolantCell)) {
/*     */         
/* 509 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 511 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 515 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.Vent))
/*     */     {
/* 517 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.Vent)) {
/*     */         
/* 519 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 521 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 525 */     if (this.typeToSlots.containsKey(IReactorPlannerComponent.ReactorComponentType.HeatSwitch))
/*     */     {
/* 527 */       for (Integer slot : this.typeToSlots.get(IReactorPlannerComponent.ReactorComponentType.HeatSwitch)) {
/*     */         
/* 529 */         if (this.inv[slot.intValue()] == null)
/*     */         {
/* 531 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 535 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addExplosionEffect(float value) {
/* 540 */     if (value > 0.0F && value < 1.0F) {
/*     */       
/* 542 */       this.prediction.boomMod *= value;
/*     */     }
/*     */     else {
/*     */       
/* 546 */       this.prediction.boomPower += value;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void finishCalculations() {
/* 552 */     this.prediction.totalExplosionPower = this.prediction.boomPower * this.explosionEffect * this.prediction.boomMod;
/* 553 */     this.prediction.breeder = (this.prediction.totalReEnrichtingPulses > 0);
/* 554 */     this.prediction.efficency = this.prediction.totalFuelRodPulses / this.prediction.totalCellCount;
/* 555 */     this.prediction.totalEfficency = (this.prediction.totalFuelRodPulses + this.prediction.totalReEnrichtingPulses) / this.prediction.totalCellCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SteamReactorPrediction
/*     */     implements ReactorLogicBase.IReactorPrediction
/*     */   {
/*     */     public long totalWaterConsumed;
/*     */     
/*     */     public long totalSteamProduced;
/*     */     
/*     */     public long totalHeatProduced;
/*     */     
/*     */     public float steamPerTick;
/*     */     
/*     */     public int heatPerTick;
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
/* 581 */     float boomPower = 10.0F;
/* 582 */     float boomMod = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void read(DataInput stream) {
/*     */       try {
/* 589 */         this.totalWaterConsumed = stream.readLong();
/* 590 */         this.totalSteamProduced = stream.readLong();
/* 591 */         this.totalHeatProduced = stream.readLong();
/* 592 */         this.steamPerTick = stream.readFloat();
/* 593 */         this.heatPerTick = stream.readInt();
/* 594 */         this.coolingPerTick = stream.readInt();
/* 595 */         this.reactorCoolingPerTick = stream.readInt();
/* 596 */         this.efficency = stream.readFloat();
/* 597 */         this.totalEfficency = stream.readFloat();
/* 598 */         this.breeder = stream.readBoolean();
/*     */       }
/* 600 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(DataOutput stream) {
/*     */       try {
/* 610 */         stream.writeLong(this.totalWaterConsumed);
/* 611 */         stream.writeLong(this.totalSteamProduced);
/* 612 */         stream.writeLong(this.totalHeatProduced);
/* 613 */         stream.writeFloat(this.steamPerTick);
/* 614 */         stream.writeInt(this.heatPerTick);
/* 615 */         stream.writeInt(this.coolingPerTick);
/* 616 */         stream.writeInt(this.reactorCoolingPerTick);
/* 617 */         stream.writeFloat(this.efficency);
/* 618 */         stream.writeFloat(this.totalEfficency);
/* 619 */         stream.writeBoolean(this.breeder);
/*     */       
/*     */       }
/* 622 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void readFromNBT(NBTTagCompound nbt) {
/* 630 */       this.totalWaterConsumed = nbt.func_74763_f("TotalWater");
/* 631 */       this.totalSteamProduced = nbt.func_74763_f("TotalSteam");
/* 632 */       this.totalHeatProduced = nbt.func_74763_f("TotalHeat");
/* 633 */       this.heatPerTick = nbt.func_74762_e("HeatProduction");
/* 634 */       this.coolingPerTick = nbt.func_74762_e("Coolant");
/* 635 */       this.reactorCoolingPerTick = nbt.func_74762_e("ReactorCoolant");
/* 636 */       this.totalExplosionPower = nbt.func_74760_g("TotalExplosionEffect");
/* 637 */       this.efficency = nbt.func_74760_g("Efficency");
/* 638 */       this.totalEfficency = nbt.func_74760_g("TotalEfficency");
/* 639 */       this.breeder = nbt.func_74767_n("Breeder");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeToNBT(NBTTagCompound nbt) {
/* 645 */       nbt.func_74772_a("TotalWater", this.totalWaterConsumed);
/* 646 */       nbt.func_74772_a("TotalSteam", this.totalSteamProduced);
/* 647 */       nbt.func_74772_a("TotalHeat", this.totalHeatProduced);
/* 648 */       nbt.func_74768_a("HeatProduction", this.heatPerTick);
/* 649 */       nbt.func_74768_a("Coolant", this.coolingPerTick);
/* 650 */       nbt.func_74768_a("ReactorCoolant", this.reactorCoolingPerTick);
/* 651 */       nbt.func_74776_a("TotalExplosionEffect", this.totalExplosionPower);
/* 652 */       nbt.func_74776_a("Efficency", this.efficency);
/* 653 */       nbt.func_74776_a("TotalEfficency", this.totalEfficency);
/* 654 */       nbt.func_74757_a("Breeder", this.breeder);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSteam() {
/* 660 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {
/* 666 */       this.totalWaterConsumed = 0L;
/* 667 */       this.totalSteamProduced = 0L;
/* 668 */       this.totalHeatProduced = 0L;
/* 669 */       this.heatPerTick = 0;
/* 670 */       this.coolingPerTick = 0;
/* 671 */       this.reactorCoolingPerTick = 0;
/* 672 */       this.efficency = 0.0F;
/* 673 */       this.totalEfficency = 0.0F;
/* 674 */       this.breeder = false;
/* 675 */       this.totalFuelRodPulses = 0;
/* 676 */       this.totalInternalFuelPulses = 0;
/* 677 */       this.totalCellCount = 0;
/* 678 */       this.totalReEnrichtingPulses = 0;
/* 679 */       this.totalExplosionPower = 0.0F;
/* 680 */       this.boomPower = 10.0F;
/* 681 */       this.boomMod = 1.0F;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\TickingSteamReactorLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */