# ActionLib
提供HoverEvent.Action的注册

注册后可实现
- 成书中使用
- /tellraw的Json模式生成

## 🤗使用

### Fabric
```
// fabric 1.16.5-1.20.2
modImplementation("io.github.kituin:ActionLib:0.6.1-fabric")

// fabric 1.20.3+
modImplementation("io.github.kituin:ActionLib:1.0.1-fabric")
```

当然你也可以添加`include`进行`jarInJar`

新建类继承`ActionRegisterApi`

```java
public class ActionLibIntegration implements ActionRegisterApi {
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
```
// forge 1.16.5-1.19.4
modImplementation("io.github.kituin:ActionLib:0.6.2-forge")

// forge 1.20-1.20.2
modImplementation("io.github.kituin:ActionLib:0.9.1-forge")

// forge 1.20.3+
modImplementation("io.github.kituin:ActionLib:1.0.1-forge")
```


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


