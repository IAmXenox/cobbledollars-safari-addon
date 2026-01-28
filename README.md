# 🦁 CobbleDollars Safari Addon

CobbleDollars Safari Addon is a lightweight addon mod that integrates **CobbleDollars** as the economy system for the **Safari Dimension** mod.

It uses the provider-based economy architecture introduced in Safari to override the default economy implementation without modifying the core mod.

When this addon is installed, Safari automatically selects CobbleDollars as the active economy provider.

---

## ✨ Features

- 💰 Uses **CobbleDollars** as the economy backend.
- 🧩 Automatically overrides the default economy provider using a priority-based system.
- 🚫 Does not require any modification to Safari source code.

---

## 📦 Requirements

- **Fabric API**
- **Safari Dimension**
- **CobbleDollars**

---

## 🚀 Installation

1. Download and install the following mods:
   - Safari Dimension
   - CobbleDollars
   - Fabric API

2. Download this addon and place the jar file into your `mods` folder.

3. Launch your server or client.

Safari will automatically detect this addon and use CobbleDollars as the active economy provider.

You can verify this in the logs at startup.

---

## 🔧 Optional: Removing the Cobblemon Economy dependency

If you only want to use **CobbleDollars** as your economy system and do not want to install **Cobblemon Economy**, you can manually remove the dependency from Safari.

### Steps

1. Open the file:
```json
safari-dimension-x.x.x.jar
```



2. Inside the jar, open the file:
```json
fabric.mod.json
```

3. Locate the `depends` section and remove the following line:


```json
"cobblemon-economy": "*"
```

Save the file.

4. Remove the mod
```
cobblemon-economy-x.x.jar
```

Safari will now run without requiring Cobblemon Economy when this addon is installed.

> ⚠️ **Note**  
> Editing jar files may be reverted when updating the mod.  
> You may need to repeat this step after updating Safari.

---

## 🧩 How It Works

Safari exposes a provider-based economy system.

This addon registers a CobbleDollars economy provider at runtime with a higher priority than the default implementation.  
The provider with the highest priority is automatically selected by Safari.

This design allows multiple economy implementations to coexist while keeping a deterministic and predictable selection mechanism.

---

## 🛠️ For Developers

Developers can easily integrate their own economy systems by creating a custom addon that implements a new economy provider.

This makes the economy layer modular, extensible, and future-proof without requiring any modification to the Safari core mod.

---

## 📜 Disclaimer

This addon is not affiliated with the Safari Dimension or CobbleDollars projects.  
All trademarks and copyrights belong to their respective owners.
