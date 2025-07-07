/*     */ package ic2.api;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Direction
/*     */ {
/*  18 */   XN,
/*     */ 
/*     */ 
/*     */   
/*  22 */   XP,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   YN,
/*     */ 
/*     */ 
/*     */   
/*  31 */   YP,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   ZN,
/*     */ 
/*     */ 
/*     */   
/*  40 */   ZP; public final int xOffset; public final int yOffset; public final int zOffset;
/*     */   
/*     */   Direction() {
/*  43 */     int side = ordinal() / 2;
/*  44 */     int sign = getSign();
/*     */     
/*  46 */     this.xOffset = (side == 0) ? sign : 0;
/*  47 */     this.yOffset = (side == 1) ? sign : 0;
/*  48 */     this.zOffset = (side == 2) ? sign : 0;
/*     */   }
/*     */   public static final Direction[] directions; public static final Set<Direction> noDirections; public static final Set<Direction> allDirections;
/*     */   public static Direction fromSideValue(int side) {
/*  52 */     return directions[(side + 2) % 6];
/*     */   }
/*     */   
/*     */   public static Direction fromForgeDirection(ForgeDirection dir) {
/*  56 */     if (dir == ForgeDirection.UNKNOWN) return null;
/*     */     
/*  58 */     return fromSideValue(dir.ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity applyToTileEntity(TileEntity te) {
/*  68 */     return applyTo(te.func_145831_w(), te.field_145851_c, te.field_145848_d, te.field_145849_e);
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
/*     */   public TileEntity applyTo(World world, int x, int y, int z) {
/*  81 */     int[] coords = { x, y, z };
/*     */     
/*  83 */     coords[ordinal() / 2] = coords[ordinal() / 2] + getSign();
/*     */     
/*  85 */     if (world != null && world.func_72899_e(coords[0], coords[1], coords[2])) {
/*     */       try {
/*  87 */         return world.func_147438_o(coords[0], coords[1], coords[2]);
/*  88 */       } catch (Exception e) {
/*  89 */         throw new RuntimeException("error getting TileEntity at dim " + world.field_73011_w.field_76574_g + " " + coords[0] + "/" + coords[1] + "/" + coords[2]);
/*     */       } 
/*     */     }
/*     */     
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Direction getInverse() {
/* 102 */     return directions[ordinal() ^ 0x1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int toSideValue() {
/* 111 */     return (ordinal() + 4) % 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getSign() {
/* 120 */     return ordinal() % 2 * 2 - 1;
/*     */   }
/*     */   
/*     */   public ForgeDirection toForgeDirection() {
/* 124 */     return ForgeDirection.getOrientation(toSideValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 131 */     directions = values();
/* 132 */     noDirections = EnumSet.noneOf(Direction.class);
/* 133 */     allDirections = EnumSet.allOf(Direction.class);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\Direction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */