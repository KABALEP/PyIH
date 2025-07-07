/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.event.RetextureEvent;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.item.BasicElectricItem;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ public class ItemTextureCopier extends BasicElectricItem {
/*     */   public static final int copyCost = 10000;
/*     */   public static final int drawCost = 1500;
/*     */   
/*     */   public ItemTextureCopier() {
/*  30 */     super(81);
/*  31 */     setSpriteID("i1");
/*  32 */     this.maxCharge = 100000;
/*  33 */     this.tier = 2;
/*  34 */     this.transferLimit = 250;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean adv) {
/*  41 */     if (hasBlock(stack)) {
/*     */       
/*  43 */       NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/*  44 */       Block block = Block.func_149684_b(nbt.func_74779_i("BlockID"));
/*  45 */       int meta = nbt.func_74762_e("BlockMeta");
/*  46 */       int blockSide = nbt.func_74762_e("BlockSide");
/*  47 */       list.add(StatCollector.func_74837_a("itemInfo.storedBlock.name", new Object[] { (new ItemStack(block, 1, meta)).func_82833_r() }));
/*  48 */       list.add(StatCollector.func_74837_a("itemInfo.storedBlockSide.name", new Object[] { Integer.valueOf(blockSide) }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/*  55 */     if (IC2.platform.isRendering()) {
/*     */       
/*  57 */       if (player.func_70093_af())
/*     */       {
/*  59 */         if (!ElectricItem.manager.canUse(stack, 10000.0D))
/*     */         {
/*  61 */           return true;
/*     */         }
/*  63 */         Block block = world.func_147439_a(x, y, z);
/*  64 */         if (!world.func_147437_c(x, y, z) && isValidBlock(block)) {
/*     */           
/*  66 */           int meta = world.func_72805_g(x, y, z);
/*     */           
/*     */           try {
/*  69 */             IIcon texture = block.func_149691_a(side, meta);
/*  70 */             IIcon worldTexture = block.func_149673_e((IBlockAccess)world, x, y, z, side);
/*  71 */             if (texture == null || texture != worldTexture || isMissingTexture(texture))
/*     */             {
/*  73 */               return true;
/*     */             }
/*  75 */             return false;
/*     */           }
/*  77 */           catch (Exception exception) {}
/*     */         } 
/*     */ 
/*     */         
/*  81 */         return false;
/*     */       
/*     */       }
/*     */     
/*     */     }
/*  86 */     else if (player.func_70093_af()) {
/*     */       
/*  88 */       if (!ElectricItem.manager.canUse(stack, 10000.0D))
/*     */       {
/*  90 */         return false;
/*     */       }
/*  92 */       Block block = world.func_147439_a(x, y, z);
/*  93 */       if (!world.func_147437_c(x, y, z) && isValidBlock(block))
/*     */       {
/*  95 */         int meta = world.func_72805_g(x, y, z);
/*  96 */         String blockID = Block.field_149771_c.func_148750_c(block);
/*  97 */         NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/*  98 */         if (blockID == nbt.func_74779_i("BlockID") && meta == nbt.func_74762_e("BlockMeta") && side == nbt.func_74762_e("BlockSide"))
/*     */         {
/* 100 */           return false;
/*     */         }
/* 102 */         nbt.func_74778_a("BlockID", blockID);
/* 103 */         nbt.func_74768_a("BlockMeta", meta);
/* 104 */         nbt.func_74768_a("BlockSide", side);
/* 105 */         ElectricItem.manager.use(stack, 10000.0D, (EntityLivingBase)player);
/* 106 */         return true;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 111 */       if (!ElectricItem.manager.canUse(stack, 1500.0D) || !hasBlock(stack))
/*     */       {
/* 113 */         return false;
/*     */       }
/* 115 */       NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/* 116 */       Block block = Block.func_149684_b(nbt.func_74779_i("BlockID"));
/* 117 */       int meta = nbt.func_74762_e("BlockMeta");
/* 118 */       int blockSide = nbt.func_74762_e("BlockSide");
/* 119 */       RetextureEvent event = new RetextureEvent(world, x, y, z, side, block, meta, blockSide);
/* 120 */       MinecraftForge.EVENT_BUS.post((Event)event);
/* 121 */       if (event.applied) {
/*     */         
/* 123 */         ElectricItem.manager.use(stack, 1500.0D, (EntityLivingBase)player);
/* 124 */         return true;
/*     */       } 
/* 126 */       return false;
/*     */     } 
/*     */     
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidBlock(Block block) {
/* 134 */     return block.func_149686_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasBlock(ItemStack item) {
/* 139 */     if (item == null)
/*     */     {
/* 141 */       return false;
/*     */     }
/* 143 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(item);
/* 144 */     if (nbt.func_74764_b("BlockID"))
/*     */     {
/* 146 */       return true;
/*     */     }
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static boolean isMissingTexture(IIcon par1) {
/* 154 */     TextureAtlasSprite textureAtlasSprite = Minecraft.func_71410_x().func_147117_R().func_110572_b(par1.func_94215_i());
/* 155 */     if (textureAtlasSprite.func_94215_i().equalsIgnoreCase("missingno"))
/*     */     {
/* 157 */       return true;
/*     */     }
/* 159 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemTextureCopier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */