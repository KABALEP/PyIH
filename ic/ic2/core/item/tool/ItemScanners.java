/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.item.IElectricItemManager;
/*     */ import ic2.api.item.ISpecialElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.IElectricHelper;
/*     */ import ic2.core.util.IExtraData;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ 
/*     */ public class ItemScanners
/*     */   extends ItemScanner
/*     */   implements IExtraData, ISpecialElectricItem
/*     */ {
/*  36 */   private IElectricItemManager manager = new ScannerManager();
/*     */ 
/*     */   
/*     */   public ItemScanners(int index) {
/*  40 */     super(index, 2);
/*  41 */     func_77627_a(true);
/*  42 */     func_77656_e(0);
/*  43 */     func_77655_b("itemMetaScanners");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack par1, EntityPlayer par2, List<String> par3, boolean par4) {
/*  50 */     if (getType(par1) == ScannerType.SettedOV)
/*     */     {
/*  52 */       par3.add(StatCollector.func_74837_a("itemInfo.scannerLevel.name", new Object[] { Integer.valueOf(getScanningLevel(par1)) }));
/*     */     }
/*  54 */     if (par1.func_77960_j() > 3)
/*     */     {
/*  56 */       if (GuiScreen.func_146272_n()) {
/*     */         
/*  58 */         par3.add(StatCollector.func_74838_a("itemInfo.scannerOres.name"));
/*  59 */         par3.addAll(getOresFromType(getType(par1), par1));
/*     */       }
/*     */       else {
/*     */         
/*  63 */         par3.add(StatCollector.func_74837_a("itemInfo.pressShiftOreInfo.name", new Object[] { EnumChatFormatting.AQUA, EnumChatFormatting.GRAY }));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public List<String> getOresFromType(ScannerType par1, ItemStack par2) {
/*     */     int i;
/*  70 */     List<ItemStack> pos, items = new ArrayList<>();
/*  71 */     switch (par1) {
/*     */       
/*     */       case HighOV:
/*  74 */         for (i = 5; i <= IC2.maxOreValue; i++) {
/*     */           
/*  76 */           List<ItemStack> list = (List<ItemStack>)IC2.oreValues.get(Integer.valueOf(i));
/*  77 */           if (list != null && list.size() > 0)
/*     */           {
/*  79 */             items.addAll(list);
/*     */           }
/*     */         } 
/*     */         break;
/*     */       case LowOV:
/*  84 */         for (i = 0; i <= 2; i++) {
/*     */           
/*  86 */           List<ItemStack> list = (List<ItemStack>)IC2.oreValues.get(Integer.valueOf(i));
/*  87 */           if (list != null && list.size() > 0)
/*     */           {
/*  89 */             items.addAll(list);
/*     */           }
/*     */         } 
/*     */         break;
/*     */       case MedOV:
/*  94 */         for (i = 3; i <= 4; i++) {
/*     */           
/*  96 */           List<ItemStack> list = (List<ItemStack>)IC2.oreValues.get(Integer.valueOf(i));
/*  97 */           if (list != null && list.size() > 0)
/*     */           {
/*  99 */             items.addAll(list);
/*     */           }
/*     */         } 
/*     */         break;
/*     */       case SettedOV:
/* 104 */         pos = (List<ItemStack>)IC2.oreValues.get(Integer.valueOf(getScanningLevel(par2)));
/* 105 */         if (pos != null && pos.size() > 0)
/*     */         {
/* 107 */           items.addAll(pos);
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 114 */     List<String> results = new ArrayList<>();
/* 115 */     List<String> oreNames = new ArrayList<>();
/* 116 */     for (int j = 0; j < items.size(); j++) {
/*     */ 
/*     */       
/*     */       try {
/* 120 */         ItemStack item = items.get(j);
/* 121 */         boolean found = false;
/* 122 */         for (int id : OreDictionary.getOreIDs(item)) {
/*     */           
/* 124 */           String oreID = OreDictionary.getOreName(id);
/* 125 */           if (oreNames.contains(oreID)) {
/*     */             
/* 127 */             found = true;
/*     */             break;
/*     */           } 
/* 130 */           oreNames.add(oreID);
/*     */         } 
/* 132 */         if (!found) {
/*     */ 
/*     */ 
/*     */           
/* 136 */           String text = item.func_82833_r();
/*     */           
/* 138 */           if (!results.contains(text))
/*     */           {
/* 140 */             results.add(text);
/*     */           }
/*     */         } 
/* 143 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/* 147 */     return results;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/* 154 */     return Ic2Icons.getTexture("i1")[this.iconIndex + par1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack item) {
/* 160 */     return "item." + getType(item).getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean showDurabilityBar(ItemStack stack) {
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDurabilityForDisplay(ItemStack stack) {
/* 172 */     return 1.0D - this.manager.getCharge(stack) / getMaxCharge(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> itemList) {
/* 179 */     for (ScannerType type : ScannerType.values()) {
/*     */       
/* 181 */       ItemStack empty = new ItemStack(par1, 1, type.ordinal());
/* 182 */       ItemStack full = new ItemStack(par1, 1, type.ordinal());
/* 183 */       ElectricItem.manager.charge(full, 2.147483647E9D, 2147483647, true, false);
/* 184 */       itemList.add(empty);
/* 185 */       itemList.add(full);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int valueOfArea(ItemStack stack, World worldObj, int x, int y, int z, int range, boolean oreValue) {
/* 192 */     ScannerType type = getType(stack);
/* 193 */     if (type == ScannerType.LowOV || type == ScannerType.MedOV || type == ScannerType.HighOV || type == ScannerType.SettedOV) {
/*     */       
/* 195 */       int totalScore = 0;
/* 196 */       int blocksScanned = 0;
/* 197 */       for (int blockY = y; blockY > 0; blockY--) {
/*     */         
/* 199 */         for (int blockX = x - range; blockX <= x + range; blockX++) {
/*     */           
/* 201 */           for (int blockZ = z - range; blockZ <= z + range; blockZ++) {
/*     */             
/* 203 */             Block blockId = worldObj.func_147439_a(blockX, blockY, blockZ);
/* 204 */             int metaData = worldObj.func_72805_g(blockX, blockY, blockZ);
/* 205 */             if (isValuableOre(stack, blockId, metaData))
/*     */             {
/* 207 */               totalScore++;
/*     */             }
/* 209 */             blocksScanned++;
/*     */           } 
/*     */         } 
/*     */       } 
/* 213 */       return (blocksScanned > 0) ? (int)(1000.0D * totalScore / blocksScanned) : 0;
/*     */     } 
/*     */     
/* 216 */     return super.valueOfArea(stack, worldObj, x, y, z, range, oreValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/* 222 */     if (entityplayer.func_70093_af() && getType(itemstack) == ScannerType.SettedOV) {
/*     */       
/* 224 */       if (world.field_72995_K)
/*     */       {
/* 226 */         return itemstack;
/*     */       }
/* 228 */       int scanningLevel = getScanningLevel(itemstack);
/* 229 */       scanningLevel++;
/* 230 */       if (scanningLevel > IC2.maxOreValue)
/*     */       {
/* 232 */         scanningLevel = 0;
/*     */       }
/* 234 */       setScanningLevel(itemstack, scanningLevel);
/* 235 */       IC2.platform.messagePlayer(entityplayer, "[Scanner]: Setted Scanning Level to: " + scanningLevel);
/* 236 */       return itemstack;
/*     */     } 
/* 238 */     return super.func_77659_a(itemstack, world, entityplayer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRange(ItemStack par1) {
/* 244 */     return getType(par1).getRange();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean searchOreValue(ItemStack par1) {
/* 250 */     return getType(par1).isOreValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyCost(ItemStack par1) {
/* 256 */     return getType(par1).getEUCost();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOreValue(ItemStack par1, Block block, int metaData) {
/* 262 */     ScannerType type = getType(par1);
/* 263 */     int value = super.getOreValue(par1, block, metaData);
/* 264 */     if (type == ScannerType.LowOV)
/*     */     {
/* 266 */       return (value <= 2) ? 1 : 0;
/*     */     }
/* 268 */     if (type == ScannerType.MedOV)
/*     */     {
/* 270 */       return (value > 2 && value <= 4) ? 1 : 0;
/*     */     }
/* 272 */     if (type == ScannerType.HighOV)
/*     */     {
/* 274 */       return (value > 4) ? 1 : 0;
/*     */     }
/* 276 */     if (type == ScannerType.SettedOV)
/*     */     {
/* 278 */       return (value == getScanningLevel(par1)) ? 1 : 0;
/*     */     }
/* 280 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   private ScannerType getType(ItemStack par1) {
/* 285 */     return ScannerType.values()[par1.func_77960_j()];
/*     */   }
/*     */ 
/*     */   
/*     */   private int getScanningLevel(ItemStack par1) {
/* 290 */     return StackUtil.getOrCreateNbtData(par1).func_74762_e("ScanLevel");
/*     */   }
/*     */ 
/*     */   
/*     */   private void setScanningLevel(ItemStack par1, int lvl) {
/* 295 */     StackUtil.getOrCreateNbtData(par1).func_74768_a("ScanLevel", lvl);
/*     */   }
/*     */   
/*     */   public enum ScannerType
/*     */   {
/* 300 */     RangedOD(7, false, 150, "rangedScanner.OD"),
/* 301 */     RangedOV(8, true, 750, "rangedScanner.OV"),
/* 302 */     BigRangedOD(9, false, 300, "bigRangedScanner.OD"),
/* 303 */     BigRangedOV(10, true, 1750, "bigRangedScanner.OV"),
/* 304 */     LowOV(6, false, 600, "LowOreValueScanner"),
/* 305 */     MedOV(6, false, 800, "MedOreValueScanner"),
/* 306 */     HighOV(6, false, 1000, "HighOreValueScanner"),
/* 307 */     SettedOV(6, false, 1250, "settedOreValueScanner");
/*     */     
/*     */     int range;
/*     */     
/*     */     int cost;
/*     */     boolean oreValue;
/*     */     String name;
/*     */     
/*     */     ScannerType(int range, boolean oreValue, int cost, String name) {
/* 316 */       this.range = range;
/* 317 */       this.oreValue = oreValue;
/* 318 */       this.cost = cost;
/* 319 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 324 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRange() {
/* 329 */       return this.range;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isOreValue() {
/* 334 */       return this.oreValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getEUCost() {
/* 339 */       return this.cost;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 346 */     Ic2Items.rangedODScanner = new ItemStack((Item)this, 1, 0);
/* 347 */     Ic2Items.rangedOVScanner = new ItemStack((Item)this, 1, 1);
/* 348 */     Ic2Items.bigRangedODScanner = new ItemStack((Item)this, 1, 2);
/* 349 */     Ic2Items.bigRangedOVScanner = new ItemStack((Item)this, 1, 3);
/* 350 */     Ic2Items.lowOreValueScanner = new ItemStack((Item)this, 1, 4);
/* 351 */     Ic2Items.medOreValueScanner = new ItemStack((Item)this, 1, 5);
/* 352 */     Ic2Items.highOreValueScanner = new ItemStack((Item)this, 1, 6);
/* 353 */     Ic2Items.setOreValueScanner = new ItemStack((Item)this, 1, 7);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IElectricItemManager getManager(ItemStack itemStack) {
/* 359 */     return this.manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class ScannerManager
/*     */     implements IElectricItemManager
/*     */   {
/*     */     public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
/* 369 */       IElectricItem item = (IElectricItem)stack.func_77973_b();
/*     */       
/* 371 */       assert item.getMaxCharge(stack) > 0.0D;
/*     */       
/* 373 */       if (amount < 0.0D || stack.field_77994_a > 1 || item.getTier(stack) > tier) {
/* 374 */         return 0.0D;
/*     */       }
/* 376 */       if (!ignoreTransferLimit && amount > item.getTransferLimit(stack)) {
/* 377 */         amount = item.getTransferLimit(stack);
/*     */       }
/* 379 */       NBTTagCompound tNBT = StackUtil.getOrCreateNbtData(stack);
/* 380 */       int tNewCharge = tNBT.func_74762_e("charge");
/*     */       
/* 382 */       if (amount > item.getMaxCharge(stack) - tNewCharge) {
/* 383 */         amount = item.getMaxCharge(stack) - tNewCharge;
/*     */       }
/* 385 */       if (!simulate) {
/*     */         
/* 387 */         tNewCharge = (int)(tNewCharge + amount);
/*     */         
/* 389 */         if (tNewCharge > 0) {
/*     */           
/* 391 */           tNBT.func_74768_a("charge", tNewCharge);
/*     */         }
/*     */         else {
/*     */           
/* 395 */           tNBT.func_82580_o("charge");
/* 396 */           if (tNBT.func_150296_c().isEmpty())
/*     */           {
/* 398 */             stack.func_77982_d(null);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 403 */       return amount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
/* 410 */       IElectricItem item = (IElectricItem)stack.func_77973_b();
/*     */       
/* 412 */       assert item.getMaxCharge(stack) > 0.0D;
/*     */       
/* 414 */       if (amount < 0.0D || stack.field_77994_a > 1 || item.getTier(stack) > tier)
/*     */       {
/* 416 */         return 0.0D;
/*     */       }
/* 418 */       if (externally && !item.canProvideEnergy(stack))
/*     */       {
/* 420 */         return 0.0D;
/*     */       }
/* 422 */       if (!ignoreTransferLimit && amount > item.getTransferLimit(stack))
/*     */       {
/* 424 */         amount = item.getTransferLimit(stack);
/*     */       }
/*     */       
/* 427 */       NBTTagCompound tNBT = StackUtil.getOrCreateNbtData(stack);
/* 428 */       int tNewCharge = tNBT.func_74762_e("charge");
/*     */       
/* 430 */       if (amount > tNewCharge)
/*     */       {
/* 432 */         amount = tNewCharge;
/*     */       }
/*     */       
/* 435 */       if (!simulate) {
/*     */         
/* 437 */         tNewCharge = (int)(tNewCharge - amount);
/*     */         
/* 439 */         if (tNewCharge > 0) {
/*     */           
/* 441 */           tNBT.func_74768_a("charge", tNewCharge);
/*     */         }
/*     */         else {
/*     */           
/* 445 */           tNBT.func_82580_o("charge");
/* 446 */           if (tNBT.func_150296_c().isEmpty())
/*     */           {
/* 448 */             stack.func_77982_d(null);
/*     */           }
/*     */         } 
/*     */       } 
/* 452 */       return amount;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double getCharge(ItemStack stack) {
/* 458 */       return ElectricItem.manager.discharge(stack, 2.147483647E9D, 2147483647, true, false, true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean canUse(ItemStack stack, double amount) {
/* 464 */       return (ElectricItem.manager.getCharge(stack) >= amount);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
/* 470 */       if (!IC2.platform.isSimulating())
/*     */       {
/* 472 */         return false;
/*     */       }
/*     */       
/* 475 */       ElectricItem.manager.chargeFromArmor(stack, entity);
/*     */       
/* 477 */       double transfer = ElectricItem.manager.discharge(stack, amount, 2147483647, true, false, true);
/*     */       
/* 479 */       if (transfer == amount) {
/*     */         
/* 481 */         ElectricItem.manager.discharge(stack, amount, 2147483647, true, false, false);
/* 482 */         ElectricItem.manager.chargeFromArmor(stack, entity);
/* 483 */         return true;
/*     */       } 
/* 485 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void chargeFromArmor(ItemStack stack, EntityLivingBase entity) {
/* 492 */       if (!IC2.platform.isSimulating() || entity == null) {
/*     */         return;
/*     */       }
/* 495 */       boolean inventoryChanged = false;
/*     */       
/* 497 */       if (entity instanceof EntityPlayer && IC2.modul.containsKey("Baubles Modul"))
/*     */       {
/* 499 */         inventoryChanged = ((IElectricHelper)IC2.modul.get("Baubles Modul")).chargeFromArmor(stack, (EntityPlayer)entity);
/*     */       }
/*     */       
/* 502 */       for (int i = 0; i < 4; i++) {
/*     */         
/* 504 */         ItemStack armorItemStack = entity.func_71124_b(i + 1);
/*     */         
/* 506 */         if (armorItemStack != null && armorItemStack.func_77973_b() instanceof IElectricItem) {
/*     */           
/* 508 */           IElectricItem armorItem = (IElectricItem)armorItemStack.func_77973_b();
/*     */           
/* 510 */           if (armorItem.canProvideEnergy(armorItemStack) && armorItem.getTier(armorItemStack) >= ((IElectricItem)stack.func_77973_b()).getTier(stack)) {
/*     */             
/* 512 */             double transfer = ElectricItem.manager.charge(stack, 2.147483647E9D, 2147483647, true, true);
/*     */             
/* 514 */             transfer = ElectricItem.manager.discharge(armorItemStack, transfer, 2147483647, true, true, false);
/*     */             
/* 516 */             if (transfer > 0.0D) {
/*     */               
/* 518 */               ElectricItem.manager.charge(stack, transfer, 2147483647, true, false);
/*     */               
/* 520 */               inventoryChanged = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 526 */       if (inventoryChanged && entity instanceof EntityPlayer)
/*     */       {
/* 528 */         ((EntityPlayer)entity).field_71070_bA.func_75142_b();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getToolTip(ItemStack stack) {
/* 535 */       IElectricItem item = (IElectricItem)stack.func_77973_b();
/* 536 */       return ElectricItem.manager.getCharge(stack) + "/" + item.getMaxCharge(stack) + " EU";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemScanners.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */