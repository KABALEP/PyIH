/*    */ package ic2.core.block;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import ic2.api.event.PaintEvent;
/*    */ import ic2.core.Ic2Icons;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.util.IExtraData;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockWall
/*    */   extends BlockTex
/*    */   implements IExtraData
/*    */ {
/*    */   public BlockWall(int sprite) {
/* 26 */     super(sprite, Material.field_151576_e);
/* 27 */     func_149711_c(3.0F);
/* 28 */     func_149752_b(30.0F);
/* 29 */     func_149663_c("blockWall");
/* 30 */     func_149672_a(Block.field_149769_e);
/* 31 */     MinecraftForge.EVENT_BUS.register(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon func_149673_e(IBlockAccess iblockaccess, int i, int j, int k, int side) {
/* 37 */     int meta = iblockaccess.func_72805_g(i, j, k);
/* 38 */     return Ic2Icons.getTexture("bcable")[this.blockIndexInTexture + meta];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon func_149691_a(int side, int meta) {
/* 44 */     return Ic2Icons.getTexture("bcable")[this.blockIndexInTexture + meta];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item func_149650_a(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
/* 52 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_149745_a(Random r) {
/* 58 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPaint(PaintEvent event) {
/* 64 */     if (event.world.func_147439_a(event.x, event.y, event.z) != this) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 69 */     if (event.color != event.world.func_72805_g(event.x, event.y, event.z)) {
/*    */       
/* 71 */       event.world.func_72921_c(event.x, event.y, event.z, event.color, 3);
/* 72 */       event.painted = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_149644_j(int i) {
/* 79 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
/* 85 */     return Ic2Items.constructionFoam.func_77946_l();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {
/* 91 */     Ic2Items.constructionFoamWall = new ItemStack(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockWall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */