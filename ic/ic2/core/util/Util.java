/*    */ package ic2.core.util;
/*    */ 
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static int roundToNegInf(float x) {
/*  7 */     int ret = (int)x;
/*  8 */     if (ret > x)
/*    */     {
/* 10 */       ret--;
/*    */     }
/* 12 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int roundToNegInf(double x) {
/* 17 */     int ret = (int)x;
/* 18 */     if (ret > x)
/*    */     {
/* 20 */       ret--;
/*    */     }
/* 22 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int countInArray(Object[] oa, Class cls) {
/* 27 */     int ret = 0;
/* 28 */     for (Object o : oa) {
/*    */       
/* 30 */       if (cls.isAssignableFrom(o.getClass()))
/*    */       {
/* 32 */         ret++;
/*    */       }
/*    */     } 
/* 35 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean match(byte[] par1, byte[] par2) {
/* 40 */     if (par1.length != par2.length)
/*    */     {
/* 42 */       return false;
/*    */     }
/* 44 */     for (int i = 0; i < par1.length; i++) {
/*    */       
/* 46 */       if (par1[i] != par2[i])
/*    */       {
/* 48 */         return false;
/*    */       }
/*    */     } 
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */