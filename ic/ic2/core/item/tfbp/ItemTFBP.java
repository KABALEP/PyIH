/*    */ package ic2.core.item.tfbp;
/*    */ 
/*    */ import ic2.api.item.ITerraformerBP;
/*    */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ItemTFBP
/*    */   extends ItemIC2
/*    */   implements ITerraformerBP
/*    */ {
/*    */   public ItemTFBP(int index) {
/* 17 */     super(index);
/* 18 */     func_77625_d(1);
/* 19 */     setSpriteID("i1");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
/* 24 */     if (world.func_147438_o(i, j, k) instanceof TileEntityTerra) {
/*    */       
/* 26 */       ((TileEntityTerra)world.func_147438_o(i, j, k)).insertBlueprint(itemstack);
/* 27 */       entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c] = null;
/* 28 */       return true;
/*    */     } 
/* 30 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tfbp\ItemTFBP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */