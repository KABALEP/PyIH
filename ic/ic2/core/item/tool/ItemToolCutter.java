/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioPosition;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.inventory.IItemTransporter;
/*     */ import ic2.core.block.inventory.TransporterManager;
/*     */ import ic2.core.block.inventory.filter.BasicItemFilter;
/*     */ import ic2.core.block.wiring.TileEntityCable;
/*     */ import ic2.core.block.wiring.TileEntityLuminatorMultipart;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class ItemToolCutter
/*     */   extends ItemIC2
/*     */ {
/*     */   public ItemToolCutter(int sprite) {
/*  27 */     super(sprite);
/*  28 */     func_77656_e(512);
/*  29 */     func_77625_d(1);
/*  30 */     setSpriteID("i1");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float a, float b, float c) {
/*  35 */     TileEntity te = world.func_147438_o(i, j, k);
/*  36 */     if (te instanceof TileEntityCable) {
/*     */       
/*  38 */       TileEntityCable cable = (TileEntityCable)te;
/*  39 */       if (cable.tryAddInsulation())
/*     */       {
/*  41 */         IItemTransporter transporter = TransporterManager.manager.getTransporter(entityplayer.field_71071_by);
/*  42 */         if (transporter != null && transporter.removeItem((IItemTransporter.IFilter)new BasicItemFilter(Ic2Items.rubber.func_77946_l()), ForgeDirection.UNKNOWN, 1, true) != null) {
/*     */           
/*  44 */           if (IC2.platform.isSimulating())
/*     */           {
/*  46 */             itemstack.func_77972_a(1, (EntityLivingBase)entityplayer);
/*     */           }
/*  48 */           entityplayer.field_71070_bA.func_75142_b();
/*  49 */           return true;
/*     */         } 
/*  51 */         cable.tryRemoveInsulation();
/*     */       }
/*     */     
/*  54 */     } else if (te instanceof TileEntityLuminatorMultipart) {
/*     */       
/*  56 */       TileEntityLuminatorMultipart multi = (TileEntityLuminatorMultipart)te;
/*  57 */       if (multi.tryAddIsolation()) {
/*     */         
/*  59 */         IItemTransporter transporter = TransporterManager.manager.getTransporter(entityplayer.field_71071_by);
/*  60 */         if (transporter != null && transporter.removeItem((IItemTransporter.IFilter)new BasicItemFilter(Ic2Items.rubber.func_77946_l()), ForgeDirection.UNKNOWN, 1, true) != null) {
/*     */           
/*  62 */           if (IC2.platform.isSimulating())
/*     */           {
/*  64 */             itemstack.func_77972_a(1, (EntityLivingBase)entityplayer);
/*     */           }
/*  66 */           entityplayer.field_71070_bA.func_75142_b();
/*  67 */           return true;
/*     */         } 
/*  69 */         multi.tryRemoveIsolation();
/*     */       } 
/*     */     } 
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void cutInsulationFrom(ItemStack itemstack, World world, int i, int j, int k, EntityLivingBase entity) {
/*  77 */     TileEntity te = world.func_147438_o(i, j, k);
/*  78 */     if (te instanceof TileEntityCable) {
/*     */       
/*  80 */       TileEntityCable cable = (TileEntityCable)te;
/*  81 */       if (cable.tryRemoveInsulation())
/*     */       {
/*  83 */         if (IC2.platform.isSimulating()) {
/*     */           
/*  85 */           double d = world.field_73012_v.nextFloat() * 0.7D + 0.15D;
/*  86 */           double d2 = world.field_73012_v.nextFloat() * 0.7D + 0.15D;
/*  87 */           double d3 = world.field_73012_v.nextFloat() * 0.7D + 0.15D;
/*  88 */           EntityItem entityitem = new EntityItem(world, i + d, j + d2, k + d3, StackUtil.copyWithSize(Ic2Items.rubber.func_77946_l(), 1));
/*  89 */           entityitem.field_145804_b = 10;
/*  90 */           world.func_72838_d((Entity)entityitem);
/*  91 */           itemstack.func_77972_a(3, entity);
/*     */         } 
/*  93 */         if (IC2.platform.isRendering())
/*     */         {
/*  95 */           IC2.audioManager.playOnce(new AudioPosition(world, i + 0.5F, j + 0.5F, k + 0.5F), PositionSpec.Center, "Tools/InsulationCutters.ogg", true, IC2.audioManager.defaultVolume);
/*     */         }
/*     */       }
/*     */     
/*  99 */     } else if (te instanceof TileEntityLuminatorMultipart) {
/*     */       
/* 101 */       TileEntityLuminatorMultipart multi = (TileEntityLuminatorMultipart)te;
/* 102 */       if (multi.tryRemoveIsolation()) {
/*     */         
/* 104 */         if (IC2.platform.isSimulating()) {
/*     */           
/* 106 */           double d = world.field_73012_v.nextFloat() * 0.7D + 0.15D;
/* 107 */           double d2 = world.field_73012_v.nextFloat() * 0.7D + 0.15D;
/* 108 */           double d3 = world.field_73012_v.nextFloat() * 0.7D + 0.15D;
/* 109 */           EntityItem entityitem = new EntityItem(world, i + d, j + d2, k + d3, StackUtil.copyWithSize(Ic2Items.rubber.func_77946_l(), 1));
/* 110 */           entityitem.field_145804_b = 10;
/* 111 */           world.func_72838_d((Entity)entityitem);
/* 112 */           itemstack.func_77972_a(3, entity);
/*     */         } 
/* 114 */         if (IC2.platform.isRendering())
/*     */         {
/* 116 */           IC2.audioManager.playOnce(new AudioPosition(world, i + 0.5F, j + 0.5F, k + 0.5F), PositionSpec.Center, "Tools/InsulationCutters.ogg", true, IC2.audioManager.defaultVolume);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemToolCutter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */