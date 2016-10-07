package edu.sjsu.cmpe275.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import edu.sjsu.cmpe275.aop.aspect.RetryAspect;

public class TweetStatsImpl implements TweetStats {

	@Autowired
	private RetryAspect retryAspect;
	public RetryAspect getRetry() {
		return retryAspect;
	}
	public void setRetry(RetryAspect retry) {
		this.retryAspect = retry;
	}

	public void resetStats() {
		retryAspect.clearMap();
		retryAspect.clearFollowMap();
		System.out.println("RESET TWEET DATA!!");
	}

	public int getLengthOfLongestTweet() {
		TreeMap<String, ArrayList<String>> tweets =  retryAspect.getMap();
		int sum =0;
		for(Map.Entry<String, ArrayList<String>> map : tweets.entrySet()){
			ArrayList<String> al = map.getValue();
			for(int i=0; i<al.size();i++){
				if(al.get(i).length()>sum)
					sum=al.get(i).length();
			}
		}
		return sum;

	}

	public String getMostActiveFollower() {
		TreeMap<String,ArrayList<String>> sortedMap = new TreeMap<String,ArrayList<String>>(retryAspect.getFollowMap());
		String name=null;
		int count = 0;
		for(Map.Entry<String,ArrayList<String>> data : sortedMap.entrySet()){
			if(data.getValue().size()>count){
				name = data.getKey();
				count = data.getValue().size();
			}
		}		
		return name;
	}

	public String getMostProductiveUser() {
		int max=0;
		String user=null;
		TreeMap<String, ArrayList<String>> tweets =  retryAspect.getMap();
		for(Map.Entry<String, ArrayList<String>> map : tweets.entrySet()){
			ArrayList<String> al = map.getValue();
			int sum =0;
			for(int i=0; i<al.size();i++){
					sum+=al.get(i).length();
			}
			if(sum>max){
				user=map.getKey();
				max = sum;
			}
		}
		return user;
	}

}
