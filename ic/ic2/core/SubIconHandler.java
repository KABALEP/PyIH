/*     */ package ic2.core;
/*     */ 
/*     */ import cpw.mods.fml.client.FMLClientHandler;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.FMLLog;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.event.TextureStitchEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class SubIconHandler
/*     */ {
/*  31 */   private static Map<String, BufferedImage> sheetCache = new HashMap<>();
/*     */ 
/*     */   
/*     */   public static TextureAtlasSprite getSubIcon(TextureMap map, int x, int y, Ic2Icons.SpriteData data, int modfiy, boolean items) {
/*  35 */     String name = "ic2:replacements/" + data.spriteID + "X" + x + "Y" + y;
/*  36 */     TextureAtlasSprite icon = new SubIcon(name, data.texture, x + y * data.info.maxX, data.info, modfiy, items);
/*  37 */     map.setTextureEntry(name, icon);
/*  38 */     return icon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BufferedImage loadSheet(IResourceManager manager, String sheet) throws Exception {
/*     */     try {
/*  45 */       InputStream stream = manager.func_110536_a(new ResourceLocation(sheet)).func_110527_b();
/*  46 */       if (stream == null)
/*     */       {
/*  48 */         throw new RuntimeException("Sheet not found: " + sheet);
/*     */       }
/*  50 */       return ImageIO.read(stream);
/*     */     }
/*  52 */     catch (IOException e) {
/*     */       
/*  54 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ForgeEventHandler
/*     */   {
/*     */     @SideOnly(Side.CLIENT)
/*     */     @SubscribeEvent
/*     */     public void onTextureStitchPost(TextureStitchEvent.Post evt) {
/*  64 */       SubIconHandler.sheetCache.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     @SideOnly(Side.CLIENT)
/*     */     @SubscribeEvent
/*     */     public void onTextureStitchPre(TextureStitchEvent.Pre evt) {
/*  71 */       Ic2Icons.load(evt.map);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SubIcon
/*     */     extends TextureAtlasSprite {
/*     */     public String sheetName;
/*     */     public int pos;
/*  79 */     public int modify = -1;
/*     */     
/*     */     public Ic2Icons.SpriteInfo info;
/*     */     public boolean item;
/*     */     
/*     */     public SubIcon(String name, String sheet, int pos, Ic2Icons.SpriteInfo info, int modify, boolean items) {
/*  85 */       super(name);
/*  86 */       this.sheetName = sheet;
/*  87 */       this.pos = pos;
/*  88 */       this.info = info;
/*  89 */       this.modify = modify;
/*  90 */       this.item = items;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean load(IResourceManager manager, ResourceLocation location) {
/*  95 */       int xCoord = 0;
/*  96 */       int yCoord = 0;
/*     */       
/*     */       try {
/*  99 */         BufferedImage sheet = (BufferedImage)SubIconHandler.sheetCache.get(this.sheetName);
/* 100 */         if (sheet == null)
/*     */         {
/* 102 */           SubIconHandler.sheetCache.put(this.sheetName, sheet = SubIconHandler.loadSheet(manager, this.sheetName));
/*     */         }
/* 104 */         this.field_130223_c = sheet.getWidth() / this.info.maxX;
/* 105 */         this.field_130224_d = sheet.getHeight() / this.info.maxY;
/* 106 */         int x = this.pos % this.info.maxX * this.field_130223_c;
/* 107 */         int y = this.pos / this.info.maxX * this.field_130224_d;
/* 108 */         xCoord = x;
/* 109 */         yCoord = y;
/* 110 */         if (this.modify != -1) {
/*     */           
/* 112 */           this.field_130223_c *= this.modify;
/* 113 */           this.field_130224_d *= this.modify;
/*     */         } 
/* 115 */         int[] data = new int[this.field_130223_c * this.field_130224_d];
/* 116 */         sheet.getRGB(x, y, this.field_130223_c, this.field_130224_d, data, 0, this.field_130223_c);
/* 117 */         int[][] mipmaps = new int[(FMLClientHandler.instance().getClient()).field_71474_y.field_151442_I + 1][];
/* 118 */         mipmaps[0] = data;
/* 119 */         this.field_110976_a.add(mipmaps);
/* 120 */         return false;
/*     */       }
/* 122 */       catch (Exception e) {
/*     */         
/* 124 */         FMLLog.getLogger().info("Crash at Texture loading: " + this.field_130223_c + ":" + this.field_130224_d + ":" + xCoord + ":" + yCoord);
/* 125 */         e.printStackTrace();
/* 126 */         FMLCommonHandler.instance().exitJava(0, false);
/* 127 */         return true;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
/*     */       try {
/* 136 */         return (manager.func_110536_a(completeResourceLocation(location)) == null);
/*     */       }
/* 138 */       catch (Exception exception) {
/*     */ 
/*     */         
/* 141 */         return true;
/*     */       } 
/*     */     }
/*     */     
/*     */     private ResourceLocation completeResourceLocation(ResourceLocation p_147634_1_) {
/* 146 */       return new ResourceLocation(p_147634_1_.func_110624_b(), String.format("%s/%s%s", new Object[] { this.item ? "textures/items" : "textures/blocks", p_147634_1_.func_110623_a(), ".png" }));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\SubIconHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */