/**
  * @filename WordCoutBolt.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.bolt;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import com.hua.storm.CommonConfig;

 /**
 * @type WordCoutBolt
 * @description 
 * @author qianye.zheng
 */
public class WordCoutBolt extends BaseRichBolt implements CommonConfig
{

	private static final long serialVersionUID = 8945037408873416457L;

	private Map<String, Integer> countMap = null;
	
	private Long count = 1L;
	
	/**
	 * @description 
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
		countMap = new HashMap<String, Integer>();
	}

	/**
	 * @description 
	 * @param input
	 * @author qianye.zheng
	 */
	@Override
	public void execute(Tuple input)
	{
		String msg = input.getStringByField(secondFieldName);
		System.out.println("第 " + count + " 次统计单词出现的次数");
		if (!countMap.containsKey(msg))
		{
			countMap.put(msg, 1);
		} else
		{
			countMap.put(msg, countMap.get(msg) + 1);
		}
		
		count ++;
	}

	/**
	 * @description Bolt的末端，下游不再有Bolt，不要再发射数据流，因此无需定义
	 * @param declarer
	 * @author qianye.zheng
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer)
	{
	}
	
	/**
	 * @description 
	 * @author qianye.zheng
	 */
	@Override
	public void cleanup()
	{
		System.out.println("开始显示单词数量");
		for (Map.Entry<String, Integer> entry : countMap.entrySet())
		{
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		super.cleanup();
	}

}
