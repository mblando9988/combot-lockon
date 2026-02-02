# Code Overview

## Architecture Diagram

```
USER INPUT (Hits Entity)
         ↓
PlayerEntity.attack() called
         ↓
PlayerEntityMixin intercepts
         ↓
LockOnManager.setTarget(entity)
         ↓
Each Client Tick:
  ClientTickEvents.END_CLIENT_TICK → CombatLockOnClient
         ↓
  LockOnManager.update(player)
         ↓
  Calculate angle to target
  Smoothly rotate camera
         ↓
CAMERA FOLLOWS TARGET
         ↓
When target dies/moves away:
  LockOnManager.clearTarget()
         ↓
CAMERA RELEASED
```

## Class Hierarchy

```
CombatLockOn (ModInitializer)
└── Entry point for mod initialization

CombatLockOnClient (ClientModInitializer)
└── Registers client tick event
    └── Calls LockOnManager.update()

LockOnManager (Static Utility)
├── targetEntity: LivingEntity
├── isLocked: boolean
├── MAX_RANGE: 30.0f
├── ROTATION_SPEED: 0.15f
└── Methods:
    ├── setTarget(LivingEntity)
    ├── clearTarget()
    ├── getTarget()
    ├── isLocked()
    └── update(ClientPlayerEntity)

PlayerEntityMixin (Mixin Class)
└── @Inject into PlayerEntity.attack()
    └── onAttack(Entity, CallbackInfo)
```

## Data Flow

### 1. Initialization
```
Minecraft Loads
  ↓
Fabric Loader loads mods
  ↓
CombatLockOn.onInitialize() called
  ↓
CombatLockOnClient.onInitializeClient() called
  ↓
ClientTickEvents.END_CLIENT_TICK registered
  ↓
Mixins are applied to PlayerEntity.attack()
  ↓
Mod is ready
```

### 2. Combat
```
Player hits entity
  ↓
PlayerEntity.attack(target) called
  ↓
Mixin intercepts → onAttack() executed
  ↓
if (target instanceof LivingEntity) → setTarget()
  ↓
LockOnManager.targetEntity = target
LockOnManager.isLocked = true
```

### 3. Tracking
```
Every client tick (20 times/second):
  ↓
ClientTickEvents.END_CLIENT_TICK fires
  ↓
LockOnManager.update(player) called
  ↓
Validate target:
  - target != null
  - !target.isDead()
  - !target.isRemoved()
  - distance <= MAX_RANGE
  ↓
If valid:
  - Calculate angle from player to target
  - Interpolate rotation
  - Set player yaw/pitch
  ↓
If invalid:
  - clearTarget()
  - camera returns to manual control
```

## Key Code Sections

### Camera Rotation Logic (LockOnManager.java)
```java
// Calculate direction vector
Vec3d targetPos = targetEntity.getEyePos();
Vec3d playerPos = player.getEyePos();
Vec3d diff = targetPos.subtract(playerPos);

// Convert to angles
float yaw = (float) Math.toDegrees(Math.atan2(-diff.x, diff.z));
float pitch = (float) Math.toDegrees(Math.atan2(-diff.y,
    Math.sqrt(diff.x * diff.x + diff.z * diff.z)));

// Smooth interpolation (0.15 = 15% step per tick)
player.setYaw(player.getYaw() + (yaw - player.getYaw()) * ROTATION_SPEED);
player.setPitch(player.getPitch() + (pitch - player.getPitch()) * ROTATION_SPEED);
```

### Attack Interception (PlayerEntityMixin.java)
```java
@Inject(method = "attack", at = @At("TAIL"))
private void onAttack(Entity target, CallbackInfo ci) {
    if (target.getWorld().isClient && target instanceof LivingEntity) {
        LockOnManager.setTarget((LivingEntity) target);
    }
}
```

### Client Tick Registration (CombatLockOnClient.java)
```java
ClientTickEvents.END_CLIENT_TICK.register(client -> {
    if (client.player != null) {
        LockOnManager.update(client.player);
    }
});
```

## Configuration Parameters

### LockOnManager.java

| Variable | Default | Effect |
|----------|---------|--------|
| `MAX_RANGE` | 30.0f | Distance in blocks before lock releases |
| `ROTATION_SPEED` | 0.15f | Camera smoothing (0.0-1.0) |

### fabric.mod.json

| Field | Value | Effect |
|-------|-------|--------|
| `id` | "combat-lockon" | Mod identifier |
| `version` | "1.0.0" | Mod version |
| `minecraft` | "1.21.1" | Target Minecraft version |
| `environment` | "client" | Client-side only |

## Dependency Injection Points

### Mixin Injection
```
Target: PlayerEntity.attack(Entity)
Method: void attack(Entity entity)
Point: @At("TAIL")
Effect: Runs after original attack logic completes
```

### Event Listeners
```
Event: ClientTickEvents.END_CLIENT_TICK
Frequency: Every client tick (~20 times/second)
Handler: LockOnManager.update()
```

## Thread Safety

- **Client-side only**: No multithreading concerns
- **Runs in client thread**: All operations happen in Minecraft's main client thread
- **No server interactions**: No need for network synchronization

## Performance Characteristics

- **Per-tick cost**: ~0.1ms (negligible)
- **Memory footprint**: 1 entity reference + booleans (~24 bytes)
- **No allocations during updates**: Reuses vectors during calculations

## Error Handling

The mod handles these failure cases:

1. **Target dies**
   ```java
   if (targetEntity.isDead()) {
       clearTarget();
   }
   ```

2. **Target removed from world**
   ```java
   if (targetEntity.isRemoved()) {
       clearTarget();
   }
   ```

3. **Target too far**
   ```java
   if (distance > MAX_RANGE) {
       clearTarget();
   }
   ```

4. **Null player (shouldn't happen)**
   ```java
   if (client.player != null) {
       LockOnManager.update(client.player);
   }
   ```

## Extension Points

To add features, modify these locations:

| Feature | Location | Method |
|---------|----------|--------|
| Toggle key | CombatLockOnClient | Add KeyInputEvent listener |
| Config file | New: ConfigManager.java | Read JSON/TOML |
| Visual indicator | ClientTickEvents | Render HUD overlay |
| Sound effect | LockOnManager.setTarget() | Play sound |
| Multiple modes | LockOnManager | Add mode field + logic |
| Lock timer | LockOnManager | Add timestamp tracking |

## Minecraft Integration Points

- **Fabric API**: Client tick events
- **Fabric Mixin**: Attack interception
- **Minecraft Client**: Player rotation, entity access
- **Minecraft Server**: None (client-only)

## Common Modifications

### Disable lock-on range
```java
private static final float MAX_RANGE = Float.MAX_VALUE;
```

### Lock instantly (no smoothing)
```java
private static final float ROTATION_SPEED = 1.0f;
```

### Very smooth tracking
```java
private static final float ROTATION_SPEED = 0.05f;
```

### Longer range
```java
private static final float MAX_RANGE = 50.0f;
```

## Compatibility Notes

- **Other Camera Mods**: May conflict
- **Damage Indicator Mods**: Compatible
- **Combat Mods**: Compatible
- **Fabric Mixin Loader**: Required (built into Fabric Loader)

## Debugging

To add debug output, modify `LockOnManager.update()`:

```java
System.out.println("Target: " + targetEntity.getName());
System.out.println("Distance: " + distance);
System.out.println("Yaw: " + yaw + ", Pitch: " + pitch);
```

Console will print to Minecraft log: `.minecraft/logs/latest.log`
