/*    */ package ic2.core.item.block;
/*    */ 
/*    */ import ic2.api.item.IBoxable;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.block.BehaviorDynamiteDispense;
/*    */ import ic2.core.block.EntityDynamite;
/*    */ import ic2.core.block.EntityStickyDynamite;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemDynamite extends ItemIC2 implements IBoxable {
/*    */   public boolean sticky;
/*    */   
/*    */   public ItemDynamite(int index, boolean st) {
/* 22 */     super(index);
/* 23 */     this.sticky = st;
/* 24 */     func_77625_d(16);
/* 25 */     BlockDispenser.field_149943_a.func_82595_a(this, new BehaviorDynamiteDispense(this.sticky));
/* 26 */     setSpriteID("i1");
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_77647_b(int i) {
/* 31 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
/* 36 */     if (this.sticky)
/*    */     {
/* 38 */       return false;
/*    */     }
/* 40 */     if (l == 0)
/*    */     {
/* 42 */       j--;
/*    */     }
/* 44 */     if (l == 1)
/*    */     {
/* 46 */       j++;
/*    */     }
/* 48 */     if (l == 2)
/*    */     {
/* 50 */       k--;
/*    */     }
/* 52 */     if (l == 3)
/*    */     {
/* 54 */       k++;
/*    */     }
/* 56 */     if (l == 4)
/*    */     {
/* 58 */       i--;
/*    */     }
/* 60 */     if (l == 5)
/*    */     {
/* 62 */       i++;
/*    */     }
/* 64 */     Block i2 = world.func_147439_a(i, j, k);
/* 65 */     if (i2 == null && Block.func_149634_a(Ic2Items.dynamiteStick.func_77973_b()).func_149742_c(world, i, j, k)) {
/*    */       
/* 67 */       world.func_147449_b(i, j, k, Block.func_149634_a(Ic2Items.dynamiteStick.func_77973_b()));
/* 68 */       itemstack.field_77994_a--;
/* 69 */       return true;
/*    */     } 
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/* 76 */     if (!entityplayer.field_71075_bZ.field_75098_d)
/*    */     {
/* 78 */       itemstack.field_77994_a--;
/*    */     }
/* 80 */     world.func_72956_a((Entity)entityplayer, "random.bow", 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
/* 81 */     if (IC2.platform.isSimulating())
/*    */     {
/* 83 */       if (this.sticky) {
/*    */         
/* 85 */         world.func_72838_d((Entity)new EntityStickyDynamite(world, (EntityLivingBase)entityplayer));
/*    */       }
/*    */       else {
/*    */         
/* 89 */         world.func_72838_d((Entity)new EntityDynamite(world, (EntityLivingBase)entityplayer));
/*    */       } 
/*    */     }
/* 92 */     return itemstack;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 98 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemDynamite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */