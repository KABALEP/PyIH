/*     */ package ic2.core.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class FilteredList<T>
/*     */   extends ArrayList<T>
/*     */ {
/*     */   Set<T> entries;
/*     */   
/*     */   public FilteredList() {
/*  16 */     this.entries = new HashSet<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public FilteredList(int size) {
/*  21 */     super(size);
/*  22 */     this.entries = new HashSet<>(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public FilteredList(Collection<? extends T> c) {
/*  27 */     super(c);
/*  28 */     this.entries = new HashSet<>(c);
/*  29 */     super.retainAll(this.entries);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(T e) {
/*  35 */     if (this.entries.add(e))
/*     */     {
/*  37 */       return super.add(e);
/*     */     }
/*  39 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends T> c) {
/*  45 */     if (c == null)
/*     */     {
/*  47 */       return false;
/*     */     }
/*  49 */     List<T> adding = new ArrayList<>(c.size());
/*  50 */     for (T data : c) {
/*     */       
/*  52 */       if (this.entries.add(data))
/*     */       {
/*  54 */         adding.add(data);
/*     */       }
/*     */     } 
/*  57 */     return super.addAll(adding);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<T> getAdded(Collection<? extends T> c) {
/*  62 */     if (c == null)
/*     */     {
/*  64 */       return new ArrayList<>();
/*     */     }
/*  66 */     List<T> result = new ArrayList<>();
/*  67 */     for (T data : c) {
/*     */       
/*  69 */       if (this.entries.add(data))
/*     */       {
/*  71 */         result.add(data);
/*     */       }
/*     */     } 
/*  74 */     super.addAll(result);
/*  75 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T remove(int index) {
/*  81 */     T data = super.remove(index);
/*  82 */     this.entries.remove(data);
/*  83 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/*  89 */     this.entries.removeAll(c);
/*  90 */     return super.removeAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/*  96 */     this.entries.remove(o);
/*  97 */     return super.remove(o);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 103 */     this.entries.retainAll(c);
/* 104 */     return super.retainAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/* 110 */     return this.entries.contains(o);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 116 */     return this.entries.containsAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 122 */     this.entries.clear();
/* 123 */     super.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\FilteredList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */