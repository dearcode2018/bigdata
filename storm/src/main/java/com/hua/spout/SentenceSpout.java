/**
  * @filename SentenceSpout.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.spout;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import com.hua.storm.CommonConfig;

 /**
 * @type SentenceSpout
 * @description 
 * @author qianye.zheng
 */
public class SentenceSpout extends BaseRichSpout implements CommonConfig
{

	private static final long serialVersionUID = -6243764608490044959L;

	private SpoutOutputCollector collector;
	
	private Integer count = 1;
	
	private String[] message = {"My nickname is xuwujing",
		    "My blog address is http://www.panchengming.com/",
		    "My interest is playing games"};
	
	/**
	 * @description 
	 * @param conf Storm配置
	 * @param context 拓扑-组件信息
	 * @param collector 收集器 - 发射元组
	 * @author qianye.zheng
	 */
	@SuppressWarnings({"rawtypes"})
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector)
	{
		System.out.println("open: " + conf.get("test"));
		this.collector = collector;
	}

	/**
	 * @description 主要执行方法
	 * 通过 collector.emit()发射
	 * @author qianye.zheng
	 */
	@Override
	public void nextTuple()
	{
		if (count <= message.length)
		{
			System.out.println("第 " + count + " 次开始发送数据...");
			collector.emit(new Values(message[count - 1]));
		}
		
		count ++;
	}

	/**
	 * @description 声明数据格式(输出字段)
	 * @param declarer
	 * @author qianye.zheng
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer)
	{
		System.out.println("SentenceSpout.声明数据格式...");
		declarer.declare(new Fields(firstFieldName));
	}
	
	/**
	 * @description 处理一个元组成功则调用此方法
	 * @param msgId
	 * @author qianye.zheng
	 */
	@Override
	public void ack(Object msgId)
	{
		System.out.println("ack: " + msgId);
		super.ack(msgId);
	}
	
	/**
	 * @description 拓扑关闭时执行 
	 * @author qianye.zheng
	 */
	@Override
	public void close()
	{
		System.out.println("close ...");
		super.close();
	}
	
	/**
	 * @description 处理一个元组失败则调用此方法
	 * @param msgId
	 * @author qianye.zheng
	 */
	@Override
	public void fail(Object msgId)
	{
		System.out.println("fail: " + msgId);
		super.fail(msgId);
	}

}
