package com.itheima.remoteservice.invokeinterface;

import com.itheima.removeservice.bean.Person;

interface InvokeInterface {

	boolean pay(in Person p, int amount);		// 服务要提供的方法事先写在接口中
	
}
