## 简介
这本来是一个仿内涵段子项目，作为自己的学习使用

但是后来想把它打造一个通用框架 + 业务型的 APP通用框架。目前大体分为三部分

SDK ：独立于业务，可作为所有APP开发的公共基础部分
framework:与业务相关的，多为可复用的公共基础组件
app appdemo:业务相关，appdemo为sdk的测试demo

## 2018/6/8 更新
由于内涵段子被禁了

所以后期该项目主要为自己的练手项目 后期主要将该项目打造成 跨平台开发 + 组件化开发的一个项目
目前已引入Cordova React Native
组件化框架也在搭建中。。。。。

## 2018/9/9 更新
已经想好后期的发展路线了，该项目主要用作实际项目开发的原型验证，也为学习新技术提供一个平台。

后期将会按照如下的方式来打造这个项目
![架构图](https://github.com/qiyei2015/EssayJoke/blob/master/Doc/image/Architecture_image.PNG)

主要分了四层，并准备使用Arouter实现组件化，使用Replugin实现插件化。

APP层打包各个APP，并依赖一个或者多个业务组件

业务层主要有以下几大板块

**UI：** 主要是各种UI方面的Demo，例如自定义View，Material Design View使用Demo,插件式换肤等

**OpenSource：** 主要是各种开源软件库的使用，包括Okhttp，Retrofit,Replugin,Glide等

**Architecture：** 架构和性能优化方面的Demo,MVP，内存泄漏，ANR等

**Media：** 音视频相关的开发的Demo，相机，音频，二维码扫描。视频录制等

**Video：** Video相关，自定义播放器等

**NDK：** Native开发部分，包括增量更新，AES加解密等

业务公共层主要有以下几部分

**Framework：** 各个业务的公共部分，包括基类Activity，基类Fragment,公共的View等

**Router：** 路由模块，为组件化实现提供基础

**React Native：** 跨平台开发React Native部分，暂时规划部分

**Flutter：** 跨平台开发Flutter部分,暂时规划

基础组件部分主要有以下几大部分

**SDK：** 公共基础SDK部分，包括接口及实现，主要有HTTP，服务管理，数据中心等组件

**Resource：** 公共资源部分，包括公共icon图片，字符串资源，动画等

以上就是对整个项目的初步规划，后期将逐步的实现


## 2018/10/29 更新
进度：

1 Mall商城APP已基本完成(参考Kotlin打造完整电商APP模块化+MVP+主流框架)，基本实现组件化，当然还有一堆bug没有解

2 对Mall APP 全部引入MVP架构及采用kotlin语言开发，MVP架构如下

![MVP](https://github.com/qiyei2015/EssayJoke/blob/master/Doc/image/MVP_image.PNG)

后期的几大目标

1 将AppDemo中所有测试用例全部按照 UI 开源 架构 音视频 NDK等划分到Android这个APP中，并且实现组件化

2 在以上的项目中优化分层，使之架构更加合理可同时出多个产品线的多个APP

3 完成及优化基础组件库的开发(待定)

4 项目进行多仓的拆分，并且公共组件上maven

目前最新的架构图如下：
![架构图2](https://github.com/qiyei2015/EssayJoke/blob/master/Doc/image/Architecture2_image.PNG)

业务层新增以下几部分

**UserManager：** Mall APP 用户管理部分，包括登录，设置等

**GoodsManager：** 商品管理模块，包括商品的列表，数量，详情，购物车添加等

**OrderManager：** 订单管理模块，包括订单展示，详情展示。收获地址管理等

**MessageManager：** 消息相关，下单，登录消息等

**PayManager：** 支付管理，主要集成支付宝(有bug)，微信(未做)，银行卡(未做)等

**Scan：** 二维码扫描模块，主要用作扫码(目前还未完善)

业务公共层新增以下部分

**Provider：** 路由模块及模块服务提供者，主要用作路由及个模块间通信说需要提供的服务

**MallResource：** Mall APP 所需要的公共资源文件，但是其他模块暂时不需要的部分

基础组件部分未做变动

## 2019/11/08 更新
进度：

工程目录重构


