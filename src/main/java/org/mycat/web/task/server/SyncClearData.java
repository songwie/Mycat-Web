package org.mycat.web.task.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hx.rainbow.common.context.RainbowProperties;
import org.hx.rainbow.common.core.SpringApplicationContext;
import org.hx.rainbow.common.util.DateUtil;
import org.mycat.web.service.ShowService;
import org.mycat.web.task.common.ITask;

public class SyncClearData implements ITask {
	private static final String NAMESPACE_SYSSQL = "SYSSQL";
	private static final String NAMESPACE_SYSSQLHIGH = "SYSSQLHIGH";
	private static final String NAMESPACE_SYSSQLSLOW = "SYSSQLSLOW";
	private static final String NAMESPACE_SYSSQLTABLE = "SYSSQLTABLE";
	private static final String NAMESPACE_SYSSQLSUM ="SYSSQLSUM";
	@Override
	public void excute(String dbName, Date nowDate) {
		// TODO Auto-generated method stub
		System.out.println("开始清理历史监控数据");
		String day = (String)RainbowProperties.getProperties("clearday");
		int clearday=3;
		if (day!=null){
		  clearday=Integer.valueOf(day);
		}
		Map<String,Object> map=new HashMap();
		map.put("clearday", DateUtil.toDateTimeString(DateUtil.addDate(DateUtil.currentDate(), -clearday)));
		ShowService showService = (ShowService)SpringApplicationContext.getBean("showService"); 
		showService.getDao().delete(NAMESPACE_SYSSQL, "delete", map);
		map.clear();
		map.put("clearday", DateUtil.toDateTimeString(DateUtil.addDate(DateUtil.currentDate(), -clearday*2)));
		showService.getDao().delete(NAMESPACE_SYSSQLTABLE, "delete", map);
		map.clear();
		map.put("clearday", DateUtil.toDateTimeString(DateUtil.addDate(DateUtil.currentDate(), -clearday*3)));		
		showService.getDao().delete(NAMESPACE_SYSSQLSUM, "delete", map);		
		showService.getDao().delete(NAMESPACE_SYSSQLHIGH, "delete", map);
		showService.getDao().delete(NAMESPACE_SYSSQLSLOW, "delete", map);		
		
	}

}
