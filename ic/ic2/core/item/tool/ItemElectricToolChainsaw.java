/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricTool;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHitSoundOverride;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemAxe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ import net.minecraftforge.common.IShearable;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.player.EntityInteractEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemElectricToolChainsaw
/*     */   extends ItemAxe
/*     */   implements IHitSoundOverride, IElectricTool
/*     */ {
/*     */   public static boolean wasEquipped = false;
/*     */   public static AudioSource audioSource;
/*     */   public int co;
/*     */   public int operationEnergyCost;
/*     */   public int maxCharge;
/*     */   public int transferLimit;
/*     */   public int tier;
/*  67 */   public Set mineableBlocks = new HashSet();
/*     */   
/*     */   public int iconIndex;
/*     */   
/*     */   public ItemElectricToolChainsaw(int sprite) {
/*  72 */     super(Item.ToolMaterial.IRON);
/*  73 */     this.maxCharge = 10000;
/*  74 */     this.transferLimit = 100;
/*  75 */     this.operationEnergyCost = 50;
/*  76 */     this.tier = 1;
/*  77 */     this.field_77864_a = 12.0F;
/*  78 */     this.co = 1;
/*  79 */     this.iconIndex = sprite;
/*  80 */     MinecraftForge.EVENT_BUS.register(this);
/*  81 */     setHarvestLevel("axe", 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  86 */     this.mineableBlocks.add(Blocks.field_150344_f);
/*  87 */     this.mineableBlocks.add(Blocks.field_150342_X);
/*  88 */     this.mineableBlocks.add(Blocks.field_150364_r);
/*  89 */     this.mineableBlocks.add(Blocks.field_150363_s);
/*  90 */     this.mineableBlocks.add(Blocks.field_150486_ae);
/*  91 */     this.mineableBlocks.add(Blocks.field_150362_t);
/*  92 */     this.mineableBlocks.add(Blocks.field_150321_G);
/*  93 */     this.mineableBlocks.add(Block.func_149634_a(Ic2Items.crop.func_77973_b()));
/*  94 */     if (Ic2Items.rubberLeaves != null)
/*     */     {
/*  96 */       this.mineableBlocks.add(Block.func_149634_a(Ic2Items.rubberLeaves.func_77973_b()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77644_a(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
/* 103 */     if (ElectricItem.manager.use(itemstack, this.operationEnergyCost, entityliving1) && ElectricItem.manager.use(itemstack, this.operationEnergyCost, entityliving1)) {
/*     */       
/* 105 */       entityliving.func_70097_a(DamageSource.func_76365_a((EntityPlayer)entityliving1), 10.0F);
/*     */     }
/*     */     else {
/*     */       
/* 109 */       entityliving.func_70097_a(DamageSource.func_76365_a((EntityPlayer)entityliving1), 1.0F);
/*     */     } 
/* 111 */     if (entityliving instanceof net.minecraft.entity.monster.EntityCreeper && entityliving.func_110143_aJ() <= 0.0F)
/*     */     {
/* 113 */       IC2.achievements.issueAchievement((EntityPlayer)entityliving1, "killCreeperChainsaw");
/*     */     }
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canHarvestBlock(Block block, ItemStack item) {
/* 121 */     return (block.func_149688_o() == Material.field_151575_d || this.mineableBlocks.contains(block));
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityInteract(EntityInteractEvent event) {
/* 127 */     Entity entity = event.target;
/* 128 */     if (entity.field_70170_p.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     EntityPlayer player = ((PlayerEvent)event).entityPlayer;
/* 133 */     ItemStack itemstack = player.field_71071_by.func_70448_g();
/* 134 */     if (itemstack != null && itemstack.func_77973_b() == this && entity instanceof IShearable && ElectricItem.manager.use(itemstack, (this.operationEnergyCost * 2), (EntityLivingBase)player)) {
/*     */       
/* 136 */       IShearable target = (IShearable)entity;
/* 137 */       if (target.isShearable(itemstack, (IBlockAccess)entity.field_70170_p, (int)entity.field_70165_t, (int)entity.field_70163_u, (int)entity.field_70161_v)) {
/*     */         
/* 139 */         ArrayList<ItemStack> drops = target.onSheared(itemstack, (IBlockAccess)entity.field_70170_p, (int)entity.field_70165_t, (int)entity.field_70163_u, (int)entity.field_70161_v, EnchantmentHelper.func_77506_a(Enchantment.field_77346_s.field_77352_x, itemstack));
/* 140 */         for (ItemStack stack : drops) {
/*     */ 
/*     */           
/* 143 */           EntityItem entityDropItem = entity.func_70099_a(stack, 1.0F), ent = entityDropItem;
/* 144 */           entityDropItem.field_70181_x += (Item.field_77697_d.nextFloat() * 0.05F);
/* 145 */           EntityItem entityItem = ent;
/* 146 */           entityItem.field_70159_w += ((Item.field_77697_d.nextFloat() - Item.field_77697_d.nextFloat()) * 0.1F);
/* 147 */           EntityItem entityItem2 = ent;
/* 148 */           entityItem2.field_70179_y += ((Item.field_77697_d.nextFloat() - Item.field_77697_d.nextFloat()) * 0.1F);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
/* 156 */     if (player.field_70170_p.field_72995_K)
/*     */     {
/* 158 */       return false;
/*     */     }
/* 160 */     Block id = player.field_70170_p.func_147439_a(X, Y, Z);
/* 161 */     if (id != null && id instanceof IShearable) {
/*     */       
/* 163 */       IShearable target = (IShearable)id;
/* 164 */       if (target.isShearable(itemstack, (IBlockAccess)player.field_70170_p, X, Y, Z) && ElectricItem.manager.use(itemstack, this.operationEnergyCost, (EntityLivingBase)player) && ElectricItem.manager.use(itemstack, this.operationEnergyCost, (EntityLivingBase)player)) {
/*     */         
/* 166 */         ArrayList<ItemStack> drops = target.onSheared(itemstack, (IBlockAccess)((Entity)player).field_70170_p, X, Y, Z, EnchantmentHelper.func_77506_a(Enchantment.field_77346_s.field_77352_x, itemstack));
/* 167 */         for (ItemStack stack : drops) {
/*     */           
/* 169 */           float f = 0.7F;
/* 170 */           double d = (Item.field_77697_d.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 171 */           double d2 = (Item.field_77697_d.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 172 */           double d3 = (Item.field_77697_d.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 173 */           EntityItem entityitem = new EntityItem(((Entity)player).field_70170_p, X + d, Y + d2, Z + d3, stack);
/* 174 */           entityitem.field_145804_b = 10;
/* 175 */           player.field_70170_p.func_72838_d((Entity)entityitem);
/*     */         } 
/* 177 */         player.func_71064_a(StatList.field_75934_C[Block.func_149682_b(id)], 1);
/*     */       } 
/*     */     } 
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77663_a(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
/* 185 */     boolean isEquipped = (flag && entity instanceof EntityLivingBase);
/* 186 */     if (!isEquipped)
/*     */     {
/* 188 */       if (!itemstack.func_82839_y()) {
/*     */         return;
/*     */       }
/*     */     }
/*     */     
/* 193 */     if (IC2.platform.isRendering()) {
/*     */       
/* 195 */       if (isEquipped && !wasEquipped) {
/*     */         
/* 197 */         if (!itemstack.func_82839_y())
/*     */         {
/* 199 */           itemstack.func_82842_a(new EntityItemFrame(world));
/*     */         }
/* 201 */         if (audioSource == null)
/*     */         {
/* 203 */           audioSource = IC2.audioManager.createSource(entity, PositionSpec.Hand, "Tools/Chainsaw/ChainsawIdle.ogg", true, false, IC2.audioManager.defaultVolume);
/*     */         }
/* 205 */         if (audioSource != null)
/*     */         {
/* 207 */           audioSource.play();
/*     */         }
/*     */       }
/* 210 */       else if (!isEquipped && audioSource != null) {
/*     */         
/* 212 */         itemstack.func_82842_a(null);
/* 213 */         audioSource.stop();
/* 214 */         audioSource.remove();
/* 215 */         audioSource = null;
/* 216 */         if (entity instanceof EntityLivingBase)
/*     */         {
/* 218 */           IC2.audioManager.playOnce(entity, PositionSpec.Hand, "Tools/Chainsaw/ChainsawStop.ogg", true, IC2.audioManager.defaultVolume);
/*     */         }
/*     */       }
/* 221 */       else if (audioSource != null) {
/*     */         
/* 223 */         audioSource.updatePosition();
/*     */       } 
/* 225 */       wasEquipped = isEquipped;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHitSoundForBlock(int x, int y, int z) {
/* 232 */     String[] soundEffects = { "Tools/Chainsaw/ChainsawUseOne.ogg", "Tools/Chainsaw/ChainsawUseTwo.ogg" };
/* 233 */     return soundEffects[Item.field_77697_d.nextInt(soundEffects.length)];
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/* 239 */     return Ic2Icons.getTexture("i1")[this.iconIndex];
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_150893_a(ItemStack tool, Block block) {
/* 244 */     if (!ElectricItem.manager.canUse(tool, this.operationEnergyCost))
/*     */     {
/* 246 */       return 1.0F;
/*     */     }
/* 248 */     if (ForgeHooks.isToolEffective(tool, block, 0))
/*     */     {
/* 250 */       return this.field_77864_a;
/*     */     }
/* 252 */     if (canHarvestBlock(block, tool))
/*     */     {
/* 254 */       return this.field_77864_a;
/*     */     }
/* 256 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDigSpeed(ItemStack tool, Block block, int md) {
/* 261 */     if (!ElectricItem.manager.canUse(tool, this.operationEnergyCost))
/*     */     {
/* 263 */       return 1.0F;
/*     */     }
/* 265 */     if (ForgeHooks.isToolEffective(tool, block, md))
/*     */     {
/* 267 */       return this.field_77864_a;
/*     */     }
/* 269 */     if (canHarvestBlock(block, tool))
/*     */     {
/* 271 */       return this.field_77864_a;
/*     */     }
/* 273 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextureFile() {
/* 278 */     return "/ic2/sprites/item_0.png";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack par1) {
/* 283 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getChargedItem(ItemStack itemStack) {
/* 289 */     return (Item)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getEmptyItem(ItemStack itemStack) {
/* 295 */     return (Item)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack par1) {
/* 300 */     return this.maxCharge;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack par1) {
/* 305 */     return this.tier;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack par1) {
/* 310 */     return this.transferLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap func_111205_h() {
/* 315 */     Multimap multimap = super.func_111205_h();
/* 316 */     multimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Weapon modifier", this.co, 0));
/* 317 */     return multimap;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRepairable() {
/* 322 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_150894_a(ItemStack par1ItemStack, World par2World, Block par3, int par4, int par5, int par6, EntityLivingBase par7EntityLiving) {
/* 327 */     Block block = par2World.func_147439_a(par4, par5, par6);
/* 328 */     if (block == Blocks.field_150350_a) {
/*     */       
/* 330 */       IC2.getInstance();
/* 331 */       IC2.log.debug("ItemElectricTool.onBlockDestroyed(): received invalid block id " + par3);
/* 332 */       return false;
/*     */     } 
/* 334 */     if (block.func_149712_f(par2World, par4, par5, par6) != 0.0D)
/*     */     {
/* 336 */       if (par7EntityLiving instanceof EntityPlayer) {
/*     */         
/* 338 */         ElectricItem.manager.use(par1ItemStack, this.operationEnergyCost, par7EntityLiving);
/*     */       }
/*     */       else {
/*     */         
/* 342 */         ElectricItem.manager.discharge(par1ItemStack, this.operationEnergyCost, this.tier, true, false, false);
/*     */       } 
/*     */     }
/* 345 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
/* 350 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item i, CreativeTabs tabs, List<ItemStack> itemList) {
/* 356 */     ItemStack charged = new ItemStack((Item)this, 1);
/* 357 */     ElectricItem.manager.charge(charged, 2.147483647E9D, 2147483647, true, false);
/* 358 */     itemList.add(charged);
/* 359 */     itemList.add(new ItemStack((Item)this, 1, func_77612_l()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister p_94581_1_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType getType(ItemStack item) {
/* 372 */     return EnumEnchantmentType.digger;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpecialSupport(ItemStack item, Enchantment ench) {
/* 378 */     if (ench instanceof net.minecraft.enchantment.EnchantmentDamage)
/*     */     {
/* 380 */       return true;
/*     */     }
/* 382 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExcluded(ItemStack item, Enchantment ench) {
/* 388 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemElectricToolChainsaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */