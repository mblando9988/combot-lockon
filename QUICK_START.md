# Quick Start Guide

## 30-Second Setup

```bash
cd /Users/michael/combat-lockon
./gradlew build
```

Output: `build/libs/combat-lockon-1.0.0.jar`

## Copy to Minecraft

```bash
cp build/libs/combat-lockon-1.0.0.jar ~/.minecraft/mods/
```

## In-Game Usage

1. Hit any mob or player with a weapon
2. Camera automatically locks onto the target
3. Move around - the camera follows the target
4. Lock-on breaks when target dies or moves >30 blocks away

## Configuration (Simple)

Edit `LockOnManager.java` to change:

```java
private static final float MAX_RANGE = 30.0f;        // Change to 50.0f for longer range
private static final float ROTATION_SPEED = 0.15f;   // Change to 0.25f for faster tracking
```

Then rebuild:
```bash
./gradlew build
```

## Troubleshooting

| Problem | Solution |
|---------|----------|
| "Cannot find Java 21" | Install Java 21+ from oracle.com or openjdk.net |
| Mod doesn't load | Check Fabric API is installed |
| Camera not locking | Check mixin log in Minecraft console |
| Rotating too slowly | Increase ROTATION_SPEED value |

## File Locations

- **Source Code**: `src/main/java/com/lockon/`
- **Config Files**: `src/main/resources/`
- **Built JAR**: `build/libs/`
- **Gradle Wrapper**: `gradlew` (use this, not system gradle)

## Testing Checklist

After installation:
- [ ] Launch Minecraft 1.21.1 with Fabric
- [ ] Find a mob (cow, zombie, etc.)
- [ ] Hit the mob with a sword
- [ ] Camera should lock onto the mob
- [ ] Walk around - camera follows mob
- [ ] Kill the mob - lock-on ends

## Common Edits

### Make lock-on range infinite
```java
private static final float MAX_RANGE = Float.MAX_VALUE;
```

### Make lock-on lock instantly (no smoothing)
```java
private static final float ROTATION_SPEED = 1.0f;
```

### Make lock-on very smooth but slower
```java
private static final float ROTATION_SPEED = 0.05f;
```

## Need Help?

See `README.md` for:
- Detailed installation steps
- How the mod works internally
- All configuration options
- Troubleshooting guide

See `IMPLEMENTATION_SUMMARY.md` for:
- File-by-file breakdown
- Technical architecture
- Development notes
