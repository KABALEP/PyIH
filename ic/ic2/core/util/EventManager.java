/*     */ package ic2.core.util;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.EventPriority;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import ic2.api.item.IWrenchHandler;
/*     */ import ic2.api.tile.IWrenchable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventManager
/*     */ {
/*     */   public EventManager() {
/*  27 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onBlockClick(PlayerInteractEvent evt) {
/*  33 */     if (evt.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
/*     */       
/*  35 */       World world = evt.world;
/*  36 */       int x = evt.x;
/*  37 */       int y = evt.y;
/*  38 */       int z = evt.z;
/*  39 */       EntityPlayer player = evt.entityPlayer;
/*  40 */       ItemStack item = player.func_71045_bC();
/*  41 */       TileEntity tile = world.func_147438_o(x, y, z);
/*  42 */       IWrenchHandler handler = getWrenchHandler(item);
/*  43 */       if (handler != null && tile instanceof IWrenchable && handler.canWrench(item, x, y, z, player)) {
/*     */         
/*  45 */         boolean flag = handleWrench(handler, (IWrenchable)tile, item, player, world, x, y, z, evt.face);
/*  46 */         if (flag)
/*     */         {
/*  48 */           evt.setCanceled(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean handleWrench(IWrenchHandler par1, IWrenchable wrenchable, ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side) {
/*  56 */     if (wrenchable instanceof TileEntityTerra) {
/*     */       
/*  58 */       TileEntityTerra terra = (TileEntityTerra)wrenchable;
/*  59 */       if (terra.ejectBlueprint()) {
/*     */         
/*  61 */         if (IC2.platform.isSimulating())
/*     */         {
/*  63 */           par1.useWrench(item, x, y, z, player);
/*     */         }
/*  65 */         if (IC2.platform.isRendering())
/*     */         {
/*  67 */           IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.defaultVolume);
/*     */         }
/*  69 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     if (IC2.keyboard.isAltKeyDown(player)) {
/*     */       
/*  75 */       if (player.func_70093_af())
/*     */       {
/*  77 */         side = (wrenchable.getFacing() + 5) % 6;
/*     */       }
/*     */       else
/*     */       {
/*  81 */         side = (wrenchable.getFacing() + 1) % 6;
/*     */       }
/*     */     
/*  84 */     } else if (player.func_70093_af()) {
/*     */       
/*  86 */       side += side % 2 * -2 + 1;
/*     */     } 
/*  88 */     if (wrenchable.wrenchCanSetFacing(player, side)) {
/*     */       
/*  90 */       if (IC2.platform.isSimulating()) {
/*     */         
/*  92 */         wrenchable.setFacing((short)side);
/*  93 */         par1.useWrench(item, x, y, z, player);
/*     */       } 
/*  95 */       if (IC2.platform.isRendering())
/*     */       {
/*  97 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.defaultVolume);
/*     */       }
/*  99 */       return IC2.platform.isSimulating();
/*     */     } 
/* 101 */     if (wrenchable.wrenchCanRemove(player)) {
/*     */       
/* 103 */       if (IC2.platform.isSimulating()) {
/*     */         
/* 105 */         Block block = world.func_147439_a(x, y, z);
/* 106 */         int meta = world.func_72805_g(x, y, z);
/* 107 */         boolean dropOriginalBlock = false;
/* 108 */         if (wrenchable.getWrenchDropRate() < 1.0F && checkIfNoloss(par1)) {
/*     */           
/* 110 */           dropOriginalBlock = true;
/* 111 */           par1.useWrench(item, x, y, z, player);
/*     */         }
/*     */         else {
/*     */           
/* 115 */           dropOriginalBlock = (world.field_73012_v.nextFloat() <= wrenchable.getWrenchDropRate());
/* 116 */           par1.useWrench(item, x, y, z, player);
/*     */         } 
/* 118 */         List<ItemStack> drops = block.getDrops(world, x, y, z, meta, 0);
/* 119 */         if (dropOriginalBlock)
/*     */         {
/* 121 */           if (drops.isEmpty()) {
/*     */             
/* 123 */             drops.add(wrenchable.getWrenchDrop(player));
/*     */           }
/*     */           else {
/*     */             
/* 127 */             drops.set(0, wrenchable.getWrenchDrop(player));
/*     */           } 
/*     */         }
/* 130 */         for (ItemStack itemStack : drops)
/*     */         {
/* 132 */           StackUtil.dropAsEntity(world, x, y, z, itemStack);
/*     */         }
/* 134 */         world.func_147468_f(x, y, z);
/*     */       } 
/* 136 */       if (IC2.platform.isRendering())
/*     */       {
/* 138 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.defaultVolume);
/*     */       }
/* 140 */       return IC2.platform.isSimulating();
/*     */     } 
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkIfNoloss(IWrenchHandler par1) {
/* 147 */     return IC2.losslessAddonWrenches;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IWrenchHandler getWrenchHandler(ItemStack par1) {
/* 153 */     if (par1 == null)
/*     */     {
/* 155 */       return null;
/*     */     }
/* 157 */     for (IWrenchHandler handler : IC2.handlers) {
/*     */       
/* 159 */       if (handler.supportsItem(par1))
/*     */       {
/* 161 */         return handler;
/*     */       }
/*     */     } 
/* 164 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\EventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */