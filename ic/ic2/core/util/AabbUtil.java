/*     */ package ic2.core.util;
/*     */ 
/*     */ import ic2.api.Direction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ChunkCoordinates;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.ChunkPosition;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AabbUtil
/*     */ {
/*     */   public static int getBlockCount(World world, int x, int y, int z, int radius, IBlockFilter filter, boolean randBeginning, boolean needsAdvFilter, List<ForgeDirection> validDirs) {
/*  22 */     int minX = x - radius;
/*  23 */     int minY = y - radius;
/*  24 */     int minZ = z - radius;
/*  25 */     int maxX = x + radius;
/*  26 */     int maxY = y + radius;
/*  27 */     int maxZ = z + radius;
/*     */     
/*  29 */     int count = 0;
/*  30 */     List<ChunkCoordinates> work = new ArrayList<>();
/*     */     
/*  32 */     for (ForgeDirection dir : validDirs)
/*     */     {
/*  34 */       work.add(new ChunkCoordinates(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ));
/*     */     }
/*  36 */     if (randBeginning)
/*     */     {
/*  38 */       Collections.shuffle(work);
/*     */     }
/*  40 */     Set<ChunkCoordinates> done = new HashSet<>();
/*  41 */     while (work.size() > 0) {
/*     */       
/*  43 */       ChunkCoordinates coords = work.remove(0);
/*  44 */       int posX = coords.field_71574_a;
/*  45 */       int posY = coords.field_71572_b;
/*  46 */       int posZ = coords.field_71573_c;
/*  47 */       if (done.contains(coords) || posX < minX || posX > maxX || posY < minY || posY > maxY || posZ < minZ || posZ > maxZ) {
/*     */         continue;
/*     */       }
/*     */       
/*  51 */       done.add(coords);
/*  52 */       if (needsAdvFilter) {
/*     */         
/*  54 */         if (filter.isBlockValid(world, posX, posY, posZ))
/*     */         {
/*     */           continue;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/*  61 */         Block block = world.func_147439_a(posX, posY, posZ);
/*  62 */         int meta = world.func_72805_g(posX, posY, posZ);
/*  63 */         if (!filter.isBlockValid(block, meta)) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/*  68 */       count++;
/*  69 */       for (ForgeDirection dir : validDirs)
/*     */       {
/*  71 */         work.add(new ChunkCoordinates(posX + dir.offsetX, posY + dir.offsetY, posZ + dir.offsetZ));
/*     */       }
/*     */     } 
/*  74 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<ChunkPosition> getTargets(World world, int x, int y, int z, int radius, IBlockFilter filter, ForgeDirection... directions) {
/*  79 */     List<ChunkPosition> list = new ArrayList<>();
/*  80 */     for (int xPos = -radius; xPos <= radius; xPos++) {
/*     */       
/*  82 */       for (int yPos = -radius; yPos <= radius; yPos++) {
/*     */         
/*  84 */         for (int zPos = -radius; zPos <= radius; zPos++) {
/*     */           
/*  86 */           if (world.func_72899_e(x + xPos, y + yPos, z + zPos))
/*     */           {
/*     */ 
/*     */             
/*  90 */             if (filter.isBlockValid(world.func_147439_a(x + xPos, y + yPos, z + zPos), 0))
/*     */             {
/*     */ 
/*     */               
/*  94 */               list.add(new ChunkPosition(x + xPos, y + yPos, z + zPos)); }  } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  98 */     return list;
/*     */   }
/*     */   
/*     */   public static Direction getIntersection(Vec3 origin, Vec3 direction, AxisAlignedBB bbox, Vec3 intersection) {
/*     */     Vec3 planeOrigin;
/* 103 */     double length = direction.func_72433_c();
/* 104 */     Vec3 normalizedDirection = Vec3.func_72443_a(direction.field_72450_a / length, direction.field_72448_b / length, direction.field_72449_c / length);
/* 105 */     Direction intersectingDirection = intersects(origin, normalizedDirection, bbox);
/* 106 */     if (intersectingDirection == null)
/*     */     {
/* 108 */       return null;
/*     */     }
/*     */     
/* 111 */     if (normalizedDirection.field_72450_a < 0.0D && normalizedDirection.field_72448_b < 0.0D && normalizedDirection.field_72449_c < 0.0D) {
/*     */       
/* 113 */       planeOrigin = Vec3.func_72443_a(bbox.field_72336_d, bbox.field_72337_e, bbox.field_72334_f);
/*     */     }
/* 115 */     else if (normalizedDirection.field_72450_a < 0.0D && normalizedDirection.field_72448_b < 0.0D && normalizedDirection.field_72449_c >= 0.0D) {
/*     */       
/* 117 */       planeOrigin = Vec3.func_72443_a(bbox.field_72336_d, bbox.field_72337_e, bbox.field_72339_c);
/*     */     }
/* 119 */     else if (normalizedDirection.field_72450_a < 0.0D && normalizedDirection.field_72448_b >= 0.0D && normalizedDirection.field_72449_c < 0.0D) {
/*     */       
/* 121 */       planeOrigin = Vec3.func_72443_a(bbox.field_72336_d, bbox.field_72338_b, bbox.field_72334_f);
/*     */     }
/* 123 */     else if (normalizedDirection.field_72450_a < 0.0D && normalizedDirection.field_72448_b >= 0.0D && normalizedDirection.field_72449_c >= 0.0D) {
/*     */       
/* 125 */       planeOrigin = Vec3.func_72443_a(bbox.field_72336_d, bbox.field_72338_b, bbox.field_72339_c);
/*     */     }
/* 127 */     else if (normalizedDirection.field_72450_a >= 0.0D && normalizedDirection.field_72448_b < 0.0D && normalizedDirection.field_72449_c < 0.0D) {
/*     */       
/* 129 */       planeOrigin = Vec3.func_72443_a(bbox.field_72340_a, bbox.field_72337_e, bbox.field_72334_f);
/*     */     }
/* 131 */     else if (normalizedDirection.field_72450_a >= 0.0D && normalizedDirection.field_72448_b < 0.0D && normalizedDirection.field_72449_c >= 0.0D) {
/*     */       
/* 133 */       planeOrigin = Vec3.func_72443_a(bbox.field_72340_a, bbox.field_72337_e, bbox.field_72339_c);
/*     */     }
/* 135 */     else if (normalizedDirection.field_72450_a >= 0.0D && normalizedDirection.field_72448_b >= 0.0D && normalizedDirection.field_72449_c < 0.0D) {
/*     */       
/* 137 */       planeOrigin = Vec3.func_72443_a(bbox.field_72340_a, bbox.field_72338_b, bbox.field_72334_f);
/*     */     }
/*     */     else {
/*     */       
/* 141 */       planeOrigin = Vec3.func_72443_a(bbox.field_72340_a, bbox.field_72338_b, bbox.field_72339_c);
/*     */     } 
/* 143 */     Vec3 planeNormalVector = null;
/* 144 */     switch (intersectingDirection) {
/*     */ 
/*     */       
/*     */       case AD:
/*     */       case AB:
/* 149 */         planeNormalVector = Vec3.func_72443_a(1.0D, 0.0D, 0.0D);
/*     */         break;
/*     */ 
/*     */       
/*     */       case AE:
/*     */       case DC:
/* 155 */         planeNormalVector = Vec3.func_72443_a(0.0D, 1.0D, 0.0D);
/*     */         break;
/*     */ 
/*     */       
/*     */       case DH:
/*     */       case BC:
/* 161 */         planeNormalVector = Vec3.func_72443_a(0.0D, 0.0D, 1.0D);
/*     */         break;
/*     */     } 
/*     */     
/* 165 */     Vec3 newIntersection = getIntersectionWithPlane(origin, normalizedDirection, planeOrigin, planeNormalVector);
/* 166 */     intersection.field_72450_a = newIntersection.field_72450_a;
/* 167 */     intersection.field_72448_b = newIntersection.field_72448_b;
/* 168 */     intersection.field_72449_c = newIntersection.field_72449_c;
/* 169 */     return intersectingDirection;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Direction intersects(Vec3 origin, Vec3 direction, AxisAlignedBB bbox) {
/* 174 */     double[] ray = getRay(origin, direction);
/* 175 */     if (direction.field_72450_a < 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c < 0.0D) {
/*     */       
/* 177 */       if (origin.field_72450_a < bbox.field_72340_a)
/*     */       {
/* 179 */         return null;
/*     */       }
/* 181 */       if (origin.field_72448_b < bbox.field_72338_b)
/*     */       {
/* 183 */         return null;
/*     */       }
/* 185 */       if (origin.field_72449_c < bbox.field_72339_c)
/*     */       {
/* 187 */         return null;
/*     */       }
/* 189 */       if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D)
/*     */       {
/* 191 */         return null;
/*     */       }
/* 193 */       if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D)
/*     */       {
/* 195 */         return null;
/*     */       }
/* 197 */       if (side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D)
/*     */       {
/* 199 */         return null;
/*     */       }
/* 201 */       if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D)
/*     */       {
/* 203 */         return null;
/*     */       }
/* 205 */       if (side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D)
/*     */       {
/* 207 */         return null;
/*     */       }
/* 209 */       if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D)
/*     */       {
/* 211 */         return null;
/*     */       }
/* 213 */       if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D)
/*     */       {
/* 215 */         return Direction.ZP;
/*     */       }
/* 217 */       if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D)
/*     */       {
/* 219 */         return Direction.YP;
/*     */       }
/* 221 */       return Direction.XP;
/*     */     } 
/* 223 */     if (direction.field_72450_a < 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c >= 0.0D) {
/*     */       
/* 225 */       if (origin.field_72450_a < bbox.field_72340_a)
/*     */       {
/* 227 */         return null;
/*     */       }
/* 229 */       if (origin.field_72448_b < bbox.field_72338_b)
/*     */       {
/* 231 */         return null;
/*     */       }
/* 233 */       if (origin.field_72449_c > bbox.field_72334_f)
/*     */       {
/* 235 */         return null;
/*     */       }
/* 237 */       if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D)
/*     */       {
/* 239 */         return null;
/*     */       }
/* 241 */       if (side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D)
/*     */       {
/* 243 */         return null;
/*     */       }
/* 245 */       if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D)
/*     */       {
/* 247 */         return null;
/*     */       }
/* 249 */       if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D)
/*     */       {
/* 251 */         return null;
/*     */       }
/* 253 */       if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D)
/*     */       {
/* 255 */         return null;
/*     */       }
/* 257 */       if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D)
/*     */       {
/* 259 */         return null;
/*     */       }
/* 261 */       if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D)
/*     */       {
/* 263 */         return Direction.XP;
/*     */       }
/* 265 */       if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D)
/*     */       {
/* 267 */         return Direction.YP;
/*     */       }
/* 269 */       return Direction.ZN;
/*     */     } 
/* 271 */     if (direction.field_72450_a < 0.0D && direction.field_72448_b >= 0.0D && direction.field_72449_c < 0.0D) {
/*     */       
/* 273 */       if (origin.field_72450_a < bbox.field_72340_a)
/*     */       {
/* 275 */         return null;
/*     */       }
/* 277 */       if (origin.field_72448_b > bbox.field_72337_e)
/*     */       {
/* 279 */         return null;
/*     */       }
/* 281 */       if (origin.field_72449_c < bbox.field_72339_c)
/*     */       {
/* 283 */         return null;
/*     */       }
/* 285 */       if (side(ray, getEdgeRay(Edge.FG, bbox)) > 0.0D)
/*     */       {
/* 287 */         return null;
/*     */       }
/* 289 */       if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D)
/*     */       {
/* 291 */         return null;
/*     */       }
/* 293 */       if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D)
/*     */       {
/* 295 */         return null;
/*     */       }
/* 297 */       if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D)
/*     */       {
/* 299 */         return null;
/*     */       }
/* 301 */       if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D)
/*     */       {
/* 303 */         return null;
/*     */       }
/* 305 */       if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D)
/*     */       {
/* 307 */         return null;
/*     */       }
/* 309 */       if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D)
/*     */       {
/* 311 */         return Direction.ZP;
/*     */       }
/* 313 */       if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D)
/*     */       {
/* 315 */         return Direction.XP;
/*     */       }
/* 317 */       return Direction.YN;
/*     */     } 
/* 319 */     if (direction.field_72450_a < 0.0D && direction.field_72448_b >= 0.0D && direction.field_72449_c >= 0.0D) {
/*     */       
/* 321 */       if (origin.field_72450_a < bbox.field_72340_a)
/*     */       {
/* 323 */         return null;
/*     */       }
/* 325 */       if (origin.field_72448_b > bbox.field_72337_e)
/*     */       {
/* 327 */         return null;
/*     */       }
/* 329 */       if (origin.field_72449_c > bbox.field_72334_f)
/*     */       {
/* 331 */         return null;
/*     */       }
/* 333 */       if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D)
/*     */       {
/* 335 */         return null;
/*     */       }
/* 337 */       if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D)
/*     */       {
/* 339 */         return null;
/*     */       }
/* 341 */       if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D)
/*     */       {
/* 343 */         return null;
/*     */       }
/* 345 */       if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D)
/*     */       {
/* 347 */         return null;
/*     */       }
/* 349 */       if (side(ray, getEdgeRay(Edge.CG, bbox)) < 0.0D)
/*     */       {
/* 351 */         return null;
/*     */       }
/* 353 */       if (side(ray, getEdgeRay(Edge.HG, bbox)) > 0.0D)
/*     */       {
/* 355 */         return null;
/*     */       }
/* 357 */       if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.DH, bbox)) > 0.0D)
/*     */       {
/* 359 */         return Direction.YN;
/*     */       }
/* 361 */       if (side(ray, getEdgeRay(Edge.DC, bbox)) < 0.0D)
/*     */       {
/* 363 */         return Direction.ZN;
/*     */       }
/* 365 */       return Direction.XP;
/*     */     } 
/* 367 */     if (direction.field_72450_a >= 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c < 0.0D) {
/*     */       
/* 369 */       if (origin.field_72450_a > bbox.field_72336_d)
/*     */       {
/* 371 */         return null;
/*     */       }
/* 373 */       if (origin.field_72448_b < bbox.field_72338_b)
/*     */       {
/* 375 */         return null;
/*     */       }
/* 377 */       if (origin.field_72449_c < bbox.field_72339_c)
/*     */       {
/* 379 */         return null;
/*     */       }
/* 381 */       if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D)
/*     */       {
/* 383 */         return null;
/*     */       }
/* 385 */       if (side(ray, getEdgeRay(Edge.AE, bbox)) < 0.0D)
/*     */       {
/* 387 */         return null;
/*     */       }
/* 389 */       if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D)
/*     */       {
/* 391 */         return null;
/*     */       }
/* 393 */       if (side(ray, getEdgeRay(Edge.HG, bbox)) < 0.0D)
/*     */       {
/* 395 */         return null;
/*     */       }
/* 397 */       if (side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D)
/*     */       {
/* 399 */         return null;
/*     */       }
/* 401 */       if (side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D)
/*     */       {
/* 403 */         return null;
/*     */       }
/* 405 */       if (side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D)
/*     */       {
/* 407 */         return Direction.XN;
/*     */       }
/* 409 */       if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D)
/*     */       {
/* 411 */         return Direction.ZP;
/*     */       }
/* 413 */       return Direction.YP;
/*     */     } 
/* 415 */     if (direction.field_72450_a >= 0.0D && direction.field_72448_b < 0.0D && direction.field_72449_c >= 0.0D) {
/*     */       
/* 417 */       if (origin.field_72450_a > bbox.field_72336_d)
/*     */       {
/* 419 */         return null;
/*     */       }
/* 421 */       if (origin.field_72448_b < bbox.field_72338_b)
/*     */       {
/* 423 */         return null;
/*     */       }
/* 425 */       if (origin.field_72449_c > bbox.field_72334_f)
/*     */       {
/* 427 */         return null;
/*     */       }
/* 429 */       if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D)
/*     */       {
/* 431 */         return null;
/*     */       }
/* 433 */       if (side(ray, getEdgeRay(Edge.AD, bbox)) > 0.0D)
/*     */       {
/* 435 */         return null;
/*     */       }
/* 437 */       if (side(ray, getEdgeRay(Edge.AE, bbox)) < 0.0D)
/*     */       {
/* 439 */         return null;
/*     */       }
/* 441 */       if (side(ray, getEdgeRay(Edge.EF, bbox)) < 0.0D)
/*     */       {
/* 443 */         return null;
/*     */       }
/* 445 */       if (side(ray, getEdgeRay(Edge.FG, bbox)) < 0.0D)
/*     */       {
/* 447 */         return null;
/*     */       }
/* 449 */       if (side(ray, getEdgeRay(Edge.CG, bbox)) > 0.0D)
/*     */       {
/* 451 */         return null;
/*     */       }
/* 453 */       if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.BC, bbox)) > 0.0D)
/*     */       {
/* 455 */         return Direction.ZN;
/*     */       }
/* 457 */       if (side(ray, getEdgeRay(Edge.BF, bbox)) < 0.0D)
/*     */       {
/* 459 */         return Direction.XN;
/*     */       }
/* 461 */       return Direction.YP;
/*     */     } 
/* 463 */     if (direction.field_72450_a >= 0.0D && direction.field_72448_b >= 0.0D && direction.field_72449_c < 0.0D) {
/*     */       
/* 465 */       if (origin.field_72450_a > bbox.field_72336_d)
/*     */       {
/* 467 */         return null;
/*     */       }
/* 469 */       if (origin.field_72448_b > bbox.field_72337_e)
/*     */       {
/* 471 */         return null;
/*     */       }
/* 473 */       if (origin.field_72449_c < bbox.field_72339_c)
/*     */       {
/* 475 */         return null;
/*     */       }
/* 477 */       if (side(ray, getEdgeRay(Edge.BF, bbox)) > 0.0D)
/*     */       {
/* 479 */         return null;
/*     */       }
/* 481 */       if (side(ray, getEdgeRay(Edge.AB, bbox)) > 0.0D)
/*     */       {
/* 483 */         return null;
/*     */       }
/* 485 */       if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D)
/*     */       {
/* 487 */         return null;
/*     */       }
/* 489 */       if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D)
/*     */       {
/* 491 */         return null;
/*     */       }
/* 493 */       if (side(ray, getEdgeRay(Edge.HG, bbox)) < 0.0D)
/*     */       {
/* 495 */         return null;
/*     */       }
/* 497 */       if (side(ray, getEdgeRay(Edge.FG, bbox)) > 0.0D)
/*     */       {
/* 499 */         return null;
/*     */       }
/* 501 */       if (side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D && side(ray, getEdgeRay(Edge.EF, bbox)) > 0.0D)
/*     */       {
/* 503 */         return Direction.XN;
/*     */       }
/* 505 */       if (side(ray, getEdgeRay(Edge.EH, bbox)) < 0.0D)
/*     */       {
/* 507 */         return Direction.YN;
/*     */       }
/* 509 */       return Direction.ZP;
/*     */     } 
/*     */ 
/*     */     
/* 513 */     if (origin.field_72450_a > bbox.field_72336_d)
/*     */     {
/* 515 */       return null;
/*     */     }
/* 517 */     if (origin.field_72448_b > bbox.field_72337_e)
/*     */     {
/* 519 */       return null;
/*     */     }
/* 521 */     if (origin.field_72449_c > bbox.field_72334_f)
/*     */     {
/* 523 */       return null;
/*     */     }
/* 525 */     if (side(ray, getEdgeRay(Edge.EF, bbox)) < 0.0D)
/*     */     {
/* 527 */       return null;
/*     */     }
/* 529 */     if (side(ray, getEdgeRay(Edge.EH, bbox)) > 0.0D)
/*     */     {
/* 531 */       return null;
/*     */     }
/* 533 */     if (side(ray, getEdgeRay(Edge.DH, bbox)) < 0.0D)
/*     */     {
/* 535 */       return null;
/*     */     }
/* 537 */     if (side(ray, getEdgeRay(Edge.DC, bbox)) > 0.0D)
/*     */     {
/* 539 */       return null;
/*     */     }
/* 541 */     if (side(ray, getEdgeRay(Edge.BC, bbox)) < 0.0D)
/*     */     {
/* 543 */       return null;
/*     */     }
/* 545 */     if (side(ray, getEdgeRay(Edge.BF, bbox)) > 0.0D)
/*     */     {
/* 547 */       return null;
/*     */     }
/* 549 */     if (side(ray, getEdgeRay(Edge.AB, bbox)) < 0.0D && side(ray, getEdgeRay(Edge.AE, bbox)) > 0.0D)
/*     */     {
/* 551 */       return Direction.XN;
/*     */     }
/* 553 */     if (side(ray, getEdgeRay(Edge.AD, bbox)) < 0.0D)
/*     */     {
/* 555 */       return Direction.ZN;
/*     */     }
/* 557 */     return Direction.YN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[] getRay(Vec3 origin, Vec3 direction) {
/* 563 */     double[] ret = { origin.field_72450_a * direction.field_72448_b - direction.field_72450_a * origin.field_72448_b, origin.field_72450_a * direction.field_72449_c - direction.field_72450_a * origin.field_72449_c, -direction.field_72450_a, origin.field_72448_b * direction.field_72449_c - direction.field_72448_b * origin.field_72449_c, -direction.field_72449_c, direction.field_72448_b };
/* 564 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private static double[] getEdgeRay(Edge edge, AxisAlignedBB bbox) {
/* 569 */     switch (edge) {
/*     */ 
/*     */       
/*     */       case AD:
/* 573 */         return new double[] { -bbox.field_72338_b, -bbox.field_72339_c, -1.0D, 0.0D, 0.0D, 0.0D };
/*     */ 
/*     */       
/*     */       case AB:
/* 577 */         return new double[] { bbox.field_72340_a, 0.0D, 0.0D, -bbox.field_72339_c, 0.0D, 1.0D };
/*     */ 
/*     */       
/*     */       case AE:
/* 581 */         return new double[] { 0.0D, bbox.field_72340_a, 0.0D, bbox.field_72338_b, -1.0D, 0.0D };
/*     */ 
/*     */       
/*     */       case DC:
/* 585 */         return new double[] { bbox.field_72336_d, 0.0D, 0.0D, -bbox.field_72339_c, 0.0D, 1.0D };
/*     */ 
/*     */       
/*     */       case DH:
/* 589 */         return new double[] { 0.0D, bbox.field_72336_d, 0.0D, bbox.field_72338_b, -1.0D, 0.0D };
/*     */ 
/*     */       
/*     */       case BC:
/* 593 */         return new double[] { -bbox.field_72337_e, -bbox.field_72339_c, -1.0D, 0.0D, 0.0D, 0.0D };
/*     */ 
/*     */       
/*     */       case BF:
/* 597 */         return new double[] { 0.0D, bbox.field_72340_a, 0.0D, bbox.field_72337_e, -1.0D, 0.0D };
/*     */ 
/*     */       
/*     */       case EH:
/* 601 */         return new double[] { -bbox.field_72338_b, -bbox.field_72334_f, -1.0D, 0.0D, 0.0D, 0.0D };
/*     */ 
/*     */       
/*     */       case EF:
/* 605 */         return new double[] { bbox.field_72340_a, 0.0D, 0.0D, -bbox.field_72334_f, 0.0D, 1.0D };
/*     */ 
/*     */       
/*     */       case CG:
/* 609 */         return new double[] { 0.0D, bbox.field_72336_d, 0.0D, bbox.field_72337_e, -1.0D, 0.0D };
/*     */ 
/*     */       
/*     */       case FG:
/* 613 */         return new double[] { -bbox.field_72337_e, -bbox.field_72334_f, -1.0D, 0.0D, 0.0D, 0.0D };
/*     */ 
/*     */       
/*     */       case HG:
/* 617 */         return new double[] { bbox.field_72336_d, 0.0D, 0.0D, -bbox.field_72334_f, 0.0D, 1.0D };
/*     */     } 
/*     */ 
/*     */     
/* 621 */     return new double[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double side(double[] ray1, double[] ray2) {
/* 628 */     return ray1[2] * ray2[3] + ray1[5] * ray2[1] + ray1[4] * ray2[0] + ray1[1] * ray2[5] + ray1[0] * ray2[4] + ray1[3] * ray2[2];
/*     */   }
/*     */ 
/*     */   
/*     */   private static Vec3 getIntersectionWithPlane(Vec3 origin, Vec3 direction, Vec3 planeOrigin, Vec3 planeNormalVector) {
/* 633 */     double distance = getDistanceToPlane(origin, direction, planeOrigin, planeNormalVector);
/* 634 */     return Vec3.func_72443_a(origin.field_72450_a + direction.field_72450_a * distance, origin.field_72448_b + direction.field_72448_b * distance, origin.field_72449_c + direction.field_72449_c * distance);
/*     */   }
/*     */ 
/*     */   
/*     */   private static double getDistanceToPlane(Vec3 origin, Vec3 direction, Vec3 planeOrigin, Vec3 planeNormalVector) {
/* 639 */     Vec3 base = Vec3.func_72443_a(planeOrigin.field_72450_a - origin.field_72450_a, planeOrigin.field_72448_b - origin.field_72448_b, planeOrigin.field_72449_c - origin.field_72449_c);
/* 640 */     return dotProduct(base, planeNormalVector) / dotProduct(direction, planeNormalVector);
/*     */   } public static interface IBlockFilter {
/*     */     boolean isBlockValid(Block param1Block, int param1Int);
/*     */     boolean isBlockValid(World param1World, int param1Int1, int param1Int2, int param1Int3); }
/*     */   private static double dotProduct(Vec3 a, Vec3 b) {
/* 645 */     return a.field_72450_a * b.field_72450_a + a.field_72448_b * b.field_72448_b + a.field_72449_c * b.field_72449_c;
/*     */   }
/*     */   
/*     */   enum Edge
/*     */   {
/* 650 */     AD,
/* 651 */     AB,
/* 652 */     AE,
/* 653 */     DC,
/* 654 */     DH,
/* 655 */     BC,
/* 656 */     BF,
/* 657 */     EH,
/* 658 */     EF,
/* 659 */     CG,
/* 660 */     FG,
/* 661 */     HG;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\AabbUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */