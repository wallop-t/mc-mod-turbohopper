# Fabric Mod Development — MC 26.x

## Unobfuscated Since 26.1

Minecraft 26.1+ ships with **Mojang's official names** in the JAR. No obfuscation, no Yarn mappings, no intermediary remapping.

Consequences for Mixins:
- Use Mojang class paths directly: `net.minecraft.world.level.block.entity.HopperBlockEntity`
- Do NOT set `remap = false` — it's unnecessary and can mask bugs
- Do NOT add a `mappings` line in `gradle.properties`
- Yarn is retired post-1.21.x — ignore any Yarn-era guides

## Toolchain Versions (MC 26.1.2)

```properties
minecraft_version=26.1.2
loader_version=0.19.3
loom_version=1.17-SNAPSHOT
fabric_api_version=0.152.1+26.1.2
```

- **Gradle:** 9.5.1 (wrapper)
- **Java:** 25+ (we use JDK 26 at `/Library/Java/JavaVirtualMachines/jdk-26.jdk/Contents/Home`)
- **Loom plugin ID:** `net.fabricmc.fabric-loom` (not the old `fabric-loom`)

## Dependencies

Use `implementation`, not `modImplementation` (changed in Loom 1.15+):

```groovy
dependencies {
    minecraft "com.mojang:minecraft:${project.minecraft_version}"
    implementation "net.fabricmc:fabric-loader:${project.loader_version}"
    implementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_api_version}"
}
```

## Mixin Compatibility Level

```json
{
    "compatibilityLevel": "JAVA_25"
}
```

## Mixin Pattern (no remap needed)

```java
@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityCooldownMixin {
    @ModifyArg(
        method = "tryMoveItems",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/HopperBlockEntity;setCooldown(I)V"),
        index = 0
    )
    private static int reduceCooldown(int cooldown) {
        return 1;
    }
}
```

The target string uses the **real method names** as they appear in the decompiled JAR — no mapping translation happens at runtime.

## Build & Deploy

```bash
make cbuild   # builds with Java 26, deploys to CurseForge client + mc-server/mods/
```

## Common Gotchas

- Gradle 9.4.x won't work with Loom 1.17 — needs 9.5+
- `modImplementation` causes resolution failures on Loom 1.15+ — use `implementation`
- Old mods targeting 1.21.x with `remap = false` and Yarn paths will silently fail (Mixin never finds the target)
- The `fabric.mod.json` `depends.java` should be `>=25`, not `>=21`
