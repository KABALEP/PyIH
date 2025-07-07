/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import ic2.api.event.PaintEvent;
/*     */ import ic2.api.network.INetworkItemEventListener;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockColored;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.event.entity.EntityEvent;
/*     */ import net.minecraftforge.event.entity.player.EntityInteractEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerEvent;
/*     */ 
/*     */ public class ItemToolPainter
/*     */   extends ItemIC2 implements INetworkItemEventListener {
/*  38 */   public static HashMap<ChunkCoordIntPair, Integer> dyes = new HashMap<>();
/*     */   
/*     */   public int color;
/*     */   
/*     */   public ItemToolPainter(int col) {
/*  43 */     super(64);
/*  44 */     func_77656_e(32);
/*  45 */     func_77625_d(1);
/*  46 */     this.color = col;
/*  47 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_77617_a(int i) {
/*  53 */     return Ic2Icons.getTexture("i1")[this.iconIndex + this.color];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float a, float b, float c) {
/*  58 */     PaintEvent event = new PaintEvent(world, i, j, k, side, this.color);
/*  59 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  60 */     if (event.painted) {
/*     */       
/*  62 */       if (IC2.platform.isSimulating())
/*     */       {
/*  64 */         damagePainter(entityplayer);
/*     */       }
/*  66 */       ((NetworkManager)IC2.network.get()).initiateItemEvent(entityplayer, itemstack, 0, false);
/*     */       
/*  68 */       return IC2.platform.isSimulating();
/*     */     } 
/*  70 */     Block blockId = world.func_147439_a(i, j, k);
/*  71 */     if (blockId != null && blockId.recolourBlock(world, i, j, k, ForgeDirection.getOrientation(side), BlockColored.func_150031_c(this.color))) {
/*     */       
/*  73 */       if (IC2.platform.isSimulating())
/*     */       {
/*  75 */         damagePainter(entityplayer);
/*     */       }
/*  77 */       ((NetworkManager)IC2.network.get()).initiateItemEvent(entityplayer, itemstack, 0, false);
/*     */       
/*  79 */       return IC2.platform.isSimulating();
/*     */     } 
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public boolean onEntityInteract(EntityInteractEvent event) {
/*  87 */     EntityPlayer player = ((PlayerEvent)event).entityPlayer;
/*  88 */     Entity entity = ((EntityEvent)event).entity;
/*  89 */     if (entity.field_70170_p.field_72995_K || player.func_71045_bC() == null || player.func_71045_bC().func_77973_b() != this)
/*     */     {
/*  91 */       return true;
/*     */     }
/*  93 */     boolean ret = true;
/*  94 */     if (entity instanceof EntitySheep) {
/*     */       
/*  96 */       EntitySheep sheep = (EntitySheep)entity;
/*  97 */       int clr = BlockColored.func_150031_c(this.color);
/*  98 */       if (sheep.func_70896_n() != clr) {
/*     */         
/* 100 */         ret = false;
/* 101 */         ((EntitySheep)entity).func_70891_b(clr);
/* 102 */         damagePainter(player);
/*     */       } 
/*     */     } 
/* 105 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/* 110 */     if (IC2.platform.isSimulating() && IC2.keyboard.isModeSwitchKeyDown(entityplayer)) {
/*     */       
/* 112 */       NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemstack);
/* 113 */       boolean newValue = !nbtData.func_74767_n("autoRefill");
/* 114 */       nbtData.func_74757_a("autoRefill", newValue);
/* 115 */       if (newValue) {
/*     */         
/* 117 */         IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.painterAutoRefillEnabled.name"));
/*     */       }
/*     */       else {
/*     */         
/* 121 */         IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.painterAutoRefillDisabled.name"));
/*     */       } 
/*     */     } 
/* 124 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> info, boolean debugTooltips) {
/* 129 */     info.add(StatCollector.func_74838_a(Items.field_151100_aR.func_77667_c(new ItemStack(Items.field_151100_aR, 1, this.color)) + ".name"));
/*     */   }
/*     */ 
/*     */   
/*     */   private void damagePainter(EntityPlayer player) {
/* 134 */     if (player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c].func_77960_j() >= player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c].func_77958_k() - 1) {
/*     */       
/* 136 */       int dyeIS = -1;
/* 137 */       NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c]);
/* 138 */       if (nbtData.func_74767_n("autoRefill"))
/*     */       {
/* 140 */         for (int l = 0; l < player.field_71071_by.field_70462_a.length; l++) {
/*     */           
/* 142 */           if (player.field_71071_by.field_70462_a[l] != null) {
/*     */             
/* 144 */             ChunkCoordIntPair ip = new ChunkCoordIntPair(Item.func_150891_b(player.field_71071_by.field_70462_a[l].func_77973_b()), player.field_71071_by.field_70462_a[l].func_77960_j());
/* 145 */             if (dyes.containsKey(ip) && ((Integer)dyes.get(ip)).intValue() == this.color) {
/*     */               
/* 147 */               dyeIS = l;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 153 */       if (dyeIS == -1)
/*     */       {
/* 155 */         player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = Ic2Items.painter.func_77946_l();
/*     */       }
/*     */       else
/*     */       {
/* 159 */         ItemStack itemStack = player.field_71071_by.field_70462_a[dyeIS];
/* 160 */         itemStack.field_77994_a--;
/* 161 */         if ((player.field_71071_by.field_70462_a[dyeIS]).field_77994_a == 0)
/*     */         {
/* 163 */           player.field_71071_by.field_70462_a[dyeIS] = null;
/*     */         }
/* 165 */         player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c].func_77964_b(0);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 170 */       player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c].func_77972_a(1, (EntityLivingBase)player);
/*     */     } 
/* 172 */     player.field_71070_bA.func_75142_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(ItemStack stack, EntityPlayer player, int event) {
/* 178 */     IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/Painter.ogg", true, IC2.audioManager.defaultVolume);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemToolPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */