/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.api.reactor.ISteamReactor;
/*     */ import ic2.api.reactor.ISteamReactorComponent;
/*     */ import ic2.core.fluids.IC2Fluid;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagFloat;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ 
/*     */ public class ItemReactorVentSteam
/*     */   extends ItemReactorHeatStorage
/*     */   implements ISteamReactorComponent {
/*     */   public int selfVent;
/*     */   public int reactorVent;
/*     */   
/*     */   public ItemReactorVentSteam(int index, int heatStorage, int selfvent, int reactorvent) {
/*  33 */     super(index, heatStorage);
/*  34 */     this.selfVent = selfvent;
/*  35 */     this.reactorVent = reactorvent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processTick(ISteamReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun, boolean damageTick) {
/*  41 */     if (heatrun) {
/*     */       
/*  43 */       if (this.reactorVent > 0) {
/*     */ 
/*     */         
/*  46 */         int reactorDrain = reactor.getHeat(), rheat = reactorDrain;
/*  47 */         if (reactorDrain > this.reactorVent)
/*     */         {
/*  49 */           reactorDrain = this.reactorVent;
/*     */         }
/*  51 */         rheat -= reactorDrain;
/*  52 */         if ((reactorDrain = alterHeat((IReactor)reactor, yourStack, x, y, reactorDrain)) > 0) {
/*     */           return;
/*     */         }
/*     */         
/*  56 */         reactor.setHeat(rheat);
/*     */       } 
/*  58 */       int heat = getHeatOfStack(yourStack);
/*  59 */       if (heat < 100) {
/*     */         
/*  61 */         if (reactor.getWorld().func_82737_E() % 40L == 0L)
/*     */         {
/*  63 */           alterHeat((IReactor)reactor, yourStack, x, y, -1);
/*     */         }
/*     */         return;
/*     */       } 
/*  67 */       double water = getWater(yourStack);
/*  68 */       if (water <= 0.0D) {
/*     */         
/*  70 */         if (reactor.getWorld().func_82737_E() % 40L == 0L)
/*     */         {
/*  72 */           alterHeat((IReactor)reactor, yourStack, x, y, -1);
/*     */         }
/*     */         return;
/*     */       } 
/*  76 */       double heatStorage = getHeatStorage(yourStack);
/*  77 */       double lvl = this.selfVent * getHeatLevel(yourStack);
/*  78 */       lvl /= 40.0D;
/*  79 */       double consume = Math.min(water, lvl);
/*  80 */       if (consume <= 0.0D) {
/*     */         return;
/*     */       }
/*     */       
/*  84 */       int steam = (int)(160.0D * consume);
/*  85 */       FluidTank tank = reactor.getSteamTank();
/*  86 */       if (steam <= 0 || tank.getCapacity() - tank.getFluidAmount() < steam) {
/*     */         return;
/*     */       }
/*     */       
/*  90 */       tank.fill(new FluidStack((Fluid)IC2Fluid.fluids.get("steam"), steam), true);
/*  91 */       reactor.addOutput(steam / 1.6F / 2.0F);
/*  92 */       water -= consume;
/*  93 */       heatStorage += consume * 40.0D;
/*  94 */       int copy = -((int)heatStorage);
/*  95 */       copy -= alterHeat((IReactor)reactor, yourStack, x, y, copy);
/*  96 */       heatStorage += copy;
/*  97 */       setWater(yourStack, water);
/*  98 */       setHeatStorage(yourStack, heatStorage);
/*     */     }
/*     */     else {
/*     */       
/* 102 */       refill(reactor, yourStack);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getHeatLevel(ItemStack yourStack) {
/* 108 */     return getHeatOfStack(yourStack) / (func_77612_l() - 100.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   private void refill(ISteamReactor reactor, ItemStack yourStack) {
/* 113 */     double water = getWater(yourStack);
/* 114 */     if (water > this.selfVent - 1.0D) {
/*     */       return;
/*     */     }
/*     */     
/* 118 */     int needed = (int)(this.selfVent - water);
/* 119 */     FluidTank tank = reactor.getWaterTank();
/* 120 */     if (tank.getFluidAmount() <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 124 */     int min = Math.min(tank.getFluidAmount(), needed);
/* 125 */     FluidStack drained = tank.drain(min, true);
/* 126 */     if (drained != null) {
/*     */       
/* 128 */       water += drained.amount;
/* 129 */       setWater(yourStack, water);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getHeatOfStack(ItemStack stack) {
/* 135 */     return getDamage(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   private double getWater(ItemStack par1) {
/* 140 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 141 */     return nbt.func_74769_h("WaterBuffer");
/*     */   }
/*     */ 
/*     */   
/*     */   private void setWater(ItemStack par1, double par2) {
/* 146 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 147 */     nbt.func_74780_a("WaterBuffer", par2);
/*     */   }
/*     */ 
/*     */   
/*     */   private double getHeatStorage(ItemStack par1) {
/* 152 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(par1);
/* 153 */     return nbtData.func_74769_h("HeatStorage");
/*     */   }
/*     */ 
/*     */   
/*     */   private void setHeatStorage(ItemStack par1, double amount) {
/* 158 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(par1);
/* 159 */     nbtData.func_74780_a("HeatStorage", amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack par1, EntityPlayer par2, List<String> par3, boolean par4) {
/* 166 */     if (!GuiScreen.func_146272_n()) {
/*     */       
/* 168 */       par3.add(StatCollector.func_74837_a("itemInfo.pressShiftInfo.name", new Object[] { EnumChatFormatting.AQUA, EnumChatFormatting.GRAY }));
/*     */       return;
/*     */     } 
/* 171 */     int waterPer = (int)(getWater(par1) / this.selfVent * 100.0D);
/* 172 */     double effizens = (getHeatOfStack(par1) < 100) ? 0.0D : (getHeatLevel(par1) * 100.0D);
/* 173 */     double steamProduction = this.selfVent * effizens;
/* 174 */     steamProduction /= 100.0D;
/* 175 */     steamProduction /= 40.0D;
/* 176 */     steamProduction *= 160.0D;
/* 177 */     par3.add(StatCollector.func_74837_a("itemInfo.steamVentWater.name", new Object[] { waterPer + "%" }));
/* 178 */     par3.add(StatCollector.func_74837_a("itemInfo.steamVentHeat.name", new Object[] { (int)effizens + "%" }));
/* 179 */     par3.add(StatCollector.func_74837_a("itemInfo.steamVentSteam.name", new Object[] { (int)steamProduction + "~ mB" }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 185 */     return IReactorPlannerComponent.ReactorComponentType.Vent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 191 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.SelfCooling)
/*     */     {
/* 193 */       return (NBTBase.NBTPrimitive)new NBTTagInt(this.selfVent);
/*     */     }
/* 195 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.ReactorCooling)
/*     */     {
/* 197 */       return (NBTBase.NBTPrimitive)new NBTTagInt(this.reactorVent);
/*     */     }
/* 199 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.SteamProduction)
/*     */     {
/* 201 */       return (NBTBase.NBTPrimitive)new NBTTagFloat((this.selfVent * 4));
/*     */     }
/* 203 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.WaterConsumtion)
/*     */     {
/* 205 */       return (NBTBase.NBTPrimitive)new NBTTagFloat(this.selfVent / 40.0F);
/*     */     }
/* 207 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.WaterStorage)
/*     */     {
/* 209 */       return (NBTBase.NBTPrimitive)new NBTTagFloat(this.selfVent);
/*     */     }
/* 211 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorType getReactorInfo(ItemStack stack) {
/* 217 */     return IReactorPlannerComponent.ReactorType.SteamReactor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IReactorPlannerComponent.ReactorComponentStat> getExtraStats(ItemStack stack) {
/* 223 */     List<IReactorPlannerComponent.ReactorComponentStat> list = new ArrayList<>();
/* 224 */     list.add(IReactorPlannerComponent.ReactorComponentStat.WaterConsumtion);
/* 225 */     list.add(IReactorPlannerComponent.ReactorComponentStat.SteamProduction);
/* 226 */     list.add(IReactorPlannerComponent.ReactorComponentStat.WaterStorage);
/* 227 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorVentSteam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */