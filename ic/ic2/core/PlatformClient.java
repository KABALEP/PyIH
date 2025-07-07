/*     */ package ic2.core;
/*     */ 
/*     */ import cpw.mods.fml.client.FMLClientHandler;
/*     */ import cpw.mods.fml.client.registry.ClientRegistry;
/*     */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*     */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.FMLLog;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.gameevent.TickEvent;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.audio.GuiChoseAudioManager;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.EntityDynamite;
/*     */ import ic2.core.block.EntityIC2Explosive;
/*     */ import ic2.core.block.RenderBlockCable;
/*     */ import ic2.core.block.RenderBlockCrop;
/*     */ import ic2.core.block.RenderBlockFence;
/*     */ import ic2.core.block.RenderBlockRotation;
/*     */ import ic2.core.block.RenderExplosiveBlock;
/*     */ import ic2.core.block.RenderFlyingItem;
/*     */ import ic2.core.block.RenderTexturedWall;
/*     */ import ic2.core.block.machine.RenderBlockMiningPipe;
/*     */ import ic2.core.block.personal.RenderBlockPersonal;
/*     */ import ic2.core.block.personal.TileEntityPersonalChest;
/*     */ import ic2.core.block.personal.TileEntityPersonalChestRenderer;
/*     */ import ic2.core.block.wiring.RenderBlockLuminator;
/*     */ import ic2.core.block.wiring.RenderBlockLuminatorMultipart;
/*     */ import ic2.core.item.boats.EntityClassicBoat;
/*     */ import ic2.core.item.boats.RenderClassicBoat;
/*     */ import ic2.core.item.tool.EntityMiningLaser;
/*     */ import ic2.core.item.tool.ItemElectricToolChainsaw;
/*     */ import ic2.core.item.tool.ItemNanoSaber;
/*     */ import ic2.core.item.tool.RenderCrossed;
/*     */ import ic2.core.item.tool.RenderTextureCopier;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import net.minecraftforge.client.MinecraftForgeClient;
/*     */ import net.minecraftforge.client.event.GuiOpenEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class PlatformClient
/*     */   extends Platform
/*     */ {
/*     */   private static Minecraft mc;
/*     */   public static boolean open = false;
/*     */   private boolean debug;
/*     */   private static Achievement a;
/*     */   private int playerCounter;
/*     */   private Map renders;
/*     */   
/*     */   public void init() {
/*  79 */     super.init();
/*  80 */     this.debug = false;
/*  81 */     this.playerCounter = -1;
/*  82 */     this.renders = new HashMap<>();
/*  83 */     mc = FMLClientHandler.instance().getClient();
/*  84 */     a = new Achievement("ic2Achievementpage", new String(new byte[] { 105, 99, 50, 105, 110, 102, 111 }, ), 0, 0, Item.func_150898_a(Blocks.field_150335_W), (Achievement)null);
/*  85 */     FMLCommonHandler.instance().bus().register(this);
/*  86 */     MinecraftForge.EVENT_BUS.register(this);
/*  87 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderBlockCable());
/*  88 */     this.renders.put("cable", Integer.valueOf(RenderBlockCable.renderId));
/*  89 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderBlockCrop());
/*  90 */     this.renders.put("crop", Integer.valueOf(RenderBlockCrop.renderId));
/*  91 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderBlockFence());
/*  92 */     this.renders.put("fence", Integer.valueOf(RenderBlockFence.renderId));
/*  93 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderBlockLuminator());
/*  94 */     this.renders.put("luminator", Integer.valueOf(RenderBlockLuminator.renderId));
/*  95 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderBlockLuminatorMultipart());
/*  96 */     this.renders.put("luminatorMulti", Integer.valueOf(RenderBlockLuminatorMultipart.renderId));
/*  97 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderBlockMiningPipe());
/*  98 */     this.renders.put("miningPipe", Integer.valueOf(RenderBlockMiningPipe.renderId));
/*  99 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderBlockPersonal());
/* 100 */     this.renders.put("personal", Integer.valueOf(RenderBlockPersonal.renderId));
/* 101 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderTexturedWall());
/* 102 */     this.renders.put("wall", Integer.valueOf(RenderTexturedWall.renderId));
/* 103 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderBlockRotation());
/* 104 */     this.renders.put("rotated", Integer.valueOf(RenderBlockRotation.renderId));
/* 105 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPersonalChest.class, (TileEntitySpecialRenderer)new TileEntityPersonalChestRenderer());
/* 106 */     RenderingRegistry.registerEntityRenderingHandler(EntityIC2Explosive.class, (Render)new RenderExplosiveBlock());
/* 107 */     RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, (Render)new RenderFlyingItem(22, Ic2Icons.getTexture("i1")));
/* 108 */     RenderingRegistry.registerEntityRenderingHandler(EntityMiningLaser.class, (Render)new RenderCrossed(new ResourceLocation("ic2", "textures/guiSprites/laser.png")));
/* 109 */     RenderingRegistry.registerEntityRenderingHandler(EntityClassicBoat.class, (Render)new RenderClassicBoat());
/* 110 */     MinecraftForgeClient.registerItemRenderer(Ic2Items.obscurator.func_77973_b(), (IItemRenderer)new RenderTextureCopier());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayError(String error) {
/* 116 */     FMLLog.severe(("IndustrialCraft 2 Error\n\n" + error).replace("\n", System.getProperty("line.separator")), new Object[0]);
/* 117 */     Minecraft minecraft = FMLClientHandler.instance().getClient();
/* 118 */     GL11.glEnable(3553);
/* 119 */     GL11.glEnable(3008);
/* 120 */     GL11.glAlphaFunc(516, 0.1F);
/* 121 */     GL11.glEnable(2929);
/* 122 */     GL11.glDepthFunc(515);
/* 123 */     GL11.glViewport(0, 0, minecraft.field_71443_c, minecraft.field_71440_d);
/* 124 */     ScaledResolution scaledResolution = new ScaledResolution(minecraft, minecraft.field_71443_c, minecraft.field_71440_d);
/* 125 */     GL11.glClear(16640);
/* 126 */     GL11.glMatrixMode(5889);
/* 127 */     GL11.glLoadIdentity();
/* 128 */     GL11.glOrtho(0.0D, scaledResolution.func_78327_c(), scaledResolution.func_78324_d(), 0.0D, 1000.0D, 3000.0D);
/* 129 */     GL11.glMatrixMode(5888);
/* 130 */     GL11.glLoadIdentity();
/* 131 */     GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
/* 132 */     minecraft.func_71364_i();
/* 133 */     GuiIC2ErrorScreen errorScreen = new GuiIC2ErrorScreen(error);
/* 134 */     errorScreen.func_146280_a(minecraft, scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
/* 135 */     errorScreen.func_73863_a(0, 0, 0.0F);
/* 136 */     GL11.glFinish();
/* 137 */     Display.update();
/*     */     
/*     */     try {
/* 140 */       Thread.sleep(30000L);
/*     */     }
/* 142 */     catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 145 */     Display.destroy();
/* 146 */     FMLCommonHandler.instance().exitJava(0, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayer getPlayerInstance() {
/* 152 */     return (EntityPlayer)(FMLClientHandler.instance().getClient()).field_71439_g;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean launchGuiClient(EntityPlayer entityPlayer, IHasGui inventory) {
/*     */     GuiScreen guiScreen;
/* 158 */     String clientPackage = "ic2.core";
/* 159 */     Package pkg = PlatformClient.class.getPackage();
/* 160 */     if (pkg != null)
/*     */     {
/* 162 */       clientPackage = pkg.getName();
/*     */     }
/* 164 */     ContainerIC2 container = inventory.getGuiContainer(entityPlayer);
/* 165 */     Class<?> containerClass = container.getClass();
/*     */ 
/*     */     
/*     */     try {
/* 169 */       guiScreen = Class.forName(clientPackage + "." + inventory.getGuiClassName(entityPlayer)).getConstructor(new Class[] { containerClass }).newInstance(new Object[] { containerClass.cast(container) });
/*     */     }
/* 171 */     catch (Exception e) {
/*     */       
/* 173 */       throw new RuntimeException(e);
/*     */     } 
/* 175 */     FMLClientHandler.instance().displayGuiScreen(entityPlayer, guiScreen);
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void profilerStartSection(String section) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void profilerEndSection() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void profilerEndStartSection(String section) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLocalization(String name, String desc) {
/* 197 */     super.addLocalization(name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPVP() {
/* 203 */     if (isSimulating())
/*     */     {
/* 205 */       return super.isPVP();
/*     */     }
/* 207 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public File getMinecraftDir() {
/* 213 */     return (FMLClientHandler.instance().getClient()).field_71412_D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSoundSp(String sound, float f, float g) {
/* 219 */     (FMLClientHandler.instance().getClient()).field_71441_e.func_72956_a((Entity)getPlayerInstance(), sound, f, g);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getBlockTexture(Block block, IBlockAccess world, int x, int y, int z, int side) {
/* 225 */     return block.func_149673_e(world, x, y, z, side);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld(int dimID) {
/* 231 */     if (isSimulating())
/*     */     {
/* 233 */       return super.getWorld(dimID);
/*     */     }
/* 235 */     WorldClient worldClient = (FMLClientHandler.instance().getClient()).field_71441_e;
/* 236 */     return (((World)worldClient).field_73011_w.field_76574_g == dimID) ? (World)worldClient : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getClientWorld(int dimID) {
/* 245 */     if (isSimulating())
/*     */     {
/* 247 */       return super.getWorld(dimID);
/*     */     }
/* 249 */     WorldClient worldClient = (FMLClientHandler.instance().getClient()).field_71441_e;
/* 250 */     return (((World)worldClient).field_73011_w.field_76574_g == dimID) ? (World)worldClient : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int addArmor(String name) {
/* 256 */     return RenderingRegistry.addNewArmourRendererPrefix(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderId(String name) {
/* 262 */     return ((Integer)this.renders.get(name)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent evt) {
/* 268 */     if (evt.phase != TickEvent.Phase.START) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 273 */     IC2.keyboard.sendKeyUpdate();
/* 274 */     IC2.audioManager.onTick();
/* 275 */     EntityPlayer player = getPlayerInstance();
/* 276 */     if (player != null) {
/*     */       
/* 278 */       for (int i = 0; i < 4; i++) {
/*     */         
/* 280 */         if (player.field_71071_by.field_70460_b[i] != null && player.field_71071_by.field_70460_b[i].func_77973_b() instanceof IItemTickListener)
/*     */         {
/* 282 */           ((IItemTickListener)player.field_71071_by.field_70460_b[i].func_77973_b()).onTick(player, player.field_71071_by.field_70460_b[i]);
/*     */         }
/*     */       } 
/* 285 */       if (ItemElectricToolChainsaw.audioSource != null && (player.field_71071_by.func_70448_g() == null || player.field_71071_by.func_70448_g().func_77973_b() != Ic2Items.chainsaw.func_77973_b())) {
/*     */         
/* 287 */         ItemElectricToolChainsaw.audioSource.stop();
/* 288 */         ItemElectricToolChainsaw.audioSource.remove();
/* 289 */         ItemElectricToolChainsaw.audioSource = null;
/* 290 */         ItemElectricToolChainsaw.wasEquipped = false;
/* 291 */         IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/Chainsaw/ChainsawStop.ogg", true, IC2.audioManager.defaultVolume);
/*     */       } 
/* 293 */       if (ItemNanoSaber.sound != null) {
/*     */         
/* 295 */         boolean found = false;
/* 296 */         for (int j = 0; j < player.field_71071_by.func_70302_i_(); j++) {
/*     */           
/* 298 */           ItemStack cu = player.field_71071_by.func_70301_a(j);
/* 299 */           if (cu != null && cu.func_77973_b() == Ic2Items.enabledNanoSaber.func_77973_b()) {
/*     */             
/* 301 */             found = true;
/*     */             break;
/*     */           } 
/* 304 */           if (j == player.field_71071_by.field_70461_c && player.field_71071_by.func_70448_g() != null && (player.field_71071_by.func_70448_g().func_77973_b() == Ic2Items.enabledNanoSaber.func_77973_b() || player.field_71071_by.func_70448_g().func_77973_b() == Ic2Items.nanoSaber.func_77973_b()))
/*     */           {
/* 306 */             found = true;
/*     */           }
/*     */         } 
/* 309 */         if (!found) {
/*     */           
/* 311 */           ItemNanoSaber.sound.stop();
/* 312 */           ItemNanoSaber.sound.remove();
/* 313 */           ItemNanoSaber.sound = null;
/*     */         } 
/*     */       } 
/*     */     } 
/* 317 */     if (this.debug)
/*     */     {
/* 319 */       mc.field_71458_u.func_146255_b(a);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onGuiOpened(GuiOpenEvent evt) {
/* 327 */     GuiScreen screen = evt.gui;
/* 328 */     if (screen != null) {
/*     */       
/* 330 */       if (screen instanceof net.minecraft.client.gui.GuiScreenOptionsSounds && !open)
/*     */       {
/* 332 */         evt.gui = (GuiScreen)new GuiChoseAudioManager();
/* 333 */         open = true;
/*     */       }
/* 335 */       else if (open && screen instanceof net.minecraft.client.gui.GuiMainMenu)
/*     */       {
/* 337 */         open = false;
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 343 */       open = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\PlatformClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */