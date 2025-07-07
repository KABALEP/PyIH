/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderTextureCopier
/*     */   implements IItemRenderer
/*     */ {
/*     */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/*  24 */     return (type != IItemRenderer.ItemRenderType.FIRST_PERSON_MAP);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/*  30 */     return (type == IItemRenderer.ItemRenderType.ENTITY && !item.func_82839_y());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/*  36 */     if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/*     */       
/*  38 */       RenderItem instance = RenderItem.getInstance();
/*  39 */       instance.func_94149_a(0, 0, item.func_77954_c(), 16, 16);
/*  40 */       if (hasBlock(item))
/*     */       {
/*  42 */         IIcon icon = getIcon(item);
/*  43 */         if (ItemTextureCopier.isMissingTexture(icon)) {
/*     */           return;
/*     */         }
/*     */         
/*  47 */         (Minecraft.func_71410_x()).field_71446_o.func_110577_a(TextureMap.field_110575_b);
/*  48 */         int color = getColor(item);
/*  49 */         GL11.glColor3f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F);
/*  50 */         instance.func_94149_a(2, 2, icon, 8, 8);
/*  51 */         GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*  52 */         (Minecraft.func_71410_x()).field_71446_o.func_110577_a(TextureMap.field_110576_c);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  57 */       if (item.func_82839_y()) {
/*     */         
/*  59 */         GL11.glTranslated(-0.5D, -0.1D, 0.0D);
/*     */       }
/*     */       else {
/*     */         
/*  63 */         GL11.glTranslated(-0.5D, -0.5D, 0.0D);
/*     */       } 
/*  65 */       IIcon icon = item.func_77954_c();
/*  66 */       ItemRenderer.func_78439_a(Tessellator.field_78398_a, icon.func_94212_f(), icon.func_94206_g(), icon.func_94209_e(), icon.func_94210_h(), icon.func_94211_a(), icon.func_94216_b(), 0.0625F);
/*  67 */       if (hasBlock(item)) {
/*     */         
/*  69 */         IIcon blockIcon = getIcon(item);
/*  70 */         if (ItemTextureCopier.isMissingTexture(blockIcon)) {
/*     */           return;
/*     */         }
/*     */         
/*  74 */         (Minecraft.func_71410_x()).field_71446_o.func_110577_a(TextureMap.field_110575_b);
/*  75 */         int color = getColor(item);
/*  76 */         GL11.glColor3f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F);
/*  77 */         renderIcon(blockIcon, 0.875D, 0.875D, 0.375D, 0.375D, 0.001D, 0.0F, 0.0F, 1.0F);
/*  78 */         renderIcon(blockIcon, 0.875D, 0.875D, 0.375D, 0.375D, -0.0635D, 0.0F, 0.0F, -1.0F);
/*  79 */         GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*  80 */         (Minecraft.func_71410_x()).field_71446_o.func_110577_a(TextureMap.field_110576_c);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasBlock(ItemStack item) {
/*  87 */     return ItemTextureCopier.hasBlock(item);
/*     */   }
/*     */ 
/*     */   
/*     */   private IIcon getIcon(ItemStack par1) {
/*  92 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/*  93 */     Block block = Block.func_149684_b(nbt.func_74779_i("BlockID"));
/*  94 */     int meta = nbt.func_74762_e("BlockMeta");
/*  95 */     int blockSide = nbt.func_74762_e("BlockSide");
/*  96 */     return block.func_149691_a(blockSide, meta);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getColor(ItemStack item) {
/* 101 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(item);
/* 102 */     Block block = Block.func_149684_b(nbt.func_74779_i("BlockID"));
/*     */     
/*     */     try {
/* 105 */       return block.func_149635_D();
/*     */     }
/* 107 */     catch (Exception exception) {
/*     */ 
/*     */       
/* 110 */       return 16777215;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderIcon(IIcon icon, double xStart, double yStart, double xEnd, double yEnd, double z, float nx, float ny, float nz) {
/* 115 */     Tessellator tessellator = Tessellator.field_78398_a;
/*     */     
/* 117 */     tessellator.func_78382_b();
/* 118 */     tessellator.func_78375_b(nx, ny, nz);
/*     */     
/* 120 */     if (nz > 0.0F) {
/*     */       
/* 122 */       tessellator.func_78374_a(xStart, yStart, z, icon.func_94209_e(), icon.func_94206_g());
/* 123 */       tessellator.func_78374_a(xEnd, yStart, z, icon.func_94212_f(), icon.func_94206_g());
/* 124 */       tessellator.func_78374_a(xEnd, yEnd, z, icon.func_94212_f(), icon.func_94210_h());
/* 125 */       tessellator.func_78374_a(xStart, yEnd, z, icon.func_94209_e(), icon.func_94210_h());
/*     */     }
/*     */     else {
/*     */       
/* 129 */       tessellator.func_78374_a(xStart, yEnd, z, icon.func_94209_e(), icon.func_94210_h());
/* 130 */       tessellator.func_78374_a(xEnd, yEnd, z, icon.func_94212_f(), icon.func_94210_h());
/* 131 */       tessellator.func_78374_a(xEnd, yStart, z, icon.func_94212_f(), icon.func_94206_g());
/* 132 */       tessellator.func_78374_a(xStart, yStart, z, icon.func_94209_e(), icon.func_94206_g());
/*     */     } 
/*     */     
/* 135 */     tessellator.func_78381_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\RenderTextureCopier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */