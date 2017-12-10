package com.yhm.wst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;

import com.yhm.wst.util.LogUtil;


/**
 * @ClassName: ActivityManager
 * @Description: activity管理器
 * @author liang_xs
 */
public class ActivityManager {
	private static List<Activity> activityList = new ArrayList<Activity>();

	public static void add(Activity activity) {
		activityList.add(activity);
	}

	public static void remove(Activity activity) {
		LogUtil.e("liang", activity.getClass().getSimpleName() + "---------------remove");
		activityList.remove(activity);
	}

	public static void finishAllActivities() {
		for (Activity activity : activityList) {
			activity.finish();
		}
	}

	public static Activity getCurrActivity() {
		if (activityList.size() == 0) {
			return null;
		}
		return activityList.get(activityList.size() - 1);
	}

	private static Map<String, Activity> destoryMap = new HashMap<String, Activity>();

	/**
	 * 添加到销毁队列
	 * 
	 * @param activity
	 *            要销毁的activity
	 */

	public static void addDestoryActivity(Activity activity, String activityName) {
		destoryMap.put(activityName, activity);
	}

	/**
	 * 销毁指定Activity
	 */
	public static void destoryActivity(String activityName) {
		destoryMap.get(activityName).finish();
	}
	
	/**
	 * 销毁指定Activity
	 */
	public static void destoryAllActivities() {
		Set<String> keySet = destoryMap.keySet();
		for (String key : keySet) {
			destoryMap.get(key).finish();
		}
	}
}
