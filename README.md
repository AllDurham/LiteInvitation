# LiteInvitation

轻量、高自定义度的 Minecraft 邀请码系统插件。支持 1.7 - 1.20 全版本。

> 峰值不完全统计，合计在线 1000+ 玩家的服务器在使用。

## 功能

- 🎫 **邀请码系统** — 自定义邀请码长度，玩家输入邀请码完成绑定
- 🏆 **里程碑奖励** — 邀请达到指定人数自动发放奖励（玩家指令/消息/后台指令）
- 🛡️ **防小号机制** — 四重防护：IP限制 / 在线时长 / 游戏等级 / 击杀生物数量
- 💾 **双存储** — MySQL 和本地 YAML 文件自由切换
- 🔌 **PlaceholderAPI** — 支持 PAPI 占位符，可在其他插件中展示邀请数据
- 📝 **全消息自定义** — 所有插件信息均可在 message.yml 中自定义
- 🔧 **配置自动迁移** — 插件更新后无需手动修改配置，自动迭代

## 命令

`/LiteInvitation` 可缩写为 `/yq`

| 命令 | 权限 | 说明 |
|------|------|------|
| `/yq help` | 玩家 | 查看帮助 |
| `/yq look` | 玩家 | 查看自己的邀请数据 |
| `/yq accept <邀请码>` | 玩家 | 输入邀请码接受邀请 |
| `/yq reload` | 管理员 | 重载插件配置 |
| `/yq check <玩家ID>` | 管理员 | 查看指定玩家邀请数据 |
| `/yq clear <玩家ID>` | 管理员 | 清除指定玩家邀请数据 |

## 权限

| 权限 | 说明 |
|------|------|
| `LiteInvitation.Admin` | 管理员权限 |
| `LiteInvitation.Bypass` | 绕过防小号检查 |

## 配置示例

```yaml
version: 1.6
prefix: '&8[&aLiteInvitation&8] '
update: true
allow-invite-each: false
CodeLength: 8

Anti-SmallAccount:
  IP:
    Enable: true        # 单IP只能接受一次邀请
  Time:
    Enable: true
    Amount: 24           # 至少游戏24小时才能接受邀请
  Level:
    Enable: true
    Amount: 20           # 至少20级才能接受邀请

mysql:
  enable: false          # 关闭则使用YAML存储
  host: localhost
  port: 3306
  user: root
  password: 123456
  database: minecraft
  table: invitation

Invite-Command:
  - '[msg]%prefix%&e你接受了%inviter%的邀请！'
  - '[msg]%prefix%&e你已成功邀请 %inviter_frequency% 人'
  - '[command]give %player% diamond 1'
```

## 安装

1. 下载 `LiteInvitation.jar`
2. 放入服务器 `plugins/` 目录
3. 重启服务器
4. 编辑 `plugins/LiteInvitation/config.yml`
5. `/yq reload` 热重载

## 依赖

- Bukkit / Spigot / Paper 1.7 - 1.20
- Java 8+
- PlaceholderAPI（可选）

## 构建

```bash
mvn clean package
```

## 链接

- [SpigotMC](https://www.spigotmc.org/resources/liteinvitation-1-7-1-20-invitationsystem-mysql-papi-anti-alt-accounts.98271/)
- [MCBBS](https://www.mcbbs.co/thread-3198-1-1.html)
