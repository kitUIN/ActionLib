# ActionLib
提供HoverEvent.Action的注册,注册后可以在成书中使用,以及/tellraw的Json模式生成

## 🤗使用

### Fabric
```
modImplementation("io.github.kituin:ActionLib:0.6.1-fabric")
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
