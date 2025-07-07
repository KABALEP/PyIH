/*     */ package ic2.api.network;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NetworkHelper
/*     */ {
/*     */   private static Object instance;
/*     */   private static Method NetworkManager_updateTileEntityField;
/*     */   private static Method NetworkManager_initiateTileEntityEvent;
/*     */   private static Method NetworkManager_initiateItemEvent;
/*     */   private static Method NetworkManager_initiateClientTileEntityEvent;
/*     */   private static Method NetworkManager_initiateClientItemEvent;
/*     */   
/*     */   public static void updateTileEntityField(TileEntity te, String field) {
/*     */     try {
/*  58 */       if (NetworkManager_updateTileEntityField == null) NetworkManager_updateTileEntityField = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("updateTileEntityField", new Class[] { TileEntity.class, String.class }); 
/*  59 */       if (instance == null) instance = getInstance();
/*     */       
/*  61 */       NetworkManager_updateTileEntityField.invoke(instance, new Object[] { te, field });
/*  62 */     } catch (Exception e) {
/*  63 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initiateTileEntityEvent(TileEntity te, int event, boolean limitRange) {
/*     */     try {
/*  80 */       if (NetworkManager_initiateTileEntityEvent == null) NetworkManager_initiateTileEntityEvent = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("initiateTileEntityEvent", new Class[] { TileEntity.class, int.class, boolean.class }); 
/*  81 */       if (instance == null) instance = getInstance();
/*     */       
/*  83 */       NetworkManager_initiateTileEntityEvent.invoke(instance, new Object[] { te, Integer.valueOf(event), Boolean.valueOf(limitRange) });
/*  84 */     } catch (Exception e) {
/*  85 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initiateItemEvent(EntityPlayer player, ItemStack itemStack, int event, boolean limitRange) {
/*     */     try {
/* 105 */       if (NetworkManager_initiateItemEvent == null) NetworkManager_initiateItemEvent = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("initiateItemEvent", new Class[] { EntityPlayer.class, ItemStack.class, int.class, boolean.class }); 
/* 106 */       if (instance == null) instance = getInstance();
/*     */       
/* 108 */       NetworkManager_initiateItemEvent.invoke(instance, new Object[] { player, itemStack, Integer.valueOf(event), Boolean.valueOf(limitRange) });
/* 109 */     } catch (Exception e) {
/* 110 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initiateClientTileEntityEvent(TileEntity te, int event) {
/*     */     try {
/* 127 */       if (NetworkManager_initiateClientTileEntityEvent == null) NetworkManager_initiateClientTileEntityEvent = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("initiateClientTileEntityEvent", new Class[] { TileEntity.class, int.class }); 
/* 128 */       if (instance == null) instance = getInstance();
/*     */       
/* 130 */       NetworkManager_initiateClientTileEntityEvent.invoke(instance, new Object[] { te, Integer.valueOf(event) });
/* 131 */     } catch (Exception e) {
/* 132 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initiateClientItemEvent(ItemStack itemStack, int event) {
/*     */     try {
/* 148 */       if (NetworkManager_initiateClientItemEvent == null) NetworkManager_initiateClientItemEvent = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("initiateClientItemEvent", new Class[] { ItemStack.class, int.class }); 
/* 149 */       if (instance == null) instance = getInstance();
/*     */       
/* 151 */       NetworkManager_initiateClientItemEvent.invoke(instance, new Object[] { itemStack, Integer.valueOf(event) });
/* 152 */     } catch (Exception e) {
/* 153 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getPackage() {
/* 163 */     Package pkg = NetworkHelper.class.getPackage();
/*     */     
/* 165 */     if (pkg != null) {
/* 166 */       String packageName = pkg.getName();
/*     */       
/* 168 */       return packageName.substring(0, packageName.length() - ".api.network".length());
/*     */     } 
/*     */     
/* 171 */     return "ic2";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object getInstance() {
/*     */     try {
/* 181 */       return Class.forName(getPackage() + ".core.util.SideGateway").getMethod("get", new Class[0]).invoke(Class.forName(getPackage() + ".core.IC2").getDeclaredField("network").get(null), new Object[0]);
/* 182 */     } catch (Throwable e) {
/* 183 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\network\NetworkHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */