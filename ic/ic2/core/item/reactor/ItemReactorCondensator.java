/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.core.item.ItemGradualInt;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ 
/*     */ public class ItemReactorCondensator
/*     */   extends ItemGradualInt implements IReactorPlannerComponent {
/*     */   boolean lapis;
/*     */   
/*     */   public ItemReactorCondensator(int index, int maxdmg, boolean lap) {
/*  17 */     super(index, maxdmg + 1);
/*  18 */     setSpriteID("i3");
/*  19 */     this.lapis = lap;
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
/*  30 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  36 */     return (getDamage(yourStack) + 1 < func_77612_l());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  42 */     return func_77612_l();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  48 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
/*  54 */     int can = func_77612_l() - getDamage(yourStack) + 1;
/*  55 */     if (can > heat)
/*     */     {
/*  57 */       can = heat;
/*     */     }
/*  59 */     heat -= can;
/*  60 */     setDamage(yourStack, getDamage(yourStack) + can);
/*  61 */     return heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
/*  67 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getSubParts() {
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSubParts() {
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getReactorPart() {
/*  85 */     return new ItemStack((Item)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/*  91 */     return IReactorPlannerComponent.ReactorComponentType.Conensator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/*  97 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.HeatStorage)
/*     */     {
/*  99 */       return (NBTBase.NBTPrimitive)new NBTTagInt(func_77612_l() - 1);
/*     */     }
/* 101 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactor par1, int x, int y, ItemStack item, IReactorPlannerComponent.ReactorComponentStat stat) {
/* 113 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getID(ItemStack stack) {
/* 119 */     return (short)(this.lapis ? 16 : 15);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorType getReactorInfo(ItemStack stack) {
/* 125 */     return IReactorPlannerComponent.ReactorType.Both;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IReactorPlannerComponent.ReactorComponentStat> getExtraStats(ItemStack stack) {
/* 131 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorCondensator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */