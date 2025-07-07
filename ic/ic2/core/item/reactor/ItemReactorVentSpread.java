/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorComponent;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.api.reactor.ISteamReactor;
/*     */ import ic2.api.reactor.ISteamReactorComponent;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMiner;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class ItemReactorVentSpread
/*     */   extends ItemIC2
/*     */   implements ISteamReactorComponent, IReactorPlannerComponent {
/*     */   public int sideVent;
/*     */   
/*     */   public ItemReactorVentSpread(int index, int sidevent) {
/*  23 */     super(index);
/*  24 */     func_77625_d(1);
/*  25 */     this.sideVent = sidevent;
/*  26 */     setSpriteID("i3");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
/*  32 */     if (heatrun) {
/*     */       
/*  34 */       cool(reactor, x - 1, y);
/*  35 */       cool(reactor, x + 1, y);
/*  36 */       cool(reactor, x, y - 1);
/*  37 */       cool(reactor, x, y + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void cool(IReactor reactor, int x, int y) {
/*  43 */     ItemStack stack = reactor.getItemAt(x, y);
/*  44 */     if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
/*     */       
/*  46 */       IReactorComponent comp = (IReactorComponent)stack.func_77973_b();
/*  47 */       if (comp.canStoreHeat(reactor, stack, x, y))
/*     */       {
/*  49 */         comp.alterHeat(reactor, stack, x, y, -this.sideVent);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  69 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/*  75 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
/*  81 */     return heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
/*  87 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processTick(ISteamReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun, boolean damageTick) {
/*  93 */     processChamber((IReactor)reactor, yourStack, x, y, heatrun);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getSubParts() {
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSubParts() {
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getReactorPart() {
/* 111 */     return new ItemStack((Item)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 117 */     return IReactorPlannerComponent.ReactorComponentType.VentSpread;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 123 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.PartCooling)
/*     */     {
/* 125 */       return (NBTBase.NBTPrimitive)new NBTTagInt(this.sideVent);
/*     */     }
/* 127 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 133 */     return (par1 == IReactorPlannerComponent.ReactorComponentStat.PartCooling);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactor par1, int x, int y, ItemStack item, IReactorPlannerComponent.ReactorComponentStat stat) {
/* 139 */     if (stat == IReactorPlannerComponent.ReactorComponentStat.PartCooling) {
/*     */       
/* 141 */       int count = 0;
/* 142 */       int sides = 0;
/* 143 */       for (ForgeDirection dir : TileEntityMiner.validDirs) {
/*     */         
/* 145 */         int newX = x + dir.offsetX;
/* 146 */         int newY = y + dir.offsetZ;
/* 147 */         ItemStack stack = par1.getItemAt(newX, newY);
/* 148 */         if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
/*     */           
/* 150 */           IReactorComponent comp = (IReactorComponent)stack.func_77973_b();
/* 151 */           if (comp.canStoreHeat(par1, stack, x, y))
/*     */           {
/* 153 */             sides++;
/*     */           }
/*     */         } 
/*     */       } 
/* 157 */       return (NBTBase.NBTPrimitive)new NBTTagInt(this.sideVent * sides);
/*     */     } 
/* 159 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getID(ItemStack stack) {
/* 165 */     return 26;
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


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorVentSpread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */