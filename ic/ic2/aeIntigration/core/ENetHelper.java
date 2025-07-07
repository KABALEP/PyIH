/*     */ package ic2.aeIntigration.core;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.gameevent.TickEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ENetHelper
/*     */ {
/*  17 */   private static Map<Long, EnergyNetwork> network = new HashMap<>();
/*  18 */   private static List<ClassicEUP2PTunnel> clickedOnce = new ArrayList<>();
/*  19 */   private static int time = 0;
/*     */ 
/*     */   
/*     */   public static void removeNetwork(long freq) {
/*  23 */     network.remove(Long.valueOf(freq));
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnergyNetwork getNetwork(long freq) {
/*  28 */     if (freq == 0L)
/*     */     {
/*  30 */       return null;
/*     */     }
/*  32 */     if (!network.containsKey(Long.valueOf(freq)))
/*     */     {
/*  34 */       network.put(Long.valueOf(freq), new EnergyNetwork(freq));
/*     */     }
/*  36 */     return network.get(Long.valueOf(freq));
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTickStart(TickEvent.ServerTickEvent evt) {
/*  42 */     time++;
/*  43 */     if (evt.phase == TickEvent.Phase.END || time <= 20) {
/*     */       return;
/*     */     }
/*     */     
/*  47 */     time = -80;
/*  48 */     handleClickedOnce();
/*  49 */     for (EnergyNetwork toCheck : Lists.newArrayList(network.values()))
/*     */     {
/*  51 */       updateNetwork(toCheck);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onWorldUnload(WorldEvent.Unload evt) {
/*  58 */     if (evt.world.field_73011_w.field_76574_g == 0) {
/*     */       
/*  60 */       for (EnergyNetwork networks : Lists.newArrayList(network.values())) {
/*     */         
/*  62 */         networks.removeing = true;
/*  63 */         networks.updateEnergyNet();
/*     */       } 
/*  65 */       network.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateNetwork(EnergyNetwork network) {
/*  71 */     if (network != null) {
/*     */       
/*  73 */       network.updateNetwork();
/*  74 */       network.validateNetwork();
/*  75 */       network.updateEnergyNet();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleClickedOnce() {
/*  81 */     for (ClassicEUP2PTunnel toCheck : Lists.newArrayList(clickedOnce)) {
/*     */       
/*  83 */       EnergyNetwork network = getNetwork(toCheck.getFrequency());
/*  84 */       if (network != null && network.canAdd(toCheck)) {
/*     */         
/*  86 */         network.addTunnel(toCheck);
/*  87 */         toCheck.getHost().markForUpdate();
/*     */       } 
/*     */     } 
/*  90 */     clickedOnce.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addTunnel(ClassicEUP2PTunnel tunnel) {
/*  95 */     EnergyNetwork network = getNetwork(tunnel.getFrequency());
/*  96 */     if (network != null && network.canAdd(tunnel))
/*     */     {
/*  98 */       network.addTunnel(tunnel);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void removeTunnel(ClassicEUP2PTunnel tunnel) {
/* 104 */     EnergyNetwork network = getNetwork(tunnel.getFrequency());
/* 105 */     if (network != null) {
/*     */       
/* 107 */       network.removeTunnel(tunnel);
/* 108 */       tunnel.getHost().markForUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addClickedTunnels(ClassicEUP2PTunnel tunnel) {
/* 114 */     if (!clickedOnce.contains(tunnel))
/*     */     {
/* 116 */       clickedOnce.add(tunnel);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\aeIntigration\core\ENetHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */