/*     */ package ic2.core.item;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.item.IMachineUpgradeItem;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.item.upgrades.BasicExportUpgrade;
/*     */ import ic2.core.item.upgrades.BasicImportUpgrade;
/*     */ import ic2.core.item.upgrades.CreativeUpgrade;
/*     */ import ic2.core.item.upgrades.EfficenyUpgrade;
/*     */ import ic2.core.item.upgrades.EnergyMultiplierUpgrade;
/*     */ import ic2.core.item.upgrades.EnergyStorageUpgrade;
/*     */ import ic2.core.item.upgrades.ExpCollectorUpgrade;
/*     */ import ic2.core.item.upgrades.ExportUpgrade;
/*     */ import ic2.core.item.upgrades.IUpgradeMetaItem;
/*     */ import ic2.core.item.upgrades.ImportUpgrade;
/*     */ import ic2.core.item.upgrades.LoudnessUpgrade;
/*     */ import ic2.core.item.upgrades.MufflerUpgrade;
/*     */ import ic2.core.item.upgrades.MuteUpgrade;
/*     */ import ic2.core.item.upgrades.OverclockerUpgrade;
/*     */ import ic2.core.item.upgrades.RedstoneInverterUpgrade;
/*     */ import ic2.core.item.upgrades.RedstoneSensitivityUpgrade;
/*     */ import ic2.core.item.upgrades.TransformerUpgrade;
/*     */ import ic2.core.util.IExtraData;
/*     */ import ic2.core.util.KeyboardClient;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemUpgradeModule
/*     */   extends ItemIC2
/*     */   implements IExtraData, IMachineUpgradeItem
/*     */ {
/*  56 */   public Map<Integer, IUpgradeMetaItem> upgrades = new HashMap<>();
/*     */ 
/*     */   
/*     */   public ItemUpgradeModule() {
/*  60 */     super(128);
/*  61 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*  62 */     func_77627_a(true);
/*  63 */     func_77655_b("ic2upgrades");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack par1, EntityPlayer par2, List<String> par3, boolean par4) {
/*  70 */     super.func_77624_a(par1, par2, par3, par4);
/*  71 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(par1)));
/*  72 */     if (item != null) {
/*     */       
/*  74 */       if (item.usesDirection())
/*     */       {
/*  76 */         par3.add(StatCollector.func_74837_a("itemInfo.upgradeDirection.name", new Object[] { getDir(par1) }));
/*     */       }
/*  78 */       if (item.usesOwner()) {
/*     */         
/*  80 */         boolean shift = GuiScreen.func_146272_n();
/*  81 */         if (getOwner(par1) == null || shift) {
/*     */           
/*  83 */           par3.add(StatCollector.func_74837_a("itemInfo.setUpgradeOwner.name", new Object[] { ((KeyboardClient)IC2.keyboard).getKey(3) }));
/*  84 */           if (shift)
/*     */           {
/*  86 */             par3.addAll(Arrays.asList(StatCollector.func_74838_a("itemInfo.setUpgradeDirection.name").split("%n ")));
/*     */           }
/*     */         } 
/*  89 */         if (getOwner(par1) != null)
/*     */         {
/*  91 */           par3.add(StatCollector.func_74837_a("itemInfo.upgradeOwner.name", new Object[] { getPlayerName(par1) }));
/*     */         }
/*     */       } 
/*  94 */       item.addInfo(par1, par2, par3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_77617_a(int meta) {
/* 102 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(meta));
/* 103 */     if (item != null)
/*     */     {
/* 105 */       return item.getTexture();
/*     */     }
/* 107 */     return Ic2Icons.getTexture("i0")[176];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack par1) {
/* 113 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(par1)));
/* 114 */     if (item != null)
/*     */     {
/* 116 */       return item.getName();
/*     */     }
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150895_a(Item i, CreativeTabs tabs, List<ItemStack> itemList) {
/* 123 */     for (Integer meta : this.upgrades.keySet()) {
/*     */       
/* 125 */       ItemStack stack = new ItemStack(this, 1, meta.intValue());
/* 126 */       if (func_77667_c(stack) == null) {
/*     */         break;
/*     */       }
/*     */       
/* 130 */       itemList.add(stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
/* 139 */     if (!p_77659_2_.field_72995_K) {
/*     */       
/* 141 */       IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(p_77659_1_)));
/* 142 */       if (IC2.keyboard.isSideinventoryKeyDown(p_77659_3_) && item.usesOwner()) {
/*     */         
/* 144 */         setOwner(p_77659_1_, p_77659_3_);
/* 145 */         IC2.platform.messagePlayer(p_77659_3_, StatCollector.func_74837_a("itemInfo.defineUpgradeOwner.name", new Object[] { p_77659_3_.func_146103_bH().getName() }));
/*     */       }
/* 147 */       else if (item.usesExtraRightClick(p_77659_1_)) {
/*     */         
/* 149 */         item.onRightClick(p_77659_1_, p_77659_2_, p_77659_3_);
/*     */       } 
/*     */     } 
/* 152 */     return super.func_77659_a(p_77659_1_, p_77659_2_, p_77659_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 160 */     Ic2Items.overclockerUpgrade = new ItemStack(this, 1, 0);
/* 161 */     Ic2Items.transformerUpgrade = new ItemStack(this, 1, 1);
/* 162 */     Ic2Items.energyStorageUpgrade = new ItemStack(this, 1, 2);
/* 163 */     Ic2Items.redstoneSUpgrade = new ItemStack(this, 1, 3);
/* 164 */     Ic2Items.redstoneIUpgrade = new ItemStack(this, 1, 4);
/* 165 */     Ic2Items.importBasicUpgrade = new ItemStack(this, 1, 5);
/* 166 */     Ic2Items.importUpgrade = new ItemStack(this, 1, 6);
/* 167 */     Ic2Items.exportBasicUpgrade = new ItemStack(this, 1, 7);
/* 168 */     Ic2Items.exportUpgrade = new ItemStack(this, 1, 8);
/* 169 */     Ic2Items.mufflerUpgrade = new ItemStack(this, 1, 9);
/* 170 */     Ic2Items.muteUpgrade = new ItemStack(this, 1, 10);
/* 171 */     Ic2Items.expCollectorUpgrade = new ItemStack(this, 1, 11);
/* 172 */     Ic2Items.efficencyUpgrade = new ItemStack(this, 1, 12);
/* 173 */     Ic2Items.energyMultiplierUpgrade = new ItemStack(this, 1, 13);
/* 174 */     Ic2Items.loudnessUpgrade = new ItemStack(this, 1, 14);
/* 175 */     Ic2Items.creativeUpgrade = new ItemStack(this, 1, 3000);
/*     */     
/* 177 */     this.upgrades.put(Integer.valueOf(0), new OverclockerUpgrade());
/* 178 */     this.upgrades.put(Integer.valueOf(1), new TransformerUpgrade());
/* 179 */     this.upgrades.put(Integer.valueOf(2), new EnergyStorageUpgrade());
/* 180 */     this.upgrades.put(Integer.valueOf(3), new RedstoneSensitivityUpgrade());
/* 181 */     this.upgrades.put(Integer.valueOf(4), new RedstoneInverterUpgrade());
/* 182 */     this.upgrades.put(Integer.valueOf(5), new BasicImportUpgrade());
/* 183 */     this.upgrades.put(Integer.valueOf(6), new ImportUpgrade());
/* 184 */     this.upgrades.put(Integer.valueOf(7), new BasicExportUpgrade());
/* 185 */     this.upgrades.put(Integer.valueOf(8), new ExportUpgrade());
/* 186 */     this.upgrades.put(Integer.valueOf(9), new MufflerUpgrade());
/* 187 */     this.upgrades.put(Integer.valueOf(10), new MuteUpgrade());
/* 188 */     this.upgrades.put(Integer.valueOf(11), new ExpCollectorUpgrade());
/* 189 */     this.upgrades.put(Integer.valueOf(12), new EfficenyUpgrade());
/* 190 */     this.upgrades.put(Integer.valueOf(13), new EnergyMultiplierUpgrade());
/* 191 */     this.upgrades.put(Integer.valueOf(14), new LoudnessUpgrade());
/* 192 */     this.upgrades.put(Integer.valueOf(3000), new CreativeUpgrade());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraProcessTime(ItemStack upgrade, IMachine machine) {
/* 198 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 199 */     if (item != null)
/*     */     {
/* 201 */       return item.getExtraProcessTime(upgrade, machine);
/*     */     }
/* 203 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getProcessTimeMultiplier(ItemStack upgrade, IMachine machine) {
/* 209 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 210 */     if (item != null)
/*     */     {
/* 212 */       return item.getProcessTimeMultiplier(upgrade, machine);
/*     */     }
/* 214 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraEnergyDemand(ItemStack upgrade, IMachine machine) {
/* 220 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 221 */     if (item != null)
/*     */     {
/* 223 */       return item.getExtraEnergyDemand(upgrade, machine);
/*     */     }
/* 225 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergyDemandMultiplier(ItemStack upgrade, IMachine machine) {
/* 231 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 232 */     if (item != null)
/*     */     {
/* 234 */       return item.getEnergyDemandMultiplier(upgrade, machine);
/*     */     }
/* 236 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraEnergyStorage(ItemStack upgrade, IMachine machine) {
/* 242 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 243 */     if (item != null)
/*     */     {
/* 245 */       return item.getExtraEnergyStorage(upgrade, machine);
/*     */     }
/* 247 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergyStorageMultiplier(ItemStack upgrade, IMachine machine) {
/* 253 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 254 */     if (item != null)
/*     */     {
/* 256 */       return item.getEnergyStorageMultiplier(upgrade, machine);
/*     */     }
/* 258 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraTier(ItemStack upgrade, IMachine machine) {
/* 264 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 265 */     if (item != null)
/*     */     {
/* 267 */       return item.getExtraTier(upgrade, machine);
/*     */     }
/* 269 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onTick(ItemStack upgrade, IMachine machine) {
/* 275 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 276 */     if (item != null && item.needsTick())
/*     */     {
/* 278 */       return item.onTick(upgrade, machine);
/*     */     }
/* 280 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProcessEnd(ItemStack upgrade, IMachine machine, List<ItemStack> results) {
/* 286 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 287 */     if (item != null)
/*     */     {
/* 289 */       item.onProcessEnd(upgrade, machine, results);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useRedstoneinverter(ItemStack upgrade, IMachine machine) {
/* 296 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 297 */     if (item != null)
/*     */     {
/* 299 */       return item.useRedstoneinverter(upgrade, machine);
/*     */     }
/* 301 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInstalling(ItemStack upgrade, IMachine machine) {
/* 307 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 308 */     if (item != null)
/*     */     {
/* 310 */       item.onInstalling(upgrade, machine);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachine.UpgradeType getType(ItemStack par1) {
/* 318 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(par1)));
/* 319 */     if (item != null)
/*     */     {
/* 321 */       return item.getType();
/*     */     }
/* 323 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSoundVolumeMultiplier(ItemStack upgrade, IMachine machine) {
/* 329 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(upgrade)));
/* 330 */     if (item != null)
/*     */     {
/* 332 */       return item.getSoundVolumeMultiplier(upgrade, machine);
/*     */     }
/* 334 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemStackLimit(ItemStack stack) {
/* 342 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(stack)));
/* 343 */     if (item != null)
/*     */     {
/* 345 */       return item.getMaxStackSize();
/*     */     }
/* 347 */     return super.getItemStackLimit(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/* 353 */     IUpgradeMetaItem item = this.upgrades.get(Integer.valueOf(getMeta(stack)));
/* 354 */     if (!world.field_72995_K)
/*     */     {
/* 356 */       if (item != null && item.usesDirection()) {
/*     */         
/* 358 */         ForgeDirection dir = ForgeDirection.getOrientation(side);
/* 359 */         setDirection(stack, player.func_70093_af() ? dir : dir.getOpposite());
/* 360 */         IC2.platform.messagePlayer(player, StatCollector.func_74837_a("itemInfo.defineDirection.name", new Object[] { player.func_70093_af() ? dir : dir.getOpposite() }));
/* 361 */         return true;
/*     */       } 
/*     */     }
/* 364 */     return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMeta(ItemStack par1) {
/* 369 */     return par1.func_77960_j();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getWorldTime(IMachine par1) {
/* 374 */     return ((TileEntity)par1).func_145831_w().func_82737_E();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirection(ItemStack par1, ForgeDirection dir) {
/* 379 */     if (par1 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 383 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 384 */     if (nbt.func_74764_b("UpgradeData")) {
/*     */       
/* 386 */       NBTTagCompound nBTTagCompound = nbt.func_74775_l("UpgradeData");
/* 387 */       nBTTagCompound.func_74768_a("Direction", dir.ordinal());
/*     */       return;
/*     */     } 
/* 390 */     NBTTagCompound data = new NBTTagCompound();
/* 391 */     data.func_74768_a("Direction", dir.ordinal());
/* 392 */     nbt.func_74782_a("UpgradeData", (NBTBase)data);
/*     */   }
/*     */ 
/*     */   
/*     */   public ForgeDirection getDir(ItemStack par1) {
/* 397 */     if (par1 == null)
/*     */     {
/* 399 */       return ForgeDirection.UNKNOWN;
/*     */     }
/* 401 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 402 */     if (!nbt.func_74764_b("UpgradeData"))
/*     */     {
/* 404 */       return ForgeDirection.UNKNOWN;
/*     */     }
/* 406 */     NBTTagCompound data = nbt.func_74775_l("UpgradeData");
/* 407 */     return ForgeDirection.getOrientation(data.func_74762_e("Direction"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getDirection(ItemStack par1) {
/* 412 */     return Direction.fromForgeDirection(getDir(par1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOwner(ItemStack par1, EntityPlayer par2) {
/* 417 */     if (par1 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 421 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 422 */     GameProfile file = par2.func_146103_bH();
/* 423 */     UUID id = file.getId();
/* 424 */     if (nbt.func_74764_b("UpgradeData")) {
/*     */       
/* 426 */       NBTTagCompound nBTTagCompound = nbt.func_74775_l("UpgradeData");
/* 427 */       nBTTagCompound.func_74772_a("PlayerMost", id.getMostSignificantBits());
/* 428 */       nBTTagCompound.func_74772_a("PlayerLeast", id.getLeastSignificantBits());
/* 429 */       nBTTagCompound.func_74778_a("PlayerName", file.getName());
/*     */       return;
/*     */     } 
/* 432 */     NBTTagCompound data = new NBTTagCompound();
/* 433 */     data.func_74772_a("PlayerMost", id.getMostSignificantBits());
/* 434 */     data.func_74772_a("PlayerLeast", id.getLeastSignificantBits());
/* 435 */     data.func_74778_a("PlayerName", file.getName());
/* 436 */     nbt.func_74782_a("UpgradeData", (NBTBase)data);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlayerName(ItemStack par1) {
/* 441 */     if (par1 == null)
/*     */     {
/* 443 */       return null;
/*     */     }
/* 445 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 446 */     if (!nbt.func_74764_b("UpgradeData"))
/*     */     {
/* 448 */       return null;
/*     */     }
/* 450 */     NBTTagCompound data = nbt.func_74775_l("UpgradeData");
/* 451 */     if (data.func_74764_b("PlayerName"))
/*     */     {
/* 453 */       return data.func_74779_i("PlayerName");
/*     */     }
/* 455 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getOwner(ItemStack par1) {
/* 460 */     if (par1 == null)
/*     */     {
/* 462 */       return null;
/*     */     }
/* 464 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 465 */     if (!nbt.func_74764_b("UpgradeData"))
/*     */     {
/* 467 */       return null;
/*     */     }
/* 469 */     NBTTagCompound data = nbt.func_74775_l("UpgradeData");
/* 470 */     if (data.func_74764_b("PlayerMost") && data.func_74764_b("PlayerLeast"))
/*     */     {
/* 472 */       return new UUID(data.func_74763_f("PlayerMost"), data.func_74763_f("PlayerLeast"));
/*     */     }
/* 474 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemUpgradeModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */