本地运行须知：
1.在terminal窗口运行命令gradle release,目的是先编译并组织好资源文件
2.打开service.MainFace类，右键点击运行MainFace.main(),ok了

注意：
ItemService中有一段获取properties文件的方法，默认情况下注释为运行在本地，如果要尝试打zip包，注释掉本地运行的方法，解注打包运行的方法

打Zip包方法

1.将ItemService中的获取文件的方法切过来
2.terminal窗口运行gradle buildZip,ok了，包打在build/distribution目录下了
