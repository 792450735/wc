完成的功能：
-a	计算输出代码行/空行/注释行
-l	输出行数
-c	输出字符数
-w 	输出单词数
-o	指定输出文件，如不指定，则输出到result.txt
-e	指定停用词表

使用：
在应用程序（wc.exe）所在的目录下使用cmd运行
示例：wc.exe -a -l -c -w test1.c -e stop.txt -o output.txt
