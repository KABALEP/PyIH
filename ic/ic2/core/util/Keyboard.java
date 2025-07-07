/*    */ package ic2.core.util;
/*    */ 
/*    */ import ic2.api.util.IKeyboard;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Keyboard
/*    */   implements IKeyboard
/*    */ {
/* 15 */   private Map<EntityPlayer, Boolean> altKeyState = new HashMap<>();
/* 16 */   private Map<EntityPlayer, Boolean> boostKeyState = new HashMap<>();
/* 17 */   private Map<EntityPlayer, Boolean> forwardKeyState = new HashMap<>();
/* 18 */   private Map<EntityPlayer, Boolean> modeSwitchKeyState = new HashMap<>();
/* 19 */   private Map<EntityPlayer, Boolean> jumpKeyState = new HashMap<>();
/* 20 */   private Map<EntityPlayer, Boolean> sideinventoryKeyState = new HashMap<>();
/* 21 */   private Map<EntityPlayer, Boolean> hudModeKeyState = new HashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAltKeyDown(EntityPlayer player) {
/* 26 */     return (this.altKeyState.containsKey(player) && ((Boolean)this.altKeyState.get(player)).booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBoostKeyDown(EntityPlayer player) {
/* 31 */     return (this.boostKeyState.containsKey(player) && ((Boolean)this.boostKeyState.get(player)).booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isForwardKeyDown(EntityPlayer player) {
/* 36 */     return (this.forwardKeyState.containsKey(player) && ((Boolean)this.forwardKeyState.get(player)).booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isJumpKeyDown(EntityPlayer player) {
/* 41 */     return (this.jumpKeyState.containsKey(player) && ((Boolean)this.jumpKeyState.get(player)).booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModeSwitchKeyDown(EntityPlayer player) {
/* 46 */     return (this.modeSwitchKeyState.containsKey(player) && ((Boolean)this.modeSwitchKeyState.get(player)).booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSideinventoryKeyDown(EntityPlayer player) {
/* 51 */     return (this.sideinventoryKeyState.containsKey(player) && ((Boolean)this.sideinventoryKeyState.get(player)).booleanValue());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isHudModeKeyDown(EntityPlayer player) {
/* 57 */     return (this.hudModeKeyState.containsKey(player) && ((Boolean)this.hudModeKeyState.get(player)).booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSneakKeyDown(EntityPlayer player) {
/* 62 */     return player.func_70093_af();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendKeyUpdate() {}
/*    */ 
/*    */   
/*    */   public void processKeyUpdate(EntityPlayer player, int keyState) {
/* 71 */     this.altKeyState.put(player, Boolean.valueOf(((keyState & 0x1) != 0)));
/* 72 */     this.boostKeyState.put(player, Boolean.valueOf(((keyState & 0x2) != 0)));
/* 73 */     this.forwardKeyState.put(player, Boolean.valueOf(((keyState & 0x4) != 0)));
/* 74 */     this.modeSwitchKeyState.put(player, Boolean.valueOf(((keyState & 0x8) != 0)));
/* 75 */     this.jumpKeyState.put(player, Boolean.valueOf(((keyState & 0x10) != 0)));
/* 76 */     this.sideinventoryKeyState.put(player, Boolean.valueOf(((keyState & 0x20) != 0)));
/* 77 */     this.hudModeKeyState.put(player, Boolean.valueOf(((keyState & 0x40) != 0)));
/*    */   }
/*    */   
/*    */   public void init() {}
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\Keyboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */