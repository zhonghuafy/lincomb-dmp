A database tool to connect database(mysql or others) and generate java dao code using mybatis plus(https://mp.baomidou.com/).
Generate entities, mappers, services.

How to use<br>
1.open Generator in lincomb-dmp-generator\src\main\java.<br>
2.edit out put directories at line 29("D:\\mybatis\\generate\\").<br>
3.edit database driver class and connection infomation at line 58 to 63. Especially username, password and url.<br>
4.edit tables you want to generate at line 82. Just replace "request_label","order_sign" to all the tables need to generate.<br>
5.edit package name at line 89(pc.setParent).<br>
6.run it.<br>


******

Thanks to my friend Jun Lei. This project was created and belongs to him. I just update the version of spring boot and mybatis-plus.