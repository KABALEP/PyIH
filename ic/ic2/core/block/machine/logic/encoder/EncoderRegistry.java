/*    */ package ic2.core.block.machine.logic.encoder;
/*    */ 
/*    */ import com.google.common.base.Strings;
/*    */ import ic2.core.block.machine.logic.encoder.impl.OldNBTEncoder;
/*    */ import ic2.core.block.machine.logic.encoder.impl.TalonEncoder;
/*    */ import ic2.core.block.machine.logic.encoder.impl.V1Encoder;
/*    */ import ic2.core.util.Tuple;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EncoderRegistry
/*    */ {
/* 18 */   public static EncoderRegistry instance = new EncoderRegistry();
/*    */   
/* 20 */   protected Map<String, IEncoder> encoderMap = new LinkedHashMap<>();
/* 21 */   protected Map<String, String> idToNameMap = new LinkedHashMap<>();
/*    */   
/*    */   protected String defaultEncoder;
/*    */ 
/*    */   
/*    */   public void init() {
/* 27 */     registerEncoder("OldNBT", (IEncoder)new OldNBTEncoder());
/* 28 */     registerEncoder("Talon", (IEncoder)new TalonEncoder());
/* 29 */     registerEncoder("V1", (IEncoder)new V1Encoder());
/* 30 */     setDefaultEncoder("V1");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDefaultEncoder(String id) {
/* 35 */     this.defaultEncoder = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDefaultEncoderID() {
/* 40 */     return this.defaultEncoder;
/*    */   }
/*    */ 
/*    */   
/*    */   public IEncoder getDefaultEncoder() {
/* 45 */     return this.encoderMap.get(getDefaultEncoderID());
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerEncoder(String id, IEncoder encoder) {
/* 50 */     if (encoder == null || id == null) {
/*    */       return;
/*    */     }
/*    */     
/* 54 */     this.encoderMap.put(id, encoder);
/* 55 */     this.idToNameMap.put(id, encoder.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public IEncoder getEncoderFromID(String id) {
/* 60 */     return this.encoderMap.get(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public Tuple<String, NBTTagCompound> createDecodedMessage(String text) {
/* 65 */     if (Strings.isNullOrEmpty(text))
/*    */     {
/* 67 */       return null;
/*    */     }
/* 69 */     for (Map.Entry<String, IEncoder> entry : this.encoderMap.entrySet()) {
/*    */       
/* 71 */       IEncoder encoder = entry.getValue();
/* 72 */       NBTTagCompound nbt = encoder.createDecodedData(text);
/* 73 */       if (nbt != null)
/*    */       {
/* 75 */         return new Tuple(entry.getKey(), nbt);
/*    */       }
/*    */     } 
/* 78 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Tuple<String[], String[]> getChoiceList() {
/* 83 */     String[] key = new String[this.idToNameMap.size()];
/* 84 */     String[] value = new String[this.idToNameMap.size()];
/* 85 */     int id = 0;
/* 86 */     for (Map.Entry<String, String> entry : this.idToNameMap.entrySet()) {
/*    */       
/* 88 */       key[id] = entry.getKey();
/* 89 */       value[id] = entry.getValue();
/* 90 */       id++;
/*    */     } 
/*    */     
/* 93 */     return new Tuple(key, value);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\encoder\EncoderRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */