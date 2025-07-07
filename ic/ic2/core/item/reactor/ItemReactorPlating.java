/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.api.reactor.ISteamReactor;
/*     */ import ic2.api.reactor.ISteamReactorComponent;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagFloat;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ 
/*     */ public class ItemReactorPlating
/*     */   extends ItemIC2
/*     */   implements ISteamReactorComponent, IReactorPlannerComponent {
/*     */   public int maxHeatAdd;
/*     */   public float effectModifier;
/*     */   public short id;
/*     */   
/*     */   public ItemReactorPlating(int index, int maxheatadd, float effectmodifier) {
/*  23 */     super(index);
/*  24 */     this.maxHeatAdd = maxheatadd;
/*  25 */     this.effectModifier = effectmodifier;
/*  26 */     func_77625_d(1);
/*  27 */     setSpriteID("i3");
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemReactorPlating setID(int id) {
/*  32 */     this.id = (short)id;
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
/*  39 */     if (heatrun) {
/*     */       
/*  41 */       reactor.setMaxHeat(reactor.getMaxHeat() + this.maxHeatAdd);
/*  42 */       reactor.setHeatEffectModifier(reactor.getHeatEffectModifier() * this.effectModifier);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  61 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  67 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
/*  73 */     return heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
/*  79 */     if (this.effectModifier >= 1.0F)
/*     */     {
/*  81 */       return 0.0F;
/*     */     }
/*  83 */     return this.effectModifier;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processTick(ISteamReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun, boolean damageTick) {
/*  89 */     processChamber((IReactor)reactor, yourStack, x, y, heatrun);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getSubParts() {
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSubParts() {
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getReactorPart() {
/* 107 */     return new ItemStack((Item)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 113 */     return IReactorPlannerComponent.ReactorComponentType.Plating;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 119 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.ReactorMaxHeat)
/*     */     {
/* 121 */       return (NBTBase.NBTPrimitive)new NBTTagInt(this.maxHeatAdd);
/*     */     }
/* 123 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.ReactorEEM)
/*     */     {
/* 125 */       return (NBTBase.NBTPrimitive)new NBTTagFloat(this.effectModifier);
/*     */     }
/* 127 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactor par1, int x, int y, ItemStack item, IReactorPlannerComponent.ReactorComponentStat stat) {
/* 139 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getID(ItemStack stack) {
/* 145 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorType getReactorInfo(ItemStack stack) {
/* 151 */     return IReactorPlannerComponent.ReactorType.Both;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IReactorPlannerComponent.ReactorComponentStat> getExtraStats(ItemStack stack) {
/* 157 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorPlating.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */