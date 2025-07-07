/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorPlanner;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.api.reactor.ISteamReactor;
/*     */ import ic2.api.reactor.ISteamReactorComponent;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.item.ItemGradual;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ 
/*     */ 
/*     */ public class ItemReactorDepletedUranium
/*     */   extends ItemGradual
/*     */   implements ISteamReactorComponent, IReactorPlannerComponent
/*     */ {
/*     */   public ItemReactorDepletedUranium(int index) {
/*  22 */     super(index);
/*  23 */     setSpriteID("i3");
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
/*  34 */     if (reactor instanceof IReactorPlanner) {
/*     */       
/*  36 */       IReactorPlanner planner = (IReactorPlanner)reactor;
/*  37 */       if (planner.isCollecting())
/*     */       {
/*  39 */         planner.addReEnrichPulse();
/*     */       }
/*     */     } 
/*  42 */     if (reactor instanceof ISteamReactor) {
/*     */       
/*  44 */       if (heatrun) {
/*     */         
/*  46 */         int i = yourStack.func_77960_j() - 1 - reactor.getHeat() / 3000;
/*  47 */         if (i <= 0) {
/*     */           
/*  49 */           reactor.setItemAt(youX, youY, new ItemStack(Ic2Items.reEnrichedUraniumCell.func_77973_b()));
/*     */         }
/*     */         else {
/*     */           
/*  53 */           yourStack.func_77964_b(i);
/*     */         } 
/*     */       } 
/*  56 */       return true;
/*     */     } 
/*  58 */     int myLevel = yourStack.func_77960_j() - 1 - reactor.getHeat() / 3000;
/*  59 */     if (myLevel <= 0) {
/*     */       
/*  61 */       reactor.setItemAt(youX, youY, new ItemStack(Ic2Items.reEnrichedUraniumCell.func_77973_b()));
/*     */     }
/*     */     else {
/*     */       
/*  65 */       yourStack.func_77964_b(myLevel);
/*     */     } 
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  79 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  85 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
/*  91 */     return heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
/*  97 */     return 0.0F;
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
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSubParts() {
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getReactorPart() {
/* 121 */     return new ItemStack((Item)this, 1, func_77612_l());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 127 */     return IReactorPlannerComponent.ReactorComponentType.IsotopeCell;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 133 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.MaxDurability)
/*     */     {
/* 135 */       return (NBTBase.NBTPrimitive)new NBTTagInt(func_77612_l());
/*     */     }
/* 137 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactor par1, int x, int y, ItemStack item, IReactorPlannerComponent.ReactorComponentStat stat) {
/* 149 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getID(ItemStack stack) {
/* 155 */     return 36;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorType getReactorInfo(ItemStack stack) {
/* 161 */     return IReactorPlannerComponent.ReactorType.Both;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IReactorPlannerComponent.ReactorComponentStat> getExtraStats(ItemStack stack) {
/* 167 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorDepletedUranium.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */