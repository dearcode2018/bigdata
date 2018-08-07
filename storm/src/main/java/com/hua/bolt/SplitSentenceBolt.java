/**
  * @filename SplitSentenceBolt.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import com.hua.storm.CommonConfig;

 /**
 * @type SplitSentenceBolt
 * @description 
 * @author qianye.zheng
 */
public class SplitSentenceBolt extends BaseRichBolt implements CommonConfig
{

	private static final long serialVersionUID = -8348788701275161967L;

	private OutputCollector collector;
	
	/**
	 * @description Bolt启动前执行，提供Bolt启动环境配置的入口
	 * @param stormConf
	 * @param context
	 * @param collector
	 * @author qianye.zheng
	 */
	@SuppressWarnings({"rawtypes"})
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector)
	{
		System.out.println("open: " + stormConf.get("test"));
		this.collector = collector;
	}

	/**
	 * @description 
	 * @param input
	 * @author qianye.zheng
	 */
	@Override
	public void execute(Tuple input)
	{
		String msg = input.getStringByField(firstFieldName);
		System.out.println("开始分割单词: " + msg);
		String[] words = msg.toLowerCase().split(" ");
		for (String word : words)
		{
			// 向下一个Bolt发射数据
			collector.emit(new Values(word));
		}
	}

	/**
	 * @description 声明数据格式(输出字段)
	 * @param declarer
	 * @author qianye.zheng
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer)
	{
		System.out.println("SplitSentenceBolt.声明数据格式...");
		declarer.declare(new Fields(secondFieldName));
	}
	
	/**
	 * @description Bolt终止之前调用
	 * @author qianye.zheng
	 */
	@Override
	public void cleanup()
	{
		System.out.println("SplitSentenceBolt 释放资源");
		super.cleanup();
	}

}
