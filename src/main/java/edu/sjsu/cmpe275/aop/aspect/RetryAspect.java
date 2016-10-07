package edu.sjsu.cmpe275.aop.aspect;

import java.util.ArrayList;
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

	private int retryCount;
	
	private TreeMap<String,ArrayList<String>> map = new TreeMap<String,ArrayList<String>>();
	private TreeMap<String,ArrayList<String>> followMap = new TreeMap<String,ArrayList<String>>();
	
	public  TreeMap<String,ArrayList<String>> getMap() {
		return map;
	}
	public void setMap(TreeMap<String,ArrayList<String>> map) {
		this.map = map;
	}
	public void clearMap(){
		this.map.clear();
	}
	public void clearFollowMap(){
		this.followMap.clear();
	}	
	public TreeMap<String,ArrayList<String>> getFollowMap() {
		return followMap;
	}
	public void setFollowMap(TreeMap<String,ArrayList<String>> followMap) {
		this.followMap = followMap;
	}

	
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
	@Around("execution(* follow*(..,..))")
	public void retryAspectFollow(ProceedingJoinPoint joinpoint) throws Throwable{
		boolean execute = true;
		Object[] followData = joinpoint.getArgs();
		addFollow(followData[0].toString(), followData[1].toString());
		int count = 0;
		do{
			try{
				System.out.println("Starting to Execute: "+joinpoint.getSignature().getName());
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
	public int getRetry() {
		return retryCount;
	}
	public void setRetry(int retryCount) {
		this.retryCount = retryCount;
	}
	
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
}
