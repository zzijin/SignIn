# 小组项目:SignIn(签到打卡)


#### 介绍
SignIn项目的Android客户端，使用JAVA编写   
  
#### 项目详情  
制作周期:2020.12.21--    
使用第三方库:BaiduMap(百度地图API)  
![警告](https://images.gitee.com/uploads/images/2021/0102/191918_8832d412_8505810.png "警告.png")时间匆忙，功能未实现完全，未经完整测试，可能存在大量BUG，请使用应用内反馈入口反馈意见  
![警告](https://images.gitee.com/uploads/images/2021/0102/191106_89e152f6_8505810.png "警告.png")使用该应用可能会遇到的BUG包括但不仅限于与服务器意外断开连接后无提示，使用某些操作后闪退等，若遭遇以上bug请重启应用   
![警告](https://images.gitee.com/uploads/images/2021/0102/191106_89e152f6_8505810.png "警告.png")已知未处理相关问题：1.相关页面列表不会主动刷新，请手动切换列表刷新 2.搜索栏搜索不存在的活动不会弹出信息框提示，长时间无信息刷新请返回主页 3.应用未实装断线重连功能，手机切换/断开网络后请重启应用   
  
#### 项目链接:
C#服务器：https://gitee.com/zzijin/SignInServer  
  
  
#### 功能进度  
  
一、用户操作：  
   1.用户登录/注册 [√]  
   2.用户修改个人信息   
   3.查看用户已加入活动或发起的活动 [√]    
   4.发布活动(可设置：活动主题，活动内容，邀请验证码，打卡位置（通过地图定位/输入地址/点击地图等确定打卡位置），打卡时间，活动时间等基本信息） [√]  
   5.修改发布的活动的信息  
   6.通过输入活动ID和邀请码加入活动 [√]  
   7.通过扫描二维码加入活动  
  
二、活动操作：  
   1.通过搜索栏查询指定ID/主题的活动  
   2.首页推荐高人气活动  
  
三、打卡操作：  
   1.点击已参加活动显示导航  
   2.在打卡点半径内完成打卡操作 [√]  
   3.打卡时间开始或结束时通知用户    

四、软件操作：  
   1.设置自动登录  
   2.设置下载更新  
   3.反馈建议  
  
  
#### 表示层设计  
  
一、主页面:包含三个子页面(Fragment):主页/我的活动列表/我的信息   
   1.首页：搜索栏（输入活动ID以转跳到活动信息页面）,推荐活动(推广不同的活动给用户)[支持但UI未实装],发起活动(在已登录状态下点击进入活动页面注册活动)  
   2.我的活动列表：包含我加入的活动/管理的活动/发起的活动三个列表,点击活动可以加入活动详情界面   
   3.我的信息：账户信息(登录用户名，ID，头像),应用相关(检查更新[支持但UI未实装]/反馈意见/本地数据管理[支持但UI未实装])   
二、其它界面:   
   1.登录界面:初始打开应用时，若已连接服务器且未登录状态下会自动跳转到登录界面，也可以通过点击我的信息页面的登录按钮登录   
   2.注册活动界面:输入活动信息：1.活动主题 2.活动签到地点(搜索栏搜索大概区域/地图定位定位自己/在地图点击获取地点) 3.活动签到开始时间/结束时间 4.活动开始时间/结束时间 4.活动签到范围[支持但UI未实装] 5.活动内容[支持但UI未实装] 6.活动封面[支持但UI未实装]  
   3.活动详情页面：显示活动各项信息,未在活动中时可以加入    
   

#### 业务层设计  
  
一、Application：存放Socket(TCP)；应用数据InfoManger；Baidu地图  
   1.Socket:(详情参照服务器文档)  
   使用Socket(TCP)连接服务器，通信消息构成(UTF编码转byte数组)：    
   START_TAG(一字节)|PACKET_SIZE(四字节)|MESSAGE_TYPE(四字节)|MAIN_DATA(不定长)|END_TAG(一字节)  
   START_TAG:消息开始标识符   
   PACKET_SIZE:消息(BasicMessage)大小  
   MESSAGE_TYPE:消息类别，用于识别信息以分别处理  
   MAIN_DATA:信息主体，JSON格式存储  
   END_TAG:信息结尾标识符  
   2.基础类:  
       ActivityInfo:存放活动各项信息  
       UserInfo/MyInfo:存放用户信息  
       Participant：活动参与人员,ActivityInfo的子类，记录参与人员的UID/签到状态/身份  
   3.InfoManger:存放活动信息表/用户信息表/我的信息  
   
   
#### 持久层设计[未实装]   
  
一、活动信息/用户信息/本地文件信息:使用安卓自带的轻量级数据库SQLite存放  
二、用户头像/活动封面/更新安装包等文件：使用FileOutputStream存储在本地(若是未接收完全的文件将该文件的BaseFile类序列化存储到本地，以便后续传输)  

#### 应用功能截图  

