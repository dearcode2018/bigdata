/**
 * 描述: 
 * StormDemoTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.storm;

// 静态导入
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.junit.Ignore;
import org.junit.Test;

import com.hua.bolt.SplitSentenceBolt;
import com.hua.bolt.WordCoutBolt;
import com.hua.spout.SentenceSpout;
import com.hua.test.BaseTest;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * StormDemoTest
 */
public final class StormDemoTest extends BaseTest {

	
	public static final String DEMO_SPOUT_ID = "sentence-spout01";
	
	public static final String DEMO_BOLT01_ID = "sentence-bolt-01";
	
	public static final String DEMO_BOLT02_ID = "sentence-bolt-02";
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testStartStormDemo() {
		try {
			// 定义拓扑
			TopologyBuilder builder = new TopologyBuilder();
			// 设置1个执行线程，默认一个
			//builder.setSpout(DEMO_SPOUT_ID, new SentenceSpout(), 1);
			// 设置2个执行线程
			builder.setSpout(DEMO_SPOUT_ID, new SentenceSpout(), 2);
			// 设置1个执行线程，2个任务，shuffle 表示随机分配 Spout的数据给Bolt
			builder.setBolt(DEMO_BOLT01_ID, new SplitSentenceBolt(), 1).setNumTasks(2).shuffleGrouping(DEMO_SPOUT_ID);
			// 数据源为 DEMO_BOLT01_ID
			builder.setBolt(DEMO_BOLT02_ID, new WordCoutBolt(), 1).setNumTasks(2).shuffleGrouping(DEMO_BOLT01_ID);
			
			Config config = new Config();
			config.put("test", "test");
			
			// 本地模式
			LocalCluster cluster = new LocalCluster();
			// 提交拓扑结构
			cluster.submitTopology("wordCount", config, builder.createTopology());
			
			Thread.sleep(20 * 1000);
			
			// 关闭本地-集群
			cluster.shutdown();
			
			/*
			 * 远程集群
			 * 在 config 对象中指定远程集群的地址端口等配置.
			 */
			//StormSubmitter.submitTopology("wordCount", config, builder.createTopology());
			Thread.sleep(20 * 1000);
		} catch (Exception e) {
			log.error("testStartStormDemo =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void test() {
		try {
			
			
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testTemp() {
		try {
			
			
		} catch (Exception e) {
			log.error("testTemp=====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testCommon() {
		try {
			
			
		} catch (Exception e) {
			log.error("testCommon =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testSimple() {
		try {
			
			
		} catch (Exception e) {
			log.error("testSimple =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testBase() {
		try {
			
			
		} catch (Exception e) {
			log.error("testBase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 解决ide静态导入消除问题 
	 * @author qye.zheng
	 * 
	 */
	@Ignore("解决ide静态导入消除问题 ")
	private void noUse() {
		String expected = null;
		String actual = null;
		Object[] expecteds = null;
		Object[] actuals = null;
		String message = null;
		
		assertEquals(expected, actual);
		assertEquals(message, expected, actual);
		assertNotEquals(expected, actual);
		assertNotEquals(message, expected, actual);
		
		assertArrayEquals(expecteds, actuals);
		assertArrayEquals(message, expecteds, actuals);
		
		assertFalse(true);
		assertTrue(true);
		assertFalse(message, true);
		assertTrue(message, true);
		
		assertSame(expecteds, actuals);
		assertNotSame(expecteds, actuals);
		assertSame(message, expecteds, actuals);
		assertNotSame(message, expecteds, actuals);
		
		assertNull(actuals);
		assertNotNull(actuals);
		assertNull(message, actuals);
		assertNotNull(message, actuals);
		
		assertThat(null, null);
		assertThat(null, null, null);
		
		fail();
		fail("Not yet implemented");
		
	}

}
