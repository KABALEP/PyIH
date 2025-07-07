/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLLog;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ 
/*     */ public class ItemArmorJetpack
/*     */   extends ItemArmorUtility {
/*     */   public static AudioSource audioSource;
/*     */   private static boolean lastJetpackUsed = false;
/*  32 */   private static JetpackUseMode lastMode = JetpackUseMode.None;
/*     */ 
/*     */   
/*     */   public ItemArmorJetpack(int spriteIndex, int armorrendering) {
/*  36 */     super(spriteIndex, armorrendering, 1);
/*  37 */     func_77656_e(18002);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/*  45 */     ItemStack stack = getArmor(armor);
/*  46 */     if (stack != null) {
/*     */       
/*  48 */       ItemArmor item = (ItemArmor)stack.func_77973_b();
/*  49 */       return new ISpecialArmor.ArmorProperties(0, item.field_77879_b / 25.0D, armor.func_77958_k() + 1 - stack.func_77960_j());
/*     */     } 
/*  51 */     return super.getProperties(player, armor, source, damage, slot);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/*  57 */     ItemStack stack = getArmor(armor);
/*  58 */     if (stack != null) {
/*     */       
/*  60 */       ItemArmor item = (ItemArmor)stack.func_77973_b();
/*  61 */       return item.field_77879_b;
/*     */     } 
/*  63 */     return super.getArmorDisplay(player, armor, slot);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
/*  69 */     ItemStack armor = getArmor(stack);
/*  70 */     if (armor != null) {
/*     */       
/*  72 */       armor.func_77972_a(damage, entity);
/*  73 */       if (armor.field_77994_a <= 0)
/*     */       {
/*  75 */         addJetpackArmor(stack, (ItemStack)null);
/*     */       }
/*     */     } 
/*     */     
/*  79 */     super.damageArmor(entity, stack, source, damage, slot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack par1, EntityPlayer p_77624_2_, List<String> par3, boolean p_77624_4_) {
/*  88 */     NBTTagCompound data = StackUtil.getOrCreateNbtData(par1);
/*  89 */     if (data.func_74767_n("RocketMode")) {
/*     */       
/*  91 */       int usedEnergy = data.func_74762_e("UsedEnergy");
/*  92 */       int result = (int)(usedEnergy / getMaxRocketCharge() * 100.0D);
/*  93 */       par3.add(StatCollector.func_74837_a("itemInfo.nuclearJetpackRocketCharge.name", new Object[] { result + "%" }));
/*  94 */       par3.add("");
/*     */     } 
/*  96 */     ItemStack stack = getArmor(par1);
/*  97 */     if (stack != null) {
/*     */       
/*  99 */       par3.add(StatCollector.func_74837_a("itemInfo.jetpackArmor.name", new Object[] { stack.func_82833_r() }));
/* 100 */       par3.add(StatCollector.func_74837_a("itemInfo.armorDurablity.name", new Object[] { Integer.valueOf(stack.func_77960_j()), Integer.valueOf(stack.func_77958_k()) }));
/* 101 */       par3.add("");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCharge(ItemStack itemStack) {
/* 107 */     int ret = (int)(getMaxCharge(itemStack) - itemStack.func_77960_j() - 1.0D);
/* 108 */     return (ret > 0) ? ret : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack itemStack) {
/* 113 */     return (itemStack.func_77958_k() - 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void use(ItemStack itemStack, int amount) {
/* 118 */     int newCharge = getCharge(itemStack) - amount;
/* 119 */     if (newCharge < 0)
/*     */     {
/* 121 */       newCharge = 0;
/*     */     }
/* 123 */     itemStack.func_77964_b(1 + itemStack.func_77958_k() - newCharge);
/*     */   }
/*     */ 
/*     */   
/*     */   public JetpackUseMode useJetpack(EntityPlayer player, HoverMode hoverMode) {
/* 128 */     ItemStack jetpack = player.field_71071_by.field_70460_b[2];
/* 129 */     if (getCharge(jetpack) == 0)
/*     */     {
/* 131 */       return JetpackUseMode.None;
/*     */     }
/* 133 */     JetpackUseMode mode = JetpackUseMode.Full;
/* 134 */     boolean electric = isElectricJetpack();
/* 135 */     float power = getPower();
/* 136 */     float dropPercentage = getDropPercentage();
/* 137 */     if (getCharge(jetpack) / getMaxCharge(jetpack) <= dropPercentage) {
/*     */       
/* 139 */       power = (float)(power * getCharge(jetpack) / getMaxCharge(jetpack) * dropPercentage);
/* 140 */       mode = JetpackUseMode.Dropped;
/*     */     } 
/* 142 */     if (IC2.keyboard.isForwardKeyDown(player)) {
/*     */       
/* 144 */       float retruster = getThruster(hoverMode);
/* 145 */       float forwardpower = power * retruster * 2.0F;
/* 146 */       if (forwardpower > 0.0F)
/*     */       {
/* 148 */         player.func_70060_a(0.0F, 0.4F * forwardpower, 0.02F);
/*     */       }
/*     */     } 
/* 151 */     int maxFlightHeight = getMaxHeight(IC2.getWorldHeight(player.field_70170_p));
/* 152 */     double y = player.field_70163_u;
/* 153 */     if (y > (maxFlightHeight - 25)) {
/*     */       
/* 155 */       if (y > maxFlightHeight)
/*     */       {
/* 157 */         y = maxFlightHeight;
/*     */       }
/* 159 */       power *= (float)((maxFlightHeight - y) / 25.0D);
/*     */     } 
/* 161 */     double prevmotion = player.field_70181_x;
/* 162 */     player.field_70181_x = Math.min(player.field_70181_x + (power * 0.2F), 0.6000000238418579D);
/* 163 */     if (hoverMode != HoverMode.None) {
/*     */       
/* 165 */       float maxHoverY = (hoverMode == HoverMode.Basic) ? -0.1F : (player.func_70093_af() ? -0.2F : 0.0F);
/* 166 */       if (electric && IC2.keyboard.isJumpKeyDown(player))
/*     */       {
/* 168 */         maxHoverY = (hoverMode == HoverMode.Basic) ? 0.1F : ((hoverMode == HoverMode.Adv) ? 0.3F : 0.6F);
/*     */       }
/* 170 */       if (player.field_70181_x > maxHoverY) {
/*     */         
/* 172 */         player.field_70181_x = maxHoverY;
/* 173 */         if (prevmotion > player.field_70181_x)
/*     */         {
/* 175 */           player.field_70181_x = prevmotion;
/*     */         }
/*     */       } 
/*     */     } 
/* 179 */     use(jetpack, getFuelCost(hoverMode));
/* 180 */     player.field_70143_R = 0.0F;
/* 181 */     player.field_70140_Q = 0.0F;
/* 182 */     IC2.platform.resetPlayerInAirTime(player);
/* 183 */     return mode;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean chargeRocketMode(EntityPlayer player) {
/* 188 */     ItemStack jetpack = player.field_71071_by.field_70460_b[2];
/* 189 */     if (getCharge(jetpack) == 0)
/*     */     {
/* 191 */       return false;
/*     */     }
/* 193 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(jetpack);
/* 194 */     int chargedEnergy = nbtData.func_74762_e("UsedEnergy");
/* 195 */     if (chargedEnergy > getMaxRocketCharge())
/*     */     {
/* 197 */       return false;
/*     */     }
/* 199 */     int fuelCost = getFuelCost(HoverMode.None) * 5;
/* 200 */     int used = Math.min(fuelCost, getCharge(jetpack));
/* 201 */     use(jetpack, used);
/* 202 */     chargedEnergy += used;
/* 203 */     nbtData.func_74768_a("UsedEnergy", chargedEnergy);
/* 204 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkRocketCharge(ItemStack jetpack) {
/* 209 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(jetpack);
/* 210 */     if (nbt.func_74764_b("UsedEnergy"))
/*     */     {
/* 212 */       nbt.func_82580_o("UsedEnergy");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
/* 219 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
/* 220 */     HoverMode hoverMode = HoverMode.values()[nbtData.func_74771_c("hoverMode")];
/* 221 */     boolean rocketMode = nbtData.func_74767_n("RocketMode");
/* 222 */     byte toggleTimer = nbtData.func_74771_c("toggleTimer");
/* 223 */     short rocketDelay = nbtData.func_74765_d("RocketDelay");
/* 224 */     boolean jetpackUsed = false;
/* 225 */     JetpackUseMode mode = JetpackUseMode.None;
/* 226 */     if (rocketMode && !canDoRocketMode())
/*     */     {
/* 228 */       nbtData.func_74757_a("RocketMode", false);
/*     */     }
/* 230 */     if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
/*     */       
/* 232 */       toggleTimer = 10;
/* 233 */       hoverMode = getNextHoverMode(hoverMode);
/* 234 */       if (IC2.platform.isSimulating()) {
/*     */         
/* 236 */         nbtData.func_74774_a("hoverMode", (byte)hoverMode.ordinal());
/* 237 */         if (hoverMode == HoverMode.Basic || hoverMode == HoverMode.Adv) {
/*     */           
/* 239 */           IC2.platform.messagePlayer(player, StatCollector.func_74838_a((hoverMode == HoverMode.Basic) ? "itemInfo.jetpackHovermodeOn.name" : "itemInfo.jetpackAdvHoverModeOn.name"));
/* 240 */           if (rocketMode)
/*     */           {
/* 242 */             rocketMode = false;
/* 243 */             nbtData.func_74757_a("RocketMode", rocketMode);
/* 244 */             IC2.platform.messagePlayer(player, StatCollector.func_74838_a("itemInfo.jetpackRocketModeForcedOff.name"));
/* 245 */             checkRocketCharge(itemStack);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 250 */           IC2.platform.messagePlayer(player, StatCollector.func_74838_a("itemInfo.jetpackHovermodeOff.name"));
/*     */         } 
/*     */       } 
/*     */     } 
/* 254 */     if (IC2.keyboard.isModeSwitchKeyDown(player) && IC2.keyboard.isBoostKeyDown(player) && canDoRocketMode() && toggleTimer == 0) {
/*     */       
/* 256 */       toggleTimer = 10;
/* 257 */       rocketMode = !rocketMode;
/* 258 */       if (IC2.platform.isSimulating()) {
/*     */         
/* 260 */         nbtData.func_74757_a("RocketMode", rocketMode);
/* 261 */         if (rocketMode) {
/*     */           
/* 263 */           IC2.platform.messagePlayer(player, StatCollector.func_74838_a("itemInfo.jetpackRocketModeOn.name"));
/* 264 */           if (hoverMode != HoverMode.None)
/*     */           {
/* 266 */             hoverMode = HoverMode.None;
/* 267 */             nbtData.func_74774_a("hoverMode", (byte)hoverMode.ordinal());
/* 268 */             IC2.platform.messagePlayer(player, StatCollector.func_74838_a("itemInfo.jetpackHovermodeForcedOff.name"));
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 273 */           IC2.platform.messagePlayer(player, StatCollector.func_74838_a("itemInfo.jetpackRocketModeOff.name"));
/* 274 */           checkRocketCharge(itemStack);
/*     */         } 
/*     */       } 
/*     */     } 
/* 278 */     if (rocketMode && IC2.keyboard.isBoostKeyDown(player) && rocketDelay <= 0) {
/*     */       
/* 280 */       jetpackUsed = chargeRocketMode(player);
/* 281 */       mode = JetpackUseMode.Full;
/*     */     } 
/* 283 */     if (IC2.keyboard.isAltKeyDown(player) && rocketMode && rocketDelay <= 0) {
/*     */       
/* 285 */       rocketDelay = (short)releaseRocket(player);
/* 286 */       nbtData.func_74757_a("RocketMode", false);
/* 287 */       world.func_72908_a(player.field_70165_t, player.field_70163_u, player.field_70161_v, "random.explode", 4.0F, (1.0F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
/*     */     } 
/* 289 */     if ((IC2.keyboard.isJumpKeyDown(player) && !rocketMode && rocketDelay <= 0) || (hoverMode == HoverMode.Basic && ((Entity)player).field_70181_x < -0.3499999940395355D) || hoverMode == HoverMode.Adv) {
/*     */       
/* 291 */       mode = useJetpack(player, hoverMode);
/* 292 */       jetpackUsed = (mode != JetpackUseMode.None);
/*     */     } 
/* 294 */     if (IC2.platform.isSimulating()) {
/*     */       
/* 296 */       if (toggleTimer > 0) {
/*     */         
/* 298 */         toggleTimer = (byte)(toggleTimer - 1);
/* 299 */         nbtData.func_74774_a("toggleTimer", toggleTimer);
/*     */       } 
/* 301 */       if (rocketDelay > 0) {
/*     */         
/* 303 */         rocketDelay = (short)(rocketDelay - 1);
/* 304 */         nbtData.func_74777_a("RocketDelay", rocketDelay);
/*     */       } 
/*     */     } 
/*     */     
/* 308 */     if (IC2.platform.isRendering() && player == IC2.platform.getPlayerInstance()) {
/*     */       
/* 310 */       if (lastJetpackUsed != jetpackUsed || lastMode != mode) {
/*     */         
/* 312 */         if (lastMode != mode && audioSource != null) {
/*     */           
/* 314 */           audioSource.remove();
/* 315 */           audioSource = null;
/*     */         } 
/* 317 */         if (jetpackUsed) {
/*     */           
/* 319 */           if (audioSource == null)
/*     */           {
/* 321 */             audioSource = IC2.audioManager.createSource(player, PositionSpec.Backpack, (mode == JetpackUseMode.Full) ? "Tools/Jetpack/JetpackLoop.ogg" : "Tools/Jetpack/JetpackFire.ogg", true, false, IC2.audioManager.defaultVolume);
/*     */           }
/* 323 */           if (audioSource != null)
/*     */           {
/* 325 */             audioSource.play();
/*     */           }
/*     */         }
/* 328 */         else if (audioSource != null) {
/*     */           
/* 330 */           audioSource.remove();
/* 331 */           audioSource = null;
/*     */         } 
/* 333 */         lastJetpackUsed = jetpackUsed;
/* 334 */         lastMode = mode;
/*     */       } 
/* 336 */       if (audioSource != null)
/*     */       {
/* 338 */         audioSource.updatePosition();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int releaseRocket(EntityPlayer player) {
/* 345 */     ItemStack jetpack = player.field_71071_by.field_70460_b[2];
/* 346 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(jetpack);
/* 347 */     int charge = nbt.func_74762_e("UsedEnergy");
/* 348 */     if (charge <= 0)
/*     */     {
/* 350 */       return 0;
/*     */     }
/*     */     
/* 353 */     nbt.func_74768_a("UsedEnergy", 0);
/* 354 */     use(jetpack, 50);
/* 355 */     int charges = charge / getFuelCost(HoverMode.None);
/* 356 */     charges /= 50;
/* 357 */     float speed = getPower() * charges;
/* 358 */     speed /= 4.0F;
/* 359 */     player.field_70181_x += Math.min(speed, 10.0F);
/* 360 */     player.field_70143_R = 0.0F;
/* 361 */     player.field_70140_Q = 0.0F;
/* 362 */     IC2.platform.resetPlayerInAirTime(player);
/* 363 */     return (int)(speed * 10.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150895_a(Item i, CreativeTabs tabs, List<ItemStack> itemList) {
/* 368 */     itemList.add(new ItemStack((Item)this, 1, 1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextureName() {
/* 374 */     return "jetpack_fuel";
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDropPercentage() {
/* 379 */     return 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPower() {
/* 384 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isElectricJetpack() {
/* 389 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFuelCost(HoverMode hoverMode) {
/* 394 */     return (hoverMode == HoverMode.Basic) ? 6 : 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeight(int worldHight) {
/* 399 */     return worldHight;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getThruster(HoverMode hoverMode) {
/* 404 */     return (hoverMode == HoverMode.Basic) ? 0.5F : 0.15F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDoRocketMode() {
/* 409 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDoAdvHover() {
/* 414 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxRocketCharge() {
/* 419 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addJetpackArmor(ItemStack jetpack, ItemStack armor) {
/* 424 */     if (armor == null) {
/*     */       
/* 426 */       NBTTagCompound nBTTagCompound = StackUtil.getOrCreateNbtData(jetpack);
/* 427 */       nBTTagCompound.func_82580_o("JetpackArmor");
/*     */       return;
/*     */     } 
/* 430 */     FMLLog.getLogger().info("Test");
/*     */     
/* 432 */     NBTTagCompound data = new NBTTagCompound();
/* 433 */     armor.func_77955_b(data);
/* 434 */     jetpack.func_77983_a("JetpackArmor", (NBTBase)data);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getArmor(ItemStack jetpack) {
/* 439 */     NBTTagCompound data = StackUtil.getOrCreateNbtData(jetpack);
/* 440 */     if (data.func_74764_b("JetpackArmor"))
/*     */     {
/* 442 */       return ItemStack.func_77949_a(data.func_74775_l("JetpackArmor"));
/*     */     }
/* 444 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasJetpackOverrideRequest(ItemStack par1) {
/* 449 */     return StackUtil.getOrCreateNbtData(par1).func_74764_b("JOverride");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJetpackOverrideRequest(ItemStack par1, boolean par2) {
/* 454 */     NBTTagCompound data = StackUtil.getOrCreateNbtData(par1);
/* 455 */     if (par2) {
/*     */       
/* 457 */       data.func_74757_a("JOverride", true);
/*     */       return;
/*     */     } 
/* 460 */     data.func_82580_o("JOverride");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private HoverMode getNextHoverMode(HoverMode old) {
/* 466 */     if (old == HoverMode.Basic && canDoAdvHover())
/*     */     {
/* 468 */       return HoverMode.Adv;
/*     */     }
/* 470 */     if (old == HoverMode.None)
/*     */     {
/* 472 */       return HoverMode.Basic;
/*     */     }
/* 474 */     return HoverMode.None;
/*     */   }
/*     */   
/*     */   public enum HoverMode
/*     */   {
/* 479 */     None,
/* 480 */     Basic,
/* 481 */     Adv;
/*     */   }
/*     */   
/*     */   public enum JetpackUseMode
/*     */   {
/* 486 */     Full,
/* 487 */     Dropped,
/* 488 */     None;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIcon(ItemStack stack, int pass) {
/* 495 */     boolean hasArmor = hasArmor(stack);
/* 496 */     if (!hasArmor || GuiScreen.func_146271_m())
/*     */     {
/* 498 */       return super.getIcon(stack, 0);
/*     */     }
/* 500 */     if (pass == 1)
/*     */     {
/* 502 */       return Ic2Icons.getTexture("i2")[45];
/*     */     }
/* 504 */     return getArmor(stack).func_77954_c();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasArmor(ItemStack stack) {
/* 509 */     return (getArmor(stack) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_77623_v() {
/* 518 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorJetpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */