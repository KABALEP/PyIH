/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricTool;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemNanoSaber
/*     */   extends ItemElectricTool
/*     */   implements IElectricTool
/*     */ {
/*     */   public boolean active;
/*     */   public int soundTicker;
/*     */   
/*     */   public ItemNanoSaber(int sprite, boolean a) {
/*  52 */     super(sprite, Item.ToolMaterial.IRON, 10);
/*  53 */     this.soundTicker = 0;
/*  54 */     this.maxCharge = 40000;
/*  55 */     this.transferLimit = 128;
/*  56 */     this.tier = 2;
/*  57 */     this.active = a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  62 */     this.mineableBlocks.add(Blocks.field_150321_G);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_150893_a(ItemStack itemstack, Block block) {
/*  68 */     if (this.active) {
/*     */       
/*  70 */       this.soundTicker++;
/*  71 */       if (this.soundTicker % 4 == 0)
/*     */       {
/*  73 */         IC2.platform.playSoundSp(getRandomSwingSound(), 1.0F, 1.0F);
/*     */       }
/*  75 */       return 4.0F;
/*     */     } 
/*  77 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77644_a(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
/*  83 */     if (!this.active)
/*     */     {
/*  85 */       return true;
/*     */     }
/*  87 */     if (IC2.platform.isSimulating() && (!(entityliving1 instanceof EntityPlayer) || !MinecraftServer.func_71276_C().func_71219_W())) {
/*     */       
/*  89 */       EntityPlayer player = null;
/*  90 */       if (entityliving1 instanceof EntityPlayer)
/*     */       {
/*  92 */         player = (EntityPlayer)entityliving1;
/*     */       }
/*  94 */       if (entityliving instanceof EntityPlayer) {
/*     */         
/*  96 */         EntityPlayer enemy = (EntityPlayer)entityliving;
/*  97 */         for (int i = 0; i < 4; i++) {
/*     */           
/*  99 */           if (enemy.field_71071_by.field_70460_b[i] != null && enemy.field_71071_by.field_70460_b[i].func_77973_b() instanceof ic2.core.item.armor.ItemArmorNanoSuit) {
/*     */             
/* 101 */             int amount = (enemy.field_71071_by.field_70460_b[i].func_77973_b() instanceof ic2.core.item.armor.ItemArmorQuantumSuit) ? 30000 : 4800;
/* 102 */             ElectricItem.manager.discharge(enemy.field_71071_by.field_70460_b[i], amount, this.tier, true, true, false);
/* 103 */             if (!ElectricItem.manager.canUse(enemy.field_71071_by.field_70460_b[i], 1.0D))
/*     */             {
/* 105 */               enemy.field_71071_by.field_70460_b[i] = null;
/*     */             }
/* 107 */             drainSaber(itemstack, 2, player);
/*     */           } 
/*     */         } 
/*     */       } 
/* 111 */       drainSaber(itemstack, 5, player);
/*     */     } 
/* 113 */     IC2.audioManager.playOnce(entityliving1, PositionSpec.Hand, "Tools/Nanosabre/" + getRandomSwingSound(), false, 20.0F);
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRandomSwingSound() {
/* 119 */     switch (IC2.random.nextInt(3)) {
/*     */ 
/*     */       
/*     */       default:
/* 123 */         return "NanosabreSwing1.ogg";
/*     */ 
/*     */       
/*     */       case 1:
/* 127 */         return "NanosabreSwing2.ogg";
/*     */       case 2:
/*     */         break;
/*     */     } 
/* 131 */     return "NanosabreSwing3.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockStartBreak(ItemStack itemstack, int i, int j, int k, EntityPlayer player) {
/* 139 */     if (this.active)
/*     */     {
/* 141 */       drainSaber(itemstack, 10, player);
/*     */     }
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap func_111205_h() {
/* 148 */     HashMultimap hashMultimap = HashMultimap.create();
/* 149 */     hashMultimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Weapon modifier", (this.active ? 20 : 4), 0));
/* 150 */     return (Multimap)hashMultimap;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77662_d() {
/* 155 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_150897_b(Block block) {
/* 161 */     return (block == Blocks.field_150321_G);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drainSaber(ItemStack saber, int damage, EntityPlayer player) {
/* 166 */     if (!IC2.platform.isSimulating()) {
/*     */       return;
/*     */     }
/*     */     
/* 170 */     if (!ElectricItem.manager.use(saber, (damage * 8), (EntityLivingBase)player))
/*     */     {
/* 172 */       saber.func_150996_a(Ic2Items.nanoSaber.func_77973_b());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/* 179 */     if (this.active) {
/*     */       
/* 181 */       if (IC2.platform.isSimulating())
/*     */       {
/* 183 */         itemstack.func_150996_a(Ic2Items.nanoSaber.func_77973_b());
/*     */       }
/*     */ 
/*     */       
/* 187 */       if (sound != null)
/*     */       {
/* 189 */         sound.stop();
/* 190 */         sound.remove();
/* 191 */         sound = null;
/*     */       }
/*     */     
/* 194 */     } else if (!this.active && ElectricItem.manager.canUse(itemstack, 16.0D)) {
/*     */       
/* 196 */       if (IC2.platform.isSimulating()) {
/*     */         
/* 198 */         itemstack.func_150996_a(Ic2Items.enabledNanoSaber.func_77973_b());
/*     */       }
/*     */       else {
/*     */         
/* 202 */         IC2.audioManager.playOnce(entityplayer, PositionSpec.Hand, "Tools/Nanosabre/NanosabrePowerup.ogg", false, 1.0F);
/* 203 */         if (sound != null) {
/*     */           
/* 205 */           sound.stop();
/* 206 */           sound.remove();
/* 207 */           sound = null;
/*     */         } 
/* 209 */         sound = IC2.audioManager.createSource(entityplayer, PositionSpec.Hand, "Tools/Nanosabre/NanosabreIdle.ogg", true, false, 0.3F);
/* 210 */         if (sound != null)
/*     */         {
/* 212 */           sound.play();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 217 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77663_a(ItemStack item, World world, Entity entity, int id, boolean flag) {
/* 223 */     super.func_77663_a(item, world, entity, id, flag);
/* 224 */     if (IC2.platform.isRendering())
/*     */     {
/* 226 */       if (sound != null)
/*     */       {
/* 228 */         sound.updatePosition();
/*     */       }
/*     */     }
/* 231 */     if (!(entity instanceof EntityPlayer) || !this.active) {
/*     */       return;
/*     */     }
/*     */     
/* 235 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(item);
/* 236 */     int delay = nbt.func_74762_e("Delay");
/* 237 */     delay++;
/* 238 */     boolean powerLoss = flag ? ((delay >= 16)) : ((delay >= 64));
/* 239 */     if (powerLoss)
/*     */     {
/* 241 */       delay = 0;
/*     */     }
/* 243 */     nbt.func_74768_a("Delay", delay);
/* 244 */     if (!powerLoss) {
/*     */       return;
/*     */     }
/*     */     
/* 248 */     this; drainSaber(item, flag ? 16 : 64, (EntityPlayer)entity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_77617_a(int i) {
/* 254 */     if (this.active && shinyrand.nextBoolean())
/*     */     {
/* 256 */       return Ic2Icons.getTexture("i1")[this.iconIndex + 1];
/*     */     }
/* 258 */     return Ic2Icons.getTexture("i1")[this.iconIndex];
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/* 264 */     return EnumRarity.uncommon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item i, CreativeTabs tabs, List itemList) {
/* 271 */     super.func_150895_a(i, tabs, itemList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 276 */   public static Random shinyrand = new Random();
/*     */   
/*     */   public static AudioSource sound;
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType getType(ItemStack item) {
/* 282 */     return EnumEnchantmentType.weapon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpecialSupport(ItemStack item, Enchantment ench) {
/* 288 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExcluded(ItemStack item, Enchantment ench) {
/* 294 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemNanoSaber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */