/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorComponent;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.api.reactor.ISteamReactor;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ 
/*     */ 
/*     */ public class ItemReactorHeatSwitch
/*     */   extends ItemReactorHeatStorage
/*     */ {
/*     */   public int switchSide;
/*     */   public int switchReactor;
/*     */   
/*     */   public ItemReactorHeatSwitch(int index, int heatStorage, int switchside, int switchreactor) {
/*  20 */     super(index, heatStorage);
/*  21 */     this.switchSide = switchside;
/*  22 */     this.switchReactor = switchreactor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
/*  27 */     int myHeat = 0;
/*     */     
/*  29 */     ArrayList<ItemStackCoord> heatAcceptors = new ArrayList<>();
/*     */     
/*  31 */     double med = getCurrentHeat(reactor, yourStack, x, y) / getMaxHeat(reactor, yourStack, x, y);
/*  32 */     int c = 1;
/*  33 */     if (this.switchReactor > 0) {
/*     */       
/*  35 */       c++;
/*  36 */       med += reactor.getHeat() / reactor.getMaxHeat();
/*     */     } 
/*  38 */     if (this.switchSide > 0) {
/*     */       
/*  40 */       med += checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
/*  41 */       med += checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
/*  42 */       med += checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
/*  43 */       med += checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
/*     */     } 
/*  45 */     med /= (c + heatAcceptors.size());
/*     */     
/*  47 */     if (this.switchSide > 0)
/*     */     {
/*  49 */       for (ItemStackCoord stackcoord : heatAcceptors) {
/*     */         
/*  51 */         IReactorComponent heatable = (IReactorComponent)stackcoord.stack.func_77973_b();
/*  52 */         int add = (int)(med * heatable.getMaxHeat(reactor, stackcoord.stack, stackcoord.x, stackcoord.y) - heatable.getCurrentHeat(reactor, stackcoord.stack, stackcoord.x, stackcoord.y));
/*     */         
/*  54 */         if (add > this.switchSide)
/*     */         {
/*  56 */           add = this.switchSide;
/*     */         }
/*  58 */         if (add < -this.switchSide)
/*     */         {
/*  60 */           add = -this.switchSide;
/*     */         }
/*     */         
/*  63 */         myHeat -= add;
/*  64 */         add = heatable.alterHeat(reactor, stackcoord.stack, stackcoord.x, stackcoord.y, add);
/*  65 */         myHeat += add;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  70 */     if (this.switchReactor > 0) {
/*     */       
/*  72 */       int add = (int)(med * reactor.getMaxHeat() - reactor.getHeat());
/*  73 */       if (add > this.switchReactor)
/*     */       {
/*  75 */         add = this.switchReactor;
/*     */       }
/*  77 */       if (add < -this.switchReactor)
/*     */       {
/*  79 */         add = -this.switchReactor;
/*     */       }
/*  81 */       myHeat -= add;
/*  82 */       reactor.setHeat(reactor.getHeat() + add);
/*     */     } 
/*     */     
/*  85 */     alterHeat(reactor, yourStack, x, y, myHeat);
/*     */   }
/*     */ 
/*     */   
/*     */   private double checkHeatAcceptor(IReactor reactor, int x, int y, ArrayList<ItemStackCoord> heatAcceptors) {
/*  90 */     ItemStack thing = reactor.getItemAt(x, y);
/*  91 */     if (thing != null && thing.func_77973_b() instanceof IReactorComponent) {
/*     */       
/*  93 */       IReactorComponent comp = (IReactorComponent)thing.func_77973_b();
/*  94 */       if (comp.canStoreHeat(reactor, thing, x, y)) {
/*     */         
/*  96 */         heatAcceptors.add(new ItemStackCoord(thing, x, y));
/*  97 */         double max = comp.getMaxHeat(reactor, thing, x, y);
/*  98 */         if (max <= 0.0D)
/*     */         {
/* 100 */           return 0.0D;
/*     */         }
/* 102 */         double cur = comp.getCurrentHeat(reactor, thing, x, y);
/* 103 */         return cur / max;
/*     */       } 
/*     */     } 
/* 106 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   private class ItemStackCoord
/*     */   {
/*     */     public ItemStack stack;
/*     */     public int x;
/*     */     public int y;
/*     */     
/*     */     public ItemStackCoord(ItemStack stack, int x, int y) {
/* 117 */       this.stack = stack;
/* 118 */       this.x = x;
/* 119 */       this.y = y;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processTick(ISteamReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun, boolean damageTick) {
/* 126 */     processChamber((IReactor)reactor, yourStack, x, y, heatrun);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 132 */     return IReactorPlannerComponent.ReactorComponentType.HeatSwitch;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 138 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.PartChange)
/*     */     {
/* 140 */       return (NBTBase.NBTPrimitive)new NBTTagInt(this.switchSide);
/*     */     }
/* 142 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.ReactorChange)
/*     */     {
/* 144 */       return (NBTBase.NBTPrimitive)new NBTTagInt(this.switchReactor);
/*     */     }
/* 146 */     return nulltag;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorHeatSwitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */