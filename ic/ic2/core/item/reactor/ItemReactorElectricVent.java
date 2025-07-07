/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ICustomDamageItem;
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.util.IExtraData;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagFloat;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemReactorElectricVent
/*     */   extends ItemIC2
/*     */   implements ICustomDamageItem, IReactorPlannerComponent, IExtraData
/*     */ {
/*  36 */   Map<Integer, VentProperty> properties = new HashMap<>();
/*     */   
/*     */   public ItemReactorElectricVent() {
/*  39 */     super(0);
/*  40 */     func_77627_a(true);
/*  41 */     func_77625_d(1);
/*  42 */     func_77655_b("itemElectricVent");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  48 */     this.properties.put(Integer.valueOf(0), new VentProperty(48, 12, 0, "i3", 7, "reactorElectricVent"));
/*  49 */     this.properties.put(Integer.valueOf(1), new VentProperty(49, 10, 10, "i3", 8, "reactorElectricVentCore"));
/*  50 */     this.properties.put(Integer.valueOf(2), new VentProperty(50, 40, 72, "i3", 9, "reactorElectricVentGold"));
/*  51 */     this.properties.put(Integer.valueOf(3), new VentProperty(51, 24, 0, "i3", 10, "reactorElectricVentDiamond"));
/*     */     
/*  53 */     Ic2Items.reactorElectricVent = new ItemStack((Item)this, 1, 0);
/*  54 */     Ic2Items.reactorElectricVentCore = new ItemStack((Item)this, 1, 1);
/*  55 */     Ic2Items.reactorElectricVentGold = new ItemStack((Item)this, 1, 2);
/*  56 */     Ic2Items.reactorElectricVentDiamond = new ItemStack((Item)this, 1, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> p_77624_3_, boolean p_77624_4_) {
/*  63 */     p_77624_3_.add(StatCollector.func_74838_a("itemInfo.electricHeatVent.name"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item p_150895_1_, CreativeTabs p_150895_2_, List<ItemStack> list) {
/*  70 */     for (Integer ints : this.properties.keySet())
/*     */     {
/*  72 */       list.add(new ItemStack((Item)this, 1, ints.intValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/*  80 */     VentProperty prop = this.properties.get(Integer.valueOf(par1));
/*  81 */     if (prop == null)
/*     */     {
/*  83 */       return super.func_77617_a(par1);
/*     */     }
/*  85 */     return prop.getIcon();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack par1) {
/*  91 */     VentProperty prop = this.properties.get(Integer.valueOf(par1.func_77960_j()));
/*  92 */     if (prop != null)
/*     */     {
/*  94 */       return prop.getUnloclaizedName();
/*     */     }
/*  96 */     return super.func_77667_c(par1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxCustomDamage(ItemStack stack) {
/* 102 */     return 10000;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCustomDamage(ItemStack stack) {
/* 108 */     return StackUtil.getOrCreateNbtData(stack).func_74762_e("Heat");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomDamage(ItemStack stack, int damage) {
/* 114 */     StackUtil.getOrCreateNbtData(stack).func_74768_a("Heat", damage);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getDisplayDamage(ItemStack stack) {
/* 121 */     return getCustomDamage(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDamaged(ItemStack stack) {
/* 127 */     return (getCustomDamage(stack) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDurabilityForDisplay(ItemStack stack) {
/* 133 */     return getCustomDamage(stack) / getMaxCustomDamage(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean showDurabilityBar(ItemStack stack) {
/* 139 */     return (getCustomDamage(stack) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src) {
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   class VentProperty
/*     */   {
/*     */     int id;
/*     */     int self;
/*     */     int reactor;
/*     */     String sprite;
/*     */     int iconPlace;
/*     */     String unlocalizedName;
/*     */     
/*     */     public VentProperty(int par1, int par2, int par3, String par4, int par5, String par6) {
/* 159 */       this.id = par1;
/* 160 */       this.self = par2;
/* 161 */       this.reactor = par3;
/* 162 */       this.sprite = par4;
/* 163 */       this.iconPlace = par5;
/* 164 */       this.unlocalizedName = par6;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getUnloclaizedName() {
/* 169 */       return "item." + this.unlocalizedName;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getID() {
/* 174 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSelfVent() {
/* 179 */       return this.self;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getReactorVent() {
/* 184 */       return this.reactor;
/*     */     }
/*     */ 
/*     */     
/*     */     public IIcon getIcon() {
/* 189 */       return Ic2Icons.getTexture(this.sprite)[this.iconPlace];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
/* 197 */     if (heatrun) {
/*     */       
/* 199 */       boolean produce = reactor.produceEnergy();
/* 200 */       VentProperty prop = this.properties.get(Integer.valueOf(yourStack.func_77960_j()));
/* 201 */       if (prop.getReactorVent() > 0) {
/*     */ 
/*     */         
/* 204 */         int reactorDrain = reactor.getHeat(), rheat = reactorDrain;
/* 205 */         int maxHeat = produce ? prop.getReactorVent() : (prop.getReactorVent() / 2);
/* 206 */         if (reactorDrain > maxHeat)
/*     */         {
/* 208 */           reactorDrain = maxHeat;
/*     */         }
/* 210 */         rheat -= reactorDrain;
/* 211 */         if ((reactorDrain = alterHeat(reactor, yourStack, x, y, reactorDrain)) > 0) {
/*     */           return;
/*     */         }
/*     */         
/* 215 */         reactor.setHeat(rheat);
/*     */       } 
/* 217 */       if (produce)
/*     */       {
/* 219 */         reactor.addOutput(-(prop.getSelfVent() * 0.005F));
/*     */       }
/* 221 */       alterHeat(reactor, yourStack, x, y, produce ? -prop.getSelfVent() : (-prop.getSelfVent() / 2));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 228 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 234 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 240 */     return getMaxCustomDamage(yourStack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 246 */     return getCustomDamage(yourStack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
/* 252 */     int myHeat = getCustomDamage(yourStack);
/* 253 */     myHeat += heat;
/* 254 */     if (myHeat > getMaxCustomDamage(yourStack)) {
/*     */       
/* 256 */       reactor.setItemAt(x, y, null);
/* 257 */       heat = getMaxCustomDamage(yourStack) - myHeat + 1;
/*     */     }
/*     */     else {
/*     */       
/* 261 */       if (myHeat < 0) {
/*     */         
/* 263 */         heat = myHeat;
/* 264 */         myHeat = 0;
/*     */       }
/*     */       else {
/*     */         
/* 268 */         heat = 0;
/*     */       } 
/* 270 */       setCustomDamage(yourStack, myHeat);
/*     */     } 
/* 272 */     return heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
/* 278 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getSubParts() {
/* 284 */     ItemStack[] array = new ItemStack[this.properties.size()];
/* 285 */     int i = 0;
/* 286 */     for (Integer ints : this.properties.keySet()) {
/*     */       
/* 288 */       array[i] = new ItemStack((Item)this, 1, ints.intValue());
/* 289 */       i++;
/*     */     } 
/* 291 */     return array;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSubParts() {
/* 297 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getReactorPart() {
/* 303 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getID(ItemStack stack) {
/* 309 */     VentProperty prop = this.properties.get(Integer.valueOf(stack.func_77960_j()));
/* 310 */     if (prop != null)
/*     */     {
/* 312 */       return (short)prop.getID();
/*     */     }
/* 314 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 320 */     return IReactorPlannerComponent.ReactorComponentType.Vent;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 326 */     VentProperty prop = this.properties.get(Integer.valueOf(par2.func_77960_j()));
/* 327 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.SelfCooling)
/*     */     {
/* 329 */       return (NBTBase.NBTPrimitive)new NBTTagInt(prop.getSelfVent());
/*     */     }
/* 331 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.ReactorCooling)
/*     */     {
/* 333 */       return (NBTBase.NBTPrimitive)new NBTTagInt(prop.getReactorVent());
/*     */     }
/* 335 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.EnergyUsage)
/*     */     {
/* 337 */       return (NBTBase.NBTPrimitive)new NBTTagFloat(prop.getSelfVent() * 0.005F * IC2.energyGeneratorNuclear);
/*     */     }
/* 339 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 345 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactor par1, int x, int y, ItemStack item, IReactorPlannerComponent.ReactorComponentStat stat) {
/* 351 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorType getReactorInfo(ItemStack stack) {
/* 357 */     return IReactorPlannerComponent.ReactorType.Reactor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IReactorPlannerComponent.ReactorComponentStat> getExtraStats(ItemStack stack) {
/* 363 */     List<IReactorPlannerComponent.ReactorComponentStat> list = new ArrayList<>();
/* 364 */     list.add(IReactorPlannerComponent.ReactorComponentStat.EnergyUsage);
/* 365 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorElectricVent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */