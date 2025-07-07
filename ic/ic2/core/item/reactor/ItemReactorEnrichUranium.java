/*     */ package ic2.core.item.reactor;
/*     */ 
/*     */ import com.google.common.math.DoubleMath;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ICustomDamageItem;
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.reactor.IReactorComponent;
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.api.reactor.ISteamReactor;
/*     */ import ic2.api.reactor.ISteamReactorComponent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.util.IExtraData;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.awt.Color;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagFloat;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemReactorEnrichUranium
/*     */   extends ItemIC2
/*     */   implements ISteamReactorComponent, IReactorPlannerComponent, ICustomDamageItem, IExtraData
/*     */ {
/*  42 */   public static int[] numberOfCells = new int[] { 1, 2, 4 };
/*  43 */   public static String[] stringOfCells = new String[] { "Simple", "Dual", "Quad" };
/*     */ 
/*     */   
/*     */   public ItemReactorEnrichUranium(int index) {
/*  47 */     super(index);
/*  48 */     setNoRepair();
/*  49 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int meta) {
/*  56 */     UraniumType type = UraniumType.values()[meta / 3];
/*  57 */     int cellCount = meta % 3;
/*  58 */     return Ic2Icons.getTexture("i3")[this.iconIndex + type.getTextureOffset() + cellCount];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack par1) {
/*  64 */     UraniumType type = getUranType(par1);
/*  65 */     int meta = par1.func_77960_j() % 3;
/*  66 */     return "item.reactorUranium" + type.name() + stringOfCells[meta];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
/*  72 */     if (!reactor.produceEnergy()) {
/*     */       return;
/*     */     }
/*     */     
/*  76 */     int count = getCellAmount(yourStack);
/*  77 */     UraniumType type = getUranType(yourStack);
/*  78 */     for (int iteration = 0; iteration < count; iteration++) {
/*     */       
/*  80 */       int pulses = (int)((1 + count / 2) * type.getPulseModifier());
/*  81 */       if (!heatrun) {
/*     */         int i;
/*  83 */         for (i = 0; i < pulses; i++)
/*     */         {
/*  85 */           acceptUraniumPulse(reactor, yourStack, yourStack, x, y, x, y, heatrun);
/*     */         }
/*  87 */         for (i = (int)type.getPulseModifier(); i > 0; i--)
/*     */         {
/*  89 */           for (int[] array : type.getPulseEffect())
/*     */           {
/*  91 */             pulses += checkPulseable(reactor, x + array[0], y + array[1], yourStack, x, y, heatrun, type);
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  97 */         for (int i = DoubleMath.roundToInt(type.getPulseModifier(), RoundingMode.DOWN); i > 0; i--) {
/*     */           
/*  99 */           for (int[] array : type.getPulseEffect())
/*     */           {
/* 101 */             pulses += checkPulseable(reactor, x + array[0], y + array[1], yourStack, x, y, heatrun, type);
/*     */           }
/*     */         } 
/* 104 */         int heat = (int)((sumUp(pulses) * 4) * type.getHeatModfier());
/* 105 */         ArrayList<ItemReactorUranium.ItemStackCoord> heatAcceptors = new ArrayList<>();
/* 106 */         for (int[] array : type.getPulseEffect())
/*     */         {
/* 108 */           checkHeatAcceptor(reactor, x + array[0], y + array[1], heatAcceptors);
/*     */         }
/* 110 */         while (heatAcceptors.size() > 0 && heat > 0) {
/*     */           
/* 112 */           int dheat = heat / heatAcceptors.size();
/* 113 */           heat -= dheat;
/* 114 */           dheat = ((IReactorComponent)((ItemReactorUranium.ItemStackCoord)heatAcceptors.get(0)).stack.func_77973_b()).alterHeat(reactor, ((ItemReactorUranium.ItemStackCoord)heatAcceptors.get(0)).stack, ((ItemReactorUranium.ItemStackCoord)heatAcceptors.get(0)).x, ((ItemReactorUranium.ItemStackCoord)heatAcceptors.get(0)).y, dheat);
/* 115 */           heat += dheat;
/* 116 */           heatAcceptors.remove(0);
/*     */         } 
/* 118 */         if (heat > 0)
/*     */         {
/* 120 */           reactor.addHeat(heat);
/*     */         }
/*     */       } 
/*     */     } 
/* 124 */     if (getCustomDamage(yourStack) >= getMaxCustomDamage(yourStack) - 1) {
/*     */       
/* 126 */       if (IC2.random.nextInt(3) == 0)
/*     */       {
/* 128 */         reactor.setItemAt(x, y, StackUtil.copyWithSize(type.getNearDepleted(), count));
/*     */       }
/*     */       else
/*     */       {
/* 132 */         reactor.setItemAt(x, y, null);
/*     */       }
/*     */     
/* 135 */     } else if (heatrun) {
/*     */       
/* 137 */       setCustomDamage(yourStack, getCustomDamage(yourStack) + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processTick(ISteamReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun, boolean damageTick) {
/* 145 */     if (!reactor.produceEnergy()) {
/*     */       return;
/*     */     }
/*     */     
/* 149 */     int count = getCellAmount(yourStack);
/* 150 */     UraniumType type = getUranType(yourStack);
/* 151 */     for (int iteration = 0; iteration < count; iteration++) {
/*     */       
/* 153 */       int pulses = (int)((1 + count / 2) * type.getPulseModifier());
/* 154 */       if (!heatrun) {
/*     */         int i;
/* 156 */         for (i = 0; i < pulses; i++)
/*     */         {
/* 158 */           acceptUraniumPulse((IReactor)reactor, yourStack, yourStack, x, y, x, y, heatrun);
/*     */         }
/* 160 */         for (i = DoubleMath.roundToInt(type.getPulseModifier(), RoundingMode.DOWN); i > 0; i--)
/*     */         {
/* 162 */           for (int[] array : type.getPulseEffect())
/*     */           {
/* 164 */             pulses += checkPulseable((IReactor)reactor, x + array[0], y + array[1], yourStack, x, y, damageTick, type);
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 170 */         for (int i = DoubleMath.roundToInt(type.getPulseModifier(), RoundingMode.DOWN); i > 0; i--) {
/*     */           
/* 172 */           for (int[] array : type.getPulseEffect())
/*     */           {
/* 174 */             pulses += checkPulseable((IReactor)reactor, x + array[0], y + array[1], yourStack, x, y, damageTick, type);
/*     */           }
/*     */         } 
/* 177 */         int heat = (int)((sumUp(pulses) * 4) * type.getHeatModfier());
/* 178 */         ArrayList<ItemReactorUranium.ItemStackCoord> heatAcceptors = new ArrayList<>();
/* 179 */         for (int[] array : type.getPulseEffect())
/*     */         {
/* 181 */           checkHeatAcceptor((IReactor)reactor, x + array[0], y + array[1], heatAcceptors);
/*     */         }
/* 183 */         while (heatAcceptors.size() > 0 && heat > 0) {
/*     */           
/* 185 */           int dheat = heat / heatAcceptors.size();
/* 186 */           heat -= dheat;
/* 187 */           dheat = ((IReactorComponent)((ItemReactorUranium.ItemStackCoord)heatAcceptors.get(0)).stack.func_77973_b()).alterHeat((IReactor)reactor, ((ItemReactorUranium.ItemStackCoord)heatAcceptors.get(0)).stack, ((ItemReactorUranium.ItemStackCoord)heatAcceptors.get(0)).x, ((ItemReactorUranium.ItemStackCoord)heatAcceptors.get(0)).y, dheat);
/* 188 */           heat += dheat;
/* 189 */           heatAcceptors.remove(0);
/*     */         } 
/* 191 */         if (heat > 0)
/*     */         {
/* 193 */           reactor.addHeat(heat);
/*     */         }
/*     */       } 
/*     */     } 
/* 197 */     if (damageTick)
/*     */     {
/* 199 */       if (getCustomDamage(yourStack) + 1 > getMaxCustomDamage(yourStack)) {
/*     */         
/* 201 */         if (IC2.random.nextInt(3) == 0)
/*     */         {
/* 203 */           reactor.setItemAt(x, y, StackUtil.copyWithSize(type.getNearDepleted(), count));
/*     */         }
/*     */         else
/*     */         {
/* 207 */           reactor.setItemAt(x, y, null);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 212 */         setCustomDamage(yourStack, getCustomDamage(yourStack) + 1);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 220 */     if (reactor instanceof ISteamReactor)
/*     */     {
/* 222 */       return true;
/*     */     }
/* 224 */     if (!heatrun) {
/*     */       
/* 226 */       UraniumType type = getUranType(yourStack);
/* 227 */       reactor.addOutput(type.getEUPerPulse());
/*     */     } 
/* 229 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 241 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
/* 247 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
/* 253 */     return heat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
/* 259 */     int amount = getCellAmount(yourStack);
/* 260 */     return getUranType(yourStack).getExplosionEffect() * amount;
/*     */   }
/*     */ 
/*     */   
/*     */   private int checkPulseable(IReactor reactor, int x, int y, ItemStack me, int mex, int mey, boolean heatrun, UraniumType type) {
/* 265 */     ItemStack other = reactor.getItemAt(x, y);
/* 266 */     if (other != null && other.func_77973_b() instanceof IReactorComponent && ((IReactorComponent)other.func_77973_b()).acceptUraniumPulse(reactor, other, me, x, y, mex, mey, heatrun))
/*     */     {
/* 268 */       return type.getPulseForConnection();
/*     */     }
/* 270 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int sumUp(int x) {
/* 275 */     int sum = 0;
/* 276 */     for (int i = 1; i <= x; i++)
/*     */     {
/* 278 */       sum += i;
/*     */     }
/* 280 */     return sum;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkHeatAcceptor(IReactor reactor, int x, int y, ArrayList<ItemReactorUranium.ItemStackCoord> heatAcceptors) {
/* 285 */     ItemStack thing = reactor.getItemAt(x, y);
/* 286 */     if (thing != null && thing.func_77973_b() instanceof IReactorComponent && ((IReactorComponent)thing.func_77973_b()).canStoreHeat(reactor, thing, x, y))
/*     */     {
/* 288 */       heatAcceptors.add(new ItemReactorUranium.ItemStackCoord(thing, x, y));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCellAmount(ItemStack par1) {
/* 294 */     int meta = par1.func_77960_j();
/* 295 */     return numberOfCells[meta % 3];
/*     */   }
/*     */ 
/*     */   
/*     */   public UraniumType getUranType(ItemStack par1) {
/* 300 */     int meta = par1.func_77960_j();
/* 301 */     return UraniumType.values()[meta / 3];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item p_150895_1_, CreativeTabs p_150895_2_, List<ItemStack> list) {
/* 308 */     for (UraniumType type : UraniumType.values()) {
/*     */       
/* 310 */       list.add(new ItemStack((Item)this, 1, type.getOffset()));
/* 311 */       list.add(new ItemStack((Item)this, 1, type.getOffset() + 1));
/* 312 */       list.add(new ItemStack((Item)this, 1, type.getOffset() + 2));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum UraniumType
/*     */   {
/* 320 */     Redstone(2, 1.0F, 8000, 0, 3, 2.0F, 2.0F, 0.5F, new ItemStack(Items.field_151137_ax)),
/* 321 */     Blaze(1, 1.0F, 10000, 3, 6, 8.0F, 1.0F, 4.0F, new ItemStack(Items.field_151072_bj)),
/* 322 */     EnderPearl(1, 1, 5000, 6, 9, 2.0F, 1.0F, 1.0F, new ItemStack(Items.field_151079_bi), new int[][] { { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 }, { 1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 } }),
/* 323 */     NetherStar(3, 5.0F, 20000, 9, 37, 2.0F, 2.0F, 1.2F, new ItemStack(Items.field_151156_bN)),
/* 324 */     Charcoal(1, 0.6F, 15000, 12, 40, 2.0F, 1.0F, 1.0F, new ItemStack(Items.field_151044_h, 1, 1))
/*     */     {
/*     */       public int getTextureOffset()
/*     */       {
/* 328 */         return 29;
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     int pulseConnection;
/*     */     
/*     */     float euPerPulse;
/*     */     
/*     */     int maxDamage;
/*     */     
/*     */     int offset;
/*     */     
/*     */     short id;
/*     */     float explosionEffect;
/*     */     float pulseModifier;
/*     */     float heatMod;
/*     */     List<int[]> pulseEffects;
/*     */     ItemStack item;
/*     */     
/*     */     UraniumType(int par1, float par2, int par3, int par4, int par5, float par6, float par7, float par8, ItemStack par9) {
/* 349 */       this.pulseConnection = par1;
/* 350 */       this.euPerPulse = par2;
/* 351 */       this.maxDamage = par3;
/* 352 */       this.offset = par4;
/* 353 */       this.id = (short)par5;
/* 354 */       this.explosionEffect = par6;
/* 355 */       this.pulseModifier = par7;
/* 356 */       this.heatMod = par8;
/* 357 */       this.item = par9;
/* 358 */       this.pulseEffects = (List)new ArrayList<>();
/* 359 */       this.pulseEffects.add(new int[] { -1, 0 });
/* 360 */       this.pulseEffects.add(new int[] { 1, 0 });
/* 361 */       this.pulseEffects.add(new int[] { 0, -1 });
/* 362 */       this.pulseEffects.add(new int[] { 0, 1 });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     UraniumType(int par1, int par2, int par3, int par4, int par5, float par6, float par7, float par8, ItemStack par9, int[][] par10) {
/* 368 */       this.pulseEffects.clear();
/* 369 */       for (int i = 0; i < par10.length; i++)
/*     */       {
/* 371 */         this.pulseEffects.add(par10[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public UraniumType setOffset(int par1) {
/* 377 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTextureOffset() {
/* 382 */       return this.offset;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOffset() {
/* 387 */       return this.offset;
/*     */     }
/*     */ 
/*     */     
/*     */     public short getID() {
/* 392 */       return this.id;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getPulseForConnection() {
/* 398 */       return this.pulseConnection;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float getExplosionEffect() {
/* 404 */       return this.explosionEffect;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float getPulseModifier() {
/* 410 */       return this.pulseModifier;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float getHeatModfier() {
/* 416 */       return this.heatMod;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public float getEUPerPulse() {
/* 422 */       return this.euPerPulse;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<int[]> getPulseEffect() {
/* 427 */       return this.pulseEffects;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMaxDamage() {
/* 433 */       return this.maxDamage;
/*     */     }
/*     */ 
/*     */     
/*     */     public Color getColor() {
/* 438 */       switch (this) {
/*     */         case Blaze:
/* 440 */           return new Color(15244039);
/* 441 */         case Charcoal: return new Color(3552822);
/* 442 */         case EnderPearl: return new Color(2338417);
/* 443 */         case NetherStar: return new Color(16772970);
/* 444 */         case Redstone: return new Color(16711680);
/*     */       } 
/* 446 */       return Color.GREEN;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getItem() {
/* 451 */       return this.item;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getIngot() {
/* 456 */       switch (this) {
/*     */         case Redstone:
/* 458 */           return Ic2Items.redstoneEnrichedUraniumIngot.func_77946_l();
/* 459 */         case Blaze: return Ic2Items.blazeEnrichedUraniumIngot.func_77946_l();
/* 460 */         case EnderPearl: return Ic2Items.enderpearlEnrichedUraniumIngot.func_77946_l();
/* 461 */         case NetherStar: return Ic2Items.netherstarEnrichedUraniumIngot.func_77946_l();
/* 462 */         case Charcoal: return Ic2Items.charcoalEnrichedUraniumIngot.func_77946_l();
/* 463 */       }  return Ic2Items.uraniumIngot.func_77946_l();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack getNearDepleted() {
/* 469 */       switch (this) {
/*     */         case Redstone:
/* 471 */           return Ic2Items.nearDepletedRedstoneEnrichedUraniumCell.func_77946_l();
/* 472 */         case Blaze: return Ic2Items.nearDepletedBlazeEnrichedUraniumCell.func_77946_l();
/* 473 */         case EnderPearl: return Ic2Items.nearDepletedEnderPearlEnrichedUraniumCell.func_77946_l();
/* 474 */         case NetherStar: return Ic2Items.nearDepletedNetherStarEnrichedUraniumCell.func_77946_l();
/* 475 */         case Charcoal: return Ic2Items.nearDepletedCharcoalEnrichedUraniumCell.func_77946_l();
/* 476 */       }  return Ic2Items.nearDepletedUraniumCell.func_77946_l();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack getIsotopic() {
/* 482 */       switch (this) {
/*     */         case Redstone:
/* 484 */           return Ic2Items.reactorRedstoneIsotopeCell.func_77946_l();
/* 485 */         case Blaze: return Ic2Items.reactorBlazeIsotopeCell.func_77946_l();
/* 486 */         case EnderPearl: return Ic2Items.reactorEnderPearlIsotopeCell.func_77946_l();
/* 487 */         case NetherStar: return Ic2Items.reactorNetherStarIsotopeCell.func_77946_l();
/* 488 */         case Charcoal: return Ic2Items.reactorCharcoalIsotopeCell.func_77946_l();
/* 489 */       }  return Ic2Items.reactorIsotopeCell.func_77946_l();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack getReEnriched() {
/* 495 */       switch (this) {
/*     */         case Redstone:
/* 497 */           return Ic2Items.reEnrichedRedstoneUraniumCell.func_77946_l();
/* 498 */         case Blaze: return Ic2Items.reEnrichedBlazeUraniumCell.func_77946_l();
/* 499 */         case EnderPearl: return Ic2Items.reEnrichedEnderPearlUraniumCell.func_77946_l();
/* 500 */         case NetherStar: return Ic2Items.reEnrichedNetherStarUraniumCell.func_77946_l();
/* 501 */         case Charcoal: return Ic2Items.reEnrichedCharcoalUraniumCell.func_77946_l();
/* 502 */       }  return Ic2Items.reEnrichedUraniumCell.func_77946_l();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack getSimpleRod() {
/* 508 */       switch (this) {
/*     */         case Redstone:
/* 510 */           return Ic2Items.reactorRedstoneEnrichedUraniumSimple.func_77946_l();
/* 511 */         case Blaze: return Ic2Items.reactorBlazeEnrichedUraniumSimple.func_77946_l();
/* 512 */         case EnderPearl: return Ic2Items.reactorEnderPearlEnrichedUraniumSimple.func_77946_l();
/* 513 */         case NetherStar: return Ic2Items.reactorNetherStarEnrichedUraniumSimple.func_77946_l();
/* 514 */         case Charcoal: return Ic2Items.reactorCharcoalEnrichedUraniumSimple.func_77946_l();
/* 515 */       }  return Ic2Items.reactorUraniumSimple.func_77946_l();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack getDualRod() {
/* 521 */       switch (this) {
/*     */         case Redstone:
/* 523 */           return Ic2Items.reactorRedstoneEnrichedUraniumDual.func_77946_l();
/* 524 */         case Blaze: return Ic2Items.reactorBlazeEnrichedUraniumDual.func_77946_l();
/* 525 */         case EnderPearl: return Ic2Items.reactorEnderPearlEnrichedUraniumDual.func_77946_l();
/* 526 */         case NetherStar: return Ic2Items.reactorNetherStarEnrichedUraniumDual.func_77946_l();
/* 527 */         case Charcoal: return Ic2Items.reactorCharcoalEnrichedUraniumDual.func_77946_l();
/* 528 */       }  return Ic2Items.reactorUraniumDual.func_77946_l();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack getQuadRod() {
/* 534 */       switch (this) {
/*     */         case Redstone:
/* 536 */           return Ic2Items.reactorRedstoneEnrichedUraniumQuad.func_77946_l();
/* 537 */         case Blaze: return Ic2Items.reactorBlazeEnrichedUraniumQuad.func_77946_l();
/* 538 */         case EnderPearl: return Ic2Items.reactorEnderPearlEnrichedUraniumQuad.func_77946_l();
/* 539 */         case NetherStar: return Ic2Items.reactorNetherStarEnrichedUraniumQuad.func_77946_l();
/* 540 */         case Charcoal: return Ic2Items.reactorCharcoalEnrichedUraniumQuad.func_77946_l();
/* 541 */       }  return Ic2Items.reactorUraniumQuad.func_77946_l();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack getNewIsotopic() {
/* 547 */       ItemStack item = getIsotopic();
/* 548 */       ICustomDamageItem custom = (ICustomDamageItem)item.func_77973_b();
/* 549 */       custom.setCustomDamage(item, 9999);
/* 550 */       return item;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDurabilityForDisplay(ItemStack stack) {
/* 557 */     return getCustomDamage(stack) / getMaxCustomDamage(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean showDurabilityBar(ItemStack stack) {
/* 563 */     return (getCustomDamage(stack) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCustomDamage(ItemStack stack) {
/* 569 */     return StackUtil.getOrCreateNbtData(stack).func_74762_e("CustomDamage");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxCustomDamage(ItemStack stack) {
/* 575 */     return getMaxDamage(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxDamage(ItemStack stack) {
/* 581 */     return getUranType(stack).getMaxDamage();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDamaged(ItemStack stack) {
/* 587 */     return (getCustomDamage(stack) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getDisplayDamage(ItemStack stack) {
/* 594 */     return getCustomDamage(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomDamage(ItemStack stack, int damage) {
/* 600 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/* 601 */     nbt.func_74768_a("CustomDamage", damage);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src) {
/* 607 */     setCustomDamage(stack, getCustomDamage(stack) + damage);
/* 608 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getSubParts() {
/* 614 */     ItemStack[] items = new ItemStack[(UraniumType.values()).length * 3];
/* 615 */     for (UraniumType type : UraniumType.values()) {
/*     */       
/* 617 */       items[type.getOffset()] = new ItemStack((Item)this, 1, type.getOffset());
/* 618 */       items[type.getOffset() + 1] = new ItemStack((Item)this, 1, type.getOffset() + 1);
/* 619 */       items[type.getOffset() + 2] = new ItemStack((Item)this, 1, type.getOffset() + 2);
/*     */     } 
/* 621 */     return items;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSubParts() {
/* 627 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getReactorPart() {
/* 633 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorComponentType getType(ItemStack par1) {
/* 639 */     return IReactorPlannerComponent.ReactorComponentType.FuelRod;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 645 */     UraniumType type = getUranType(par2);
/* 646 */     int cellCount = getCellAmount(par2);
/* 647 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.HeatProduction) {
/*     */       
/* 649 */       int amount = 0;
/* 650 */       for (int i = 0; i < cellCount; i++)
/*     */       {
/* 652 */         amount = (int)(amount + (sumUp((int)((1 + cellCount / 2) * type.getPulseModifier())) * 4) * type.getHeatModfier());
/*     */       }
/* 654 */       return (NBTBase.NBTPrimitive)new NBTTagInt(amount);
/*     */     } 
/* 656 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.EnergyProduction) {
/*     */       
/* 658 */       float amount = 0.0F;
/* 659 */       for (int i = 0; i < cellCount; i++) {
/*     */         
/* 661 */         int pulses = (int)((1 + cellCount / 2) * type.getPulseModifier());
/* 662 */         for (int z = 0; z < pulses; z++)
/*     */         {
/* 664 */           amount += type.getEUPerPulse();
/*     */         }
/*     */       } 
/* 667 */       return (NBTBase.NBTPrimitive)new NBTTagFloat(amount * IC2.energyGeneratorNuclear);
/*     */     } 
/* 669 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.MaxDurability)
/*     */     {
/* 671 */       return (NBTBase.NBTPrimitive)new NBTTagInt(getMaxCustomDamage(par2));
/*     */     }
/* 673 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.ReactorEEM)
/*     */     {
/* 675 */       return (NBTBase.NBTPrimitive)new NBTTagFloat(type.getExplosionEffect() * cellCount);
/*     */     }
/* 677 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.RodAmount)
/*     */     {
/* 679 */       return (NBTBase.NBTPrimitive)new NBTTagInt(cellCount);
/*     */     }
/* 681 */     if (par1 == IReactorPlannerComponent.ReactorComponentStat.PulseAmount)
/*     */     {
/* 683 */       return (NBTBase.NBTPrimitive)new NBTTagInt((int)((1 + cellCount / 2) * type.getPulseModifier() * cellCount));
/*     */     }
/* 685 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdvancedStat(IReactorPlannerComponent.ReactorComponentStat par1, ItemStack par2) {
/* 691 */     return (par1 == IReactorPlannerComponent.ReactorComponentStat.EnergyProduction || par1 == IReactorPlannerComponent.ReactorComponentStat.HeatProduction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase.NBTPrimitive getReactorStat(IReactor par1, int x, int y, ItemStack item, IReactorPlannerComponent.ReactorComponentStat stat) {
/* 697 */     UraniumType type = getUranType(item);
/* 698 */     int cellCount = getCellAmount(item);
/* 699 */     if (stat == IReactorPlannerComponent.ReactorComponentStat.HeatProduction) {
/*     */       
/* 701 */       int amount = 0;
/* 702 */       int pulsePerTick = (int)type.getPulseModifier();
/* 703 */       for (int i = 0; i < cellCount; i++) {
/*     */         
/* 705 */         int pulses = (int)((1 + cellCount / 2) * type.getPulseModifier());
/*     */         
/* 707 */         for (int z = 0; z < pulsePerTick; z++) {
/*     */           
/* 709 */           for (int[] array : type.getPulseEffect())
/*     */           {
/* 711 */             pulses += checkPulseable(par1, x + array[0], y + array[1], item, x, y, false, type);
/*     */           }
/*     */         } 
/* 714 */         amount = (int)(amount + (sumUp(pulses) * 4) * type.getHeatModfier());
/*     */       } 
/* 716 */       return (NBTBase.NBTPrimitive)new NBTTagInt(amount);
/*     */     } 
/* 718 */     if (stat == IReactorPlannerComponent.ReactorComponentStat.EnergyProduction) {
/*     */       
/* 720 */       for (int i = 0; i < cellCount; i++) {
/*     */         
/* 722 */         int pulsePerTick = (int)type.getPulseModifier();
/* 723 */         int pulses = (1 + cellCount / 2) * pulsePerTick; int z;
/* 724 */         for (z = 0; z < pulses; z++)
/*     */         {
/* 726 */           acceptUraniumPulse(par1, item, item, x, y, x, y, false);
/*     */         }
/* 728 */         for (z = 0; z < pulsePerTick; z++) {
/*     */           
/* 730 */           for (int[] array : type.getPulseEffect())
/*     */           {
/* 732 */             pulses += checkPulseable(par1, x + array[0], y + array[1], item, x, y, false, type);
/*     */           }
/*     */         } 
/*     */       } 
/* 736 */       return (NBTBase.NBTPrimitive)new NBTTagFloat(par1.getReactorEnergyOutput());
/*     */     } 
/* 738 */     return nulltag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReactorPlannerComponent.ReactorType getReactorInfo(ItemStack stack) {
/* 744 */     return IReactorPlannerComponent.ReactorType.Both;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IReactorPlannerComponent.ReactorComponentStat> getExtraStats(ItemStack stack) {
/* 750 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 756 */     UraniumType type = UraniumType.Redstone;
/* 757 */     Ic2Items.reactorRedstoneEnrichedUraniumSimple = new ItemStack((Item)this, 1, type.getOffset());
/* 758 */     Ic2Items.reactorRedstoneEnrichedUraniumDual = new ItemStack((Item)this, 1, type.getOffset() + 1);
/* 759 */     Ic2Items.reactorRedstoneEnrichedUraniumQuad = new ItemStack((Item)this, 1, type.getOffset() + 2);
/* 760 */     type = UraniumType.Blaze;
/* 761 */     Ic2Items.reactorBlazeEnrichedUraniumSimple = new ItemStack((Item)this, 1, type.getOffset());
/* 762 */     Ic2Items.reactorBlazeEnrichedUraniumDual = new ItemStack((Item)this, 1, type.getOffset() + 1);
/* 763 */     Ic2Items.reactorBlazeEnrichedUraniumQuad = new ItemStack((Item)this, 1, type.getOffset() + 2);
/* 764 */     type = UraniumType.EnderPearl;
/* 765 */     Ic2Items.reactorEnderPearlEnrichedUraniumSimple = new ItemStack((Item)this, 1, type.getOffset());
/* 766 */     Ic2Items.reactorEnderPearlEnrichedUraniumDual = new ItemStack((Item)this, 1, type.getOffset() + 1);
/* 767 */     Ic2Items.reactorEnderPearlEnrichedUraniumQuad = new ItemStack((Item)this, 1, type.getOffset() + 2);
/* 768 */     type = UraniumType.NetherStar;
/* 769 */     Ic2Items.reactorNetherStarEnrichedUraniumSimple = new ItemStack((Item)this, 1, type.getOffset());
/* 770 */     Ic2Items.reactorNetherStarEnrichedUraniumDual = new ItemStack((Item)this, 1, type.getOffset() + 1);
/* 771 */     Ic2Items.reactorNetherStarEnrichedUraniumQuad = new ItemStack((Item)this, 1, type.getOffset() + 2);
/* 772 */     type = UraniumType.Charcoal;
/* 773 */     Ic2Items.reactorCharcoalEnrichedUraniumSimple = new ItemStack((Item)this, 1, type.getOffset());
/* 774 */     Ic2Items.reactorCharcoalEnrichedUraniumDual = new ItemStack((Item)this, 1, type.getOffset() + 1);
/* 775 */     Ic2Items.reactorCharcoalEnrichedUraniumQuad = new ItemStack((Item)this, 1, type.getOffset() + 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short getID(ItemStack stack) {
/* 781 */     UraniumType type = getUranType(stack);
/* 782 */     int meta = stack.func_77960_j() % 3;
/* 783 */     return (short)(type.getID() + meta);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\reactor\ItemReactorEnrichUranium.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */