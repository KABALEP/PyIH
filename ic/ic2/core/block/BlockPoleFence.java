/*     */ package ic2.core.block;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLLog;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import ic2.api.item.IMetalArmor;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.IExtraData;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.config.Configuration;
/*     */ import net.minecraftforge.common.config.Property;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPoleFence
/*     */   extends BlockTex
/*     */   implements IExtraData
/*     */ {
/*  31 */   public static Set<Item> validBoots = new HashSet<>();
/*     */ 
/*     */   
/*     */   public BlockPoleFence(int sprite) {
/*  35 */     super(sprite, Material.field_151573_f);
/*  36 */     func_149711_c(1.5F);
/*  37 */     func_149752_b(5.0F);
/*  38 */     func_149672_a(field_149777_j);
/*  39 */     func_149663_c("blockFenceIron");
/*  40 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  52 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149637_q() {
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/*  63 */     return IC2.platform.getRenderId("fence");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
/*  69 */     if (this.field_149764_J == Material.field_151573_f && isPole(world, i, j, k))
/*     */     {
/*  71 */       return AxisAlignedBB.func_72330_a((i + 0.375F), j, (k + 0.375F), (i + 0.625F), (j + 1.0F), (k + 0.625F));
/*     */     }
/*  73 */     return AxisAlignedBB.func_72330_a(i, j, k, (i + 1), (j + 1.5F), (k + 1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_149633_g(World world, int i, int j, int k) {
/*  79 */     if (this.field_149764_J == Material.field_151573_f && isPole(world, i, j, k))
/*     */     {
/*  81 */       return AxisAlignedBB.func_72330_a((i + 0.375F), j, (k + 0.375F), (i + 0.625F), (j + 1.0F), (k + 0.625F));
/*     */     }
/*  83 */     return AxisAlignedBB.func_72330_a(i, j, k, (i + 1), (j + 1), (k + 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPole(World world, int i, int j, int k) {
/*  88 */     return (world.func_147439_a(i - 1, j, k) != this && world.func_147439_a(i + 1, j, k) != this && world.func_147439_a(i, j, k - 1) != this && world.func_147439_a(i, j, k + 1) != this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
/*  94 */     if (this.field_149764_J != Material.field_151573_f || !isPole(world, i, j, k) || !(entity instanceof EntityPlayer)) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     boolean powered = (world.func_72805_g(i, j, k) > 0);
/*  99 */     boolean metalShoes = false;
/* 100 */     EntityPlayer player = (EntityPlayer)entity;
/* 101 */     ItemStack shoes = player.field_71071_by.field_70460_b[0];
/* 102 */     if (shoes != null) {
/*     */       
/* 104 */       Item id = shoes.func_77973_b();
/* 105 */       if (validBoots.contains(id) || (shoes.func_77973_b() instanceof IMetalArmor && ((IMetalArmor)shoes.func_77973_b()).isMetalArmor(shoes, player)))
/*     */       {
/* 107 */         metalShoes = true;
/*     */       }
/*     */     } 
/* 110 */     if (!powered || !metalShoes) {
/*     */       
/* 112 */       if (player.func_70093_af())
/*     */       {
/* 114 */         if (player.field_70181_x < -0.25D)
/*     */         {
/* 116 */           EntityPlayer entityPlayer = player;
/* 117 */           entityPlayer.field_70181_x *= 0.8999999761581421D;
/*     */         }
/*     */         else
/*     */         {
/* 121 */           player.field_70143_R = 0.0F;
/*     */         }
/*     */       
/*     */       }
/*     */     } else {
/*     */       
/* 127 */       world.func_72921_c(i, j, k, world.func_72805_g(i, j, k) - 1, 0);
/* 128 */       EntityPlayer entityPlayer2 = player;
/* 129 */       entityPlayer2.field_70181_x += 0.07500000298023224D;
/* 130 */       if (player.field_70181_x > 0.0D) {
/*     */         
/* 132 */         EntityPlayer entityPlayer3 = player;
/* 133 */         entityPlayer3.field_70181_x *= 1.029999971389771D;
/* 134 */         player.field_70143_R = 0.0F;
/*     */       } 
/* 136 */       if (player.func_70093_af()) {
/*     */         
/* 138 */         if (player.field_70181_x > 0.300000011920929D)
/*     */         {
/* 140 */           player.field_70181_x = 0.300000011920929D;
/*     */         }
/*     */       }
/* 143 */       else if (player.field_70181_x > 1.5D) {
/*     */         
/* 145 */         player.field_70181_x = 1.5D;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 153 */     Ic2Items.ironFence = new ItemStack(this);
/* 154 */     validBoots.add(Items.field_151151_aj);
/* 155 */     validBoots.add(Items.field_151167_ab);
/* 156 */     validBoots.add(Items.field_151029_X);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadBoots(Configuration config) {
/* 161 */     Property prop = config.get("general", "Magnetizer Boots Registry", getBootsRegistryString());
/* 162 */     prop.comment = "Allows to add Boots to the magnitzer registry";
/* 163 */     validBoots.clear();
/* 164 */     setBootsRegistryString(prop.getString());
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getBootsRegistryString() {
/* 169 */     StringBuilder ret = new StringBuilder();
/* 170 */     boolean first = true;
/* 171 */     for (Item entry : validBoots) {
/*     */       
/* 173 */       if (entry != null) {
/*     */         
/* 175 */         if (first) {
/*     */           
/* 177 */           first = false;
/*     */         }
/*     */         else {
/*     */           
/* 181 */           ret.append(", ");
/*     */         } 
/* 183 */         ret.append(Item.field_150901_e.func_148750_c(entry));
/*     */       } 
/*     */     } 
/* 186 */     return ret.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setBootsRegistryString(String str) {
/* 191 */     String[] items = str.trim().split("\\s*,\\s*");
/* 192 */     for (String part : items) {
/*     */       
/* 194 */       String[] split = part.split(":");
/* 195 */       Item itemID = GameRegistry.findItem(split[0], split[1]);
/* 196 */       if (itemID == null) {
/*     */         
/* 198 */         FMLLog.getLogger().info("Boots Registry Error: Could not find: " + part + " Please check the registry names to fix it");
/*     */       } else {
/*     */         
/* 201 */         validBoots.add(itemID);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockPoleFence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */