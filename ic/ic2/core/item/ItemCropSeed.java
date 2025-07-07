/*     */ package ic2.core.item;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.Crops;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.TileEntityCrop;
/*     */ import ic2.core.block.crop.IC2Crops;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ItemCropSeed
/*     */   extends ItemIC2
/*     */ {
/*     */   public ItemCropSeed(int index) {
/*  28 */     super(index);
/*  29 */     func_77625_d(1);
/*  30 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77653_i(ItemStack par1) {
/*  36 */     if (par1 == null)
/*     */     {
/*  38 */       return StatCollector.func_74838_a("item.cropSeedUn.name");
/*     */     }
/*     */     
/*  41 */     int id = getIdFromStack(par1);
/*  42 */     byte scan = getScannedFromStack(par1);
/*  43 */     if (scan < 0)
/*     */     {
/*  45 */       return StatCollector.func_74838_a("item.cropSeedInvalid.name");
/*     */     }
/*  47 */     if (scan == 0)
/*     */     {
/*  49 */       return StatCollector.func_74838_a("item.cropSeedUn.name");
/*     */     }
/*  51 */     if (id >= 0)
/*     */     {
/*  53 */       return StatCollector.func_74837_a("item.cropItem.name", new Object[] { Crops.instance.getCropList()[id].displayName() });
/*     */     }
/*  55 */     return super.func_77653_i(par1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77645_m() {
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRepairable() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getIcon(ItemStack stack, int pass) {
/*  72 */     if (IC2.platform.isRendering())
/*     */     {
/*  74 */       if (getScannedFromStack(stack) >= 1 && isCtrlDown()) {
/*     */         
/*  76 */         Crops crops = Crops.instance;
/*  77 */         if (crops instanceof IC2Crops) {
/*     */           
/*  79 */           ItemStack item = ((IC2Crops)crops).getDisplayItem(getCropCard(stack));
/*  80 */           if (item != null)
/*     */           {
/*  82 */             return item.func_77954_c();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*  87 */     return super.getIcon(stack, pass);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private boolean isCtrlDown() {
/*  93 */     return GuiScreen.func_146271_m();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderPasses(int metadata) {
/*  99 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77623_v() {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> info, boolean debugTooltips) {
/* 110 */     if (getScannedFromStack(stack) >= 4) {
/*     */       
/* 112 */       info.add(EnumChatFormatting.DARK_GREEN + "Gr " + EnumChatFormatting.RESET + getGrowthFromStack(stack));
/* 113 */       info.add(EnumChatFormatting.GOLD + "Ga " + EnumChatFormatting.RESET + getGainFromStack(stack));
/* 114 */       info.add(EnumChatFormatting.DARK_AQUA + "Re " + EnumChatFormatting.RESET + getResistanceFromStack(stack));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float a, float b, float c) {
/* 120 */     if (!(world.func_147438_o(x, y, z) instanceof TileEntityCrop))
/*     */     {
/* 122 */       return false;
/*     */     }
/* 124 */     TileEntityCrop crop = (TileEntityCrop)world.func_147438_o(x, y, z);
/* 125 */     if (crop.tryPlantIn(getIdFromStack(itemstack), 1, getGrowthFromStack(itemstack), getGainFromStack(itemstack), getResistanceFromStack(itemstack), getScannedFromStack(itemstack))) {
/*     */       
/* 127 */       entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c] = null;
/* 128 */       return true;
/*     */     } 
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150895_a(Item id, CreativeTabs tabs, List<ItemStack> items) {
/* 135 */     for (int i = 0; i < (Crops.instance.getCropList()).length; i++) {
/*     */       
/* 137 */       CropCard card = Crops.instance.getCropList()[i];
/* 138 */       if (card != null)
/*     */       {
/* 140 */         items.add(generateItemStackFromValues((short)i, (byte)1, (byte)1, (byte)1, (byte)4));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack generateItemStackFromValues(short id, byte statGrowth, byte statGain, byte statResistance, byte scan) {
/* 147 */     ItemStack is = new ItemStack(Ic2Items.cropSeed.func_77973_b());
/* 148 */     NBTTagCompound tag = new NBTTagCompound();
/* 149 */     tag.func_74777_a("id", id);
/* 150 */     tag.func_74774_a("growth", statGrowth);
/* 151 */     tag.func_74774_a("gain", statGain);
/* 152 */     tag.func_74774_a("resistance", statResistance);
/* 153 */     tag.func_74774_a("scan", scan);
/* 154 */     is.func_77982_d(tag);
/* 155 */     return is;
/*     */   }
/*     */ 
/*     */   
/*     */   public static short getIdFromStack(ItemStack is) {
/* 160 */     if (is.func_77978_p() == null)
/*     */     {
/* 162 */       return -1;
/*     */     }
/* 164 */     return is.func_77978_p().func_74765_d("id");
/*     */   }
/*     */ 
/*     */   
/*     */   public static CropCard getCropCard(ItemStack is) {
/* 169 */     if (is.func_77978_p() == null)
/*     */     {
/* 171 */       return null;
/*     */     }
/* 173 */     return Crops.instance.getCropList()[is.func_77978_p().func_74765_d("id")];
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte getGrowthFromStack(ItemStack is) {
/* 178 */     if (is.func_77978_p() == null)
/*     */     {
/* 180 */       return -1;
/*     */     }
/* 182 */     return is.func_77978_p().func_74771_c("growth");
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte getGainFromStack(ItemStack is) {
/* 187 */     if (is.func_77978_p() == null)
/*     */     {
/* 189 */       return -1;
/*     */     }
/* 191 */     return is.func_77978_p().func_74771_c("gain");
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte getResistanceFromStack(ItemStack is) {
/* 196 */     if (is.func_77978_p() == null)
/*     */     {
/* 198 */       return -1;
/*     */     }
/* 200 */     return is.func_77978_p().func_74771_c("resistance");
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte getScannedFromStack(ItemStack is) {
/* 205 */     if (is.func_77978_p() == null)
/*     */     {
/* 207 */       return -1;
/*     */     }
/* 209 */     return is.func_77978_p().func_74771_c("scan");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void incrementScannedOfStack(ItemStack is) {
/* 214 */     if (is.func_77978_p() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     is.func_77978_p().func_74774_a("scan", (byte)(getScannedFromStack(is) + 1));
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemCropSeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */