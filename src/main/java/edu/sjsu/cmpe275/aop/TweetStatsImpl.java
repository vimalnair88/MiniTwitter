package edu.sjsu.cmpe275.aop;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import edu.sjsu.cmpe275.aop.aspect.RetryAspect;
/*
 * Class implementation for retreiving the stats about the Twitter functionality
 */

public class TweetStatsImpl implements TweetStats {
	/*
	 * Data for the Stats is pulled in from the Retry Aspect Beans, which is wired below.
	 */
	
	@Autowired
	private RetryAspect retryAspect;
	public RetryAspect getRetry() {
		return retryAspect;
	}
	public void setRetry(RetryAspect retry) {
		this.retryAspect = retry;
	}
	
	/*
	 *Function for clearing the data store for tweet data and follow data.
	 */

	public void resetStats() {
		retryAspect.clearMap();
		retryAspect.clearFollowMap();
		System.out.println("RESET TWEET DATA!!");
	}
	
	/*
	 * Function for getting the length of the longest tweet from the tweets that has been tweeted so far.
	 */
	
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
	/*
	 * Function for getting the Most Active follower.
	 */

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
	
	/*
	 * Function for returning the most productive User(The user with the longest tweets so far) 
	 */
	
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
