# ActionLib
提供HoverEvent.Action的注册

注册后可实现
- 成书中使用
- /tellraw的Json模式生成

## 🤗使用
```gradle
maven {
    name "kituinMavenReleases"
    url "https://maven.kituin.fun/releases"
}
```
```
modImplementation("io.github.kituin:ActionLib:{action_lib_version}")
```
| mc | ActionLib | loader |
| --- | --- | --- |
| 1.16.5-1.20.2 | 0.6.3-fabric | Fabric |
| 1.20.3/1.20.4 | 1.0.2-fabric | Fabric |
| 1.20.5+ | 1.1.1-fabric | Fabric |
| 1.16.5 | 0.6.4-forge | Forge |
| 1.17-1.20.2 | 0.9.4-fabric | Forge |
| 1.20.3/1.20.4 | 1.0.4-forge | Forge |
| 1.20.2 | 1.6.1-neoforge | NeoForge |
| 1.20.3/1.20.4 | 1.9.2-neoforge | NeoForge |
| 1.20.5+ | 1.10.1-neoforge | NeoForge |
### Fabric

当然你也可以添加`include`进行`jarInJar`

新建类继承`IActionRegisterApi`

```java
public class ActionLibIntegration implements IActionRegisterApi {
    @Override
    public List<HoverEvent.Action> registerHoverEventAction() {
        return List.of(SHOW_IMAGE);
    }
}
```
`SHOW_IMAGE`为你定义的`HoverEvent.Action`,模组`ChatImage`中的[HoverEvent.Action示例](https://github.com/kitUIN/ChatImage/blob/f83113414199aea2b75a8b283e87fa7cf3d53d49/src/main/java/github/kituin/chatimage/tool/ChatImageStyle.java#L19)

在`fabric.mod.json`中新增你的类
```json
"entrypoints": {
    "client": [
      "github.kituin.chatimage.client.ChatImageClient"
    ],
    "main": [
      "github.kituin.chatimage.ChatImage"
    ],
    "modmenu": [
      "github.kituin.chatimage.integration.ModmenuIntegration"
    ],
+    "actionlib": ["github.kituin.chatimage.integration.ActionLibIntegration"]
  },
```



### Forge

新建类继承`IActionRegisterApi`,并使用注解`@AlPlugin`

```java
@AlPlugin
public class ActionLibIntegration implements IActionRegisterApi {
    @Override
    public List<HoverEvent.Action> registerHoverEventAction() {
        return List.of(SHOW_IMAGE);
    }
}
```
`SHOW_IMAGE`为你定义的`HoverEvent.Action`,模组`ChatImage`中的[HoverEvent.Action示例](https://github.com/kitUIN/ChatImage/blob/f83113414199aea2b75a8b283e87fa7cf3d53d49/src/main/java/github/kituin/chatimage/tool/ChatImageStyle.java#L19)


### NeoForge

新建类继承`IActionRegisterApi`,并使用注解`@AlPlugin`

```java
@AlPlugin
public class ActionLibIntegration implements IActionRegisterApi {
    @Override
    public List<HoverEvent.Action> registerHoverEventAction() {
        return List.of(SHOW_IMAGE);
    }
}
```
`SHOW_IMAGE`为你定义的`HoverEvent.Action`,模组`ChatImage`中的[HoverEvent.Action示例](https://github.com/kitUIN/ChatImage/blob/f83113414199aea2b75a8b283e87fa7cf3d53d49/src/main/java/github/kituin/chatimage/tool/ChatImageStyle.java#L19)


