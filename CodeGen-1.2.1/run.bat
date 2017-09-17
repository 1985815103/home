@echo off  
echo path:%~dp0
  
::取得bat文件所在的当前目录  
set base=%~dp0

set class=%base%bin

set libs0=%base%lib\common\*
set libs1=%base%lib\dom4j\*
set libs2=%base%lib\freemarker\*
set libs3=%base%lib\log4j\*
set libs4=%base%lib\servlet\*
set libs5=%base%lib\spring\*
set libs6=%base%lib\springMVC\*

echo libs0:%libs0%
echo libs1:%libs1%
echo libs2:%libs2%
echo libs3:%libs3%
echo libs4:%libs4%
echo libs5:%libs5%
echo libs6:%libs6%


set class_path=.;%class%;%libs0%;%libs1%;%libs2%;%libs3%;%libs4%;%libs5%;%libs6%;
echo class_path:%class_path%

java -cp %class_path% code.Main
@pause  