/*     */ package ic2.core;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.ObfuscationReflectionHelper;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.AddonModifier;
/*     */ import java.io.File;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Platform
/*     */ {
/*     */   public void init() {}
/*     */   
/*     */   public void postInit() {
/*  30 */     AddonModifier.modify();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSimulating() {
/*  35 */     return !FMLCommonHandler.instance().getEffectiveSide().isClient();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRendering() {
/*  40 */     return !isSimulating();
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayError(String error) {
/*  45 */     throw new RuntimeException(("IndustrialCraft 2 Error\n\n=== IndustrialCraft 2 Error ===\n\n" + error + "\n\n===============================\n").replace("\n", System.getProperty("line.separator")));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPlayer getPlayerInstance() {
/*  50 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorld(int dimID) {
/*  55 */     return (World)DimensionManager.getWorld(dimID);
/*     */   }
/*     */ 
/*     */   
/*     */   public World getClientWorld(int dimID) {
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void messagePlayer(EntityPlayer player, String message) {
/*  65 */     if (player instanceof EntityPlayerMP) {
/*     */       
/*  67 */       ((EntityPlayerMP)player).func_146105_b((IChatComponent)new ChatComponentText(message));
/*     */     }
/*     */     else {
/*     */       
/*  71 */       player.func_145747_a((IChatComponent)new ChatComponentText(message));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean launchGui(EntityPlayer player, IHasGui inventory) {
/*  77 */     if (player instanceof EntityPlayerMP) {
/*     */       
/*  79 */       EntityPlayerMP entityPlayerMp = (EntityPlayerMP)player;
/*  80 */       int windowId = entityPlayerMp.field_71139_cq % 100 + 1;
/*  81 */       entityPlayerMp.field_71139_cq = windowId;
/*  82 */       entityPlayerMp.func_71128_l();
/*  83 */       ((NetworkManager)IC2.network.get()).initiateGuiDisplay(entityPlayerMp, inventory, windowId);
/*  84 */       player.field_71070_bA = inventory.getGuiContainer(player);
/*  85 */       player.field_71070_bA.field_75152_c = windowId;
/*  86 */       player.field_71070_bA.func_75132_a((ICrafting)entityPlayerMp);
/*  87 */       return true;
/*     */     } 
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean launchGuiClient(EntityPlayer player, IHasGui inventory) {
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void profilerStartSection(String section) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void profilerEndSection() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void profilerEndStartSection(String section) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLocalization(String name, String desc) {}
/*     */ 
/*     */   
/*     */   public File getMinecraftDir() {
/* 115 */     return new File(".");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSoundSp(String sound, float f, float g) {}
/*     */ 
/*     */   
/*     */   public void resetPlayerInAirTime(EntityPlayer player) {
/* 124 */     if (!(player instanceof EntityPlayerMP)) {
/*     */       return;
/*     */     }
/*     */     
/* 128 */     ObfuscationReflectionHelper.setPrivateValue(NetHandlerPlayServer.class, ((EntityPlayerMP)player).field_71135_a, Integer.valueOf(0), new String[] { "field_147365_f", "floatingTickCount" });
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon getBlockTexture(Block block, IBlockAccess world, int x, int y, int z, int side) {
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int addArmor(String name) {
/* 138 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePotion(EntityLivingBase entity, int potion) {
/* 143 */     entity.func_82170_o(potion);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderId(String name) {
/* 148 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean isPVP() {
/* 152 */     return MinecraftServer.func_71276_C().func_71219_W();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\Platform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */