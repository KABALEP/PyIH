/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorComponent;
/*     */ import ic2.api.reactor.IReactorPlanner;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.api.reactor.ISteamReactor;
/*     */ import ic2.api.reactor.ISteamReactorComponent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.item.ItemGradual;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagFloat;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ 
/*     */ 
/*     */ public class ItemReactorUranium
/*     */   extends ItemGradual
/*     */   implements ISteamReactorComponent, IReactorPlannerComponent
/*     */ {
/*     */   public int numberOfCells;
/*     */   
/*     */   public ItemReactorUranium(int index, int cells) {
/*  28 */     super(index);
/*  29 */     this.numberOfCells = cells;
/*  30 */     setSpriteID("i3");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
/*  36 */     if (!reactor.produceEnergy()) {
/*     */       return;
/*     */     }
/*     */     
/*  40 */     for (int iteration = 0; iteration < this.numberOfCells; iteration++) {
/*     */       
/*  42 */       int pulses = 1 + this.numberOfCells / 2;
/*  43 */       if (!heatrun) {
/*     */         
/*  45 */         for (int i = 0; i < pulses; i++)
/*     */         {
/*  47 */           acceptUraniumPulse(reactor, yourStack, yourStack, x, y, x, y, heatrun);
/*     */         }
/*  49 */         this; this; this; this; pulses += checkPulseable(reactor, x - 1, y, yourStack, x, y, heatrun) + checkPulseable(reactor, x + 1, y, yourStack, x, y, heatrun) + checkPulseable(reactor, x, y - 1, yourStack, x, y, heatrun) + checkPulseable(reactor, x, y + 1, yourStack, x, y, heatrun);
/*     */       }
/*     */       else {
/*     */         
/*  53 */         this; this; this; this; pulses += checkPulseable(reactor, x - 1, y, yourStack, x, y, heatrun) + checkPulseable(reactor, x + 1, y, yourStack, x, y, heatrun) + checkPulseable(reactor, x, y - 1, yourStack, x, y, heatrun) + checkPulseable(reactor, x, y + 1, yourStack, x, y, heatrun);
/*  54 */         int heat = sumUp(pulses) * 4;
/*  55 */         ArrayList<ItemStackCoord> heatAcceptors = new ArrayList<>();
/*  56 */         checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
/*  57 */         checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
/*  58 */         checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
/*  59 */         checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
/*  60 */         while (heatAcceptors.size() > 0 && heat > 0) {
/*     */           
/*  62 */           int dheat = heat / heatAcceptors.size();
/*  63 */           heat -= dheat;
/*  64 */           dheat = ((IReactorComponent)((ItemStackCoord)heatAcceptors.get(0)).stack.func_77973_b()).alterHeat(reactor, ((ItemStackCoord)heatAcceptors.get(0)).stack, ((ItemStackCoord)heatAcceptors.get(0)).x, ((ItemStackCoord)heatAcceptors.get(0)).y, dheat);
/*  65 */           heat += dheat;
/*  66 */           heatAcceptors.remove(0);
/*     */         } 
/*  68 */         if (heat > 0)
/*     */         {
/*  70 */           reactor.addHeat(heat);
/*     */         }
/*     */       } 
/*     */     } 
/*  74 */     if (yourStack.func_77960_j() >= func_77612_l() - 1) {
/*     */       
/*  76 */       if (IC2.random.nextInt(3) == 0)
/*     */       {
/*  78 */         reactor.setItemAt(x, y, new ItemStack(Ic2Items.nearDepletedUraniumCell.func_77973_b(), this.numberOfCells));
/*     */       }
/*     */       else
/*     */       {
/*  82 */         reactor.setItemAt(x, y, null);
/*     */       }
/*     */     
/*  85 */     } else if (heatrun) {
/*     */       
/*  87 */       yourStack.func_77964_b(yourStack.func_77960_j() + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int checkPulseable(IReactor reactor, int x, int y, ItemStack me, int mex, int mey, boolean heatrun) {
/*  93 */     ItemStack other = reactor.getItemAt(x, y);
/*  94 */     if (other != null && other.func_77973_b() instanceof IReactorComponent && ((IReactorComponent)other.func_77973_b()).acceptUraniumPulse(reactor, other, me, x, y, mex, mey, heatrun))
/*     */     {
/*  96 */       return 1;
/*     */     }
/*  98 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int sumUp(int x) {
/* 103 */     int sum = 0;
/* 104 */     for (int i = 1; i <= x; i++)
/*     */     {
/* 106 */       sum += i;
/*     */     }
/* 108 */     return sum;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkHeatAcceptor(IReactor reactor, int x, int y, ArrayList<ItemStackCoord> heatAcceptors) {
/* 113 */     ItemStack thing = reactor.getItemAt(x, y);
/* 114 */     if (thing != null && thing.func_77973_b() instanceof IReactorComponent && ((IReactorComponent)thing.func_77973_b()).canStoreHeat(reactor, thing, x, y))
/*     */     {
/* 116 */       heatAcceptors.add(new ItemStackCoord(thing, x, y));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 123 */     if (reactor instanceof IReactorPlanner) {
/*     */       
/* 125 */       IReactorPlanner planner = (IReactorPlanner)reactor;
/* 126 */       if (planner.isCollecting())
/*     */       {
/* 128 */         planner.addFuelPulse();
/*     */       }
/*     */     } 
/* 131 */     if (reactor instanceof ISteamReactor)
/*     */     {
/* 133 */       return true;
/*     */     }
/* 135 */     if (!heatrun)
/*     */     {
/* 137 */       reactor.addOutput(1.0F);
/*     */     }
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 151 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 157 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
/* 163 */     return heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
/* 169 */     return (2 * this.numberOfCells);
/*     */   }
/*     */ 
/*     */   
/*     */   static class ItemStackCoord
/*     */   {
/*     */     public ItemStack stack;
/*     */     public int x;
/*     */     public int y;
/*     */     
/*     */     public ItemStackCoord(ItemStack stack, int x, int y) {
/* 180 */       this.stack = stack;
/* 181 */       this.x = x;
/* 182 */       this.y = y;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processTick(ISteamReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun, boolean damageTick) {
/* 189 */     if (!reactor.produceEnergy()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 194 */     for (int iteration = 0; iteration < this.numberOfCells; iteration++) {
/*     */       
/* 196 */       int pulses = 1 + this.numberOfCells / 2;
/* 197 */       if (!heatrun) {
/*     */         
/* 199 */         for (int i = 0; i < pulses; i++)
/*     */         {
/* 201 */           acceptUraniumPulse((IReactor)reactor, yourStack, yourStack, x, y, x, y, heatrun);
/*     */         }
/* 203 */         this; this; this; this; pulses += checkPulseable((IReactor)reactor, x - 1, y, yourStack, x, y, damageTick) + checkPulseable((IReactor)reactor, x + 1, y, yourStack, x, y, damageTick) + checkPulseable((IReactor)reactor, x, y - 1, yourStack, x, y, damageTick) + checkPulseable((IReactor)reactor, x, y + 1, yourStack, x, y, damageTick);
/*     */       }
/*     */       else {
/*     */         
/* 207 */         this; this; this; this; pulses += checkPulseable((IReactor)reactor, x - 1, y, yourStack, x, y, damageTick) + checkPulseable((IReactor)reactor, x + 1, y, yourStack, x, y, damageTick) + checkPulseable((IReactor)reactor, x, y - 1, yourStack, x, y, damageTick) + checkPulseable((IReactor)reactor, x, y + 1, yourStack, x, y, damageTick);
/* 208 */         int heat = sumUp(pulses) * 4;
/* 209 */         ArrayList<ItemStackCoord> heatAcceptors = new ArrayList<>();
/* 210 */         checkHeatAcceptor((IReactor)reactor, x - 1, y, heatAcceptors);
/* 211 */         checkHeatAcceptor((IReactor)reactor, x + 1, y, heatAcceptors);
/* 212 */         checkHeatAcceptor((IReactor)reactor, x, y - 1, heatAcceptors);
/* 213 */         checkHeatAcceptor((IReactor)reactor, x, y + 1, heatAcceptors);
/* 214 */         while (heatAcceptors.size() > 0 && heat > 0) {
/*     */           
/* 216 */           int dheat = heat / heatAcceptors.size();
/* 217 */           heat -= dheat;
/* 218 */           dheat = ((IReactorComponent)((ItemStackCoord)heatAcceptors.get(0)).stack.func_77973_b()).alterHeat((IReactor)reactor, ((ItemStackCoord)heatAcceptors.get(0)).stack, ((ItemStackCoord)heatAcceptors.get(0)).x, ((ItemStackCoord)heatAcceptors.get(0)).y, dheat);
/* 219 */           heat += dheat;
/* 220 */           heatAcceptors.remove(0);
/*     */         } 
/* 222 */         if (heat > 0)
/*     */         {
/* 224 */           reactor.addHeat(heat);
/*     */         }
/*     */       } 
/*     */     } 
/* 228 */     if (damageTick)
/*     */     {
/* 230 */       if (yourStack.func_77960_j() >= func_77612_l() - 1) {
/*     */         
/* 232 */         if (IC2.random.nextInt(3) == 0)
/*     */         {
/* 234 */           reactor.setItemAt(x, y, new ItemStack(Ic2Items.nearDepletedUraniumCell.func_77973_b(), this.numberOfCells));
/*     */         }
/*     */         else
/*     */         {
/* 238 */           reactor.setItemAt(x, y, null);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 243 */         yourStack.func_77964_b(yourStack.func_77960_j() + 1);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getSubParts() {
/* 251 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSubParts() {
/* 257 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getID(ItemStack stack) {
/* 263 */     switch (this.numberOfCells) {
/*     */       case 1:
/* 265 */         return 0;
/* 266 */       case 2: return 1;
/* 267 */       case 4: return 2;
/*     */     } 
/* 269 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getReactorPart() {
/* 275 */     return new ItemStack((Item)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 281 */     return IReactorPlannerComponent.ReactorComponentType.FuelRod;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 287 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.HeatProduction) {
/*     */       
/* 289 */       int amount = 0;
/* 290 */       for (int i = 0; i < this.numberOfCells; i++)
/*     */       {
/* 292 */         amount += sumUp(1 + this.numberOfCells / 2) * 4;
/*     */       }
/* 294 */       return (NBTBase.NBTPrimitive)new NBTTagInt(amount);
/*     */     } 
/* 296 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.EnergyProduction) {
/*     */       
/* 298 */       int amount = 0;
/* 299 */       for (int i = 0; i < this.numberOfCells; i++) {
/*     */         
/* 301 */         int pulses = 1 + this.numberOfCells / 2;
/* 302 */         for (int z = 0; z < pulses; z++)
/*     */         {
/* 304 */           amount++;
/*     */         }
/*     */       } 
/* 307 */       return (NBTBase.NBTPrimitive)new NBTTagFloat(amount);
/*     */     } 
/* 309 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.MaxDurability)
/*     */     {
/* 311 */       return (NBTBase.NBTPrimitive)new NBTTagInt(func_77612_l());
/*     */     }
/* 313 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.ReactorEEM)
/*     */     {
/* 315 */       return (NBTBase.NBTPrimitive)new NBTTagFloat((2 * this.numberOfCells));
/*     */     }
/* 317 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.RodAmount)
/*     */     {
/* 319 */       return (NBTBase.NBTPrimitive)new NBTTagInt(this.numberOfCells);
/*     */     }
/* 321 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.PulseAmount)
/*     */     {
/* 323 */       return (NBTBase.NBTPrimitive)new NBTTagInt((1 + this.numberOfCells / 2) * this.numberOfCells);
/*     */     }
/* 325 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 331 */     return (par1 == IReactorPlannerComponent.ReactorComponentStat.EnergyProduction || par1 == IReactorPlannerComponent.ReactorComponentStat.HeatProduction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactor par1, int x, int y, ItemStack item, IReactorPlannerComponent.ReactorComponentStat stat) {
/* 337 */     if (stat == IReactorPlannerComponent.ReactorComponentStat.HeatProduction) {
/*     */       
/* 339 */       int amount = 0;
/* 340 */       for (int i = 0; i < this.numberOfCells; i++) {
/*     */         
/* 342 */         int pulses = 1 + this.numberOfCells / 2;
/* 343 */         this; this; this; this; pulses += checkPulseable(par1, x - 1, y, item, x, y, false) + checkPulseable(par1, x + 1, y, item, x, y, false) + checkPulseable(par1, x, y - 1, item, x, y, false) + checkPulseable(par1, x, y + 1, item, x, y, false);
/* 344 */         amount += sumUp(pulses) * 4;
/*     */       } 
/* 346 */       return (NBTBase.NBTPrimitive)new NBTTagInt(amount);
/*     */     } 
/* 348 */     if (stat == IReactorPlannerComponent.ReactorComponentStat.EnergyProduction) {
/*     */       
/* 350 */       for (int i = 0; i < this.numberOfCells; i++) {
/*     */         
/* 352 */         int pulses = 1 + this.numberOfCells / 2;
/* 353 */         for (int z = 0; z < pulses; z++)
/*     */         {
/* 355 */           acceptUraniumPulse(par1, item, item, x, y, x, y, false);
/*     */         }
/* 357 */         this; this; this; this; pulses += checkPulseable(par1, x - 1, y, item, x, y, false) + checkPulseable(par1, x + 1, y, item, x, y, false) + checkPulseable(par1, x, y - 1, item, x, y, false) + checkPulseable(par1, x, y + 1, item, x, y, false);
/*     */       } 
/* 359 */       return (NBTBase.NBTPrimitive)new NBTTagFloat(par1.getReactorEnergyOutput());
/*     */     } 
/* 361 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorType getReactorInfo(ItemStack stack) {
/* 367 */     return IReactorPlannerComponent.ReactorType.Both;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IReactorPlannerComponent.ReactorComponentStat> getExtraStats(ItemStack stack) {
/* 373 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorUranium.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */