/*    */ package ic2.aeIntigration.core;
/*    */ 
/*    */ import appeng.api.AEApi;
/*    */ import appeng.api.parts.IPart;
/*    */ import appeng.api.parts.IPartItem;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AEItem
/*    */   extends ItemIC2
/*    */   implements IPartItem
/*    */ {
/* 25 */   IIcon texture = null;
/*    */   
/*    */   public AEItem() {
/* 28 */     super(0);
/* 29 */     func_77627_a(true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_150895_a(Item p_150895_1_, CreativeTabs p_150895_2_, List<ItemStack> p_150895_3_) {
/* 36 */     for (ClassicEUP2PTunnel.CableType type : ClassicEUP2PTunnel.CableType.values()) {
/*    */       
/* 38 */       if (type != ClassicEUP2PTunnel.CableType.None)
/*    */       {
/*    */ 
/*    */         
/* 42 */         p_150895_3_.add(new ItemStack(p_150895_1_, 1, type.ordinal()));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public IPart createPartFromItemStack(ItemStack arg0) {
/* 49 */     return (IPart)new ClassicEUP2PTunnel(arg0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int func_94901_k() {
/* 56 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon func_77617_a(int par1) {
/* 63 */     return this.texture;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack is, EntityPlayer player, World w, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
/* 69 */     return AEApi.instance().partHelper().placeBus(is, x, y, z, side, player, w);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack par1, EntityPlayer par2, List<String> par3, boolean par4) {
/* 76 */     par3.add("Type: " + ClassicEUP2PTunnel.CableType.values()[par1.func_77960_j()].getType());
/* 77 */     par3.add("The P2P Busses can melt");
/* 78 */     par3.add("if you send to much energy through them!");
/* 79 */     par3.add("Work on in same Dimensions");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {
/* 86 */     this.texture = par1IconRegister.func_94245_a("appliedenergistics2:ItemPart.P2PTunnel");
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\aeIntigration\core\AEItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */