/*    */ package ic2.core.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class MathUtil
/*    */ {
/*    */   public static int[] fromIntegerToInt(Set<Integer> key) {
/* 11 */     int[] result = new int[key.size()];
/* 12 */     int slot = 0;
/* 13 */     for (Integer entry : key)
/*    */     {
/* 15 */       result[slot++] = entry.intValue();
/*    */     }
/* 17 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<Integer> fromIntToInteger(int[] array) {
/* 22 */     List<Integer> result = new ArrayList<>(array.length);
/* 23 */     for (int i = 0; i < array.length; i++)
/*    */     {
/* 25 */       result.add(Integer.valueOf(array[i]));
/*    */     }
/* 27 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int[] fromTo(int min, int max) {
/* 32 */     if (min >= max)
/*    */     {
/* 34 */       return new int[0];
/*    */     }
/* 36 */     int limit = max - min;
/* 37 */     int[] array = new int[limit];
/* 38 */     for (int i = 0; i < limit; i++)
/*    */     {
/* 40 */       array[i] = min + i;
/*    */     }
/* 42 */     return array;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\MathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */