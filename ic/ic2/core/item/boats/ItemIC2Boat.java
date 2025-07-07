/*     */ package ic2.core.item.boats;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.util.IExtraData;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemIC2Boat
/*     */   extends ItemIC2
/*     */   implements IExtraData
/*     */ {
/*     */   public ItemIC2Boat() {
/*  31 */     super(0);
/*  32 */     func_77655_b("itemIC2Boats");
/*  33 */     func_77627_a(true);
/*  34 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item p_150895_1_, CreativeTabs p_150895_2_, List<ItemStack> p_150895_3_) {
/*  41 */     for (int i = 0; i < 4; i++)
/*     */     {
/*  43 */       p_150895_3_.add(new ItemStack((Item)this, 1, i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack stack) {
/*  50 */     switch (stack.func_77960_j()) {
/*     */       case 0:
/*  52 */         return "item.itemCarbonBoat";
/*  53 */       case 1: return "item.itemRubberBoat";
/*  54 */       case 2: return "item.itemBrokenRubberBoat";
/*  55 */       case 3: return "item.itemElectricBoat";
/*     */     } 
/*  57 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  63 */     BlockDispenser.field_149943_a.func_82595_a(this, new BehaviorClassicBoatDispense());
/*  64 */     Ic2Items.carbonBoat = new ItemStack((Item)this, 1, 0);
/*  65 */     Ic2Items.rubberBoat = new ItemStack((Item)this, 1, 1);
/*  66 */     Ic2Items.brokenRubberBoat = new ItemStack((Item)this, 1, 2);
/*  67 */     Ic2Items.electricBoat = new ItemStack((Item)this, 1, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/*  74 */     return Ic2Icons.getTexture("i1")[82 + par1];
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityClassicBoat makeBoat(World world, double x, double y, double z, int meta) {
/*  79 */     switch (meta) {
/*     */       case 0:
/*  81 */         return new EntityCarboneBoat(world, x, y, z);
/*  82 */       case 1: return new EntityRubberBoat(world, x, y, z);
/*  83 */       case 3: return new EntityElectricBoat(world, x, y, z);
/*     */     } 
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
/*  90 */     float f = 1.0F;
/*  91 */     float f1 = p_77659_3_.field_70127_C + (p_77659_3_.field_70125_A - p_77659_3_.field_70127_C) * f;
/*  92 */     float f2 = p_77659_3_.field_70126_B + (p_77659_3_.field_70177_z - p_77659_3_.field_70126_B) * f;
/*  93 */     double d0 = p_77659_3_.field_70169_q + (p_77659_3_.field_70165_t - p_77659_3_.field_70169_q) * f;
/*  94 */     double d1 = p_77659_3_.field_70167_r + (p_77659_3_.field_70163_u - p_77659_3_.field_70167_r) * f + 1.62D - p_77659_3_.field_70129_M;
/*  95 */     double d2 = p_77659_3_.field_70166_s + (p_77659_3_.field_70161_v - p_77659_3_.field_70166_s) * f;
/*  96 */     Vec3 vec3 = Vec3.func_72443_a(d0, d1, d2);
/*  97 */     float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - 3.1415927F);
/*  98 */     float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - 3.1415927F);
/*  99 */     float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
/* 100 */     float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
/* 101 */     float f7 = f4 * f5;
/* 102 */     float f8 = f3 * f5;
/* 103 */     double d3 = 5.0D;
/* 104 */     Vec3 vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
/* 105 */     MovingObjectPosition movingobjectposition = p_77659_2_.func_72901_a(vec3, vec31, true);
/*     */     
/* 107 */     if (movingobjectposition == null)
/*     */     {
/* 109 */       return p_77659_1_;
/*     */     }
/*     */ 
/*     */     
/* 113 */     Vec3 vec32 = p_77659_3_.func_70676_i(f);
/* 114 */     boolean flag = false;
/* 115 */     float f9 = 1.0F;
/* 116 */     List<Entity> list = p_77659_2_.func_72839_b((Entity)p_77659_3_, p_77659_3_.field_70121_D.func_72321_a(vec32.field_72450_a * d3, vec32.field_72448_b * d3, vec32.field_72449_c * d3).func_72314_b(f9, f9, f9));
/*     */     
/*     */     int i;
/* 119 */     for (i = 0; i < list.size(); i++) {
/*     */       
/* 121 */       Entity entity = list.get(i);
/*     */       
/* 123 */       if (entity.func_70067_L()) {
/*     */         
/* 125 */         float f10 = entity.func_70111_Y();
/* 126 */         AxisAlignedBB axisalignedbb = entity.field_70121_D.func_72314_b(f10, f10, f10);
/*     */         
/* 128 */         if (axisalignedbb.func_72318_a(vec3))
/*     */         {
/* 130 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     if (flag)
/*     */     {
/* 137 */       return p_77659_1_;
/*     */     }
/*     */ 
/*     */     
/* 141 */     if (movingobjectposition.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
/*     */       
/* 143 */       i = movingobjectposition.field_72311_b;
/* 144 */       int j = movingobjectposition.field_72312_c;
/* 145 */       int k = movingobjectposition.field_72309_d;
/*     */       
/* 147 */       if (p_77659_2_.func_147439_a(i, j, k) == Blocks.field_150431_aC)
/*     */       {
/* 149 */         j--;
/*     */       }
/*     */       
/* 152 */       EntityClassicBoat entityboat = makeBoat(p_77659_2_, (i + 0.5F), (j + 1.0F), (k + 0.5F), p_77659_1_.func_77960_j());
/* 153 */       if (entityboat == null)
/*     */       {
/* 155 */         return p_77659_1_;
/*     */       }
/* 157 */       entityboat.field_70177_z = (((MathHelper.func_76128_c((p_77659_3_.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
/*     */       
/* 159 */       if (!p_77659_2_.func_72945_a(entityboat, entityboat.field_70121_D.func_72314_b(-0.1D, -0.1D, -0.1D)).isEmpty())
/*     */       {
/* 161 */         return p_77659_1_;
/*     */       }
/*     */       
/* 164 */       if (!p_77659_2_.field_72995_K)
/*     */       {
/* 166 */         p_77659_2_.func_72838_d(entityboat);
/*     */       }
/*     */       
/* 169 */       if (!p_77659_3_.field_71075_bZ.field_75098_d)
/*     */       {
/* 171 */         p_77659_1_.field_77994_a--;
/*     */       }
/*     */     } 
/*     */     
/* 175 */     return p_77659_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\boats\ItemIC2Boat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */