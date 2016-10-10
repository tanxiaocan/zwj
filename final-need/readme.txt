本地运行须知：
1.在terminal窗口运行命令gradle release,目的是先编译并组织好资源文件
2.打开service.MainFace类，右键点击运行MainFace.main(),ok了

打Zip包方法

1.将ItemService中的获取文件的方法切过来
2.terminal窗口运行gradle clean buildZip,ok了，包打在build/distribution目录下了

运行
1.解压distribution目录下的word-resolver.zip到当前目录
2.双击start.bat

添加纵向解析，把标题值设置成“标题名称-1”会纵向从表格去获取这个标题对应的值