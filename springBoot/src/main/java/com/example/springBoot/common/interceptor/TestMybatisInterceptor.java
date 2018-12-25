package com.example.springBoot.common.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

@Component
@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class ,Integer.class}) })
public class TestMybatisInterceptor implements Interceptor{

	@Override
	public Object intercept(Invocation arg0) throws Throwable {
		Object result = arg0.proceed();
	    System.out.println("Invocation.proceed()");
	    return result;
	}

	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub
		
	}

}
