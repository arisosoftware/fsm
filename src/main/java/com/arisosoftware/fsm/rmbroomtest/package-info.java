package com.arisosoftware.fsm.rmbroomtest;

/*

这是一个人民币发钱的随机模拟游戏

故事是1个party上,有100人参加, 游戏开始每个人都有1块钱, 
每个人按顺序上台, 上台的人必须随机指定一个人接受钱.
当然,如果没有钱就不能上台.
问100,1000, 1万次后的钱财概率分布.


解题思路:

启动1千个vertil , 

信道:
    1: 收钱:	
    		收到信号后,就加1, 
    		如果之前是0元,那就订阅发钱信道.
    2: 发钱:
    		收到信号后,就减1
    		如果减完后是0, 那就取消订阅发钱信道.
	3: 统计信道:
    		收到信号后,发送消息到报告信道,汇报当前余额,和ID.
    		

系统架构

前端  SPA single page applicaiton, 采用vue.js
     图像采用chart.js or d3.js
后端  vert.x web api


需求是:
	前端有一个 start, stop 按钮
	一按start 就开始 游戏. 按 stop 停止
	还有一个住装图 bar chart  如果开始游戏,bar chart将从服务器定时抓数据过来.显示
	如果可以,加上一个datagrid是一样从服务器抓数据.


task
	1: 学习 https://github.com/caprica/spa-vertx-vue ==> 编译失败.不知为什么,npm install也无用.
		    或者 https://github.com/vertx-howtos/single-page-react-vertx-howto
		    或者 https://github.com/wowselim/vertx-spa  
		    
	1a 学习 https://www.freecodecamp.org/news/vert-x-vuejs-oauth2-in-5-steps-c04ee78475b7/ ==跑不起来.	    
	https://www.youtube.com/watch?v=jxm3dLSEqnA
		    
		    https://github.com/soasada/vertx-vuejs vertx ok, vuejs fail.
		    
		    https://github.com/Jezorko/vertx-vue-example
		    
	2: 学习 https://github.com/wowselim/gameserver  
*/
