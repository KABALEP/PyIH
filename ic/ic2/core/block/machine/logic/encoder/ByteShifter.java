/*    */ package ic2.core.block.machine.logic.encoder;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ByteShifter
/*    */ {
/* 16 */   private BigInteger dataStack = null;
/*    */ 
/*    */   
/*    */   public ByteShifter() {
/* 20 */     this.dataStack = new BigInteger("0");
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteShifter(String dataCode) {
/* 25 */     if (dataCode != null) {
/*    */       
/*    */       try
/*    */       {
/* 29 */         this.dataStack = new BigInteger(dataCode, 36);
/*    */       }
/* 31 */       catch (Exception e)
/*    */       {
/* 33 */         this.dataStack = new BigInteger("0");
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 38 */       this.dataStack = new BigInteger("0");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int readChar() {
/* 44 */     return readBigInteger(8).intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean readBoolean() {
/* 49 */     return (readBigInteger(1).intValue() != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int readInt(int bits) {
/* 54 */     return readBigInteger(bits).intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void shiftLeft(int bits) {
/* 59 */     this.dataStack = this.dataStack.shiftLeft(bits);
/*    */   }
/*    */ 
/*    */   
/*    */   public BigInteger readBigInteger(int bits) {
/* 64 */     StringBuilder bitString = new StringBuilder();
/* 65 */     for (int i = 0; i < bits; i++)
/*    */     {
/* 67 */       bitString.append('1');
/*    */     }
/* 69 */     BigInteger data = this.dataStack.and(new BigInteger(bitString.toString(), 2));
/* 70 */     this.dataStack = this.dataStack.shiftRight(bits);
/* 71 */     return data;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeCharacter(char data) {
/* 76 */     writeInteger(data, 8);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeBigInteger(BigInteger data, int bits) {
/* 81 */     this.dataStack = this.dataStack.shiftLeft(bits);
/* 82 */     this.dataStack = this.dataStack.add(data);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeInteger(int data, int bits) {
/* 87 */     this.dataStack = this.dataStack.shiftLeft(bits);
/* 88 */     this.dataStack = this.dataStack.add(BigInteger.valueOf(data));
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeBoolean(boolean data) {
/* 93 */     this.dataStack = this.dataStack.shiftLeft(1);
/* 94 */     this.dataStack = this.dataStack.add(BigInteger.valueOf(data ? 1L : 0L));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDataCode() {
/* 99 */     return this.dataStack.toString(36);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\encoder\ByteShifter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */