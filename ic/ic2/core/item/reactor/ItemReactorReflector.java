/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorPlanner;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.api.reactor.ISteamReactor;
/*     */ import ic2.api.reactor.ISteamReactorComponent;
/*     */ import ic2.core.item.ItemGradualInt;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagFloat;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ 
/*     */ public class ItemReactorReflector
/*     */   extends ItemGradualInt
/*     */   implements ISteamReactorComponent, IReactorPlannerComponent {
/*     */   boolean adv;
/*     */   
/*     */   public ItemReactorReflector(int index, int maxDamage, boolean advance) {
/*  22 */     super(index, maxDamage);
/*  23 */     setSpriteID("i3");
/*  24 */     this.adv = advance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/*  35 */     if (reactor instanceof IReactorPlanner) {
/*     */       
/*  37 */       IReactorPlanner planner = (IReactorPlanner)reactor;
/*  38 */       if (planner.isCollecting())
/*     */       {
/*  40 */         planner.addFuelPulse();
/*     */       }
/*     */     } 
/*  43 */     if (reactor instanceof ISteamReactor) {
/*     */       
/*  45 */       if (heatrun)
/*     */       {
/*  47 */         if (getDamage(yourStack) + 1 >= func_77612_l()) {
/*     */           
/*  49 */           reactor.setItemAt(youX, youY, null);
/*     */         }
/*     */         else {
/*     */           
/*  53 */           setDamage(yourStack, getDamage(yourStack) + 1);
/*     */         } 
/*     */       }
/*  56 */       return true;
/*     */     } 
/*  58 */     if (!heatrun) {
/*     */       
/*  60 */       reactor.addOutput(1.0F);
/*     */ 
/*     */     
/*     */     }
/*  64 */     else if (getDamage(yourStack) + 1 >= func_77612_l()) {
/*     */       
/*  66 */       reactor.setItemAt(youX, youY, null);
/*     */     }
/*     */     else {
/*     */       
/*  70 */       setDamage(yourStack, getDamage(yourStack) + 1);
/*     */     } 
/*     */     
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  85 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  91 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
/*  97 */     return heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
/* 103 */     return -1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processTick(ISteamReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun, boolean damageTick) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getSubParts() {
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSubParts() {
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getReactorPart() {
/* 127 */     return new ItemStack((Item)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 133 */     return IReactorPlannerComponent.ReactorComponentType.Reflection;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 139 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.MaxDurability)
/*     */     {
/* 141 */       return (NBTBase.NBTPrimitive)new NBTTagInt(func_77612_l());
/*     */     }
/* 143 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.ReactorEEM)
/*     */     {
/* 145 */       return (NBTBase.NBTPrimitive)new NBTTagFloat(-1.0F);
/*     */     }
/* 147 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactor par1, int x, int y, ItemStack item, IReactorPlannerComponent.ReactorComponentStat stat) {
/* 159 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getID(ItemStack stack) {
/* 165 */     return (short)(this.adv ? 35 : 34);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorType getReactorInfo(ItemStack stack) {
/* 171 */     return IReactorPlannerComponent.ReactorType.Both;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IReactorPlannerComponent.ReactorComponentStat> getExtraStats(ItemStack stack) {
/* 177 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorReflector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */