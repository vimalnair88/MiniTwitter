package edu.sjsu.cmpe275.aop.aspect;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/*	AUTHOR : Vimal Nair
 *  Aspect for handling the retry in case of a network error, The Aspect will retry for 2 times and will
 *  exit.
 */


@Aspect
public class RetryAspect {

	private int retryCount=2;	
	private TreeMap<String,ArrayList<String>> map = new TreeMap<String,ArrayList<String>>();
	private TreeMap<String,ArrayList<String>> followMap = new TreeMap<String,ArrayList<String>>();
	/*
	 * Getters and Setters for storing the Tweet Data and Follow Data(Implemented using TreeMap)
	 */
	public  TreeMap<String,ArrayList<String>> getMap() {
		return map;
	}
	public void setMap(TreeMap<String,ArrayList<String>> map) {
		this.map = map;
	}	
	public TreeMap<String,ArrayList<String>> getFollowMap() {
		return followMap;
	}
	public void setFollowMap(TreeMap<String,ArrayList<String>> followMap) {
		this.followMap = followMap;
	}
	
	/*
	 * RESET functionality for the data Store(Tweet Data and Follow Data)
	 */
	
	public void clearMap(){
		this.map.clear();
	}
	public void clearFollowMap(){
		this.followMap.clear();
	}
	
	/*
	 *Aspect for handling the RETRY in case of IO Exception, will be retrying 2 times. 
	 */
	
	@Around("execution(* tweet*(..,..))")
	public void retryAspectService(ProceedingJoinPoint joinpoint) throws Throwable{
		boolean execute = true;
		Object[] data = joinpoint.getArgs();
		int count = 0;
		do{
			try{
				System.out.println("Starting to Execute: "+joinpoint.getSignature().getName());				
				Object obj = joinpoint.proceed();
				addTweet(data[0].toString(), data[1].toString());
				execute = false;
			}catch(Exception e){
				if(e instanceof IllegalArgumentException){
					System.out.println("Tweet Message Greater than 140, RETRY!!");
					execute = false;
				}else if(count>=retryCount){
					System.out.println("Failed after 2 retry attempts, EXIT!");
					execute = false;
				}else{
				count++;
				System.out.println("RETRY :"+count);
				}
			}			
		}while(execute);		
	}
	
	/*
	 *Aspect for handling RETRY functionality for follow method. Data will be pushed to data store in case of 
	 *failure also. RETRY will happen 2 times, other than the initial attempt. 
	 */
	
	@Around("execution(* follow*(..,..))")
	public void retryAspectFollow(ProceedingJoinPoint joinpoint) throws Throwable{
		boolean execute = true;
		Object[] followData = joinpoint.getArgs();
		if(alreadyFollowed(followData[0].toString(),followData[1].toString())){
			System.out.println("Already Followed! Wont be adding this Data to DataStore");
		}else{
			addFollow(followData[0].toString(), followData[1].toString());
			int count = 0;
			do{
				try{
					System.out.println("Starting to Execute: "+joinpoint.getSignature().getName()+"()");
					Object obj = joinpoint.proceed();
					execute = false;
				}catch(Exception e){
					if(e instanceof IllegalArgumentException){
						System.out.println("Tweet Message Greater than 140, RETRY!!");
						execute = false;
					}else if(count>=retryCount){
						System.out.println("Failed after 2 retry attempts, EXIT!");
						execute = false;
					}else{
					count++;
					System.out.println("RETRY :"+count);
					}
				}			
			}while(execute);
		}
				
	}
	
	/*
	 * Getters and Setters for the retry count. Can be configured through Beans Configuration.
	 */
	public int getRetry() {
		return retryCount;
	}
	public void setRetry(int retryCount) {
		this.retryCount = retryCount;
	}
	/*
	 * Utlity function for adding the tweet data to the Tweet Data Store.
	 */
	
	public  void addTweet(String str1,String  str2){
		if(map.containsKey(str1)){
			ArrayList al = map.get(str1);
			al.add(str2);
			map.put(str1, al);
		}else{			
			ArrayList<String> al = new ArrayList<String>();
			al.add(str2);
			map.put(str1, al);
		}
	}
	/*
	 * Utlity function to add Follow data to the Follow Data Store.
	 */
	public void addFollow(String str1,String str2){
		
		if(followMap.containsKey(str1)){
			ArrayList al = map.get(str1);
			al.add(str2);
			followMap.put(str1, al);
		}else{			
			ArrayList<String> al = new ArrayList<String>();
			al.add(str2);
			followMap.put(str1, al);
		}		
	}
	public boolean alreadyFollowed(String str1,String str2){
		boolean result = false;
		for(Map.Entry<String, ArrayList<String>> follow : followMap.entrySet() ){
			ArrayList<String> al = follow.getValue();
			for(int i=0;i<al.size();i++){
				if(al.get(i).equals(str2) && follow.getKey().equals(str1)){
					result = true;
					return result;
				}
			}
		}
		return result;
	}
}
