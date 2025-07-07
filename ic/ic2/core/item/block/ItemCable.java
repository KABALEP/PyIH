/*     */ package ic2.core.item.block;
/*     */ 
/*     */ import ic2.api.item.IBoxable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.wiring.BlockCable;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.util.IExtraData;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemCable
/*     */   extends ItemIC2
/*     */   implements IBoxable, IExtraData {
/*     */   public ItemCable(int j) {
/*  27 */     super(j);
/*  28 */     func_77627_a(true);
/*  29 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*  30 */     func_77655_b("ic2cable");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_77617_a(int i) {
/*  36 */     return Ic2Icons.getTexture("i0")[this.iconIndex + i];
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemstack) {
/*  41 */     int meta = itemstack.func_77960_j();
/*  42 */     switch (meta) {
/*     */ 
/*     */       
/*     */       case 0:
/*  46 */         return "itemCable";
/*     */ 
/*     */       
/*     */       case 1:
/*  50 */         return "itemCableO";
/*     */ 
/*     */       
/*     */       case 2:
/*  54 */         return "itemGoldCable";
/*     */ 
/*     */       
/*     */       case 3:
/*  58 */         return "itemGoldCableI";
/*     */ 
/*     */       
/*     */       case 4:
/*  62 */         return "itemGoldCableII";
/*     */ 
/*     */       
/*     */       case 5:
/*  66 */         return "itemIronCable";
/*     */ 
/*     */       
/*     */       case 6:
/*  70 */         return "itemIronCableI";
/*     */ 
/*     */       
/*     */       case 7:
/*  74 */         return "itemIronCableII";
/*     */ 
/*     */       
/*     */       case 8:
/*  78 */         return "itemIronCableIIII";
/*     */ 
/*     */       
/*     */       case 9:
/*  82 */         return "itemGlassCable";
/*     */ 
/*     */       
/*     */       case 10:
/*  86 */         return "itemTinCable";
/*     */ 
/*     */       
/*     */       case 11:
/*  90 */         return "itemDetectorCable";
/*     */ 
/*     */       
/*     */       case 12:
/*  94 */         return "itemSplitterCable";
/*     */ 
/*     */       
/*     */       case 13:
/*  98 */         return "itemBronzeCable";
/*     */ 
/*     */       
/*     */       case 14:
/* 102 */         return "itemBronzeCableI";
/*     */ 
/*     */       
/*     */       case 15:
/* 106 */         return "itemBronzeCableII";
/*     */     } 
/*     */ 
/*     */     
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float a, float b, float c) {
/* 117 */     Block block = world.func_147439_a(x, y, z);
/*     */     
/* 119 */     if (block == Blocks.field_150431_aC && (world.func_72805_g(x, y, z) & 0x7) < 1) {
/*     */       
/* 121 */       side = 1;
/*     */     }
/* 123 */     else if (block != Blocks.field_150395_bd && block != Blocks.field_150329_H && block != Blocks.field_150330_I && !block.isReplaceable((IBlockAccess)world, x, y, z)) {
/*     */       
/* 125 */       if (side == 0)
/*     */       {
/* 127 */         y--;
/*     */       }
/*     */       
/* 130 */       if (side == 1)
/*     */       {
/* 132 */         y++;
/*     */       }
/*     */       
/* 135 */       if (side == 2)
/*     */       {
/* 137 */         z--;
/*     */       }
/*     */       
/* 140 */       if (side == 3)
/*     */       {
/* 142 */         z++;
/*     */       }
/*     */       
/* 145 */       if (side == 4)
/*     */       {
/* 147 */         x--;
/*     */       }
/*     */       
/* 150 */       if (side == 5)
/*     */       {
/* 152 */         x++;
/*     */       }
/*     */     } 
/*     */     
/* 156 */     if (itemstack.field_77994_a == 0)
/*     */     {
/* 158 */       return false;
/*     */     }
/* 160 */     if (!entityplayer.func_82247_a(x, y, z, side, itemstack))
/*     */     {
/* 162 */       return false;
/*     */     }
/* 164 */     if (y == 255 && block.func_149688_o().func_76220_a())
/*     */     {
/* 166 */       return false;
/*     */     }
/* 168 */     BlockCable cable = (BlockCable)Block.func_149634_a(Ic2Items.insulatedCopperCableBlock.func_77973_b());
/* 169 */     if ((world.func_147437_c(x, y, z) || world.func_147472_a(Block.func_149634_a(Ic2Items.insulatedCopperCableBlock.func_77973_b()), x, y, z, false, side, (Entity)entityplayer, itemstack)) && world.func_72855_b(cable.func_149668_a(world, x, y, z)) && world.func_147465_d(x, y, z, (Block)cable, itemstack.func_77960_j(), 3)) {
/*     */       
/* 171 */       cable.func_149714_e(world, x, y, z, side);
/* 172 */       cable.func_149689_a(world, x, y, z, (EntityLivingBase)entityplayer, itemstack);
/* 173 */       if (!entityplayer.field_71075_bZ.field_75098_d)
/*     */       {
/* 175 */         itemstack.field_77994_a--;
/*     */       }
/* 177 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_150895_a(Item i, CreativeTabs tabs, List<ItemStack> itemList) {
/* 187 */     for (int meta = 0; meta < 32767; meta++) {
/*     */       
/* 189 */       ItemStack stack = new ItemStack((Item)this, 1, meta);
/* 190 */       if (func_77667_c(stack) == null) {
/*     */         break;
/*     */       }
/*     */       
/* 194 */       itemList.add(stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 201 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 207 */     Ic2Items.copperCableItem = new ItemStack((Item)this, 1, 1);
/* 208 */     Ic2Items.insulatedCopperCableItem = new ItemStack((Item)this, 1, 0);
/* 209 */     Ic2Items.goldCableItem = new ItemStack((Item)this, 1, 2);
/* 210 */     Ic2Items.insulatedGoldCableItem = new ItemStack((Item)this, 1, 3);
/* 211 */     Ic2Items.doubleInsulatedGoldCableItem = new ItemStack((Item)this, 1, 4);
/* 212 */     Ic2Items.ironCableItem = new ItemStack((Item)this, 1, 5);
/* 213 */     Ic2Items.insulatedIronCableItem = new ItemStack((Item)this, 1, 6);
/* 214 */     Ic2Items.doubleInsulatedIronCableItem = new ItemStack((Item)this, 1, 7);
/* 215 */     Ic2Items.trippleInsulatedIronCableItem = new ItemStack((Item)this, 1, 8);
/* 216 */     Ic2Items.glassFiberCableItem = new ItemStack((Item)this, 1, 9);
/* 217 */     Ic2Items.tinCableItem = new ItemStack((Item)this, 1, 10);
/* 218 */     Ic2Items.detectorCableItem = new ItemStack((Item)this, 1, 11);
/* 219 */     Ic2Items.splitterCableItem = new ItemStack((Item)this, 1, 12);
/* 220 */     Ic2Items.bronzeCableItem = new ItemStack((Item)this, 1, 13);
/* 221 */     Ic2Items.insulatedBronzeCableItem = new ItemStack((Item)this, 1, 14);
/* 222 */     Ic2Items.doubleInsulatedBronzeCableItem = new ItemStack((Item)this, 1, 15);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemCable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */