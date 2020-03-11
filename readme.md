<html lang="en">
<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width initial-scale=1'>
    <title>企业实战后台管理系统</title>
</head>
<body>
<h2><a name="" class="md-header-anchor"></a><span>企业实战后台管理系统</span></h2>
<h3><a name="" class="md-header-anchor"></a><span>1. 简介</span></h3>
<p><span>这是一套基于spring boot 2.16、shiro、jwt、redis、swagger2、mybatis 、thymeleaf、layui 后台管理系统， 权限控制的方式为 RBAC。代码通熟易懂 、JWT（无状态token）过期自动刷新，数据全程 ajax 获取，封装 ajax 工具类、菜单无限层级展示，解决 layui.tree 树形组件，回显问题。</span>
</p>
<h3><a name="" class="md-header-anchor"></a><span>2. 系统功能</span></h3>
<ol>
    <li><span>用户管理：用户是系统操作者，该功能主要完成系统用户配置。</span></li>
    <li><span>部门管理：配置系统组织机构（公司、部门、小组），树结构展现。</span></li>
    <li><span>菜单管理：配置系统菜单，操作权限，按钮权限标识等。</span></li>
    <li><span>角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。</span></li>
    <li><span>接口管理：根据业务代码自动生成相关的 api 接口文档。</span></li>
    <li><span>SQL 监控：对系统使用的 sql 进行监控，可快速查询运行效率。</span></li>
    <li><span>日志管理：对用户的操作进行记录。</span></li>

</ol>
<h3><a name=""
       class="md-header-anchor"></a><span>3. 具有如下特点</span></h3>
<ul>
    <li><span>灵活的权限控制，可控制到页面或按钮，满足绝大部分的权限需求</span></li>
    <li><span>当角色或者菜单权限发生变化的时候能够自动刷新用户权限无需退出登录</span></li>
    <li><span>完善的企业、部门、小组管理。</span></li>
    <li><span>支持分布式部署，jwt 无状态身份认证。</span></li>
    <li><span>友好的代码结构及注释，便于阅读及二次开发</span></li>
    <li><span>页面交互使用thymeleaf+layui ，极大的提高了开发效率。</span></li>
    <li><span>菜单支持无线层级展示、解决 layui.tree 树形组件数据回显错乱。</span></li>
    <li><span>引入swagger文档支持，方便编写API接口文档。</span></li>

</ul>
</body>
</html>