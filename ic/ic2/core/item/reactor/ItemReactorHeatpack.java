/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorComponent;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMiner;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class ItemReactorHeatpack
/*     */   extends ItemIC2
/*     */   implements IReactorComponent, IReactorPlannerComponent
/*     */ {
/*     */   public int maxPer;
/*     */   public int heatPer;
/*     */   
/*     */   public ItemReactorHeatpack(int index, int maxper, int heatper) {
/*  23 */     super(index);
/*  24 */     this.maxPer = maxper;
/*  25 */     this.heatPer = heatper;
/*  26 */     setSpriteID("i3");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
/*  32 */     heat(reactor, yourStack.field_77994_a, x + 1, y);
/*  33 */     heat(reactor, yourStack.field_77994_a, x - 1, y);
/*  34 */     heat(reactor, yourStack.field_77994_a, x, y + 1);
/*  35 */     heat(reactor, yourStack.field_77994_a, x, y - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void heat(IReactor reactor, int stacksize, int x, int y) {
/*  40 */     int want = this.maxPer * stacksize;
/*  41 */     if (reactor.getHeat() >= want) {
/*     */       return;
/*     */     }
/*     */     
/*  45 */     ItemStack stack = reactor.getItemAt(x, y);
/*  46 */     if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
/*     */       
/*  48 */       IReactorComponent comp = (IReactorComponent)stack.func_77973_b();
/*  49 */       if (comp.canStoreHeat(reactor, stack, x, y)) {
/*     */         
/*  51 */         int add = this.heatPer * stacksize;
/*  52 */         int curr = comp.getCurrentHeat(reactor, stack, x, y);
/*  53 */         if (add > want - curr)
/*     */         {
/*  55 */           add = want - curr;
/*     */         }
/*  57 */         if (add > 0)
/*     */         {
/*  59 */           comp.alterHeat(reactor, stack, x, y, add);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  80 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  86 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
/*  92 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
/*  98 */     return (yourStack.field_77994_a / 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getSubParts() {
/* 104 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSubParts() {
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getReactorPart() {
/* 116 */     return new ItemStack((Item)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 122 */     return IReactorPlannerComponent.ReactorComponentType.HeatPack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 128 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.HeatProduction)
/*     */     {
/* 130 */       return (NBTBase.NBTPrimitive)new NBTTagInt(par2.field_77994_a * 2);
/*     */     }
/* 132 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 138 */     return (par1 == IReactorPlannerComponent.ReactorComponentStat.HeatProduction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactor par1, int x, int y, ItemStack item, IReactorPlannerComponent.ReactorComponentStat stat) {
/* 144 */     if (stat == IReactorPlannerComponent.ReactorComponentStat.HeatProduction) {
/*     */       
/* 146 */       int sides = 0;
/* 147 */       for (ForgeDirection dir : TileEntityMiner.validDirs) {
/*     */         
/* 149 */         int newX = x + dir.offsetX;
/* 150 */         int newY = y + dir.offsetZ;
/* 151 */         ItemStack stack = par1.getItemAt(newX, newY);
/* 152 */         if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
/*     */           
/* 154 */           IReactorComponent comp = (IReactorComponent)stack.func_77973_b();
/* 155 */           if (comp.canStoreHeat(par1, stack, x, y))
/*     */           {
/* 157 */             sides++;
/*     */           }
/*     */         } 
/*     */       } 
/* 161 */       return (NBTBase.NBTPrimitive)new NBTTagInt(item.field_77994_a * 2 * sides);
/*     */     } 
/* 163 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getID(ItemStack stack) {
/* 169 */     return 17;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorType getReactorInfo(ItemStack stack) {
/* 175 */     return IReactorPlannerComponent.ReactorType.Reactor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IReactorPlannerComponent.ReactorComponentStat> getExtraStats(ItemStack stack) {
/* 181 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorHeatpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */