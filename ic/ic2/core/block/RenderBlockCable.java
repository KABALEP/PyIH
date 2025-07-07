/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*     */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.Direction;
/*     */ import ic2.core.block.wiring.TileEntityCable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.RenderBlocks;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RenderBlockCable
/*     */   implements ISimpleBlockRenderingHandler
/*     */ {
/*  25 */   private static Direction[] directions = Direction.values();
/*  26 */   public static int renderId = RenderingRegistry.getNextAvailableRenderId();
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {}
/*     */ 
/*     */   
/*     */   public boolean renderWorldBlock(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks) {
/*  34 */     TileEntity te = iBlockAccess.func_147438_o(x, y, z);
/*  35 */     if (!(te instanceof TileEntityCable))
/*     */     {
/*  37 */       return true;
/*     */     }
/*  39 */     TileEntityCable cable = (TileEntityCable)te;
/*  40 */     if (cable.foamed > 0) {
/*     */       
/*  42 */       if (cable.foamed == 2 && cable.textureMimic) {
/*     */         
/*  44 */         renderFoam(cable, (BlockMultiID)block, renderblocks, iBlockAccess, x, y, z);
/*  45 */         return true;
/*     */       } 
/*  47 */       renderblocks.func_147784_q(block, x, y, z);
/*     */     }
/*     */     else {
/*     */       
/*  51 */       float th = cable.getCableThickness();
/*  52 */       float sp = (1.0F - th) / 2.0F;
/*  53 */       int connectivity = 0;
/*  54 */       int renderSide = 0;
/*  55 */       int mask = 1;
/*  56 */       for (Direction direction : directions) {
/*     */         
/*  58 */         TileEntity neighbor = direction.applyToTileEntity((TileEntity)cable);
/*  59 */         if (cable.canConnect(direction.toForgeDirection().ordinal()))
/*     */         {
/*  61 */           connectivity |= mask;
/*     */         }
/*  63 */         if (neighbor != null && cable.canInteractWith(neighbor, direction.toForgeDirection()))
/*     */         {
/*  65 */           if (neighbor instanceof TileEntityCable && ((TileEntityCable)neighbor).getCableThickness() < th)
/*     */           {
/*  67 */             renderSide |= mask;
/*     */           }
/*     */         }
/*  70 */         mask *= 2;
/*     */       } 
/*  72 */       Tessellator tessellator = Tessellator.field_78398_a;
/*  73 */       IIcon texture = block.func_149673_e(iBlockAccess, x, y, z, 0);
/*  74 */       double xD = x;
/*  75 */       double yD = y;
/*  76 */       double zD = z;
/*  77 */       tessellator.func_78380_c(block.func_149677_c(iBlockAccess, x, y, z));
/*  78 */       if (connectivity == 0) {
/*     */         
/*  80 */         block.func_149676_a(sp, sp, sp, sp + th, sp + th, sp + th);
/*  81 */         renderblocks.func_147775_a(block);
/*  82 */         tessellator.func_78386_a(0.5F, 0.5F, 0.5F);
/*  83 */         renderblocks.func_147768_a(block, xD, yD, zD, texture);
/*  84 */         tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/*  85 */         renderblocks.func_147806_b(block, xD, yD, zD, texture);
/*  86 */         tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/*  87 */         renderblocks.func_147761_c(block, xD, yD, zD, texture);
/*  88 */         renderblocks.func_147734_d(block, xD, y, zD, texture);
/*  89 */         tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/*  90 */         renderblocks.func_147798_e(block, xD, yD, zD, texture);
/*  91 */         renderblocks.func_147764_f(block, xD, yD, zD, texture);
/*     */       }
/*  93 */       else if (connectivity == 3) {
/*     */         
/*  95 */         block.func_149676_a(0.0F, sp, sp, 1.0F, sp + th, sp + th);
/*  96 */         renderblocks.func_147775_a(block);
/*  97 */         tessellator.func_78386_a(0.5F, 0.5F, 0.5F);
/*  98 */         renderblocks.func_147768_a(block, xD, yD, zD, texture);
/*  99 */         tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/* 100 */         renderblocks.func_147806_b(block, xD, yD, zD, texture);
/* 101 */         tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 102 */         renderblocks.func_147761_c(block, xD, yD, zD, texture);
/* 103 */         renderblocks.func_147734_d(block, xD, y, zD, texture);
/* 104 */         if ((renderSide & 0x1) != 0) {
/*     */           
/* 106 */           tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 107 */           renderblocks.func_147798_e(block, xD, yD, zD, texture);
/*     */         } 
/* 109 */         if ((renderSide & 0x2) != 0)
/*     */         {
/* 111 */           tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 112 */           renderblocks.func_147764_f(block, xD, yD, zD, texture);
/*     */         }
/*     */       
/* 115 */       } else if (connectivity == 12) {
/*     */         
/* 117 */         block.func_149676_a(sp, 0.0F, sp, sp + th, 1.0F, sp + th);
/* 118 */         renderblocks.func_147775_a(block);
/* 119 */         tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 120 */         renderblocks.func_147761_c(block, xD, yD, zD, texture);
/* 121 */         renderblocks.func_147734_d(block, xD, y, zD, texture);
/* 122 */         tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 123 */         renderblocks.func_147798_e(block, xD, yD, zD, texture);
/* 124 */         renderblocks.func_147764_f(block, xD, yD, zD, texture);
/* 125 */         if ((renderSide & 0x4) != 0) {
/*     */           
/* 127 */           tessellator.func_78386_a(0.5F, 0.5F, 0.5F);
/* 128 */           renderblocks.func_147768_a(block, xD, yD, zD, texture);
/*     */         } 
/* 130 */         if ((renderSide & 0x8) != 0)
/*     */         {
/* 132 */           tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/* 133 */           renderblocks.func_147806_b(block, xD, yD, zD, texture);
/*     */         }
/*     */       
/* 136 */       } else if (connectivity == 48) {
/*     */         
/* 138 */         block.func_149676_a(sp, sp, 0.0F, sp + th, sp + th, 1.0F);
/* 139 */         renderblocks.func_147775_a(block);
/* 140 */         tessellator.func_78386_a(0.5F, 0.5F, 0.5F);
/* 141 */         renderblocks.func_147768_a(block, xD, yD, zD, texture);
/* 142 */         tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/* 143 */         renderblocks.func_147806_b(block, xD, yD, zD, texture);
/* 144 */         tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 145 */         renderblocks.func_147798_e(block, xD, yD, zD, texture);
/* 146 */         renderblocks.func_147764_f(block, xD, yD, zD, texture);
/* 147 */         if ((renderSide & 0x10) != 0) {
/*     */           
/* 149 */           tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 150 */           renderblocks.func_147761_c(block, xD, y, zD, texture);
/*     */         } 
/* 152 */         if ((renderSide & 0x20) != 0)
/*     */         {
/* 154 */           tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 155 */           renderblocks.func_147734_d(block, xD, yD, zD, texture);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 160 */         if ((connectivity & 0x1) == 0) {
/*     */           
/* 162 */           block.func_149676_a(sp, sp, sp, sp + th, sp + th, sp + th);
/* 163 */           renderblocks.func_147775_a(block);
/* 164 */           tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 165 */           renderblocks.func_147798_e(block, xD, yD, zD, texture);
/*     */         }
/*     */         else {
/*     */           
/* 169 */           block.func_149676_a(0.0F, sp, sp, sp, sp + th, sp + th);
/* 170 */           renderblocks.func_147775_a(block);
/* 171 */           tessellator.func_78386_a(0.5F, 0.5F, 0.5F);
/* 172 */           renderblocks.func_147768_a(block, xD, yD, zD, texture);
/* 173 */           tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/* 174 */           renderblocks.func_147806_b(block, xD, yD, zD, texture);
/* 175 */           tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 176 */           renderblocks.func_147761_c(block, xD, yD, zD, texture);
/* 177 */           renderblocks.func_147734_d(block, xD, y, zD, texture);
/* 178 */           if ((renderSide & 0x1) != 0) {
/*     */             
/* 180 */             tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 181 */             renderblocks.func_147798_e(block, xD, yD, zD, texture);
/*     */           } 
/*     */         } 
/* 184 */         if ((connectivity & 0x2) == 0) {
/*     */           
/* 186 */           block.func_149676_a(sp, sp, sp, sp + th, sp + th, sp + th);
/* 187 */           renderblocks.func_147775_a(block);
/* 188 */           tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 189 */           renderblocks.func_147764_f(block, xD, yD, zD, texture);
/*     */         }
/*     */         else {
/*     */           
/* 193 */           block.func_149676_a(sp + th, sp, sp, 1.0F, sp + th, sp + th);
/* 194 */           renderblocks.func_147775_a(block);
/* 195 */           tessellator.func_78386_a(0.5F, 0.5F, 0.5F);
/* 196 */           renderblocks.func_147768_a(block, xD, yD, zD, texture);
/* 197 */           tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/* 198 */           renderblocks.func_147806_b(block, xD, yD, zD, texture);
/* 199 */           tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 200 */           renderblocks.func_147761_c(block, xD, yD, zD, texture);
/* 201 */           renderblocks.func_147734_d(block, xD, y, zD, texture);
/* 202 */           if ((renderSide & 0x2) != 0) {
/*     */             
/* 204 */             tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 205 */             renderblocks.func_147764_f(block, xD, yD, zD, texture);
/*     */           } 
/*     */         } 
/* 208 */         if ((connectivity & 0x4) == 0) {
/*     */           
/* 210 */           block.func_149676_a(sp, sp, sp, sp + th, sp + th, sp + th);
/* 211 */           renderblocks.func_147775_a(block);
/* 212 */           tessellator.func_78386_a(0.5F, 0.5F, 0.5F);
/* 213 */           renderblocks.func_147768_a(block, xD, yD, zD, texture);
/*     */         }
/*     */         else {
/*     */           
/* 217 */           block.func_149676_a(sp, 0.0F, sp, sp + th, sp, sp + th);
/* 218 */           renderblocks.func_147775_a(block);
/* 219 */           tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 220 */           renderblocks.func_147761_c(block, xD, yD, zD, texture);
/* 221 */           renderblocks.func_147734_d(block, xD, y, zD, texture);
/* 222 */           tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 223 */           renderblocks.func_147798_e(block, xD, yD, zD, texture);
/* 224 */           renderblocks.func_147764_f(block, xD, yD, zD, texture);
/* 225 */           if ((renderSide & 0x4) != 0) {
/*     */             
/* 227 */             tessellator.func_78386_a(0.5F, 0.5F, 0.5F);
/* 228 */             renderblocks.func_147768_a(block, xD, yD, zD, texture);
/*     */           } 
/*     */         } 
/* 231 */         if ((connectivity & 0x8) == 0) {
/*     */           
/* 233 */           block.func_149676_a(sp, sp, sp, sp + th, sp + th, sp + th);
/* 234 */           renderblocks.func_147775_a(block);
/* 235 */           tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/* 236 */           renderblocks.func_147806_b(block, xD, yD, zD, texture);
/*     */         }
/*     */         else {
/*     */           
/* 240 */           block.func_149676_a(sp, sp + th, sp, sp + th, 1.0F, sp + th);
/* 241 */           renderblocks.func_147775_a(block);
/* 242 */           tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 243 */           renderblocks.func_147761_c(block, xD, yD, zD, texture);
/* 244 */           renderblocks.func_147734_d(block, xD, y, zD, texture);
/* 245 */           tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 246 */           renderblocks.func_147798_e(block, xD, yD, zD, texture);
/* 247 */           renderblocks.func_147764_f(block, xD, yD, zD, texture);
/* 248 */           if ((renderSide & 0x8) != 0) {
/*     */             
/* 250 */             tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/* 251 */             renderblocks.func_147806_b(block, xD, yD, zD, texture);
/*     */           } 
/*     */         } 
/* 254 */         if ((connectivity & 0x10) == 0) {
/*     */           
/* 256 */           block.func_149676_a(sp, sp, sp, sp + th, sp + th, sp + th);
/* 257 */           renderblocks.func_147775_a(block);
/* 258 */           tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 259 */           renderblocks.func_147761_c(block, xD, y, zD, texture);
/*     */         }
/*     */         else {
/*     */           
/* 263 */           block.func_149676_a(sp, sp, 0.0F, sp + th, sp + th, sp);
/* 264 */           renderblocks.func_147775_a(block);
/* 265 */           tessellator.func_78386_a(0.5F, 0.5F, 0.5F);
/* 266 */           renderblocks.func_147768_a(block, xD, yD, zD, texture);
/* 267 */           tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/* 268 */           renderblocks.func_147806_b(block, xD, yD, zD, texture);
/* 269 */           tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 270 */           renderblocks.func_147798_e(block, xD, yD, zD, texture);
/* 271 */           renderblocks.func_147764_f(block, xD, yD, zD, texture);
/* 272 */           if ((renderSide & 0x10) != 0) {
/*     */             
/* 274 */             tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 275 */             renderblocks.func_147761_c(block, xD, y, zD, texture);
/*     */           } 
/*     */         } 
/* 278 */         if ((connectivity & 0x20) == 0) {
/*     */           
/* 280 */           block.func_149676_a(sp, sp, sp, sp + th, sp + th, sp + th);
/* 281 */           renderblocks.func_147775_a(block);
/* 282 */           tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 283 */           renderblocks.func_147734_d(block, xD, yD, zD, texture);
/*     */         }
/*     */         else {
/*     */           
/* 287 */           block.func_149676_a(sp, sp, sp + th, sp + th, sp + th, 1.0F);
/* 288 */           renderblocks.func_147775_a(block);
/* 289 */           tessellator.func_78386_a(0.5F, 0.5F, 0.5F);
/* 290 */           renderblocks.func_147768_a(block, xD, yD, zD, texture);
/* 291 */           tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
/* 292 */           renderblocks.func_147806_b(block, xD, yD, zD, texture);
/* 293 */           tessellator.func_78386_a(0.6F, 0.6F, 0.6F);
/* 294 */           renderblocks.func_147798_e(block, xD, yD, zD, texture);
/* 295 */           renderblocks.func_147764_f(block, xD, yD, zD, texture);
/* 296 */           if ((renderSide & 0x20) != 0) {
/*     */             
/* 298 */             tessellator.func_78386_a(0.8F, 0.8F, 0.8F);
/* 299 */             renderblocks.func_147734_d(block, xD, yD, zD, texture);
/*     */           } 
/*     */         } 
/*     */       } 
/* 303 */       block.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 304 */       renderblocks.func_147775_a(block);
/*     */     } 
/* 306 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderFoam(TileEntityCable cable, BlockMultiID block, RenderBlocks blocks, IBlockAccess world, int x, int y, int z) {
/* 311 */     RenderInfo endInfo = new RenderInfo((Block)block, 0, block.func_149720_d(world, x, y, z));
/* 312 */     List<RenderInfo> infos = new ArrayList<>();
/* 313 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 315 */       Block mimBlock = cable.textureBlock[i];
/* 316 */       if (mimBlock == null || mimBlock == Blocks.field_150350_a) {
/*     */         
/* 318 */         endInfo.addSide(i);
/*     */       } else {
/*     */         
/* 321 */         int meta = cable.textureMeta[i];
/*     */         
/*     */         try {
/* 324 */           int color = mimBlock.func_149720_d(world, x, y, z);
/* 325 */           addToList(mimBlock, meta, color, i, infos);
/*     */         }
/* 327 */         catch (Exception e) {
/*     */           
/* 329 */           endInfo.addSide(i);
/*     */         } 
/*     */       } 
/* 332 */     }  for (RenderInfo info : infos) {
/*     */       
/* 334 */       if (!info.hasSides()) {
/*     */         continue;
/*     */       }
/*     */       
/* 338 */       block.applySpecialRender(info);
/* 339 */       blocks.func_147784_q((Block)block, x, y, z);
/*     */     } 
/* 341 */     if (!endInfo.hasSides()) {
/*     */       
/* 343 */       block.resetSpecialRender();
/*     */       return;
/*     */     } 
/* 346 */     block.applySpecialRender(endInfo);
/* 347 */     blocks.func_147784_q((Block)block, x, y, z);
/* 348 */     block.resetSpecialRender();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToList(Block par1, int par2, int par3, int side, List<RenderInfo> par4) {
/* 353 */     boolean found = false;
/* 354 */     for (RenderInfo info : par4) {
/*     */       
/* 356 */       if (info.matches(par1, par2, par3)) {
/*     */         
/* 358 */         info.addSide(side);
/* 359 */         found = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 363 */     if (found) {
/*     */       return;
/*     */     }
/*     */     
/* 367 */     par4.add(new RenderInfo(par1, par2, par3, side));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender3DInInventory(int id) {
/* 372 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderId() {
/* 377 */     return renderId;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static class RenderInfo
/*     */   {
/* 383 */     boolean[] renderSides = new boolean[6];
/*     */     int color;
/*     */     Block block;
/*     */     int meta;
/* 387 */     int sides = 0;
/*     */ 
/*     */     
/*     */     public RenderInfo(Block par1, int par2, int par3, int par4) {
/* 391 */       this.block = par1;
/* 392 */       this.meta = par2;
/* 393 */       this.color = par3;
/* 394 */       this.renderSides[par4] = true;
/* 395 */       this.sides++;
/*     */     }
/*     */     
/*     */     public RenderInfo(Block par1, int par2, int par3) {
/* 399 */       this.block = par1;
/* 400 */       this.meta = par2;
/* 401 */       this.color = par3;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasSides() {
/* 406 */       return (this.sides > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Block par1, int par2, int par3) {
/* 411 */       if (Block.func_149680_a(this.block, par1) && this.meta == par2 && this.color == par3)
/*     */       {
/* 413 */         return true;
/*     */       }
/* 415 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void addSide(int side) {
/* 420 */       this.renderSides[side] = true;
/* 421 */       this.sides++;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\RenderBlockCable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */