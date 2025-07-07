/*     */ package ic2.core.item.tool;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricTool;
/*     */ import ic2.api.network.INetworkItemEventListener;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemToolMiningLaser extends ItemElectricTool implements INetworkItemEventListener, IElectricTool {
/*  24 */   private static int EventShotMining = 0;
/*  25 */   private static int EventShotLowFocus = 1;
/*  26 */   private static int EventShotLongRange = 2;
/*  27 */   private static int EventShotHorizontal = 3;
/*  28 */   private static int EventShotSuperHeat = 4;
/*  29 */   private static int EventShotScatter = 5;
/*  30 */   private static int EventShotExplosive = 6;
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemToolMiningLaser(int sprite) {
/*  35 */     super(sprite, Item.ToolMaterial.IRON, 100);
/*  36 */     this.maxCharge = 200000;
/*  37 */     this.transferLimit = 120;
/*  38 */     this.tier = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getMode(String par1) {
/*  44 */     return StatCollector.func_74838_a(par1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/*  49 */     if (!IC2.platform.isSimulating())
/*     */     {
/*  51 */       return itemstack;
/*     */     }
/*  53 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
/*  54 */     int laserSetting = nbtData.func_74762_e("laserSetting");
/*  55 */     if (IC2.keyboard.isModeSwitchKeyDown(entityplayer)) {
/*     */       
/*  57 */       laserSetting = (laserSetting + 1) % 7;
/*  58 */       nbtData.func_74768_a("laserSetting", laserSetting);
/*  59 */       String[] modes = { "laserMode.mining.name", "laserMode.low_focus.name", "laserMode.long_range.name", "laserMode.horizontal.name", "laserMode.super_heat.name", "laserMode.scatter.name", "laserMode.explosive.name" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  68 */       IC2.platform.messagePlayer(entityplayer, StatCollector.func_74837_a("itemInfo.laserMode.name", new Object[] { getMode(modes[laserSetting]) }));
/*     */     } else {
/*     */       int x;
/*     */       
/*  72 */       (new int[7])[0] = 1250; (new int[7])[1] = 100; (new int[7])[2] = 5000; (new int[7])[3] = 0; (new int[7])[4] = 2500; (new int[7])[5] = 10000; (new int[7])[6] = 5000; int consume = (new int[7])[laserSetting];
/*  73 */       if (!ElectricItem.manager.use(itemstack, consume, (EntityLivingBase)entityplayer))
/*     */       {
/*  75 */         return itemstack;
/*     */       }
/*  77 */       switch (laserSetting) {
/*     */ 
/*     */         
/*     */         case 0:
/*  81 */           world.func_72838_d(new EntityMiningLaser(world, (EntityLivingBase)entityplayer, 2.0F, 5.0F, 2147483647, false));
/*  82 */           ((NetworkManager)IC2.network.get()).initiateItemEvent(entityplayer, itemstack, 0, true);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1:
/*  87 */           world.func_72838_d(new EntityMiningLaser(world, (EntityLivingBase)entityplayer, 1.1F, 5.0F, 1, false));
/*  88 */           ((NetworkManager)IC2.network.get()).initiateItemEvent(entityplayer, itemstack, 1, true);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/*  93 */           world.func_72838_d(new EntityMiningLaser(world, (EntityLivingBase)entityplayer, 2.0F, 20.0F, 2147483647, false));
/*  94 */           ((NetworkManager)IC2.network.get()).initiateItemEvent(entityplayer, itemstack, 2, true);
/*     */ 
/*     */         
/*     */         case 4:
/*  98 */           world.func_72838_d(new EntityMiningLaser(world, (EntityLivingBase)entityplayer, 2.0F, 8.0F, 2147483647, false, true));
/*  99 */           ((NetworkManager)IC2.network.get()).initiateItemEvent(entityplayer, itemstack, 4, true);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 5:
/* 104 */           for (x = -2; x <= 2; x++) {
/*     */             
/* 106 */             for (int y = -2; y <= 2; y++)
/*     */             {
/* 108 */               world.func_72838_d(new EntityMiningLaser(world, (EntityLivingBase)entityplayer, 2.0F, 12.0F, 2147483647, false, (((Entity)entityplayer).field_70177_z + 20.0F * x), (((Entity)entityplayer).field_70125_A + 20.0F * y)));
/*     */             }
/*     */           } 
/* 111 */           ((NetworkManager)IC2.network.get()).initiateItemEvent(entityplayer, itemstack, 5, true);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 6:
/* 116 */           world.func_72838_d(new EntityMiningLaser(world, (EntityLivingBase)entityplayer, 2.0F, 12.0F, 2147483647, true));
/* 117 */           ((NetworkManager)IC2.network.get()).initiateItemEvent(entityplayer, itemstack, 6, true);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 122 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/* 127 */     if (!IC2.platform.isSimulating())
/*     */     {
/* 129 */       return false;
/*     */     }
/* 131 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
/* 132 */     if (!IC2.keyboard.isModeSwitchKeyDown(entityPlayer) && nbtData.func_74762_e("laserSetting") == 3)
/*     */     {
/* 134 */       if (Math.abs(entityPlayer.field_70163_u + entityPlayer.func_70047_e() - 0.1D - y + 0.5D) < 1.5D) {
/*     */         
/* 136 */         if (ElectricItem.manager.use(itemstack, 3000.0D, (EntityLivingBase)entityPlayer))
/*     */         {
/* 138 */           world.func_72838_d(new EntityMiningLaser(world, (EntityLivingBase)entityPlayer, 1.0F, 5.0F, 2147483647, false, entityPlayer.field_70177_z, 0.0D, y + 0.5D));
/* 139 */           ((NetworkManager)IC2.network.get()).initiateItemEvent(entityPlayer, itemstack, 3, true);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 144 */         IC2.platform.messagePlayer(entityPlayer, StatCollector.func_74838_a("itemInfo.wrongLaserAim.name"));
/*     */       } 
/*     */     }
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/* 153 */     return EnumRarity.uncommon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(ItemStack metaData, EntityPlayer player, int event) {
/* 159 */     switch (event) {
/*     */ 
/*     */       
/*     */       case 0:
/* 163 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.defaultVolume);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 168 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserLowFocus.ogg", true, IC2.audioManager.defaultVolume);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 173 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserLongRange.ogg", true, IC2.audioManager.defaultVolume);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 178 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.defaultVolume);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 183 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaser.ogg", true, IC2.audioManager.defaultVolume);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 188 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserScatter.ogg", true, IC2.audioManager.defaultVolume);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 193 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/MiningLaser/MiningLaserExplosive.ogg", true, IC2.audioManager.defaultVolume);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType getType(ItemStack item) {
/* 202 */     return EnumEnchantmentType.breakable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpecialSupport(ItemStack item, Enchantment ench) {
/* 208 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExcluded(ItemStack item, Enchantment ench) {
/* 214 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemToolMiningLaser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */