/*     */ package ic2.core.item;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.wiring.TileEntityChargePad;
/*     */ import ic2.core.block.wiring.TileEntityElectricMFE;
/*     */ import ic2.core.block.wiring.TileEntityElectricMFSU;
/*     */ import ic2.core.util.IExtraData;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemChargePadUpgrade
/*     */   extends ItemIC2
/*     */   implements IExtraData
/*     */ {
/*     */   public ItemChargePadUpgrade(int index) {
/*  34 */     super(index);
/*  35 */     func_77627_a(true);
/*  36 */     func_77625_d(64);
/*  37 */     func_77655_b("itemChargePadUpgrade");
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityChargePad.PadUpgrade getUpgrade(ItemStack par1) {
/*  42 */     int meta = par1.func_77960_j();
/*  43 */     if (meta >= 4 && meta <= 11) {
/*     */       
/*  45 */       meta -= 4;
/*  46 */       return TileEntityChargePad.PadUpgrade.values()[meta];
/*     */     } 
/*  48 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  54 */     Ic2Items.crystalUpgradeKid = new ItemStack(this, 1, 0);
/*  55 */     Ic2Items.mfsUpgradeKid = new ItemStack(this, 1, 1);
/*  56 */     Ic2Items.lapotronicUpgradeKid = new ItemStack(this, 1, 2);
/*  57 */     Ic2Items.fissionUpgradeKid = new ItemStack(this, 1, 3);
/*  58 */     Ic2Items.padUpgradeDamage = new ItemStack(this, 1, 4);
/*  59 */     Ic2Items.padUpgradeDrain = new ItemStack(this, 1, 5);
/*  60 */     Ic2Items.padUpgradeProximity = new ItemStack(this, 1, 6);
/*  61 */     Ic2Items.padUpgradeWideBand = new ItemStack(this, 1, 7);
/*  62 */     Ic2Items.padUpgradeArmorPriorty = new ItemStack(this, 1, 8);
/*  63 */     Ic2Items.padUpgradeBasicFieldUpgrade = new ItemStack(this, 1, 9);
/*  64 */     Ic2Items.padUpgradeFieldUpgrade = new ItemStack(this, 1, 10);
/*  65 */     Ic2Items.padUpgradeAdvFieldUpgrade = new ItemStack(this, 1, 11);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/*  72 */     return Ic2Icons.getTexture(this.spriteID)[this.iconIndex + par1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item p_150895_1_, CreativeTabs p_150895_2_, List<ItemStack> list) {
/*  79 */     for (int meta = 0; meta <= 32767; meta++) {
/*     */       
/*  81 */       ItemStack stack = new ItemStack(this, 1, meta);
/*  82 */       if (func_77667_c(stack) == null) {
/*     */         break;
/*     */       }
/*     */       
/*  86 */       list.add(stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack item) {
/*  95 */     switch (item.func_77960_j()) {
/*     */       
/*     */       case 0:
/*  98 */         return "item.upgradeKit.crystalizor";
/*     */       case 1:
/* 100 */         return "item.upgradeKit.mfs";
/*     */       case 2:
/* 102 */         return "item.upgradeKit.lapotronic";
/*     */       case 3:
/* 104 */         return "item.upgradeKit.fission";
/*     */       case 4:
/* 106 */         return "item.padUpgrade.damage";
/*     */       case 5:
/* 108 */         return "item.padUpgrade.drain";
/*     */       case 6:
/* 110 */         return "item.padUpgrade.proximity";
/*     */       case 7:
/* 112 */         return "item.padUpgrade.widebad";
/*     */       case 8:
/* 114 */         return "item.padUpgrade.armorPriority";
/*     */       case 9:
/* 116 */         return "item.padUpgrade.fieldExp1";
/*     */       case 10:
/* 118 */         return "item.padUpgrade.fieldExp2";
/*     */       case 11:
/* 120 */         return "item.padUpgrade.fieldExp3";
/*     */     } 
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/* 128 */     TileEntityChargePad.PadUpgrade upgrade = getUpgrade(stack);
/* 129 */     if (upgrade != null)
/*     */     {
/* 131 */       return upgrade.isRare ? EnumRarity.uncommon : EnumRarity.common;
/*     */     }
/* 133 */     int meta = stack.func_77960_j();
/* 134 */     if (meta == 2 || meta == 3 || meta == 1)
/*     */     {
/* 136 */       return EnumRarity.uncommon;
/*     */     }
/* 138 */     return super.func_77613_e(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/* 144 */     int meta = stack.func_77960_j();
/* 145 */     if (meta < 4 && meta != 1) {
/*     */       
/* 147 */       TileEntity tile = world.func_147438_o(x, y, z);
/* 148 */       if (tile instanceof TileEntityChargePad) {
/*     */         
/* 150 */         TileEntityChargePad pad = (TileEntityChargePad)tile;
/* 151 */         int newMeta = -1;
/* 152 */         switch (meta) {
/*     */           
/*     */           case 0:
/* 155 */             if (pad.type == TileEntityChargePad.ChargePadType.LV)
/*     */             {
/* 157 */               newMeta = TileEntityChargePad.ChargePadType.MV.ordinal();
/*     */             }
/*     */             break;
/*     */           case 2:
/* 161 */             if (pad.type == TileEntityChargePad.ChargePadType.MV)
/*     */             {
/* 163 */               newMeta = TileEntityChargePad.ChargePadType.HV.ordinal();
/*     */             }
/*     */             break;
/*     */           case 3:
/* 167 */             if (pad.type == TileEntityChargePad.ChargePadType.HV)
/*     */             {
/* 169 */               newMeta = TileEntityChargePad.ChargePadType.Nuclear.ordinal();
/*     */             }
/*     */             break;
/*     */         } 
/* 173 */         if (newMeta == -1 || world.field_72995_K)
/*     */         {
/* 175 */           return !world.field_72995_K;
/*     */         }
/*     */         
/* 178 */         if (!IC2.chargePadUpgradeAnySide && ForgeDirection.getOrientation(side) != ForgeDirection.DOWN) {
/*     */           
/* 180 */           IC2.platform.messagePlayer(player, StatCollector.func_74838_a("itemInfo.chargePadUpgradeKidwrongSide.name"));
/* 181 */           return true;
/*     */         } 
/* 183 */         ItemStack[] itemInv = new ItemStack[pad.inv.func_70302_i_()];
/* 184 */         for (int i = 0; i < itemInv.length; i++)
/*     */         {
/* 186 */           itemInv[i] = pad.inv.func_70301_a(i);
/*     */         }
/* 188 */         int storedEnergy = pad.storedEnergy;
/* 189 */         if (world.func_147465_d(x, y, z, Block.func_149634_a(Ic2Items.lvChargePad.func_77973_b()), newMeta, 3)) {
/*     */           
/* 191 */           TileEntity newTile = world.func_147438_o(x, y, z);
/* 192 */           world.func_147459_d(x, y, z, Block.func_149634_a(Ic2Items.lvChargePad.func_77973_b()));
/* 193 */           if (newTile instanceof TileEntityChargePad)
/*     */           {
/* 195 */             if (!player.field_71075_bZ.field_75098_d)
/*     */             {
/* 197 */               stack.field_77994_a--;
/*     */             }
/* 199 */             TileEntityChargePad newPad = (TileEntityChargePad)newTile;
/* 200 */             newPad.inv.func_70299_a(0, itemInv[0]);
/* 201 */             if (pad.type.getTier() > 1) {
/*     */               
/* 203 */               newPad.inv.func_70299_a(1, itemInv[1]);
/* 204 */               newPad.inv.func_70299_a(2, itemInv[2]);
/*     */             } 
/* 206 */             int dest = (newPad.type.getTier() > 1) ? 3 : 1;
/* 207 */             for (int src = (pad.type.getTier() > 1) ? 3 : 1; src < itemInv.length && dest < newPad.inv.func_70302_i_(); dest++) {
/*     */               
/* 209 */               newPad.inv.func_70299_a(dest, itemInv[src]);
/* 210 */               itemInv[src] = null;
/* 211 */               src++;
/*     */             } 
/* 213 */             newPad.setFacing(pad.getFacing());
/* 214 */             newPad.updateUpgrades();
/* 215 */             world.func_147471_g(x, y, z);
/* 216 */             world.func_147459_d(x, y, z, Block.func_149634_a(Ic2Items.lvChargePad.func_77973_b()));
/* 217 */             newPad.storedEnergy = storedEnergy;
/* 218 */             return true;
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 223 */     } else if (meta == 1) {
/*     */       
/* 225 */       TileEntity tile = world.func_147438_o(x, y, z);
/* 226 */       if (tile instanceof TileEntityElectricMFE) {
/*     */         
/* 228 */         TileEntityElectricMFE mfe = (TileEntityElectricMFE)tile;
/* 229 */         int stored = mfe.energy;
/* 230 */         ItemStack[] items = mfe.inventory;
/* 231 */         mfe.inventory = new ItemStack[2];
/* 232 */         if (world.field_72995_K)
/*     */         {
/* 234 */           return false;
/*     */         }
/* 236 */         if (world.func_147465_d(x, y, z, Block.func_149634_a(Ic2Items.mfsUnit.func_77973_b()), Ic2Items.mfsUnit.func_77960_j(), 3)) {
/*     */           
/* 238 */           TileEntity newTile = world.func_147438_o(x, y, z);
/* 239 */           if (newTile instanceof TileEntityElectricMFSU) {
/*     */             
/* 241 */             if (!player.field_71075_bZ.field_75098_d)
/*     */             {
/* 243 */               stack.field_77994_a--;
/*     */             }
/* 245 */             TileEntityElectricMFSU mfsu = (TileEntityElectricMFSU)newTile;
/* 246 */             mfsu.inventory = items;
/* 247 */             mfsu.energy = stored;
/* 248 */             mfsu.setFacing(mfe.getFacing());
/* 249 */             world.func_147471_g(x, y, z);
/* 250 */             world.func_147459_d(x, y, z, Block.func_149634_a(Ic2Items.lvChargePad.func_77973_b()));
/* 251 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 256 */     return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemChargePadUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */