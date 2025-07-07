/*    */ package ic2.core.util;
/*    */ 
/*    */ 
/*    */ public class Tuple<K, V>
/*    */ {
/*    */   K first;
/*    */   V second;
/*    */   
/*    */   public Tuple(K in, V out) {
/* 10 */     this.first = in;
/* 11 */     this.second = out;
/*    */   }
/*    */ 
/*    */   
/*    */   public K getFirst() {
/* 16 */     return this.first;
/*    */   }
/*    */ 
/*    */   
/*    */   public V getSecond() {
/* 21 */     return this.second;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\Tuple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */