/*     */ package ic2.core.util;
/*     */ 
/*     */ import cpw.mods.fml.client.FMLClientHandler;
/*     */ import cpw.mods.fml.client.registry.ClientRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class KeyboardClient
/*     */   extends Keyboard
/*     */ {
/*     */   private Minecraft mc;
/*     */   private KeyBinding altKey;
/*     */   private KeyBinding boostKey;
/*     */   private KeyBinding modeSwitchKey;
/*     */   private KeyBinding sideinventoryKey;
/*     */   private KeyBinding expandinfo;
/*  25 */   private int lastKeyState = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  32 */     this.mc = FMLClientHandler.instance().getClient();
/*  33 */     this.altKey = new KeyBinding("ALT Key", 56, "IC2 Keys");
/*  34 */     this.boostKey = new KeyBinding("Boost Key", 29, "IC2 Keys");
/*  35 */     this.modeSwitchKey = new KeyBinding("Mode Switch Key", 50, "IC2 Keys");
/*  36 */     this.sideinventoryKey = new KeyBinding("Side Inventory Key", 46, "IC2 Keys");
/*  37 */     this.expandinfo = new KeyBinding("IC2 Hub Expand Key", 45, "IC2 Keys");
/*  38 */     ClientRegistry.registerKeyBinding(this.altKey);
/*  39 */     ClientRegistry.registerKeyBinding(this.boostKey);
/*  40 */     ClientRegistry.registerKeyBinding(this.modeSwitchKey);
/*  41 */     ClientRegistry.registerKeyBinding(this.sideinventoryKey);
/*  42 */     ClientRegistry.registerKeyBinding(this.expandinfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendKeyUpdate() {
/*  50 */     int currentKeyState = (GameSettings.func_100015_a(this.altKey) ? 1 : 0) << 0 | (GameSettings.func_100015_a(this.boostKey) ? 1 : 0) << 1 | (GameSettings.func_100015_a(this.mc.field_71474_y.field_74351_w) ? 1 : 0) << 2 | (GameSettings.func_100015_a(this.modeSwitchKey) ? 1 : 0) << 3 | (GameSettings.func_100015_a(this.mc.field_71474_y.field_74314_A) ? 1 : 0) << 4 | (GameSettings.func_100015_a(this.sideinventoryKey) ? 1 : 0) << 5 | (GameSettings.func_100015_a(this.expandinfo) ? 1 : 0) << 6;
/*  51 */     if ((IC2.guiDissablesKeys && this.mc.field_71462_r != null) || this.mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat)
/*     */     {
/*  53 */       currentKeyState = 0;
/*     */     }
/*  55 */     if (currentKeyState != this.lastKeyState || currentKeyState == 0) {
/*     */       
/*  57 */       if (currentKeyState != this.lastKeyState)
/*     */       {
/*  59 */         ((NetworkManager)IC2.network.get()).initiateKeyUpdate(currentKeyState);
/*     */       }
/*  61 */       processKeyUpdate(IC2.platform.getPlayerInstance(), currentKeyState);
/*  62 */       this.lastKeyState = currentKeyState;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKey(int type) {
/*  68 */     int key = 0;
/*  69 */     switch (type) {
/*     */       
/*     */       case 0:
/*  72 */         key = this.altKey.func_151463_i();
/*     */         break;
/*     */       case 1:
/*  75 */         key = this.boostKey.func_151463_i();
/*     */         break;
/*     */       case 2:
/*  78 */         key = this.modeSwitchKey.func_151463_i();
/*     */         break;
/*     */       case 3:
/*  81 */         key = this.sideinventoryKey.func_151463_i();
/*     */         break;
/*     */       case 4:
/*  84 */         key = this.expandinfo.func_151463_i();
/*     */         break;
/*     */     } 
/*     */     
/*     */     try {
/*  89 */       String s = Keyboard.getKeyName(key);
/*  90 */       return s;
/*     */     }
/*  92 */     catch (Exception e) {
/*     */ 
/*     */       
/*     */       try {
/*  96 */         String s = Mouse.getButtonName(key);
/*  97 */         if (s == null)
/*     */         {
/*  99 */           return "Unknowen Key";
/*     */         }
/* 101 */         return s;
/*     */       }
/* 103 */       catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 108 */         return "Unknowen Key";
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\KeyboardClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */