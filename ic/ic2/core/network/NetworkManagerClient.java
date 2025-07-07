/*     */ package ic2.core.network;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.network.INetworkDataProvider;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.network.internal.INetworkGuiDataProvider;
/*     */ import ic2.core.network.packets.IC2Packet;
/*     */ import ic2.core.network.packets.client.FieldRequestPacket;
/*     */ import ic2.core.network.packets.client.ItemClientEventPacket;
/*     */ import ic2.core.network.packets.client.KeyboardPacket;
/*     */ import ic2.core.network.packets.client.LoginPacket;
/*     */ import ic2.core.network.packets.client.TileClientEventPacket;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class NetworkManagerClient
/*     */   extends NetworkManager
/*     */ {
/*     */   public void requestInitialData(INetworkDataProvider dataProvider) {
/*  27 */     if (IC2.platform.isSimulating()) {
/*     */       return;
/*     */     }
/*     */     
/*  31 */     if (dataProvider instanceof TileEntity) {
/*     */       
/*  33 */       TileEntity te = (TileEntity)dataProvider;
/*  34 */       FieldRequestPacket packet = new FieldRequestPacket(te);
/*  35 */       PacketManager.instance.sendToServer((IC2Packet)packet);
/*     */     }
/*     */     else {
/*     */       
/*  39 */       IC2.platform.displayError("An unknown network data provider attempted to request data from the\nmultiplayer server.\nThis could happen due to a bug.\n\n(Technical information: " + dataProvider + ")");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateClientItemEvent(ItemStack itemStack, int event) {
/*  46 */     ItemClientEventPacket packet = new ItemClientEventPacket(itemStack, event);
/*  47 */     PacketManager.instance.sendToServer((IC2Packet)packet);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateKeyUpdate(int keyState) {
/*  53 */     KeyboardPacket packet = new KeyboardPacket(keyState);
/*  54 */     PacketManager.instance.sendToServer((IC2Packet)packet);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateClientTileEntityEvent(TileEntity te, int event) {
/*  60 */     TileClientEventPacket packet = new TileClientEventPacket(te, event);
/*  61 */     PacketManager.instance.sendToServer((IC2Packet)packet);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendLoginData() {
/*  67 */     LoginPacket packet = new LoginPacket(IC2.enableQuantumSpeedOnSprint);
/*  68 */     PacketManager.instance.sendToServer((IC2Packet)packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendInitialData(EntityPlayerMP mp, TileEntity tile) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void announceBlockUpdate(World world, int x, int y, int z) {
/*  79 */     if (IC2.platform.isSimulating())
/*     */     {
/*  81 */       super.announceBlockUpdate(world, x, y, z);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTileEntityField(TileEntity te, String field) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateTileEntityEvent(TileEntity te, int event, boolean limitRange) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateItemEvent(EntityPlayer player, ItemStack itemStack, int event, boolean limitRange) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateGuiDisplay(EntityPlayerMP entityPlayer, IHasGui inventory, int windowId) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateExplosionEffect(World world, double x, double y, double z) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateExplosionEffect(World world, double x, double y, double z, boolean nuke) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendCustomPacket(EntityPlayer player, IC2Packet packet) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendCustomPacket(IC2Packet packet) {
/* 123 */     PacketManager.instance.sendToServer(packet);
/*     */   }
/*     */   
/*     */   public void requestInitialGuiData(EntityPlayer player, INetworkGuiDataProvider dataProvider) {}
/*     */   
/*     */   public void updateTileGuiField(TileEntity te, String field) {}
/*     */   
/*     */   public void updateTileEntityField(TileEntity te, String field, EntityPlayerMP player) {}
/*     */   
/*     */   public void updateGuiChanges(EntityPlayer player, TileEntity tile) {}
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\network\NetworkManagerClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */