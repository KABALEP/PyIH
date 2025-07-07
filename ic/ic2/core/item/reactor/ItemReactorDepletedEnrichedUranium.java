/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ICustomDamageItem;
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorPlanner;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.api.reactor.ISteamReactor;
/*     */ import ic2.api.reactor.ISteamReactorComponent;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.util.IExtraData;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemReactorDepletedEnrichedUranium
/*     */   extends ItemIC2
/*     */   implements ICustomDamageItem, ISteamReactorComponent, IReactorPlannerComponent, IExtraData
/*     */ {
/*     */   public ItemReactorDepletedEnrichedUranium(int index) {
/*  36 */     super(index);
/*  37 */     func_77627_a(true);
/*  38 */     func_77656_e(10000);
/*  39 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/*  46 */     return Ic2Icons.getTexture(this.spriteID)[this.iconIndex + par1];
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemReactorEnrichUranium.UraniumType getUranType(ItemStack par1) {
/*  51 */     return ItemReactorEnrichUranium.UraniumType.values()[par1.func_77960_j()];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  57 */     Ic2Items.reactorRedstoneIsotopeCell = new ItemStack((Item)this, 1, ItemReactorEnrichUranium.UraniumType.Redstone.ordinal());
/*  58 */     Ic2Items.reactorBlazeIsotopeCell = new ItemStack((Item)this, 1, ItemReactorEnrichUranium.UraniumType.Blaze.ordinal());
/*  59 */     Ic2Items.reactorEnderPearlIsotopeCell = new ItemStack((Item)this, 1, ItemReactorEnrichUranium.UraniumType.EnderPearl.ordinal());
/*  60 */     Ic2Items.reactorNetherStarIsotopeCell = new ItemStack((Item)this, 1, ItemReactorEnrichUranium.UraniumType.NetherStar.ordinal());
/*  61 */     Ic2Items.reactorCharcoalIsotopeCell = new ItemStack((Item)this, 1, ItemReactorEnrichUranium.UraniumType.Charcoal.ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack item) {
/*  67 */     ItemReactorEnrichUranium.UraniumType uran = getUranType(item);
/*  68 */     switch (uran) {
/*     */       
/*     */       case Redstone:
/*  71 */         return "item.itemDepletedRedstoneEnrichedUraniumCell";
/*     */       case Blaze:
/*  73 */         return "item.itemDepletedBlazeEnrichedUraniumCell";
/*     */       case EnderPearl:
/*  75 */         return "item.itemDepletedEnderPearlEnrichedUraniumCell";
/*     */       case NetherStar:
/*  77 */         return "item.itemDepletedNetherStarEnrichedUraniumCell";
/*     */       case Charcoal:
/*  79 */         return "item.itemDepletedCharcoalEnrichedUraniumCell";
/*     */     } 
/*  81 */     return super.func_77667_c(item);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDurabilityForDisplay(ItemStack stack) {
/*  87 */     return getCustomDamage(stack) / getMaxCustomDamage(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean showDurabilityBar(ItemStack stack) {
/*  93 */     return (getCustomDamage(stack) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCustomDamage(ItemStack stack) {
/*  99 */     return StackUtil.getOrCreateNbtData(stack).func_74762_e("CustomDamage");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxCustomDamage(ItemStack stack) {
/* 105 */     int meta = stack.func_77960_j();
/* 106 */     if (meta < (ItemReactorEnrichUranium.UraniumType.values()).length)
/*     */     {
/* 108 */       return ItemReactorEnrichUranium.UraniumType.values()[meta].getMaxDamage();
/*     */     }
/* 110 */     return getMaxDamage(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDamaged(ItemStack stack) {
/* 116 */     return (getCustomDamage(stack) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getDisplayDamage(ItemStack stack) {
/* 123 */     return getCustomDamage(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomDamage(ItemStack stack, int damage) {
/* 129 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/* 130 */     nbt.func_74768_a("CustomDamage", damage);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src) {
/* 136 */     setCustomDamage(stack, getCustomDamage(stack) + damage);
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 149 */     if (reactor instanceof IReactorPlanner) {
/*     */       
/* 151 */       IReactorPlanner planner = (IReactorPlanner)reactor;
/* 152 */       if (planner.isCollecting())
/*     */       {
/* 154 */         planner.addReEnrichPulse();
/*     */       }
/*     */     } 
/* 157 */     if (reactor instanceof ISteamReactor) {
/*     */       
/* 159 */       if (heatrun) {
/*     */         
/* 161 */         int i = getCustomDamage(yourStack) - 1 - reactor.getHeat() / 3000;
/* 162 */         if (i <= 0) {
/*     */           
/* 164 */           ItemReactorEnrichUranium.UraniumType type = getUranType(yourStack);
/* 165 */           reactor.setItemAt(youX, youY, type.getReEnriched());
/*     */         }
/*     */         else {
/*     */           
/* 169 */           setCustomDamage(yourStack, i);
/*     */         } 
/*     */       } 
/* 172 */       return true;
/*     */     } 
/* 174 */     int myLevel = getCustomDamage(yourStack) - 1 - reactor.getHeat() / 3000;
/* 175 */     if (myLevel <= 0) {
/*     */       
/* 177 */       reactor.setItemAt(youX, youY, new ItemStack(Ic2Items.reEnrichedUraniumCell.func_77973_b()));
/*     */     }
/*     */     else {
/*     */       
/* 181 */       setCustomDamage(yourStack, myLevel);
/*     */     } 
/* 183 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 195 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 201 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
/* 207 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
/* 213 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getSubParts() {
/* 219 */     ItemStack[] items = new ItemStack[(ItemReactorEnrichUranium.UraniumType.values()).length];
/* 220 */     for (int i = 0; i < items.length; i++)
/*     */     {
/* 222 */       items[i] = new ItemStack((Item)this, 1, i);
/*     */     }
/* 224 */     return items;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSubParts() {
/* 230 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getReactorPart() {
/* 236 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getID(ItemStack stack) {
/* 242 */     ItemReactorEnrichUranium.UraniumType type = getUranType(stack);
/* 243 */     switch (type) {
/*     */       
/*     */       case Redstone:
/* 246 */         return 43;
/*     */       case Blaze:
/* 248 */         return 44;
/*     */       case EnderPearl:
/* 250 */         return 45;
/*     */       case NetherStar:
/* 252 */         return 46;
/*     */       case Charcoal:
/* 254 */         return 47;
/*     */     } 
/* 256 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 262 */     return IReactorPlannerComponent.ReactorComponentType.IsotopeCell;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 268 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.MaxDurability)
/*     */     {
/* 270 */       return (NBTBase.NBTPrimitive)new NBTTagInt(func_77612_l());
/*     */     }
/* 272 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 278 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactor par1, int x, int y, ItemStack item, IReactorPlannerComponent.ReactorComponentStat stat) {
/* 284 */     return nulltag;
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
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item p_150895_1_, CreativeTabs p_150895_2_, List<ItemStack> list) {
/* 297 */     for (ItemReactorEnrichUranium.UraniumType uran : ItemReactorEnrichUranium.UraniumType.values())
/*     */     {
/* 299 */       list.add(new ItemStack((Item)this, 1, uran.ordinal()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorType getReactorInfo(ItemStack stack) {
/* 306 */     return IReactorPlannerComponent.ReactorType.Both;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IReactorPlannerComponent.ReactorComponentStat> getExtraStats(ItemStack stack) {
/* 312 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorDepletedEnrichedUranium.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */