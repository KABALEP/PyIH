/*    */ package ic2.core.block;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Icons;
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.World;
/*    */ import org.apache.logging.log4j.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockITNT
/*    */   extends BlockIC2Explosive
/*    */   implements IRareBlock
/*    */ {
/*    */   public boolean isITNT;
/*    */   
/*    */   public BlockITNT(boolean is) {
/* 32 */     super(is);
/* 33 */     this.isITNT = is;
/* 34 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon func_149691_a(int side, int meta) {
/* 40 */     if (side == 0)
/*    */     {
/* 42 */       return Ic2Icons.getTexture("b0")[this.isITNT ? 58 : 61];
/*    */     }
/* 44 */     if (side == 1)
/*    */     {
/* 46 */       return Ic2Icons.getTexture("b0")[this.isITNT ? 59 : 62];
/*    */     }
/* 48 */     return this.isITNT ? Ic2Icons.getTexture("b0")[60] : Ic2Icons.getTexture("b0")[63];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityIC2Explosive getExplosionEntity(World world, float x, float y, float z, String username, BlockIC2Explosive.ActivationType type) {
/* 54 */     if (this.isITNT)
/*    */     {
/* 56 */       return new EntityItnt(world, x, y, z);
/*    */     }
/* 58 */     if (type == BlockIC2Explosive.ActivationType.Explosion)
/*    */     {
/* 60 */       return null;
/*    */     }
/* 62 */     return (new EntityNuke(world, x, y, z)).setIgniter(username);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149689_a(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack stack) {
/* 68 */     if (!this.isITNT && entityliving instanceof EntityPlayer)
/*    */     {
/* 70 */       IC2.log.log(Level.INFO, "Player " + ((EntityPlayer)entityliving).func_146103_bH().getName() + " placed a nuke at " + world.field_73011_w.field_76574_g + ":(" + x + "," + y + "," + z + ")");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onIgnite(World world, EntityPlayer player, int x, int y, int z) {
/* 77 */     if (!this.isITNT)
/*    */     {
/* 79 */       IC2.log.log(Level.INFO, "Nuke at " + world.field_73011_w.field_76574_g + ":(" + x + "," + y + "," + z + ") was ignited " + ((player == null) ? "indirectly" : ("by " + player.func_146103_bH().getName())));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity getRarity(ItemStack stack) {
/* 87 */     return this.isITNT ? EnumRarity.common : EnumRarity.uncommon;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149666_a(Item i, CreativeTabs tabs, List itemList) {
/* 93 */     if (!this.isITNT && !IC2.enableCraftingNuke) {
/*    */       return;
/*    */     }
/*    */     
/* 97 */     super.func_149666_a(i, tabs, itemList);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockITNT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */